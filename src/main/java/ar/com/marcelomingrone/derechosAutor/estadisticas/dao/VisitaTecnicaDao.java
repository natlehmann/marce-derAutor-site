package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.VisitaTecnica;

@Repository
public class VisitaTecnicaDao extends EntidadDao<VisitaTecnica> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected VisitaTecnicaDao() {
		super(VisitaTecnica.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Transactional
	public VisitaTecnica buscarPorFuente(Long idFuente) {
		
		Session session = sessionFactory.getCurrentSession();		
		
		return (VisitaTecnica) session.createQuery(
				"SELECT v FROM VisitaTecnica v WHERE v.fuenteAuditada.id = :idFuente")
					.setParameter("idFuente", idFuente).uniqueResult();
	}

}
