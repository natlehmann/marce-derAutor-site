package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Pais;

@Repository
public class PaisDao extends EntidadDao<Pais> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public PaisDao() {
		super(Pais.class, "nombre");
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
