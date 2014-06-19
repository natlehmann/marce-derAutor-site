package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Ranking;

public abstract class RankingDao extends EntidadDao<Ranking> {
	
	@SuppressWarnings("rawtypes")
	public RankingDao(Class clazz) {
		super(clazz);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	protected List<Ranking> getAutoresMasCobradosOEjecutados(
			Long idPais, Integer anio,
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro,
			String nombreTabla) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT dc FROM " + nombreTabla + " dc ");
		
		buffer.append(DaoUtils.getWhereClauseOrNull(trimestre, anio, idPais, filtro));
		
		buffer.append("ORDER BY dc.ranking");
		
		Query query = session.createQuery(buffer.toString());
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
		
		query.setFirstResult(primerResultado);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

	
	@Transactional(value="transactionManager")
	protected long getCantidadAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, String filtro, String nombreEntidad) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT COUNT(DISTINCT dc.autor.id) FROM " + nombreEntidad + " dc ");
		
		buffer.append(DaoUtils.getWhereClauseOrNull(trimestre, anio, idPais, filtro));
		
		Query query = session.createQuery(buffer.toString());
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
		
		Long resultado = (Long) query.uniqueResult();
		
		return resultado != null ? resultado : 0;
	}

}
