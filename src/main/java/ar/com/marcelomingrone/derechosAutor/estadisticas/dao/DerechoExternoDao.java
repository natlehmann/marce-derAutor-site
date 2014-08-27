package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.DerechoExterno;

@Repository
public class DerechoExternoDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private List<DerechoExterno> derechos;
	
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	private void init() {
		
		Session session = sessionFactory.getCurrentSession();
		this.derechos = session.createQuery(
				"SELECT DISTINCT new " + DerechoExterno.class.getName() + "(dc.nombreDerechoExterno) "
				+ "from DatosCancion dc ORDER BY dc.nombreDerechoExterno").list();
	}
	
	@Transactional(value="transactionManager")
	public List<DerechoExterno> getDerechosEnUso() {
		
		if (this.derechos == null) {
			this.init();
		}
		
		return this.derechos;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<DerechoExterno> getTodosFiltrado(String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"SELECT DISTINCT new " + DerechoExterno.class.getName() + "(dc.nombreDerechoExterno) "
				+ "from DatosCancion dc WHERE dc.nombreDerechoExterno LIKE :filtro "
				+ "ORDER BY dc.nombreDerechoExterno")
				.setParameter("filtro", "%" + filtro + "%")
				.list();
	}

	@Transactional(value="transactionManager")
	public DerechoExterno buscar(String nombre) {
		
		if (this.derechos == null) {
			this.init();
		}
		
		DerechoExterno derecho = null;
		Iterator<DerechoExterno> it = this.derechos.iterator();
		
		while(it.hasNext() && derecho == null) {
			DerechoExterno encontrado = it.next();
			if (encontrado.getNombre().equalsIgnoreCase(nombre)) {
				derecho = encontrado;
			}
		}
		
		return derecho;
		
	}

}
