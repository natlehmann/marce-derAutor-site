package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Newsletter;

@Service
public class ServicioEnvioMail {
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private EnvioNewsletterRunnable envioNewsletterRunnable;
	
	
	public void enviarNewsletter(Newsletter newsletter) {
		
		envioNewsletterRunnable.setNewsletter(newsletter);
		taskExecutor.execute(envioNewsletterRunnable);
		
	}

}
