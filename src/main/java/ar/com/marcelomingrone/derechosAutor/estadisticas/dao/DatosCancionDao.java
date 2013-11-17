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
	public List<UnidadesVendidasPorAutor> getAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro,
			boolean calcularRanking) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.UnidadesVendidasPorAutor( ")
			.append("dc.autor.id as idAutor, dc.autor.nombre as nombreAutor, ")
			.append("SUM(dc.cantidadUnidades) as cantidadUnidades, ")
			.append("SUM(dc.montoPercibido) as monto) ")
			.append("FROM DatosCancion dc ");
		
		buffer.append(DaoUtils.getWhereClause(trimestre, anio, idPais, filtro));
		
		buffer.append("GROUP BY dc.autor.id ORDER BY cantidadUnidades desc, idAutor asc");
		
		Query query = session.createQuery(buffer.toString());
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
		
		query.setFirstResult(primerResultado);
		query.setMaxResults(cantidadResultados);
		
		List<UnidadesVendidasPorAutor> resultado = query.list();
		
		if (calcularRanking) {
//			calcularRankingMasEjecutados(resultado, idPais, anio, trimestre);
		}
		
		return resultado;
	}

	@Transactional
	private void calcularRankingMasEjecutados(
			List<UnidadesVendidasPorAutor> resultado, Long idPais,
			Integer anio, Integer trimestre) {
		
		for (UnidadesVendidasPorAutor unidad : resultado) {
			
			Session session = sessionFactory.getCurrentSession();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("select count(DISTINCT dc2.autor.id) ")
				.append("FROM DatosCancion dc2 ")
				.append("WHERE dc2.autor.id IN ( ")
				.append("SELECT dc.autor.id ")
				.append("FROM DatosCancion dc ");
			
			buffer.append(DaoUtils.getWhereClause(trimestre, anio, idPais, null));
			
			buffer.append("GROUP BY dc.autor.id ")
				.append("HAVING (SUM(dc.cantidadUnidades) > :cantidad) ")
				.append("OR (SUM(dc.cantidadUnidades) = :cantidad and dc.autor.id < :autorId) ")
				.append("ORDER BY SUM(dc.cantidadUnidades) desc, dc.autor.id asc )");
			
			Query query = session.createQuery(buffer.toString());
			query.setParameter("cantidad", unidad.getCantidadUnidades());
			query.setParameter("autorId", unidad.getIdAutor());
			
			DaoUtils.setearParametros(query, idPais, anio, trimestre, null);
			
			Long ranking = (Long) query.uniqueResult();
			
			unidad.setRanking(ranking != null ? ranking.longValue() + 1 : 1);
			
		}
		
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
	public List<Long> getIdsPaises() {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"select DISTINCT(dc.pais.id) from DatosCancion dc order by dc.pais.nombre asc").list();
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
		
		buffer.append(DaoUtils.getWhereClause(trimestre, anio, idPais, filtro));
		
		buffer.append("GROUP BY dc.autor.id ORDER BY monto desc");
		
		Query query = session.createQuery(buffer.toString());
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
		
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
		
		buffer.append(DaoUtils.getWhereClause(trimestre, anio, idPais, filtro));
		
		Query query = session.createQuery(buffer.toString());
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
		
		Long resultado = (Long) query.uniqueResult();
		
		return resultado != null ? resultado : 0;
	}

}
