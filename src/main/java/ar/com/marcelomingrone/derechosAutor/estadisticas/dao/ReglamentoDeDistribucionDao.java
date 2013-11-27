package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ReglamentoDeDistribucion;

@Repository
public class ReglamentoDeDistribucionDao extends EntidadDao<ReglamentoDeDistribucion> {
	
	@Autowired
	private SessionFactory sessionFactory;

	protected ReglamentoDeDistribucionDao() {
		super(ReglamentoDeDistribucion.class, "fecha");
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ReglamentoDeDistribucion> getTodosPaginadoFiltrado(int inicio,
			int cantidadResultados, String filtro, String campoOrdenamiento, String direccionOrdenamiento) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT r FROM ReglamentoDeDistribucion r ");
		
		if (!StringUtils.isEmpty(filtro)) {
			buffer.append("WHERE r.fuente.nombre = :filtro ");
		}
		
		if (!StringUtils.isEmpty(campoOrdenamiento)) {
			buffer.append("ORDER BY r.").append(campoOrdenamiento).append(" ").append(direccionOrdenamiento);
			
		} else {
			buffer.append("ORDER BY r.fecha DESC");
		}
		
		Query query = session.createQuery(buffer.toString());
		
		if (!StringUtils.isEmpty(filtro)) {
			query.setParameter("filtro", filtro);
		}
		
		query.setFirstResult(inicio);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

	@Transactional
	public long getCantidadResultados(String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT COUNT(r) FROM ReglamentoDeDistribucion r ");
		
		if (!StringUtils.isEmpty(filtro)) {
			buffer.append("WHERE r.fuente.nombre = :filtro ");
		}
		
		Query query = session.createQuery(buffer.toString());
		
		if (!StringUtils.isEmpty(filtro)) {
			query.setParameter("filtro", filtro);
		}
		
		Long resultado = (Long) query.uniqueResult();
		
		return resultado != null ? resultado.longValue() : 0;
	}

}
