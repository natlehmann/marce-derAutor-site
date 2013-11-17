package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DatosCancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.RankingArtistasMasCobrados;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.RankingArtistasMasEjecutados;

public class DatosCancionDao {
	
	private SessionFactory sessionFactory;
	
	public DatosCancionDao() {}
	
	public DatosCancionDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<RankingArtistasMasEjecutados> getAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT dc FROM RankingArtistasMasEjecutados dc ");
		
		buffer.append(DaoUtils.getWhereClauseOrNull(trimestre, anio, idPais, filtro));
		
		buffer.append("ORDER BY dc.ranking");
		
		Query query = session.createQuery(buffer.toString());
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
		
		query.setFirstResult(primerResultado);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
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
	public List<RankingArtistasMasCobrados> getAutoresMasCobrados(Long idPais, Integer anio,
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT dc FROM RankingArtistasMasCobrados dc ");
		
		buffer.append(DaoUtils.getWhereClauseOrNull(trimestre, anio, idPais, filtro));
		
		buffer.append("ORDER BY dc.ranking");
		
		Query query = session.createQuery(buffer.toString());
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
		
		query.setFirstResult(primerResultado);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

	
	@Transactional
	public long getCantidadAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, String filtro) {
		
		return getCantidadAutoresMasEjecutados(
				idPais, anio, trimestre, filtro, "RankingArtistasMasEjecutados");
	}
	
	@Transactional
	public long getCantidadAutoresMasCobrados(Long idPais, Integer anio, 
			Integer trimestre, String filtro) {
		
		return getCantidadAutoresMasEjecutados(
				idPais, anio, trimestre, filtro, "RankingArtistasMasCobrados");
	}
	
	@Transactional
	private long getCantidadAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, String filtro, String nombreEntidad) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT COUNT(DISTINCT dc.autor.id) FROM " + nombreEntidad + " dc ");
		
		buffer.append(DaoUtils.getWhereClauseOrNull(trimestre, anio, idPais, filtro));
		
		Query query = session.createQuery(buffer.toString());
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
		
		Long resultado = (Long) query.uniqueResult();
		
		return resultado != null ? resultado : 0;
	}

}
