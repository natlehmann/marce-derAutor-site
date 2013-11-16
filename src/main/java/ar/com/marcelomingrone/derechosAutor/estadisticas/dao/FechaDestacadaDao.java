package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.FechaDestacada;

@Repository
public class FechaDestacadaDao extends EntidadDao<FechaDestacada> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public FechaDestacadaDao() {
		super(FechaDestacada.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}



}
