package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion;

@Repository
public class HistorialImportacionDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public HistorialImportacion guardar(HistorialImportacion historial) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(historial);
		
		return historial;
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
}
