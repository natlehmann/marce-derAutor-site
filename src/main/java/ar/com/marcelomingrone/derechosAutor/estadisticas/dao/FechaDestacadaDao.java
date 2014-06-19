package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<FechaDestacada> getTodosPaginadoFiltrado(int inicio, int cantidadResultados,
			String filtro, String campoOrdenamiento, String direccionOrdenamiento) {
		
		if (StringUtils.isEmpty(filtro)) {
			return super.getTodosPaginado(
					inicio, cantidadResultados, campoOrdenamiento, direccionOrdenamiento);
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "from FechaDestacada where descripcion like :filtro";
			
			if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
				query += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
			}
			
			return session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%")
					.setFirstResult(inicio)
					.setMaxResults(cantidadResultados).list();
			
		}
	}

	@Transactional(value="transactionManager")
	public Long getCantidadResultados(String filtro) {
		
		if (StringUtils.isEmpty(filtro)) {
			return super.getCantidadTotal();
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "select count(e) from FechaDestacada e where e.descripcion like :filtro";
			
			Long resultado = (Long) session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%").uniqueResult();
			
			return resultado != null ? resultado.longValue() : 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<FechaDestacada> getDesde(Date fechaDesde) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.createQuery(
				"SELECT e FROM FechaDestacada e WHERE e.fecha >= :fechaDesde ORDER BY e.fecha")
				.setParameter("fechaDesde", fechaDesde).list();
	}


}
