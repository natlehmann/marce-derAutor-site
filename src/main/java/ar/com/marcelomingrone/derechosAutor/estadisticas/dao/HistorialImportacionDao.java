package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

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
				+ "FROM HistorialImportacion h where h.resultado = 'COMPLETED'")
				.uniqueResult();
		
		return (promedio != null) ? promedio.longValue() : 0;
	}
}
