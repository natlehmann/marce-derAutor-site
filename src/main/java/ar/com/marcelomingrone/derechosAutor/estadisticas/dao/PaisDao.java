package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Pais;

@Repository
public class PaisDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Pais guardar(Pais pais) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(pais);
		
		return pais;
	}
	
	@Transactional
	public void borrarTodo() {
		
		Session session = sessionFactory.getCurrentSession();		
		session.createSQLQuery("delete from Pais").executeUpdate();		
	}

}
