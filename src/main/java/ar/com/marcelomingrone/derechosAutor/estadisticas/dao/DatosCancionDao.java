package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DatosCancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.PercibidoPorAutor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.UnidadesVendidasPorAutor;

public class DatosCancionDao {
	
	private SessionFactory sessionFactory;
	
	public DatosCancionDao() {}
	
	public DatosCancionDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<UnidadesVendidasPorAutor> getAutoresMasEjecutados(
			Long idPais, Integer anio, Integer trimestre, int cantidadResultados) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.UnidadesVendidasPorAutor( ")
			.append("dc.autor.nombre as nombreAutor, SUM(dc.cantidadUnidades) as cantidadUnidades) ")
			.append("FROM DatosCancion dc ");
		
		buffer.append(getWhereClause(trimestre, anio, idPais));
		
		buffer.append("GROUP BY dc.autor.id ORDER BY cantidadUnidades desc");
		
		Query query = session.createQuery(buffer.toString());
		
		setearParametros(query, idPais, anio, trimestre);
		
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

	private void setearParametros(Query query, Long idPais, Integer anio,
			Integer trimestre) {
		
		if (trimestre != null) {
			query.setParameter("trimestre", trimestre);
		}
		
		if (anio != null) {
			query.setParameter("anio", anio);
		}
		
		if (idPais != null) {
			query.setParameter("idPais", idPais);
		}
	}
	
	private String getWhereClause(Integer trimestre, Integer anio, Long idPais) {
		
		StringBuffer buffer = new StringBuffer("");
		
		if (trimestre != null || anio != null || idPais != null) {
			buffer.append("WHERE ");
		}
		
		if (trimestre != null) {
			buffer.append("dc.trimestre = :trimestre ");
			if (anio != null || idPais != null) {
				buffer.append("AND ");
			}
		}
		
		if (anio != null) {
			buffer.append("dc.anio = :anio ");
			if (idPais != null) {
				buffer.append("AND ");
			}
		}
		
		if (idPais != null) {
			buffer.append("dc.pais.id = :idPais ");
		}
		
		return buffer.toString();
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

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Integer> getAnios() {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"select DISTINCT(dc.anio) from DatosCancion dc order by dc.anio desc").list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<PercibidoPorAutor> getAutoresMasCobrados(Long idPais, Integer anio,
			Integer trimestre, int cantidadResultados) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.PercibidoPorAutor( ")
			.append("dc.autor.nombre as nombreAutor, SUM(dc.montoPercibido) as monto) ")
			.append("FROM DatosCancion dc ");
		
		buffer.append(getWhereClause(trimestre, anio, idPais));
		
		buffer.append("GROUP BY dc.autor.id ORDER BY monto desc");
		
		Query query = session.createQuery(buffer.toString());
		
		setearParametros(query, idPais, anio, trimestre);
		
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

}
