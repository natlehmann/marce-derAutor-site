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
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.HistorialImportacionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasCobradosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion.Estado;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Pais;

public class ProcesarRankingsMasCobrados implements Tasklet {
	
	private static Log log = LogFactory.getLog(ProcesarRankingsMasCobrados.class);
	
	@Autowired
	private RankingArtistasMasCobradosDao rankingArtistasMasCobradosDao;
	
	@Autowired
	private DatosCancionDao datosCancionDao;
	
	@Autowired
	private HistorialImportacionDao historialImportacionDao;
	
	private Date inicioEjecucion;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		HistorialImportacion historial = historialImportacionDao.buscarPorFecha(inicioEjecucion);
		if (historial.getEstado().equals(Estado.EJECUTADO.toString())) {
		
			// sin filtros
			log.info("Procesando rankings sin filtros");
			rankingArtistasMasCobradosDao.importarDatosCanciones(null, null, null);
			
			List<Pais> paises = datosCancionDao.getPaises();
			List<Integer> anios = datosCancionDao.getAnios();
			
			
			log.info("Procesando rankings con filtro solo de pais, de pais y anio, de pais, anio y trimestre");
			for (Pais pais : paises) {
				// solo por pais
				rankingArtistasMasCobradosDao.importarDatosCanciones(pais.getId(), null, null);
				
				// pais y anio
				for (Integer anio : anios) {
					rankingArtistasMasCobradosDao.importarDatosCanciones(pais.getId(), anio, null);
					
					// pais, anio y trimestre
					for (int i = 1; i <=4; i++) {
						rankingArtistasMasCobradosDao.importarDatosCanciones(pais.getId(), anio, i);
					}
				}
				
				// pais y trimestre
				for (int i = 1; i <= 4; i++) {
					rankingArtistasMasCobradosDao.importarDatosCanciones(pais.getId(), null, i);
				}
			}
			
			// solo por anio
			log.info("Procesando rankings con filtro solo de anio, de anio y trimestre");
			for (Integer anio : anios) {
				rankingArtistasMasCobradosDao.importarDatosCanciones(null, anio, null);
				
				// anio y trimestre
				for (int i = 1; i <=4; i++) {
					rankingArtistasMasCobradosDao.importarDatosCanciones(null, anio, i);
				}
			}
			
			// solo por trimestre
			log.info("Procesando rankings con filtro solo de trimestre");
			for (int i = 1; i <=4; i++) {
				rankingArtistasMasCobradosDao.importarDatosCanciones(null, null, i);
			}
		}
		
		return RepeatStatus.FINISHED;
		
	}
	
	@Value("#{jobParameters['fechaEjecucion']}")
	public void setInicioEjecucion(Date inicioEjecucion) {
		this.inicioEjecucion = inicioEjecucion;
	}

}
