package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Fuente;

@Repository
public class FuenteDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List<Fuente> getTodos() {
		
		Session session = sessionFactory.getCurrentSession();
		List fuentes = session.createQuery("from Fuente").list();
		return fuentes;
	}

	@Transactional
	public Fuente guardar(Fuente fuente) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(fuente);
		
		return fuente;
	}
	
	@Transactional
	public void borrarTodo() {
		
		Session session = sessionFactory.getCurrentSession();		
		session.createSQLQuery("delete from Fuente").executeUpdate();		
	}
	
	@Transactional
	public Fuente buscar(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (Fuente) session.get(Fuente.class, id);
	}

}
