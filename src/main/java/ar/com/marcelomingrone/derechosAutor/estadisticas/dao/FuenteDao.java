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
public class FuenteDao extends EntidadExternaDao<Fuente> {

	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	@Autowired
	private DatosCancionDao datosCancionDao;
	
	private Map<Long, Fuente> fuentes;
	
	public FuenteDao() {
		super(Fuente.class, "nombre");
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactoryExterno;
	}
	
	@Transactional(value="transactionManagerExterno")
	private void init() {
		
		fuentes = new LinkedHashMap<>();
		
		List<Fuente> enUso = datosCancionDao.getFuentes(null);
System.out.println("-------------------------------- " + enUso);
		Collections.sort(enUso, new ComparadorPorNombre());
		
		for (Fuente fuente : enUso) {
System.out.println("GUARDANDO FUENTE " + fuente);			
			this.fuentes.put(fuente.getId(), fuente);
		}
	}
	
	@Override
	@Transactional(value="transactionManagerExterno")
	public Fuente buscar(Long id) {
		
		if (this.fuentes == null) {
			this.init();
		}
		
		Fuente fuente = this.fuentes.get(id);
		if (fuente == null) {
			
			fuente = super.buscar(id);
			if (fuente != null) {
				this.fuentes.put(id, fuente);
			}
		}
		
		return fuente;
	}
	
	@Transactional(value="transactionManagerExterno")
	public List<Fuente> getFuentesEnUso() {
		
		if (this.fuentes == null) {
			this.init();
		}
		
		return new LinkedList<>(this.fuentes.values());
	}

}
