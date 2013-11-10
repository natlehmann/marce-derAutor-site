package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import ar.com.marcelomingrone.derechosAutor.estadisticas.servicios.ServicioImportacion;

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
	public DeferredResult<String> iniciarImportacion(@RequestParam("archivo")String archivo, Model model) {
		
		DeferredResult<String> deferredResult = new DeferredResult<>();
		ejecutarImportacion(archivo, deferredResult);
		
		return deferredResult;
		
//		return "importar/exito";
		
	}

	
	private void ejecutarImportacion(String nombreArchivo,
			DeferredResult<String> deferredResult) {

		try {
			// TODO: PASAR NOMBRE DE ARCHIVO COMO PARAMETRO ????
			// RESOLVER RELACIONES ENTIDADES !!!!!!!!!!
			JobExecution execution = jobLauncher.run(importacionJob, new JobParameters());
			deferredResult.setResult("Exit Status : " + execution.getStatus());

		} catch (Exception e) {
			e.printStackTrace();
			deferredResult.setResult("error");
		}

	}
}
