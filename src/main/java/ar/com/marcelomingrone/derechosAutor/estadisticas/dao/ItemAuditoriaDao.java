package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ItemAuditoria;

@Repository
public class ItemAuditoriaDao extends EntidadDao<ItemAuditoria> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected ItemAuditoriaDao() {
		super(ItemAuditoria.class, "orden");
	}


	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
