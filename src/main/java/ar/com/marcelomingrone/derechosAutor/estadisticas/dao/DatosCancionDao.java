package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Autor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Configuracion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DatosCancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Derecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Fuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotal;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotalPorDerecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotalPorFuente;
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
	
	@Transactional
	public List<Integer> getUltimosTresAnios() {
		
		List<Integer> anios = new LinkedList<>();
		
		Session session = sessionFactory.getCurrentSession();
		Integer ultimoAnio = (Integer) session.createQuery(
				"SELECT MAX(dc.anio) FROM DatosCancion dc").uniqueResult();
		
		if (ultimoAnio != null) {
			anios.add(ultimoAnio);
			anios.add(ultimoAnio - 1);
			anios.add(ultimoAnio - 2);
		}
		
		return anios;
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
	
	/**
	 * Pais y trimestre son opcionales
	 * @param pais
	 * @param trimestre
	 * @return
	 */
	@Transactional
	public List<MontoTotal> getMontosTotalesSACMPorAnio(Pais pais, Integer trimestre) {
		return getMontosTotalesPorAnio(pais, trimestre, false);
	}
	
	/**
	 * Pais y trimestre son opcionales
	 * @param pais
	 * @param trimestre
	 * @return
	 */
	@Transactional
	public List<MontoTotal> getMontosTotalesOtrosPorAnio(Pais pais, Integer trimestre) {
		return getMontosTotalesPorAnio(pais, trimestre, true);
	}
	

	@SuppressWarnings("unchecked")
	@Transactional
	private List<MontoTotal> getMontosTotalesPorAnio(Pais pais, Integer trimestre, boolean excluirSACM) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotal(")
			.append("dc.anio, SUM(dc.montoPercibido)) ")
			.append("FROM DatosCancion dc ");
		
		if (excluirSACM) {
			buffer.append("WHERE dc.companyId != :companyId ");
			
		} else {
			buffer.append("WHERE dc.companyId = :companyId ");
		}
			
		buffer.append(DaoUtils.getWhereClause(trimestre, null, (pais != null) ? pais.getId() : null, null))			
			.append("GROUP BY dc.anio");
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, (pais != null) ? pais.getId() : null, null, trimestre, null);
		
		List<MontoTotal> montos = query.list();
		
		List<Integer> anios = getUltimosTresAnios();
		for (Integer anio : anios) {
			
			MontoTotal nuevoMonto = new MontoTotal(anio, 0.0);
			if (!montos.contains(nuevoMonto)) {
				
				montos.add(nuevoMonto);
			}
		}
		
		Collections.sort(montos, new MontoTotal.ComparadorPorAnio());
		
		if (montos.size() > 3) {
			montos = montos.subList(0, 3);
		}
		
		return montos;
	}

	
	/**
	 * Anio es obligatorio, pais es opcional
	 * @param anio
	 * @param pais
	 * @return
	 */
	@Transactional
	public List<MontoTotal> getMontosTotalesSACMPorTrimestre(Integer anio, Pais pais) {
		
		if (anio == null) {
			throw new IllegalArgumentException("El año debe estar seteado para esta consulta");
		}
		
		return getMontosTotalesPorTrimestre(anio, pais, false);
	}
	
	/**
	 * Anio es obligatorio, pais es opcional
	 * @param anio
	 * @param pais
	 * @return
	 */
	@Transactional
	public List<MontoTotal> getMontosTotalesOtrosPorTrimestre(Integer anio, Pais pais) {
		
		if (anio == null) {
			throw new IllegalArgumentException("El año debe estar seteado para esta consulta");
		}
		
		return getMontosTotalesPorTrimestre(anio, pais, true);
	}
	

	@SuppressWarnings("unchecked")
	@Transactional
	private List<MontoTotal> getMontosTotalesPorTrimestre(Integer anio, Pais pais, boolean excluirSACM) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotal(")
			.append(anio).append(", dc.trimestre, SUM(dc.montoPercibido)) ")
			.append("FROM DatosCancion dc ");
		
		if (excluirSACM) {
			buffer.append("WHERE dc.companyId != :companyId ");
			
		} else {
			buffer.append("WHERE dc.companyId = :companyId ");
		}
			
		buffer.append(DaoUtils.getWhereClause(null, anio, (pais != null) ? pais.getId() : null, null))			
			.append("GROUP BY dc.trimestre");
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, (pais != null) ? pais.getId() : null, anio, null, null);
		
		List<MontoTotal> montos = query.list();
		
		for (int i = 1 ; i <= 4; i++) {
			
			MontoTotal nuevoMonto = new MontoTotal(anio, Integer.valueOf(i), 0.0);
			if (!montos.contains(nuevoMonto)) {
				
				montos.add(nuevoMonto);
			}
		}
		
		Collections.sort(montos, new MontoTotal.ComparadorPorTrimestre());
		
		return montos;
	}

	/**
	 * Anio y trimestre son obligatorios, pais es opcional
	 * @param anio
	 * @param trimestre
	 * @param pais
	 * @return
	 */
	@Transactional
	public List<MontoTotal> getMontosTotalesSACMPorPais(Integer anio,
			Integer trimestre, Pais pais) {
		
		if (anio == null || trimestre == null) {
			throw new IllegalArgumentException(
					"El año y el trimestre deben estar seteados para esta consulta");
		}
		
		return getMontosTotalesPorPais(anio, trimestre, pais, false);
	}
	
	/**
	 * Anio y trimestre son obligatorios, pais es opcional
	 * @param anio
	 * @param trimestre
	 * @param pais
	 * @return
	 */
	@Transactional
	public List<MontoTotal> getMontosTotalesOtrosPorPais(Integer anio,
			Integer trimestre, Pais pais) {
		
		if (anio == null || trimestre == null) {
			throw new IllegalArgumentException(
					"El año y el trimestre deben estar seteados para esta consulta");
		}
		
		return getMontosTotalesPorPais(anio, trimestre, pais, true);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	private List<MontoTotal> getMontosTotalesPorPais(Integer anio, Integer trimestre, 
			Pais pais, boolean excluirSACM) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotal(")
			.append(anio).append(", ")
			.append(trimestre).append(", dc.pais, SUM(dc.montoPercibido)) ")
			.append("FROM DatosCancion dc ");
		
		if (excluirSACM) {
			buffer.append("WHERE dc.companyId != :companyId ");
			
		} else {
			buffer.append("WHERE dc.companyId = :companyId ");
		}
			
		buffer.append(DaoUtils.getWhereClause(trimestre, anio, (pais != null) ? pais.getId() : null, null))			
			.append("GROUP BY dc.pais");
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, (pais != null) ? pais.getId() : null, anio, trimestre, null);
		
		List<MontoTotal> montos = query.list();
		
		if (pais == null) {
			
			List<Pais> todosLosPaises = getPaises();
			for (Pais otroPais : todosLosPaises) {
				
				MontoTotal nuevoMonto = new MontoTotal(anio, trimestre, otroPais, 0.0);
				if (!montos.contains(nuevoMonto)) {
					montos.add(nuevoMonto);
				}
			}
		}
		
		Collections.sort(montos, new MontoTotal.ComparadorPorPais());
		
		return montos;
	}

	
	@Transactional
	public List<MontoTotalPorFuente> getTotalesPorFuente(Long idPais, Integer anio) {
		
		Session session = sessionFactory.getCurrentSession();
		
		List<MontoTotalPorFuente> resultado = new LinkedList<>();
		
		MontoTotalPorFuente monto = new MontoTotalPorFuente();
		monto.setFuente((Fuente)session.get(Fuente.class, 1L));
		List<MontoTotalPorDerecho> montosPorDerecho = new LinkedList<>();
		monto.setMontosPorDerecho(montosPorDerecho);
		
		MontoTotalPorDerecho monto2 = new MontoTotalPorDerecho();
		monto2.setCuartoTrimestreOtros(1);
		monto2.setCuartoTrimestreSACM(2);
		monto2.setDerecho((Derecho)session.get(Derecho.class, "CABLE"));
		monto2.setPrimerTrimestreOtros(3);
		monto2.setPrimerTrimestreSACM(4);
		monto2.setSegundoTrimestreOtros(5);
		monto2.setSegundoTrimestreSACM(6);
		monto2.setTercerTrimestreOtros(7);
		monto2.setTercerTrimestreSACM(8);
		montosPorDerecho.add(monto2);
		
		resultado.add(monto);
		
		return resultado;
	}
}
