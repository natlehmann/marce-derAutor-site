package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import java.util.Date;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.HistorialImportacionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasCobradosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasEjecutadosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion.Estado;

public class BorrarDatosPrevioImportacion implements Tasklet {
	
	@Autowired
	private DatosCancionDao datosCancionDao;
	
	@Autowired
	private RankingArtistasMasEjecutadosDao rankingArtistasMasEjecutadosDao;
	
	@Autowired
	private RankingArtistasMasCobradosDao rankingArtistasMasCobradosDao;
	
	@Autowired
	private HistorialImportacionDao historialImportacionDao;
	
	private Date inicioEjecucion;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		HistorialImportacion historial = historialImportacionDao.buscarPorFecha(inicioEjecucion);
		if (historial.getEstado().equals(Estado.EJECUTADO.toString())) {
		
			datosCancionDao.borrarTodo();
			rankingArtistasMasEjecutadosDao.borrarTodo();
			rankingArtistasMasCobradosDao.borrarTodo();
		}
		
		return RepeatStatus.FINISHED;
	}
	
	public void setDatosCancionDao(DatosCancionDao datosCancionDao) {
		this.datosCancionDao = datosCancionDao;
	}
	
	@Value("#{jobParameters['fechaEjecucion']}")
	public void setInicioEjecucion(Date inicioEjecucion) {
		this.inicioEjecucion = inicioEjecucion;
	}

}
