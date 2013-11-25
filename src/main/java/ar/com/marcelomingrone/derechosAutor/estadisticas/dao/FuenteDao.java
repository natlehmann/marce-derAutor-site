package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Fuente;

@Repository
public class FuenteDao extends EntidadDao<Fuente> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public FuenteDao() {
		super(Fuente.class, "nombre");
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
