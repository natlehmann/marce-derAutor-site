package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.HistorialImportacionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion;

@Controller
@RequestMapping("/admin")
public class ImportarArchivo {
	
	private static Log log = LogFactory.getLog(ImportarArchivo.class);
	
	@Value("${tomcat.home}")
	private String TOMCAT_HOME;
	
	private static final String RUTA_IMPORTACION = "/importacion/";

	@Autowired
	private HistorialImportacionDao historialImportacionDao;
	
	
	
//	@Autowired
//	private ServicioImportacion servicioImportacion;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("importacionJob")
	private Job importacionJob;

	@RequestMapping("/importar")
	public String armarFormulario(ModelMap model, HttpSession session) {
		
		File file = new File(TOMCAT_HOME + RUTA_IMPORTACION);
		model.addAttribute("archivos", file.list());
		
		session.setAttribute("jobExecution", null);
		
		return "importar/form";
	}
	
	@RequestMapping("/iniciar_importacion")
	@ResponseBody
	public String iniciarImportacion(@RequestParam("archivo")String archivo, HttpSession session) {
		
		if ( !ejecutarImportacion(archivo, session) ) {
			return "Se produjo un error importando el archivo. "
					+ "Por favor consulte al administrador del sistema.";
		}
		
		return "";
	}
	
	
	@RequestMapping("/status_importacion")
	@ResponseBody
	public String statusImportacion(HttpSession session) {
		
		JobExecution execution = (JobExecution) session.getAttribute("jobExecution");
		HistorialImportacion historial = (HistorialImportacion) session.getAttribute("historial");
		
		String msg = null;
		
		if (execution != null) {
			if (execution.isRunning()) {
				msg = calcularProgreso(historial);
				
			} else {
				
				BatchStatus status = execution.getStatus();				
				
				switch(status) {
				
					case COMPLETED : 
						msg = "El proceso ha finalizado con éxito.";
						break;
					case FAILED : 
						msg = "El proceso finalizó con errores. Por favor consulte al administrador del sistema.";
						break;
					case STOPPED : 
						msg = "El proceso fue interrumpido.";
						break;
					default:
						msg = "El proceso no finalizó correctamente. Por favor consulte al administrador del sistema.";
				}
				
				historial.setFin(execution.getEndTime());
				historial.setResultado(status.toString());
				historial.setDuracion(historial.getFin().getTime() - historial.getInicio().getTime());
				historial.setDuracionEstimada1024bytes(
						1024 * historial.getDuracion() / historial.getTamanioArchivo());
				
				historialImportacionDao.guardar(historial);
				
				session.setAttribute("jobExecution", null);
				session.setAttribute("historial", null);
			}
				
		}
		
		return msg;
	}

	

	private String calcularProgreso(HistorialImportacion historial) {
		
		if (historial.getDuracionEstimada() == 0) {
			return "0";
		}
		
		Date ahora = new Date();
		long tiempoTranscurrido = ahora.getTime() - historial.getInicio().getTime();
		
		long progreso = tiempoTranscurrido * 100 / historial.getDuracionEstimada();
		
		return String.valueOf(progreso);
	}

	private boolean ejecutarImportacion(String nombreArchivo, HttpSession session) {
		
		JobExecution execution = null;

		try {
			
			JobParametersBuilder builder = new JobParametersBuilder()
				.addString("nombreArchivo", nombreArchivo)
				.addDate("fechaEjecucion", new Date());
			
			execution = jobLauncher.run(importacionJob, builder.toJobParameters());
			
			HistorialImportacion historial = crearHistorial(nombreArchivo);
			
			session.setAttribute("jobExecution", execution);
			session.setAttribute("historial", historial);

		} catch (Exception e) {
			
			log.error("Se produjo un error importando el archivo " + nombreArchivo, e);
			session.setAttribute("jobExecution", null);
			return false;
		}
		
		return true;

	}

	private HistorialImportacion crearHistorial(String nombreArchivo) {
		
		HistorialImportacion historial = new HistorialImportacion();
		historial.setNombreArchivo(nombreArchivo);
		historial.setInicio(new Date());
		
		File archivo = new File(TOMCAT_HOME + RUTA_IMPORTACION + nombreArchivo);
		historial.setTamanioArchivo(archivo.length());
		
		long duracion1024bytes = historialImportacionDao.getPromedioDuracionEstimadaPara1Kb();
		historial.setDuracionEstimada(historial.getTamanioArchivo() * duracion1024bytes / 1024);
		
		return historial;
		
	}
}
