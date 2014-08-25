package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionExternoDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasEjecutadosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Pais;

public class ProcesarRankingsMasEjecutados implements Tasklet {
	
	private static Log log = LogFactory.getLog(ProcesarRankingsMasEjecutados.class);
	
	@Autowired
	private RankingArtistasMasEjecutadosDao rankingArtistasMasEjecutadosDao;
	
	@Autowired
	private DatosCancionExternoDao datosCancionDao;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
//		// sin filtros
//		log.info("Procesando rankings sin filtros");
//		rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, null, null);
//		
//		List<Pais> paises = datosCancionDao.getPaises();
//		List<Integer> anios = datosCancionDao.getAnios();
//		
//		
//		log.info("Procesando rankings con filtro solo de pais, de pais y anio, de pais, anio y trimestre");
//		for (Pais pais : paises) {
//			// solo por pais
//			rankingArtistasMasEjecutadosDao.importarDatosCanciones(pais.getId(), null, null);
//			
//			// pais y anio
//			for (Integer anio : anios) {
//				rankingArtistasMasEjecutadosDao.importarDatosCanciones(pais.getId(), anio, null);
//				
//				// pais, anio y trimestre
//				for (int i = 1; i <=4; i++) {
//					rankingArtistasMasEjecutadosDao.importarDatosCanciones(pais.getId(), anio, i);
//				}
//			}
//			
//			// pais y trimestre
//			for (int i = 1; i <= 4; i++) {
//				rankingArtistasMasEjecutadosDao.importarDatosCanciones(pais.getId(), null, i);
//			}
//		}
//		
//		// solo por anio
//		log.info("Procesando rankings con filtro solo de anio, de anio y trimestre");
//		for (Integer anio : anios) {
//			rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, anio, null);
//			
//			// anio y trimestre
//			for (int i = 1; i <=4; i++) {
//				rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, anio, i);
//			}
//		}
//		
//		// solo por trimestre
//		log.info("Procesando rankings con filtro solo de trimestre");
//		for (int i = 1; i <=4; i++) {
//			rankingArtistasMasEjecutadosDao.importarDatosCanciones(null, null, i);
//		}
		
		return RepeatStatus.FINISHED;
		
	}

}
