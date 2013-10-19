package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ArchivoImportacion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.servicios.ServicioImportacion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.validadores.ValidadorArchivoImportacion;

@Controller
@RequestMapping("/admin")
public class ImportarArchivo {
	
	private static Log log = LogFactory.getLog(ImportarArchivo.class);
	
	@Autowired
	private ValidadorArchivoImportacion validador;
	
	@Autowired
	private ServicioImportacion servicioImportacion;

	@RequestMapping("/importar")
	public String armarFormulario(@ModelAttribute("archivo") ArchivoImportacion archivo) {
		return "importar/form";
	}
	
	@RequestMapping("/upload")
	public String upload(@ModelAttribute("archivo") ArchivoImportacion archivo, 
			BindingResult bindingResult, Model model) {
		
		MultipartFile file = archivo.getFile();  
		validador.validate(archivo, bindingResult); 
		
		if (bindingResult.hasErrors()) {  
			return "importar/form";  
			
		} else {
			
			try {
				String resultado = servicioImportacion.importarDatos(file.getInputStream());
				model.addAttribute("resultado", resultado);
				
			} catch (IOException e) {
				model.addAttribute("resultado", "Se produjo un error procesando el archivo a importar.");
				log.error("Se produjo un error procesando el archivo a importar.", e);
			}
		}
		
		return "importar/exito";
		
	}
}
