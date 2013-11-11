package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.AutorDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.CancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FuenteDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.PaisDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Autor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Cancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DatosCancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Fuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Pais;

public class DatosCancionProcessor implements ItemProcessor<DatosCancion, DatosCancion> {
	
	@Autowired
	private AutorDao autorDao;
	
	@Autowired
	private PaisDao paisDao;
	
	@Autowired
	private CancionDao cancionDao;
	
	@Autowired
	private FuenteDao fuenteDao;

	@Override
	public DatosCancion process(DatosCancion datos) throws Exception {
		
		Autor autor = autorDao.buscar(datos.getAutor().getId());
		if (autor == null) {
			autor = autorDao.guardar(datos.getAutor());
		}		
		datos.setAutor(autor);
		
		Pais pais = paisDao.buscar(datos.getPais().getId());
		if (pais == null) {
			pais = paisDao.guardar(datos.getPais());
		}		
		datos.setPais(pais);
		
		Cancion cancion = cancionDao.buscar(datos.getCancion().getId());
		if (cancion == null) {
			cancion = cancionDao.guardar(datos.getCancion());
		}
		datos.setCancion(cancion);
		
		Fuente fuente = fuenteDao.buscar(datos.getFuente().getId());
		if (fuente == null) {
			fuente = fuenteDao.guardar(datos.getFuente());
		}
		datos.setFuente(fuente);
		
		return datos;
	}

}
