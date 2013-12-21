package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.NewsletterDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.UsuarioDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.EnvioNewsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ErrorEnvioNewsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Newsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Usuario;

@Service
public class ServicioEnvioMail {
	
	private static Log log = LogFactory.getLog(ServicioEnvioMail.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired @Qualifier(value="fromAddress")
	private InternetAddress fromAddress;
	
	@Autowired
	private MimeMessage mimeMessage;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private NewsletterDao newsletterDao;
	
	
	public void enviarNewsletter(final Newsletter newsletter) {
		
		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				
				String contenido = reemplazarImagenes(newsletter.getContenido());
				
				EnvioNewsletter envio = new EnvioNewsletter();
				envio.setFechaEnvio(new Date());
				newsletter.agregarEnvio(envio);
				
				List<Usuario> usuarios = usuarioDao.getReceptoresNewsletter();
				
				
				try {
					mimeMessage.setHeader("Content-Type", "text/html");
					mimeMessage.setHeader("Content-Transfer-Encoding", "base64");
					
					mimeMessage.setSubject(newsletter.getSubject(), "UTF-8");					
					mimeMessage.setSentDate(new Date());					
					mimeMessage.setFrom(fromAddress);
				
					log.info("Enviando newsletter ID " + newsletter.getId() + " a " + usuarios.size() + " usuarios");
					long contador = 0;
					
					for (Usuario usuario : usuarios) {
						
						try {
							
							contenido = contenido + getLinksParaUsuario(usuario);
							
							mimeMessage.setText(new String(contenido.getBytes("UTF-8"), "UTF-8"), "UTF-8");
							mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(usuario.getEmail()));
							
							javaMailSender.send(mimeMessage);
							envio.agregarReceptor(usuario);
							contador++;
							
						} catch (MessagingException e) {
							log.error("Error al enviar mail a " + usuario.getEmail(), e);
							envio.agregarErrorEnvio(new ErrorEnvioNewsletter(
									"No se pudo enviar el newsletter '" + newsletter.getSubject() 
									+ "' al mail " + usuario.getEmail()));
						}
					}
					
					log.info("Newsletter enviado a " + contador + " usuarios.");
					
					
				} catch (MessagingException | UnsupportedEncodingException e) {
					log.error("Error en la configuración del envio de newsletter", e);
					envio.agregarErrorEnvio(new ErrorEnvioNewsletter(
							"No se ha podido enviar el newsletter '" + newsletter.getSubject() 
							+ "' a ningún usuario por problemas en su configuración. Consulte al administrador del sistema."));
				}
				
				newsletterDao.guardar(newsletter);
				
			}

			private String getLinksParaUsuario(Usuario usuario) {
				// TODO !!!!!!!!!!!!!!
				return "";
			}

			private String reemplazarImagenes(String contenido) {
				// TODO !!!!!!!!!!
				return contenido;
			}
		});
		
	}

}
