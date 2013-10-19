package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ArchivoImportacion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.validadores.ValidadorArchivoImportacion;

@Controller
@RequestMapping("/admin")
public class ImportarArchivo {
	
	@Autowired
	private ValidadorArchivoImportacion validador;

	@RequestMapping("/importar")
	public String armarFormulario(@ModelAttribute("archivo") ArchivoImportacion archivo) {
		return "importar/form";
	}
	
	@RequestMapping("/upload")
	public String upload(@ModelAttribute("archivo") ArchivoImportacion archivo, 
			BindingResult bindingResult) {
		
		MultipartFile file = archivo.getFile();  
		validador.validate(archivo, bindingResult); 
		
		if (bindingResult.hasErrors()) {  
			return "importar/form";  
			
		} else {
			
			System.out.println("algo");
		}
		
		return "importar/exito";
		
	}
}
