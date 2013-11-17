package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
	public List<UnidadesVendidasPorAutor> getAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.UnidadesVendidasPorAutor( ")
			.append("dc.autor.nombre as nombreAutor, SUM(dc.cantidadUnidades) as cantidadUnidades, ")
			.append("SUM(dc.montoPercibido) as monto) ")
			.append("FROM DatosCancion dc ");
		
		buffer.append(getWhereClause(trimestre, anio, idPais, filtro));
		
		buffer.append("GROUP BY dc.autor.id ORDER BY cantidadUnidades desc");
		
		Query query = session.createQuery(buffer.toString());
		
		setearParametros(query, idPais, anio, trimestre, filtro);
		
		query.setFirstResult(primerResultado);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

	private void setearParametros(Query query, Long idPais, Integer anio,
			Integer trimestre, String filtro) {
		
		if (trimestre != null) {
			query.setParameter("trimestre", trimestre);
		}
		
		if (anio != null) {
			query.setParameter("anio", anio);
		}
		
		if (idPais != null) {
			query.setParameter("idPais", idPais);
		}
		
		if (!StringUtils.isEmpty(filtro)) {
			query.setParameter("filtro", "%" + filtro + "%");
		}
	}
	
	private String getWhereClause(Integer trimestre, Integer anio, Long idPais, String filtro) {
		
		StringBuffer buffer = new StringBuffer("");
		
		if (trimestre != null || anio != null || idPais != null || !StringUtils.isEmpty(filtro)) {
			buffer.append("WHERE ");
		}
		
		if (trimestre != null) {
			buffer.append("dc.trimestre = :trimestre ");
			if (anio != null || idPais != null || !StringUtils.isEmpty(filtro)) {
				buffer.append("AND ");
			}
		}
		
		if (anio != null) {
			buffer.append("dc.anio = :anio ");
			if (idPais != null || !StringUtils.isEmpty(filtro)) {
				buffer.append("AND ");
			}
		}
		
		if (idPais != null) {
			buffer.append("dc.pais.id = :idPais ");
			if (!StringUtils.isEmpty(filtro)) {
				buffer.append("AND ");
			}
		}
		
		if (!StringUtils.isEmpty(filtro)) {
			buffer.append("dc.autor.nombre like :filtro ");
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
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.PercibidoPorAutor( ")
			.append("dc.autor.nombre as nombreAutor, SUM(dc.montoPercibido) as monto) ")
			.append("FROM DatosCancion dc ");
		
		buffer.append(getWhereClause(trimestre, anio, idPais, filtro));
		
		buffer.append("GROUP BY dc.autor.id ORDER BY monto desc");
		
		Query query = session.createQuery(buffer.toString());
		
		setearParametros(query, idPais, anio, trimestre, filtro);
		
		query.setFirstResult(primerResultado);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

	
	@Transactional
	public long getCantidadAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT COUNT(DISTINCT dc.autor.id) FROM DatosCancion dc ");
		
		buffer.append(getWhereClause(trimestre, anio, idPais, filtro));
		
		Query query = session.createQuery(buffer.toString());
		
		setearParametros(query, idPais, anio, trimestre, filtro);
		
		Long resultado = (Long) query.uniqueResult();
		
		return resultado != null ? resultado : 0;
	}

}
