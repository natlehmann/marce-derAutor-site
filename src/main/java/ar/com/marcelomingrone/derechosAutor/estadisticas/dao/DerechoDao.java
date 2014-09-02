package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Derecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DerechoEditable;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ReglamentoDeDistribucion;

@Repository
public class DerechoDao {
	
	@Autowired
	private DerechoEditableDao derechoEditableDao;
	
	@Autowired
	private DerechoExternoReplicaDao derechoExternoDao;
	
	@Autowired
	private ReglamentoDeDistribucionDao reglamentoDeDistribucionDao;

	@Cacheable("derechos")
	public List<Derecho> getTodosPaginadoFiltrado(int inicio, int cantidadResultados,
			String filtro) {
		
		List<Derecho> derechos = derechoEditableDao.getTodosFiltrado(filtro);
		derechos.addAll(derechoExternoDao.getTodosFiltrado(filtro));
		
		Collections.sort(derechos);
		
		int indiceFinal = inicio + cantidadResultados;
		
		if (derechos.size() < indiceFinal) {
			indiceFinal = derechos.size();
		}

		return derechos.subList(inicio, indiceFinal);
		
	}

	@Cacheable("derechos")
	public long getCantidadResultados(String filtro) {
		
		return getTodosPaginadoFiltrado(0, 100000, filtro).size();
	}

	
	public Derecho buscar(String nombre) {
		
		Derecho derecho = derechoEditableDao.buscar(nombre);
		if (derecho == null) {
			derecho = derechoExternoDao.buscar(nombre);
		}
		
		return derecho;
	}


	@CacheEvict(value="derechos", allEntries=true)
	public void guardar(DerechoEditable derecho) {
		
		Derecho existente = derechoExternoDao.buscar(derecho.getNombre());
		if (existente != null) {
			throw new IllegalArgumentException("El nombre del derecho ya existe.");
		}
		
		derechoEditableDao.guardar(derecho);
	}

	@CacheEvict(value="derechos", allEntries=true)
	public void eliminar(DerechoEditable derecho) {
		
		List<ReglamentoDeDistribucion> reglamentos = reglamentoDeDistribucionDao.buscarDerecho(derecho);
		if (!reglamentos.isEmpty()) {
			throw new IllegalArgumentException(
					"El derecho no se puede eliminar por estar vinculado a Reglamentos de Distribución. "
					+ "Elimine primero el reglamento de distribución asociado.");
		}
		
		derechoEditableDao.eliminar(derecho);		
	}

	@Cacheable("derechos")
	public List<Derecho> getTodos() {
		
		List<Derecho> derechos = derechoEditableDao.getTodos();
		derechos.addAll(derechoExternoDao.getDerechosEnUso());
		
		Collections.sort(derechos);
		
		return derechos;
	}

}
