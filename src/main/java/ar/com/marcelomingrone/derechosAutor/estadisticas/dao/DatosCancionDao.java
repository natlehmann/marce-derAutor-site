package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Configuracion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Derecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Fuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoPorDerecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotal;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotalPorDerecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotalPorFuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.RankingCancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Autor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Pais;

@Repository
public class DatosCancionDao {
	
	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	public DatosCancionDao() {}
	
	public DatosCancionDao(SessionFactory sessionFactory) {
		this.sessionFactoryExterno = sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactoryExterno = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public List<Integer> getAnios() {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		return session.createQuery(
				"SELECT DISTINCT anio from SumarizacionMontos ORDER BY anio desc")
				.list();
	}
	
	@Transactional(value="transactionManagerExterno")
	public List<Integer> getUltimosTresAnios() {
		
		List<Integer> anios = new LinkedList<>();
		
		Session session = sessionFactoryExterno.getCurrentSession();
		Integer ultimoAnio = (Integer) session.createQuery(
				"SELECT MAX(anio) FROM SumarizacionMontos").uniqueResult();
		
		if (ultimoAnio != null) {
			anios.add(ultimoAnio);
			anios.add(ultimoAnio - 1);
			anios.add(ultimoAnio - 2);
		}
		
		return anios;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public List<Pais> getPaises() {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		return session.createQuery(
				"select DISTINCT new " + Pais.class.getName() + "(idPais,nombrePais) "
				+ "FROM SumarizacionMontos ORDER BY nombrePais ASC").list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public List<Autor> getAutoresLikeNombre(String nombreAutor) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		return session.createQuery(
				"select DISTINCT new " + Autor.class.getName() + "(dc.idAutor, dc.nombreAutor) "
				+ "from SumarizacionMontos dc "
				+ "WHERE dc.companyId = :companyId AND dc.nombreAutor LIKE :nombreAutor order by dc.nombreAutor asc")
				.setParameter("companyId", Configuracion.SACM_COMPANY_ID)
				.setParameter("nombreAutor", "%" + nombreAutor + "%").list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public List<RankingCancion> getCanciones(Long idPais,
			Integer anio, Integer trimestre, Long idAutor, int inicio,
			int cantidadResultados, String filtro) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ").append(RankingCancion.class.getName()).append("( ")
			.append("dc.idCancion, dc.nombreCancion, dc.idAutor, dc.nombreAutor, ")
			.append("SUM(dc.cantidadUnidades), SUM(dc.montoPercibido)) ")
			.append("FROM SumaUnidadesYMontos dc ")
			.append("WHERE dc.companyId = :companyId ");
		
		buffer.append(DaoUtils.getWhereClauseExt(trimestre, anio, idPais, filtro, idAutor));
		
		buffer.append("GROUP BY dc.idCancion, dc.nombreCancion, dc.idAutor, dc.nombreAutor ")
			.append("ORDER BY dc.nombreCancion asc");
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro, idAutor);
		
		query.setFirstResult(inicio);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

	@Transactional(value="transactionManagerExterno")
	public long getCantidadCanciones(Long idPais,
			Integer anio, Integer trimestre, Long idAutor, String filtro) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(DISTINCT dc.idCancion) FROM SumaUnidadesYMontos dc ");
		buffer.append("WHERE dc.companyId = :companyId ");
		
		buffer.append(DaoUtils.getWhereClauseExt(trimestre, anio, idPais, filtro, idAutor));
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro, idAutor);
		
		Long resultado = (Long) query.uniqueResult();
		
		return resultado != null ? resultado.longValue() : 0;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public List<Fuente> getFuentes(Long idPais) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT DISTINCT new ").append(Fuente.class.getName()).append("(");
		buffer.append("dc.idFuente,dc.nombreFuente) from SumarizacionMontos dc ");
		
		if (idPais != null) {
			buffer.append("WHERE dc.idPais = :idPais ");
		}
		
		buffer.append("ORDER BY dc.idFuente");
		
		Query query = session.createQuery(buffer.toString());
		DaoUtils.setearParametros(query, idPais, null, null, null);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public List<Derecho> getDerechosPorFuente(Fuente fuente) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		return session.createQuery(
				"SELECT DISTINCT new " + Derecho.class.getName() + "(dc.rightName) "
				+ "from SumarizacionMontos dc "
				+ "WHERE dc.idFuente = :fuente ORDER BY dc.rightName")
				.setParameter("fuente", fuente.getId()).list();
	}
	
	/**
	 * Pais y trimestre son opcionales
	 * @param pais
	 * @param trimestre
	 * @return
	 */
	@Transactional(value="transactionManagerExterno")
	public List<MontoTotal> getMontosTotalesSACMPorAnio(Pais pais, Integer trimestre) {
		return getMontosTotalesPorAnio(pais, trimestre, false);
	}
	
	/**
	 * Pais y trimestre son opcionales
	 * @param pais
	 * @param trimestre
	 * @return
	 */
	@Transactional(value="transactionManagerExterno")
	public List<MontoTotal> getMontosTotalesOtrosPorAnio(Pais pais, Integer trimestre) {
		return getMontosTotalesPorAnio(pais, trimestre, true);
	}
	

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	private List<MontoTotal> getMontosTotalesPorAnio(Pais pais, Integer trimestre, boolean excluirSACM) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ").append(MontoTotal.class.getName()).append("(")
			.append("dc.anio, SUM(dc.montoPercibido)) ")
			.append("FROM SumarizacionMontos dc ");
		
		if (excluirSACM) {
			buffer.append("WHERE dc.companyId != :companyId ");
			
		} else {
			buffer.append("WHERE dc.companyId = :companyId ");
		}
			
		buffer.append(DaoUtils.getWhereClauseExt(trimestre, null, (pais != null) ? pais.getId() : null, null))			
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
	@Transactional(value="transactionManagerExterno")
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
	@Transactional(value="transactionManagerExterno")
	public List<MontoTotal> getMontosTotalesOtrosPorTrimestre(Integer anio, Pais pais) {
		
		if (anio == null) {
			throw new IllegalArgumentException("El año debe estar seteado para esta consulta");
		}
		
		return getMontosTotalesPorTrimestre(anio, pais, true);
	}
	

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	private List<MontoTotal> getMontosTotalesPorTrimestre(Integer anio, Pais pais, boolean excluirSACM) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ").append(MontoTotal.class.getName()).append("(")
			.append(anio).append(", dc.trimestre, SUM(dc.montoPercibido)) ")
			.append("FROM SumarizacionMontos dc ");
		
		if (excluirSACM) {
			buffer.append("WHERE dc.companyId != :companyId ");
			
		} else {
			buffer.append("WHERE dc.companyId = :companyId ");
		}
			
		buffer.append(DaoUtils.getWhereClauseExt(null, anio, (pais != null) ? pais.getId() : null, null))			
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
	@Transactional(value="transactionManagerExterno")
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
	@Transactional(value="transactionManagerExterno")
	public List<MontoTotal> getMontosTotalesOtrosPorPais(Integer anio,
			Integer trimestre, Pais pais) {
		
		if (anio == null || trimestre == null) {
			throw new IllegalArgumentException(
					"El año y el trimestre deben estar seteados para esta consulta");
		}
		
		return getMontosTotalesPorPais(anio, trimestre, pais, true);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	private List<MontoTotal> getMontosTotalesPorPais(Integer anio, Integer trimestre, 
			Pais pais, boolean excluirSACM) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ").append(MontoTotal.class.getName()).append("(")
			.append(anio).append(", ")
			.append(trimestre).append(", dc.idPais, dc.nombrePais, SUM(dc.montoPercibido)) ")
			.append("FROM SumarizacionMontos dc ");
		
		if (excluirSACM) {
			buffer.append("WHERE dc.companyId != :companyId ");
			
		} else {
			buffer.append("WHERE dc.companyId = :companyId ");
		}
			
		buffer.append(DaoUtils.getWhereClauseExt(trimestre, anio, (pais != null) ? pais.getId() : null, null))			
			.append("GROUP BY dc.idPais, dc.nombrePais ");
		
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

	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public List<MontoTotalPorFuente> getTotalesPorFuente(Long idPais, Integer anio) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		
		Query query = getQueryTotalesPorFuenteSACM(session, idPais, anio);
		List<MontoPorDerecho> montosSACM = query.list();
		
		query = getQueryTotalesPorFuenteOtros(session, idPais, anio);
		List<MontoPorDerecho> montosOtros = query.list();
		
		List<Fuente> fuentes = getFuentes(idPais);
		
		return procesarTotalesPorFuente(montosSACM, montosOtros, fuentes);
			
	}

	private List<MontoTotalPorFuente> procesarTotalesPorFuente(
			List<MontoPorDerecho> montosSACM,
			List<MontoPorDerecho> montosOtros, List<Fuente> fuentes) {
		
		List<MontoTotalPorFuente> resultado = new LinkedList<>();
		
		for (Fuente fuente : fuentes) {
			
			MontoTotalPorFuente montoTotalPorFuente = new MontoTotalPorFuente();
			montoTotalPorFuente.setFuente(fuente);
			resultado.add(montoTotalPorFuente);
			
			List<Derecho> derechos = getDerechosPorFuente(fuente);
			for (Derecho derecho : derechos) {
				
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
		buffer.append("SELECT new ").append(MontoPorDerecho.class.getName()).append("(")
			.append("dc.idFuente, dc.nombreFuente, dc.rightName, dc.trimestre, SUM(dc.montoPercibido)) ")
			.append("FROM SumarizacionMontos dc ");
		
		if (excluirSACM) {
			buffer.append("WHERE dc.companyId != :companyId ");
			
		} else {
			buffer.append("WHERE dc.companyId = :companyId ");
		}
			
		buffer.append(DaoUtils.getWhereClauseExt(null, anio, idPais, null))			
			.append("GROUP BY dc.idFuente, dc.nombreFuente, dc.rightName, dc.trimestre ")
			.append("ORDER BY dc.idFuente, dc.rightName");
		
		Query query = session.createQuery(buffer.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, idPais, anio, null, null);
		
		return query;
	}
}
