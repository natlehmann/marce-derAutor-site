package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import java.util.Date;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionExternoDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.HistorialImportacionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion.Estado;

public class InicializarHistorial implements Tasklet {
	
	private Date inicioEjecucion;
	
	@Autowired
	private HistorialImportacionDao historialImportacionDao;
	
	@Autowired
	private DatosCancionExternoDao datosCancionExternoDao;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		HistorialImportacion historial = new HistorialImportacion();
		historial.setInicio(inicioEjecucion);
		
		long cantidadRegistros = datosCancionExternoDao.getCantidadSumarizacionMontos();
		historial.setCantidadRegistrosMontos(cantidadRegistros);
		
		cantidadRegistros = datosCancionExternoDao.getCantidadSumarizacionUnidades();
		historial.setCantidadRegistrosEjecuciones(cantidadRegistros);
		
		HistorialImportacion anterior = historialImportacionDao.buscarHistorialPrevio(inicioEjecucion);
		
		if ( anterior != null 
				&& anterior.getCantidadRegistrosEjecuciones().equals(historial.getCantidadRegistrosEjecuciones()) 
				&& anterior.getCantidadRegistrosMontos().equals(historial.getCantidadRegistrosMontos())) {
			
			historial.setEstado(Estado.NO_EJECUTADO.toString());
			
		} else {
			historial.setEstado(Estado.EJECUTADO.toString());
		}
		
		historialImportacionDao.guardar(historial);
		
		return RepeatStatus.FINISHED;
	}
	
	@Value("#{jobParameters['fechaEjecucion']}")
	public void setInicioEjecucion(Date inicioEjecucion) {
		this.inicioEjecucion = inicioEjecucion;
	}
	

}
