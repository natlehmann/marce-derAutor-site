package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.NewsletterDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.UsuarioDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.EnvioNewsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ErrorEnvioNewsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Newsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Usuario;

@Component
public class EnvioNewsletterRunnable implements Runnable {
	
	private static Log log = LogFactory.getLog(EnvioNewsletterRunnable.class);
	
	@Value("${mail.imagenes.prefix}")
	private String IMG_PREFIX;
	
	@Value("${mail.link.desuscripcion}")
	private String LINK_DESUSCRIPCION;
	
	@Value("${base.url}")
	private String BASE_URL;
	
	@Value("${newsletter.img.folder}")
	private String NEWSLETTER_FOLDER_NAME;
	
	@Value("${newsletter.home}")
	private String NEWSLETTER_HOME;
	
	
	@Autowired
	protected JavaMailSender javaMailSender;
	
	@Autowired @Qualifier(value="fromAddress")
	private InternetAddress fromAddress;
	
	@Autowired
	protected MimeMessage mimeMessage;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private NewsletterDao newsletterDao;
	
	
	protected Newsletter newsletter;
	
	
	public void setNewsletter(Newsletter newsletter) {
		this.newsletter = newsletter;
	}
	
	
	@Override
	public void run() {
		
		if (this.newsletter == null) {
			throw new IllegalArgumentException("No se ha seteado un newsletter para su envio.");
		}
		
		Map<String, String> imagenes = new LinkedHashMap<>();				
		Document document = Jsoup.parse(newsletter.getContenido());
		
		reemplazarImagenesContenido(document, imagenes);
		
		EnvioNewsletter envio = new EnvioNewsletter();
		envio.setFechaEnvio(new Date());
		newsletter.agregarEnvio(envio);
		
		List<Usuario> usuarios = usuarioDao.getReceptoresNewsletter();
		
		
		try {
			configurarMimeMessage();
		
			log.info("Enviando newsletter ID " + newsletter.getId() + " a " + usuarios.size() + " usuarios");
			long contador = 0;
			
			for (Usuario usuario : usuarios) {
				
				try {
					
					armarMailParaUsuario(mimeMessage, document, usuario, imagenes);
					
					javaMailSender.send(mimeMessage);
					envio.agregarReceptor(usuario);
					contador++;
					
				} catch (Exception e) {
					log.error("Error al enviar mail a " + usuario.getEmail(), e);
					envio.agregarErrorEnvio(new ErrorEnvioNewsletter(
							"No se pudo enviar el newsletter al mail '" + usuario.getEmail() + "'"));
				}
			}
			
			log.info("Newsletter enviado a " + contador + " usuarios.");
			
			
		} catch (MessagingException e) {
			log.error("Error en la configuración del envio de newsletter", e);
			envio.agregarErrorEnvio(new ErrorEnvioNewsletter(
					"No se ha podido enviar el newsletter '" + newsletter.getSubject() 
					+ "' a ningún usuario por problemas en su configuración. Consulte al administrador del sistema."));
		}
		
		newsletterDao.guardar(newsletter);
		
	}


	protected void configurarMimeMessage() throws MessagingException {
		
		mimeMessage.setHeader("Content-Type", "text/html");
		mimeMessage.setHeader("Content-Transfer-Encoding", "base64");
		
		mimeMessage.setSubject(newsletter.getSubject(), "UTF-8");					
		mimeMessage.setSentDate(new Date());					
		mimeMessage.setFrom(fromAddress);
	}
	
	public MimeMessageHelper armarMailParaUsuario(MimeMessage mimeMessage,
			Document document, Usuario usuario, Map<String, String> imagenes) 
			throws MessagingException {
		
		return armarMailParaUsuario(mimeMessage, document, usuario.getEmail(), usuario.getId(), imagenes);
	}


	public MimeMessageHelper armarMailParaUsuario(MimeMessage mimeMessage,
			Document document, String emailUsuario, Long idUsuario, Map<String, String> imagenes) 
			throws MessagingException {
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);					
		
		agregarLinksUsuario(document, idUsuario);
		
		// use the true flag to indicate the text included is HTML
		helper.setText(document.toString(), true);
		
		for (String idImagen : imagenes.keySet()) {
			
			FileSystemResource res = new FileSystemResource(new File(imagenes.get(idImagen)));
			helper.addInline(idImagen, res);
			
		}

		helper.setTo(new InternetAddress(emailUsuario));
		
		return helper;
	}

	public void agregarLinksUsuario(Document document, Usuario usuario) {
		agregarLinksUsuario(document, usuario.getId());
	}

	public void agregarLinksUsuario(Document document, Long idUsuario) {
		
		// TODO: FALTA AGREGAR EL LINK PARA SABER SI VIO EL NEWSLETTER
		
		Document linkUsuario = Jsoup.parseBodyFragment(LINK_DESUSCRIPCION);
		
		// si ya existe en el documento, eliminarlo
		if (document.body().children().last().html().contains(
				BASE_URL + "/newsletter/desuscribir/")) {
			
			document.body().children().last().remove();
		}
		
		Elements links = linkUsuario.getElementsByTag("a");
		if (!links.isEmpty()) {
			links.get(0).attr("href", BASE_URL + "/newsletter/desuscribir/" + idUsuario);
		}
		
		document.body().appendChild(linkUsuario.body().childNode(0));
	}
	
	
	public void reemplazarImagenesContenido(Document document, Map<String, String> imagenes) {
		
		Elements imgs = document.getElementsByTag("img");
		
		for (Element img : imgs) {
			
			String src = img.attr("src");	
			
			String carpetaNewsletter = "/" + NEWSLETTER_FOLDER_NAME + "/";
			int indice = src.indexOf(carpetaNewsletter);
			
			if (indice < 0) {
				log.error("--------------------------------------------------------");
				log.error("ATENCION: La ruta de la imagen " + src + " es incorrecta. No se puede procesar para su envio por mail.");
				log.error("--------------------------------------------------------");
			
			} else {
				
				indice = indice + carpetaNewsletter.length();
				
				String nombreImg = IMG_PREFIX + src.substring(indice).replaceAll("/", "_");
				img.attr("src", "cid:" + nombreImg);
				
				String rutaFisica = NEWSLETTER_HOME + "/" + src.substring(indice);
				
				imagenes.put(nombreImg, rutaFisica);
			}
			
		}	    
	}
	
	public void setNewsletterHome(String newsletterHome) {
		NEWSLETTER_HOME = newsletterHome;
	}
	
	public void setNewsletterFolderName(String newsletterFolderName) {
		NEWSLETTER_FOLDER_NAME = newsletterFolderName;
	}
	
	public void setImgPrefix(String imgPrefix) {
		IMG_PREFIX = imgPrefix;
	}
	
	public void setLinkDesuscripcion(String linkDesuscripcion) {
		LINK_DESUSCRIPCION = linkDesuscripcion;
	}
	
	public void setBaseUrl(String baseUrl) {
		BASE_URL = baseUrl;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;		
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;		
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;		
	}

}
