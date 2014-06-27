package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Configuracion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Ranking;

public abstract class RankingDao extends EntidadDao<Ranking> {
	
	@SuppressWarnings("rawtypes")
	public RankingDao(Class clazz) {
		super(clazz);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	protected List<Ranking> getAutoresMasCobradosOEjecutados(
			Long idPais, Integer anio,
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro,
			String criterio) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
//		buffer.append("SELECT new ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Ranking(");
		buffer.append("SELECT ");
		
		buffer.append(DaoUtils.getSelectClauseOrNull(trimestre, anio, idPais));
		
		buffer.append("ROW_NUMBER() OVER(ORDER BY SUM(").append(criterio).append(") desc) AS ranking, ");
//		buffer.append("1 as ranking, ");
		buffer.append("idAutor, nombreAutor, ");
		buffer.append("SUM(cantidadUnidades) as cantidadUnidades, ");
		buffer.append("SUM(montoPercibido) as montoPercibido ");
//		buffer.append("SUM(montoPercibido) as montoPercibido) ");
		buffer.append("FROM VIEW_UnitsAndAmounts ");
		
		buffer.append("WHERE companyId = :companyId ");
		
		buffer.append(DaoUtils.getWhereClauseExt2(trimestre, anio, idPais, filtro));
		
		buffer.append("GROUP BY idAutor, nombreAutor ");		
		buffer.append(DaoUtils.getGroupByClause(trimestre, anio, idPais));
		
		buffer.append("ORDER BY ").append(criterio).append(" desc, nombreAutor asc");

		Query query = session.createSQLQuery(buffer.toString())
				.addScalar("trimestre")
				.addScalar("anio")
				.addScalar("idPais", LongType.INSTANCE)
				.addScalar("ranking", LongType.INSTANCE)
				.addScalar("idAutor", LongType.INSTANCE)
				.addScalar("nombreAutor")
				.addScalar("cantidadUnidades", LongType.INSTANCE)
				.addScalar("montoPercibido", DoubleType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(Ranking.class));
		
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
		
		query.setFirstResult(primerResultado);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}

	
	@Transactional(value="transactionManagerExterno")
	protected long getCantidadAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, String filtro, String nombreEntidad) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(1) from ( ");
		buffer.append("select 1 as item from VIEW_UnitsAndAmounts ");
		
		buffer.append("WHERE companyId = :companyId ");
		
		buffer.append(DaoUtils.getWhereClauseExt2(trimestre, anio, idPais, filtro));
		
		buffer.append("GROUP BY idAutor ");	
		buffer.append(DaoUtils.getGroupByClause(trimestre, anio, idPais));
		
		buffer.append(") as tmp");
		
		Query query = session.createSQLQuery(buffer.toString());
		
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);

		Integer resultado = (Integer) query.uniqueResult();
		
		return resultado != null ? resultado : 0;
	}

}
