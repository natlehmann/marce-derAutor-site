package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasEjecutadosDao;

public class ProcesarRankings implements Tasklet {
	
	@Autowired
	private RankingArtistasMasEjecutadosDao rankingArtistasMasEjecutadosDao;
	
	@Autowired
	private DatosCancionDao datosCancionDao;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		// sin filtros
		rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, null, null);
		
		List<Long> idsPaises = datosCancionDao.getIdsPaises();
		List<Integer> anios = datosCancionDao.getAnios();
		
		
		for (Long id : idsPaises) {
			// solo por pais
			rankingArtistasMasEjecutadosDao.importarDatosCanciones(id, null, null);
			
			// pais y anio
			for (Integer anio : anios) {
				rankingArtistasMasEjecutadosDao.importarDatosCanciones(id, anio, null);
				
				// pais, anio y trimestre
				for (int i = 1; i <=4; i++) {
					rankingArtistasMasEjecutadosDao.importarDatosCanciones(id, anio, i);
				}
			}
		}
		
		// solo por anio
		for (Integer anio : anios) {
			rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, anio, null);
			
			// anio y trimestre
			for (int i = 1; i <=4; i++) {
				rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, anio, i);
			}
		}
		
		// solo por trimestre
		for (int i = 1; i <=4; i++) {
			rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, null, i);
		}
		
		return RepeatStatus.FINISHED;
		
	}

}
