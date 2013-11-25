package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Fuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ItemAuditoria;
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

	@SuppressWarnings("unchecked")
	@Transactional
	public List<PuntoAuditoria> buscarPorFuente(Long idFuente) {
		
		Session session = sessionFactory.getCurrentSession();		
		return session.createQuery(
				"SELECT p FROM PuntoAuditoria p WHERE p.fuente.id = :idFuente "
				+ "ORDER BY p.itemAuditoria.orden").setParameter("idFuente", idFuente).list();
	}


	@Transactional
	public void guardar(List<PuntoAuditoria> puntosAuditoria) {
		
		Session session = getSessionFactory().getCurrentSession();
		for (PuntoAuditoria punto : puntosAuditoria) {
			session.saveOrUpdate(punto);
		}
	}


	@Transactional
	public PuntoAuditoria buscarPorItemyFuente(ItemAuditoria item, Fuente fuente) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		return (PuntoAuditoria) session.createQuery(
					"SELECT p FROM PuntoAuditoria p WHERE p.fuente = :fuente AND p.itemAuditoria = :item")
					.setParameter("fuente", fuente)
					.setParameter("item", item)
					.uniqueResult();
	}

}
