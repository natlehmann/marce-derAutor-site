package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Autor;

@Repository
public class AutorDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List<Autor> getTodos() {
		
		Session session = sessionFactory.getCurrentSession();
		List autores = session.createQuery("from Autor").list();
		return autores;
	}

	@Transactional
	public Autor guardar(Autor autor) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(autor);
		
		return autor;
	}
	
	@Transactional
	public void borrarTodo() {
		
		Session session = sessionFactory.getCurrentSession();		
		session.createSQLQuery("delete from Autor").executeUpdate();		
	}

}
