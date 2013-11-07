package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.PercibidoPorAutor;

@Repository
public class PercibidoPorAutorDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public PercibidoPorAutor guardar(PercibidoPorAutor percibidoPorAutor) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(percibidoPorAutor);
		
		return percibidoPorAutor;
	}
	
	@Transactional
	public void guardar(List<PercibidoPorAutor> percibidoPorAutor) {
		Session session = sessionFactory.getCurrentSession();
		
		for (PercibidoPorAutor unidad : percibidoPorAutor) {
			session.saveOrUpdate(unidad);
		}
	}

	@Transactional
	public void borrarTodo() {
		
		Session session = sessionFactory.getCurrentSession();		
		session.createSQLQuery("delete from PercibidoPorAutor").executeUpdate();	
	}

}
