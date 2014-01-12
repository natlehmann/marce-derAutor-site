package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EnvioNewsletterPruebaRunnable extends EnvioNewsletterRunnable {
	
	private static Log log = LogFactory.getLog(EnvioNewsletterPruebaRunnable.class);
	
	private String email;
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void run() {
		
		if (this.newsletter == null || StringUtils.isEmpty(email)) {
			throw new IllegalArgumentException(
					"No se ha seteado un newsletter o una dirección de email para su envio.");
		}
		
		Map<String, String> imagenes = new LinkedHashMap<>();				
		Document document = Jsoup.parse(newsletter.getContenido());
		
		reemplazarImagenesContenido(document, imagenes);
		
		
		try {
			configurarMimeMessage();
		
			log.info("Enviando prueba de newsletter ID " + newsletter.getId() + " a " + this.email);
					
			armarMailParaUsuario(mimeMessage, document, email, null, imagenes);
			
			javaMailSender.send(mimeMessage);
			
			log.info("Newsletter de prueba enviado a " + this.email);
			
			
		} catch (Exception e) {
			log.error("Error en la configuración del envio de newsletter", e);
		}
		
	}
}
