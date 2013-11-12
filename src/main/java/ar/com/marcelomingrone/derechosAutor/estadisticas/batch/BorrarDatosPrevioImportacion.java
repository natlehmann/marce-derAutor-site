package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;

public class BorrarDatosPrevioImportacion implements Tasklet {
	
	private DatosCancionDao datosCancionDao;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		datosCancionDao.borrarTodo();
		return RepeatStatus.FINISHED;
	}
	
	public void setDatosCancionDao(DatosCancionDao datosCancionDao) {
		this.datosCancionDao = datosCancionDao;
	}

}
