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

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.UsuarioDao;
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
	
	public void enviarNewsletter() {
		
		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				
				// TODO: FALTA ABRIR Y CERRAR UN ENVIO_NEWSLETTER CON EL RESULTADO DE ESTE PROCESO
				
				List<Usuario> usuarios = usuarioDao.getReceptoresNewsletter();
				
				for (Usuario usuario : usuarios) {
				
					try {
						mimeMessage.setHeader("Content-Type", "application/octet-stream");
						mimeMessage.setHeader("Content-Transfer-Encoding", "base64");
						
						mimeMessage.setText(new String("hola que tal".getBytes("UTF-8"), "UTF-8"), "UTF-8");
						
						mimeMessage.setSubject("Acá va el título", "UTF-8");
						
						mimeMessage.setSentDate(new Date());
						mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(usuario.getEmail()));
						mimeMessage.setFrom(fromAddress);
						
						log.debug("Enviando mail a " + usuario.getEmail());
						javaMailSender.send(mimeMessage);
						log.debug("Envio exitoso.");
						
					} catch (MessagingException | UnsupportedEncodingException e) {
						// TODO !!!!
						e.printStackTrace();
					}
				}
				
			}
		});
		
	}

}
