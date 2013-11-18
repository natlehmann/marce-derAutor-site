package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasCobradosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasEjecutadosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Pais;

public class ProcesarRankings implements Tasklet {
	
	@Autowired
	private RankingArtistasMasEjecutadosDao rankingArtistasMasEjecutadosDao;
	
	@Autowired
	private RankingArtistasMasCobradosDao rankingArtistasMasCobradosDao;
	
	@Autowired
	private DatosCancionDao datosCancionDao;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		// sin filtros
		rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, null, null);
		rankingArtistasMasCobradosDao.importarDatosCanciones(null, null, null);
		
		List<Pais> paises = datosCancionDao.getPaises();
		List<Integer> anios = datosCancionDao.getAnios();
		
		
		for (Pais pais : paises) {
			// solo por pais
			rankingArtistasMasEjecutadosDao.importarDatosCanciones(pais.getId(), null, null);
			rankingArtistasMasCobradosDao.importarDatosCanciones(pais.getId(), null, null);
			
			// pais y anio
			for (Integer anio : anios) {
				rankingArtistasMasEjecutadosDao.importarDatosCanciones(pais.getId(), anio, null);
				rankingArtistasMasCobradosDao.importarDatosCanciones(pais.getId(), anio, null);
				
				// pais, anio y trimestre
				for (int i = 1; i <=4; i++) {
					rankingArtistasMasEjecutadosDao.importarDatosCanciones(pais.getId(), anio, i);
					rankingArtistasMasCobradosDao.importarDatosCanciones(pais.getId(), anio, i);
				}
			}
		}
		
		// solo por anio
		for (Integer anio : anios) {
			rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, anio, null);
			rankingArtistasMasCobradosDao.importarDatosCanciones(null, anio, null);
			
			// anio y trimestre
			for (int i = 1; i <=4; i++) {
				rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, anio, i);
				rankingArtistasMasCobradosDao.importarDatosCanciones(null, anio, i);
			}
		}
		
		// solo por trimestre
		for (int i = 1; i <=4; i++) {
			rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, null, i);
			rankingArtistasMasCobradosDao.importarDatosCanciones(null, null, i);
		}
		
		return RepeatStatus.FINISHED;
		
	}

}
