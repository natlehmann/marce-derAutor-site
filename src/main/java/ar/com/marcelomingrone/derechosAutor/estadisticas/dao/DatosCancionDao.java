package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DatosCancion;

public class DatosCancionDao {
	
	private SessionFactory sessionFactory;
	
	public DatosCancionDao() {}
	
	public DatosCancionDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public DatosCancion guardar(DatosCancion datos) {
		Session session = sessionFactory.getCurrentSession();
		datos = (DatosCancion) session.merge(datos);
		
		return datos;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
