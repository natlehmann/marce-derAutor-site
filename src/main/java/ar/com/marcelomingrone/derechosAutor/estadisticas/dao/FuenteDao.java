package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Fuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Fuente.ComparadorPorNombre;

@Repository
public class FuenteDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DatosCancionDao datosCancionDao;
	
	private Map<Long, Fuente> fuentes;
	
	@Transactional(value="transactionManager")
	private void init() {
		
		fuentes = new LinkedHashMap<>();
		
		List<Fuente> enUso = datosCancionDao.getFuentes(null);
		Collections.sort(enUso, new ComparadorPorNombre());
		
		for (Fuente fuente : enUso) {
			this.fuentes.put(fuente.getId(), fuente);
		}
	}
	
	@Transactional(value="transactionManager")
	public Fuente buscar(Long id) {
		
		if (this.fuentes == null) {
			this.init();
		}
		
		Fuente fuente = this.fuentes.get(id);
		return fuente;
	}
	
	@Transactional(value="transactionManager")
	public List<Fuente> getFuentesEnUso() {
		
		if (this.fuentes == null) {
			this.init();
		}
		
		return new LinkedList<>(this.fuentes.values());
	}

}
