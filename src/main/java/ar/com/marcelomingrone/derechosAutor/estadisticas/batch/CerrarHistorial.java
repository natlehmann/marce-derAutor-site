package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import java.util.Date;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.HistorialImportacionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion;

public class CerrarHistorial implements Tasklet {
	
	private String nombreArchivo;
	
	private Date inicioEjecucion;
	
	@Autowired
	private HistorialImportacionDao historialImportacionDao;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		HistorialImportacion historial = historialImportacionDao.buscarPorNombreYFecha(
				nombreArchivo, inicioEjecucion);
		
		historial.setFin(new Date());
		historial.setDuracion(historial.getFin().getTime() - historial.getInicio().getTime());
		historial.setDuracionEstimada1024bytes(
				1024 * historial.getDuracion() / historial.getTamanioArchivo());
		
		historialImportacionDao.guardar(historial);
		
		return RepeatStatus.FINISHED;
	}
	
	@Value("#{jobParameters['nombreArchivo']}")
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	@Value("#{jobParameters['fechaEjecucion']}")
	public void setInicioEjecucion(Date inicioEjecucion) {
		this.inicioEjecucion = inicioEjecucion;
	}

}
