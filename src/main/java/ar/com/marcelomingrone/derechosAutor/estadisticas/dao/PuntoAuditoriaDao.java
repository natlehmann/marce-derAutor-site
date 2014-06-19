package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.PuntoAuditoria;

@Repository
public class PuntoAuditoriaDao extends EntidadDao<PuntoAuditoria> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected PuntoAuditoriaDao() {
		super(PuntoAuditoria.class);
	}


	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Transactional(value="transactionManager")
	public void guardar(List<PuntoAuditoria> puntosAuditoria) {
		
		Session session = getSessionFactory().getCurrentSession();
		for (PuntoAuditoria punto : puntosAuditoria) {
			session.saveOrUpdate(punto);
		}
	}

}
