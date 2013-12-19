package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.com.marcelomingrone.derechosAutor.estadisticas.servicios.ServicioEnvioMail;

@Controller
@RequestMapping("/admin/newsletter")
public class NewsletterController {
	
	@Autowired
	private ServicioEnvioMail servicioEnvioMail;

	@RequestMapping("/enviar")
	public String enviar(ModelMap model) {
		
		servicioEnvioMail.enviarNewsletter();
			
		
		model.addAttribute("msg", "El email se ha enviado con éxito.");
		return "admin/newsletter_listar";
	}
}
