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

public class InicializarHistorial implements Tasklet {
	
	private long tamanioArchivo;
	
	private String nombreArchivo;
	
	private Date inicioEjecucion;
	
	@Autowired
	private HistorialImportacionDao historialImportacionDao;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		HistorialImportacion historial = new HistorialImportacion();
		historial.setNombreArchivo(nombreArchivo);
		historial.setInicio(inicioEjecucion);
		historial.setTamanioArchivo(tamanioArchivo);
		
		long duracion1024bytes = historialImportacionDao.getPromedioDuracionEstimadaPara1Kb();
		historial.setDuracionEstimada(historial.getTamanioArchivo() * duracion1024bytes / 1024);
		
		historialImportacionDao.guardar(historial);
		
		return RepeatStatus.FINISHED;
	}
	
	@Value("#{jobParameters['tamanioArchivo']}")
	public void setTamanioArchivo(final long tamanioArchivo) {
		this.tamanioArchivo = tamanioArchivo;
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
