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


	@Transactional
	public long getPromedioDuracionEstimadaPara1Kb() {
		
		Session session = sessionFactory.getCurrentSession();
		Double promedio = (Double) session.createQuery(
				"SELECT AVG(h.duracionEstimada1024bytes) "
				+ "FROM HistorialImportacion h")
				.uniqueResult();
		
		return (promedio != null) ? promedio.longValue() : 0;
	}

	@Transactional
	public HistorialImportacion buscarPorNombreYFecha(String nombre,
			Date inicioEjecucion) {
		
		Session session = sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<HistorialImportacion> resultados = session.createQuery(
				"select h from HistorialImportacion h where h.nombreArchivo = :nombreArchivo "
				+ "and h.inicio = :inicio order by h.inicio desc")
				.setParameter("nombreArchivo", nombre)
				.setParameter("inicio", inicioEjecucion).list();
		
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
	@Transactional
	public List<HistorialImportacion> getTodosPaginadoFiltrado(int inicio, int cantidadResultados,
			String filtro, String campoOrdenamiento, String direccionOrdenamiento) {
		
		if (StringUtils.isEmpty(filtro)) {
			return super.getTodosPaginado(
					inicio, cantidadResultados, campoOrdenamiento, direccionOrdenamiento);
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "from HistorialImportacion where nombreArchivo like :filtro";
			
			if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
				query += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
			}
			
			return session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%")
					.setFirstResult(inicio)
					.setMaxResults(cantidadResultados).list();
			
		}
	}
	
	
	@Transactional
	public Long getCantidadResultados(String filtro) {
		
		if (StringUtils.isEmpty(filtro)) {
			return super.getCantidadTotal();
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "select count(e) from HistorialImportacion e where e.nombreArchivo like :filtro";
			
			Long resultado = (Long) session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%").uniqueResult();
			
			return resultado != null ? resultado.longValue() : 0;
		}
	}
}
