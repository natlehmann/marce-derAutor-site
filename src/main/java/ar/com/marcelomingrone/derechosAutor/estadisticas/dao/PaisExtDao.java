package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.PaisExt;


@Repository
public class PaisExtDao extends EntidadExternaDao<PaisExt> {

	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	public PaisExtDao() {
		super(PaisExt.class, "nombre");
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactoryExterno;
	}

}
