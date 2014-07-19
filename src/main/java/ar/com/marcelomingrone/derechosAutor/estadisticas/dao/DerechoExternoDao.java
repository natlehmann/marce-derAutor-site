package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.DerechoExterno;

@Repository
public class DerechoExternoDao {
	
	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	private List<DerechoExterno> derechos;
	
	@Transactional(value="transactionManagerExterno")
	@SuppressWarnings("unchecked")
	private void init() {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		this.derechos = session.createQuery(
				"SELECT DISTINCT new " + DerechoExterno.class.getName() + "(dc.rightName) "
				+ "from SumarizacionMontos dc ORDER BY dc.rightName").list();
	}
	
	@Transactional(value="transactionManagerExterno")
	public List<DerechoExterno> getDerechosEnUso() {
		
		if (this.derechos == null) {
			this.init();
		}
		
		return this.derechos;
	}
	
	public List<DerechoExterno> getTodosFiltrado(String filtro) {
		
		if (this.derechos == null) {
			this.init();
		}
		
		List<DerechoExterno> resultado = new LinkedList<>();
		if (!StringUtils.isEmpty(filtro)) {
			
			for (DerechoExterno derecho : this.derechos) {
				if (derecho.getNombre().toUpperCase().contains(filtro.toUpperCase())) {
					resultado.add(derecho);
				}
			}
			
		} else {
			resultado.addAll(this.derechos);
		}
		
		return resultado;
	}

	@Transactional(value="transactionManagerExterno")
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
		
		if (derecho == null) {
			Session session = sessionFactoryExterno.getCurrentSession();
			derecho = (DerechoExterno) session.get(DerechoExterno.class, nombre);
			
			if (derecho != null) {
				this.derechos.add(derecho);
				Collections.sort(this.derechos);
			}
		}
		
		return derecho;
		
	}

	@Transactional(value="transactionManagerExterno")
	@SuppressWarnings("unchecked")
	public List<DerechoExterno> getTodos() {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		return session.createQuery("FROM DerechoExterno d ORDER BY d.nombre").list();
	}

}
