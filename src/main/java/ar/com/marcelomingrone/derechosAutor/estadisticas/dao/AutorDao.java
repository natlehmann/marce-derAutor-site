package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Autor;

@Repository
public class AutorDao extends EntidadDao<Autor> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public AutorDao() {
		super(Autor.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}



}
