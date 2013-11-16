package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Cancion;

@Repository
public class CancionDao extends EntidadDao<Cancion> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public CancionDao() {
		super(Cancion.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
