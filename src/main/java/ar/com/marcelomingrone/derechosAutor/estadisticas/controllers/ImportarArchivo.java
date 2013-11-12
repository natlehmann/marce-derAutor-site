package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class ImportarArchivo {
	
	private static final String RUTA_IMPORTACION = "../importacion/";

	private static Log log = LogFactory.getLog(ImportarArchivo.class);
	
	
//	@Autowired
//	private ServicioImportacion servicioImportacion;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("importacionJob")
	private Job importacionJob;

	@RequestMapping("/importar")
	public String armarFormulario(ModelMap model) {
		
		File file = new File(RUTA_IMPORTACION);
		model.addAttribute("archivos", file.list());
		
		return "importar/form";
	}
	
	@RequestMapping("/iniciar_importacion")
	@ResponseBody
	public String iniciarImportacion(@RequestParam("archivo")String archivo, Model model) {
		
		return ejecutarImportacion(archivo);
	}

	
	private String ejecutarImportacion(String nombreArchivo) {

		String resultado = "error";
		
		try {
			// COMO PRIMER PASO BORRAR TODO -> VER TASKLET QUE BORRABA DIRECTORIO
			// PROGRESS BAR ???
			
			JobParametersBuilder builder = new JobParametersBuilder()
				.addString("nombreArchivo", nombreArchivo);
			
			JobExecution execution = jobLauncher.run(importacionJob, builder.toJobParameters());
			resultado = "Exit: " + execution.getStatus();

		} catch (Exception e) {
			log.error("Se produjo un error importando el archivo " + nombreArchivo, e);
		}
		
		return resultado;

	}
}
