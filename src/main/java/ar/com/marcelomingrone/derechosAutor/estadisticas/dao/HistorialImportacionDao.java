package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion;

@Repository
public class HistorialImportacionDao extends EntidadDao<HistorialImportacion> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public HistorialImportacionDao() {
		super(HistorialImportacion.class, "inicio DESC");
	}


	@Transactional(value="transactionManager")
	public HistorialImportacion buscarPorFecha(Date inicioEjecucion) {
		
		Session session = sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<HistorialImportacion> resultados = session.createQuery(
				"select h from HistorialImportacion h where h.inicio = :inicio order by h.inicio desc")
				.setParameter("inicio", inicioEjecucion)
				.setMaxResults(1)
				.list();
		
		if (resultados.isEmpty()) {
			return null;
		}
		
		return resultados.get(0);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<HistorialImportacion> getTodosPaginadoFiltrado(int inicio, int cantidadResultados,
			String filtro, String campoOrdenamiento, String direccionOrdenamiento) {
		
		if (StringUtils.isEmpty(filtro)) {
			return super.getTodosPaginado(
					inicio, cantidadResultados, campoOrdenamiento, direccionOrdenamiento);
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "from HistorialImportacion where (estado like :filtro OR resultadoEjecucion like :filtro)";
			
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
			
			String query = "select count(e) from HistorialImportacion e where (e.estado like :filtro OR e.resultadoEjecucion like :filtro)";
			
			Long resultado = (Long) session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%").uniqueResult();
			
			return resultado != null ? resultado.longValue() : 0;
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public HistorialImportacion buscarHistorialPrevio(Date inicioEjecucion) {
		
		Session session = sessionFactory.getCurrentSession();
		List<HistorialImportacion> resultados = session.createQuery(
				"SELECT h FROM HistorialImportacion h WHERE h.inicio < :fecha ORDER BY h.inicio DESC")
				.setParameter("fecha", inicioEjecucion)
				.setMaxResults(1)
				.list();
		
		if (!resultados.isEmpty()) {
			return resultados.get(0);
		}
		
		return null;
	}
}
