package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Cancion;

@Repository
public class CancionDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List<Cancion> getTodos() {
		
		Session session = sessionFactory.getCurrentSession();
		List canciones = session.createQuery("from Cancion").list();
		return canciones;
	}

	@Transactional
	public Cancion guardar(Cancion cancion) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cancion);
		
		return cancion;
	}
	
	@Transactional
	public void borrarTodo() {
		
		Session session = sessionFactory.getCurrentSession();		
		session.createSQLQuery("delete from Cancion").executeUpdate();		
	}
	
	@Transactional
	public Cancion buscar(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (Cancion) session.get(Cancion.class, id);
	}

}
