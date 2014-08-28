package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionExternoDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.HistorialImportacionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DatosCancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion.Estado;

public class ProcesarSumarizacionMontos implements Tasklet {
	
	private static Log log = LogFactory.getLog(ProcesarSumarizacionMontos.class);
	
	private static final int REGISTROS_POR_PAGINA = 5000;
	
	@Autowired
	private DatosCancionExternoDao datosCancionExternoDao;
	
	@Autowired
	private DatosCancionDao datosCancionLocalDao;
	
	@Autowired
	private HistorialImportacionDao historialImportacionDao;
	
	private Date inicioEjecucion;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		HistorialImportacion historial = historialImportacionDao.buscarPorFecha(inicioEjecucion);
		if (historial.getEstado().equals(Estado.EJECUTADO.toString())) {
		
			long cantidadRegistros = historial.getCantidadRegistrosMontos();
			log.info("Procesando " + cantidadRegistros + " registros de sumarizacion de montos ...................");
			
			int registroInicial = 0;
			
			while (registroInicial < cantidadRegistros) {
				
				log.info("Importando registros " + (registroInicial + 1) + " a " + (registroInicial + REGISTROS_POR_PAGINA));
				
				List<DatosCancion> canciones = datosCancionExternoDao.getSumarizacionMontos(
						registroInicial, REGISTROS_POR_PAGINA);
				
				datosCancionLocalDao.guardar(canciones);
				
				registroInicial += REGISTROS_POR_PAGINA;
			}
			
			log.info("Fin de importacion de sumarizacion de montos");
		}
		
		return RepeatStatus.FINISHED;
	}
	
	
	@Value("#{jobParameters['fechaEjecucion']}")
	public void setInicioEjecucion(Date inicioEjecucion) {
		this.inicioEjecucion = inicioEjecucion;
	}

}
