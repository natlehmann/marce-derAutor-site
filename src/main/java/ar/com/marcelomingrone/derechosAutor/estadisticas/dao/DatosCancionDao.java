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
	
	@Transactional
	public void borrarTodo() {
		
		Session session = sessionFactory.getCurrentSession();		
		session.createSQLQuery("drop table DatosCancion").executeUpdate();
		
		session.createSQLQuery("create table DatosCancion("
				+ "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "companyId BIGINT,pais_id BIGINT,trimestre int not null,"
				+ "anio int not null,formatId int,autor_id BIGINT NULL,"
				+ "cancion_id BIGINT NULL,fuente_id BIGINT NULL,"
				+ "cantidadUnidades BIGINT default 0,montoPercibido DECIMAL(10,2) default 0,"
				+ "FOREIGN KEY (pais_id) REFERENCES Pais(id),"
				+ "FOREIGN KEY (autor_id) REFERENCES Autor(id),"
				+ "FOREIGN KEY (cancion_id) REFERENCES Cancion(id),"
				+ "FOREIGN KEY (fuente_id) REFERENCES Fuente(id))ENGINE=InnoDB;").executeUpdate();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
