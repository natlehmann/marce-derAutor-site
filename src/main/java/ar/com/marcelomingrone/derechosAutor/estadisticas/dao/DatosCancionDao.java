package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Configuracion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DatosCancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Derecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoPorDerecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotal;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotalPorDerecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotalPorFuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.DerechoExterno;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Fuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Pais;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.RankingCancion;

@Repository
public class DatosCancionDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DerechoExternoReplicaDao derechoDao;
	
	public DatosCancionDao() {}
	
	public DatosCancionDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	

	@Transactional(value="transactionManager")
	public DatosCancion guardar(DatosCancion datos) {
		Session session = sessionFactory.getCurrentSession();
		datos = (DatosCancion) session.merge(datos);
		
		return datos;
	}
	
	@Transactional(value="transactionManager")
	public void guardar(List<DatosCancion> datos) {
		
		Session session = sessionFactory.getCurrentSession();
		for(DatosCancion dato : datos) {
			
			session.merge(dato);
		}
	}
	
	@Transactional(value="transactionManager")
	public void borrarTodo() {
		
		Session session = sessionFactory.getCurrentSession();		
		session.createSQLQuery("drop table DatosCancion").executeUpdate();
		
		session.createSQLQuery("create table DatosCancion("
				+ "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "companyId BIGINT,pais_id BIGINT,nombrePais varchar(255) null,trimestre int not null,"
				+ "anio int not null,autor_id BIGINT NULL,nombreAutor varchar(255) null,"
				+ "cancion_id BIGINT NULL,nombreCancion varchar(255) null,fuente_id BIGINT NULL,"
				+ "nombreFuente varchar(255) null,derecho_nombre varchar(255) NULL,"
				+ "cantidadUnidades BIGINT default 0,montoPercibido double default 0"
				+ ")ENGINE=InnoDB;").executeUpdate();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	@Cacheable("anios")
	public List<Integer> getAnios() {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"select DISTINCT(dc.anio) from DatosCancion dc order by dc.anio desc").list();
	}
	
	@Transactional(value="transactionManager")
	@Cacheable("anios")
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
	@Transactional(value="transactionManager")
	@Cacheable("paises")
	public List<Pais> getPaises() {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"select DISTINCT new " + Pais.class.getName() 
				+ "(idPais,nombrePais) from DatosCancion dc order by dc.nombrePais asc").list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	@Cacheable("canciones")
	public List<RankingCancion> getCanciones(Long idPais,
			Integer anio, Integer trimestre, Long idAutor, int inicio,
			int cantidadResultados, String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new " + RankingCancion.class.getName() + "( ")
			.append("dc.idCancion, dc.nombreCancion, dc.idAutor, dc.nombreAutor, ")
			.append("SUM(dc.cantidadUnidades), SUM(dc.montoPercibido)) ")
			.append("FROM DatosCancion dc ")
			.append("WHERE (dc.companyId = :companyId OR dc.companyId is null) ");
		
		buffer.append(DaoUtils.getWhereClause(trimestre, anio, idPais, filtro, idAutor));
		
		buffer.append("GROUP BY dc.idCancion, dc.idAutor ")
			.append("ORDER BY dc.nombreCancion asc");
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro, idAutor);
		
		query.setFirstResult(inicio);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

	@Transactional(value="transactionManager")
	@Cacheable("cancionesCount")
	public long getCantidadCanciones(Long idPais,
			Integer anio, Integer trimestre, Long idAutor, String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(DISTINCT dc.idCancion) FROM DatosCancion dc ")
				.append("WHERE (dc.companyId = :companyId OR dc.companyId is null) ");
		
		buffer.append(DaoUtils.getWhereClause(trimestre, anio, idPais, filtro, idAutor));
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro, idAutor);
		
		Long resultado = (Long) query.uniqueResult();
		
		return resultado != null ? resultado.longValue() : 0;
	}
	
	
	@Transactional(value="transactionManager")
	@Cacheable("fuentes")
	public List<Fuente> getFuentes(Long idPais) {
		
		Session session = sessionFactory.getCurrentSession();		
		return getFuentes(idPais, session);
	}

	@SuppressWarnings("unchecked")
	private List<Fuente> getFuentes(Long idPais, Session session) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT DISTINCT new ").append(Fuente.class.getName())
				.append("(dc.idFuente, dc.nombreFuente) from DatosCancion dc ")
				.append("WHERE dc.idFuente is not null ");
		
		if (idPais != null) {
			buffer.append("AND dc.idPais = :idPais ");
		}
		
		buffer.append("ORDER BY dc.idFuente");
		
		Query query = session.createQuery(buffer.toString());
		DaoUtils.setearParametros(query, idPais, null, null, null);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Cacheable("derechosExternos")
	private List<DerechoExterno> getDerechosPorFuente(Fuente fuente, Session session) {
		
		return session.createQuery(
				"SELECT DISTINCT new " + DerechoExterno.class.getName() 
				+ "(dc.nombreDerechoExterno) from DatosCancion dc WHERE dc.idFuente = :fuente ORDER BY dc.nombreDerechoExterno")
				.setParameter("fuente", fuente.getId()).list();
	}
	
	/**
	 * Pais y trimestre son opcionales
	 * @param pais
	 * @param trimestre
	 * @return
	 */
	@Transactional(value="transactionManager")
	@Cacheable("montosSACMPorAnio")
	public List<MontoTotal> getMontosTotalesSACMPorAnio(Pais pais, Integer trimestre) {
		return getMontosTotalesPorAnio(pais, trimestre, false);
	}
	
	/**
	 * Pais y trimestre son opcionales
	 * @param pais
	 * @param trimestre
	 * @return
	 */
	@Transactional(value="transactionManager")
	@Cacheable("montosOtrosPorAnio")
	public List<MontoTotal> getMontosTotalesOtrosPorAnio(Pais pais, Integer trimestre) {
		return getMontosTotalesPorAnio(pais, trimestre, true);
	}
	

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	private List<MontoTotal> getMontosTotalesPorAnio(Pais pais, Integer trimestre, boolean excluirSACM) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new " + MontoTotal.class.getName() + "(")
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
			montos = montos.subList(montos.size() - 3, montos.size());
		}
		
		return montos;
	}

	
	/**
	 * Anio es obligatorio, pais es opcional
	 * @param anio
	 * @param pais
	 * @return
	 */
	@Transactional(value="transactionManager")
	@Cacheable("montosSACMPorTrimestre")
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
	@Transactional(value="transactionManager")
	@Cacheable("montosOtrosPorTrimestre")
	public List<MontoTotal> getMontosTotalesOtrosPorTrimestre(Integer anio, Pais pais) {
		
		if (anio == null) {
			throw new IllegalArgumentException("El año debe estar seteado para esta consulta");
		}
		
		return getMontosTotalesPorTrimestre(anio, pais, true);
	}
	

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	private List<MontoTotal> getMontosTotalesPorTrimestre(Integer anio, Pais pais, boolean excluirSACM) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new " + MontoTotal.class.getName() + "(")
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
	@Transactional(value="transactionManager")
	@Cacheable("montosSACMPorPais")
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
	@Transactional(value="transactionManager")
	@Cacheable("montosOtrosPorPais")
	public List<MontoTotal> getMontosTotalesOtrosPorPais(Integer anio,
			Integer trimestre, Pais pais) {
		
		if (anio == null || trimestre == null) {
			throw new IllegalArgumentException(
					"El año y el trimestre deben estar seteados para esta consulta");
		}
		
		return getMontosTotalesPorPais(anio, trimestre, pais, true);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	private List<MontoTotal> getMontosTotalesPorPais(Integer anio, Integer trimestre, 
			Pais pais, boolean excluirSACM) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new " + MontoTotal.class.getName() + "(")
			.append(anio).append(", ")
			.append(trimestre).append(", dc.idPais, dc.nombrePais, SUM(dc.montoPercibido)) ")
			.append("FROM DatosCancion dc ");
		
		if (excluirSACM) {
			buffer.append("WHERE dc.companyId != :companyId ");
			
		} else {
			buffer.append("WHERE dc.companyId = :companyId ");
		}
			
		buffer.append(DaoUtils.getWhereClause(trimestre, anio, (pais != null) ? pais.getId() : null, null))			
			.append("GROUP BY dc.idPais");
		
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

	
//	@Cacheable("montosPorFuente")
	public List<MontoTotalPorFuente> getTotalesPorFuente(Long idPais, Integer anio) {
		
		List<MontoTotalPorFuente> montos = obtenerMontos(idPais, anio);		
		ordenarDerechos(montos);

		return montos;
			
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	private List<MontoTotalPorFuente> obtenerMontos(Long idPais, Integer anio) {
		
		Session session = sessionFactory.openSession();
		
		Query query = getQueryTotalesPorFuenteSACM(session, idPais, anio);
		List<MontoPorDerecho> montosSACM = query.list();
		
		query = getQueryTotalesPorFuenteOtros(session, idPais, anio);
		List<MontoPorDerecho> montosOtros = query.list();
		
		List<Fuente> fuentes = getFuentes(idPais, session);
		List<MontoTotalPorFuente> montos = procesarTotalesPorFuente(montosSACM, montosOtros, fuentes, session);
		return montos;
	}

	private void ordenarDerechos(List<MontoTotalPorFuente> montos) {
		
		for (MontoTotalPorFuente monto : montos) {
			monto.setMontosPorDerecho(derechoDao.ordenarDerechos(monto.getMontosPorDerecho()));
		}
		
	}

	private List<MontoTotalPorFuente> procesarTotalesPorFuente(
			List<MontoPorDerecho> montosSACM,
			List<MontoPorDerecho> montosOtros, List<Fuente> fuentes, Session session) {
		
		List<MontoTotalPorFuente> resultado = new LinkedList<>();
		
		for (Fuente fuente : fuentes) {
			
			MontoTotalPorFuente montoTotalPorFuente = new MontoTotalPorFuente();
			montoTotalPorFuente.setFuente(fuente);
			resultado.add(montoTotalPorFuente);
			
			List<DerechoExterno> derechos = getDerechosPorFuente(fuente, session);
			for (DerechoExterno derecho : derechos) {
				
				MontoTotalPorDerecho montoTotalPorDerecho = new MontoTotalPorDerecho();
				montoTotalPorDerecho.setDerecho(derecho);
				montoTotalPorFuente.agregarMontoPorDerecho(montoTotalPorDerecho);
				
				for (int trimestre = 1; trimestre <= 4; trimestre++) {
					
					MontoPorDerecho montoSACM = buscarPorFuenteDerechoYTrimestre(
							montosSACM, fuente, derecho, trimestre);
					
					if (montoSACM != null) {
						montoTotalPorDerecho.setMontoSACM(montoSACM.getMonto(), trimestre);
					}
					
					MontoPorDerecho montoOtros = buscarPorFuenteDerechoYTrimestre(
							montosOtros, fuente, derecho, trimestre);
					
					if (montoOtros != null) {
						montoTotalPorDerecho.setMontoOtros(montoOtros.getMonto(), trimestre);
					}
				}
			}
		}
		
		return resultado;
	}

	private MontoPorDerecho buscarPorFuenteDerechoYTrimestre(
			List<MontoPorDerecho> montos, Fuente fuente, Derecho derecho,
			int trimestre) {
		
		MontoPorDerecho resultado = null;
		Iterator<MontoPorDerecho> it = montos.iterator();
		
		while (it.hasNext() && resultado == null) {
			
			MontoPorDerecho encontrado = it.next();
			if (encontrado.getDerecho().equals(derecho) 
					&& encontrado.getFuente().equals(fuente) 
					&& encontrado.getTrimestre().equals(Integer.valueOf(trimestre))) {
				
				resultado = encontrado;
				it.remove();
			}
		}
		
		return resultado;
	}

	private Query getQueryTotalesPorFuenteSACM(Session session, Long idPais, Integer anio) {
		return getQueryTotalesPorFuente(session, idPais, anio, false);
	}
	
	private Query getQueryTotalesPorFuenteOtros(Session session, Long idPais, Integer anio) {
		return getQueryTotalesPorFuente(session, idPais, anio, true);
	}

	private Query getQueryTotalesPorFuente(Session session, Long idPais, 
			Integer anio, boolean excluirSACM) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new " + MontoPorDerecho.class.getName() + "(")
			.append("dc.idFuente, dc.nombreFuente, dc.nombreDerechoExterno, dc.trimestre, SUM(dc.montoPercibido)) ")
			.append("FROM DatosCancion dc ");
		
		if (excluirSACM) {
			buffer.append("WHERE dc.companyId != :companyId ");
			
		} else {
			buffer.append("WHERE dc.companyId = :companyId ");
		}
			
		buffer.append(DaoUtils.getWhereClause(null, anio, idPais, null))			
			.append("GROUP BY dc.idFuente, dc.nombreDerechoExterno, dc.trimestre ")
			.append("ORDER BY dc.idFuente, dc.nombreDerechoExterno");
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, idPais, anio, null, null);
		
		return query;
	}
}