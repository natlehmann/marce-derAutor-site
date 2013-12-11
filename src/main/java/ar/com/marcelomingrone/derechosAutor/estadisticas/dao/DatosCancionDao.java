package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Autor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Configuracion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DatosCancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Pais;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.RankingCancion;

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
				+ "derecho_nombre varchar(255) NULL,"
				+ "cantidadUnidades BIGINT default 0,montoPercibido DECIMAL(10,2) default 0,"
				+ "FOREIGN KEY (pais_id) REFERENCES Pais(id),"
				+ "FOREIGN KEY (autor_id) REFERENCES Autor(id),"
				+ "FOREIGN KEY (cancion_id) REFERENCES Cancion(id),"
				+ "FOREIGN KEY (fuente_id) REFERENCES Fuente(id),"
				+ "FOREIGN KEY (derecho_nombre) REFERENCES Derecho(nombre))ENGINE=InnoDB;").executeUpdate();
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
	public List<Pais> getPaises() {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"select DISTINCT(dc.pais) from DatosCancion dc order by dc.pais.nombre asc").list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Autor> getAutoresLikeNombre(String nombreAutor) {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"select DISTINCT(dc.autor) from DatosCancion dc "
				+ "WHERE dc.companyId = :companyId AND dc.autor.nombre LIKE :nombreAutor order by dc.autor.nombre asc")
				.setParameter("companyId", Configuracion.SACM_COMPANY_ID)
				.setParameter("nombreAutor", "%" + nombreAutor + "%").list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<RankingCancion> getCanciones(Long idPais,
			Integer anio, Integer trimestre, Long idAutor, int inicio,
			int cantidadResultados, String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.RankingCancion( ")
			.append("dc.cancion.id, dc.cancion.nombre, dc.autor.id, dc.autor.nombre, ")
			.append("SUM(dc.cantidadUnidades), SUM(dc.montoPercibido)) ")
			.append("FROM DatosCancion dc ")
			.append("WHERE dc.companyId = :companyId ");
		
		buffer.append(DaoUtils.getWhereClause(trimestre, anio, idPais, filtro, idAutor));
		
		buffer.append("GROUP BY dc.cancion.id, dc.autor.id ")
			.append("ORDER BY dc.cancion.nombre asc");
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro, idAutor);
		
		query.setFirstResult(inicio);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

	@Transactional
	public long getCantidadCanciones(Long idPais,
			Integer anio, Integer trimestre, Long idAutor, String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(DISTINCT dc.cancion.id) FROM DatosCancion dc WHERE dc.companyId = :companyId ");
		
		buffer.append(DaoUtils.getWhereClause(trimestre, anio, idPais, filtro, idAutor));
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro, idAutor);
		
		Long resultado = (Long) query.uniqueResult();
		
		return resultado != null ? resultado.longValue() : 0;
	}


}
