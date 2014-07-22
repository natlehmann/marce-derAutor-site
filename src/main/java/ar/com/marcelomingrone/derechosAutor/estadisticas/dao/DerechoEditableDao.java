package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Derecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DerechoEditable;

@Repository
public class DerechoEditableDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public List<Derecho> getTodosFiltrado(String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer queryStr = new StringBuffer("SELECT d FROM DerechoEditable d ");
		
		if ( !StringUtils.isEmpty(filtro) ) {
			queryStr.append("WHERE d.nombre LIKE :filtro ");
		}
		
		queryStr.append("ORDER BY d.nombre");
		
		Query query = session.createQuery(queryStr.toString());
		
		if ( !StringUtils.isEmpty(filtro) ) {
			query.setParameter("filtro", "%" + filtro + "%");
		}
		
		return query.list();
		
	}

	@Transactional(value="transactionManager")
	public long getCantidadResultados(String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer queryStr = new StringBuffer("SELECT COUNT(d) FROM DerechoEditable d ");
		
		if ( !StringUtils.isEmpty(filtro) ) {
			queryStr.append("WHERE d.nombre LIKE :filtro ");
		}
		
		Query query = session.createQuery(queryStr.toString());
		
		if ( !StringUtils.isEmpty(filtro) ) {
			query.setParameter("filtro", "%" + filtro + "%");
		}
		
		Long resultado = (Long) query.uniqueResult();
		
		return resultado != null ? resultado.longValue() : 0;
	}

	@Transactional(value="transactionManager")
	public DerechoEditable buscar(String nombre) {
		
		Session session = sessionFactory.getCurrentSession();
		return (DerechoEditable) session.get(DerechoEditable.class, nombre);
	}

	@Transactional(value="transactionManager")
	public void guardar(DerechoEditable derecho) {
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(derecho);
		
	}

	@Transactional(value="transactionManager")
	public void eliminar(DerechoEditable derecho) {
		
		Session session = sessionFactory.getCurrentSession();
		session.delete(derecho);		
	}

	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public List<Derecho> getTodos() {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("FROM DerechoEditable d ORDER BY d.nombre").list();
	}

}
