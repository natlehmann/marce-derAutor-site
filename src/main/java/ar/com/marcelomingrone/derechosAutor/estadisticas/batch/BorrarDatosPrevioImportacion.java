package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionExternoDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasCobradosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasEjecutadosDao;

public class BorrarDatosPrevioImportacion implements Tasklet {
	
	private DatosCancionExternoDao datosCancionDao;
	
	@Autowired
	private RankingArtistasMasEjecutadosDao rankingArtistasMasEjecutadosDao;
	
	@Autowired
	private RankingArtistasMasCobradosDao rankingArtistasMasCobradosDao;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
//		datosCancionDao.borrarTodo();
//		rankingArtistasMasEjecutadosDao.borrarTodo();
//		rankingArtistasMasCobradosDao.borrarTodo();
		
		return RepeatStatus.FINISHED;
	}
	
	public void setDatosCancionDao(DatosCancionExternoDao datosCancionDao) {
		this.datosCancionDao = datosCancionDao;
	}

}
