package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.EstadoDeTareas;

@Repository
public class EstadoDeTareasDao extends EntidadDao<EstadoDeTareas> {
	
	@Autowired
	private SessionFactory sessionFactory;

	protected EstadoDeTareasDao() {
		super(EstadoDeTareas.class, "fecha DESC");
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<EstadoDeTareas> filtrar(Long idAutor, Long idFuente,
			String asunto, String estado, String prioridad) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("SELECT e FROM EstadoDeTareas e WHERE 1=1 ");
		
		if (idAutor != null) {
			queryStr.append("AND e.autor.id = :idAutor ");
		}
		
		if (idFuente != null) {
			queryStr.append("AND e.fuente.id = :idFuente ");
		}
		
		if (!StringUtils.isEmpty(asunto)) {
			queryStr.append("AND e.asunto LIKE :asunto ");
		}
		
		if (!StringUtils.isEmpty(estado)) {
			queryStr.append("AND e.estado = :estado ");
		}
		
		if (!StringUtils.isEmpty(prioridad)) {
			queryStr.append("AND e.prioridad = :prioridad ");
		}
		
		queryStr.append("ORDER BY e.fecha desc");
		
		Query query = session.createQuery(queryStr.toString());
		
		if (idAutor != null) {
			query.setParameter("idAutor", idAutor);
		}
		
		if (idFuente != null) {
			query.setParameter("idFuente", idFuente);
		}
		
		if (!StringUtils.isEmpty(asunto)) {
			query.setParameter("asunto", "%" + asunto + "%");
		}
		
		if (!StringUtils.isEmpty(estado)) {
			query.setParameter("estado", estado);
		}
		
		if (!StringUtils.isEmpty(prioridad)) {
			query.setParameter("prioridad", prioridad);
		}
		
		return query.list();
	}

}
