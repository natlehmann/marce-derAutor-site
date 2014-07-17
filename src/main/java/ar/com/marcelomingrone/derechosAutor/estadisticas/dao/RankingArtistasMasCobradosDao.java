package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Configuracion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Ranking;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.RankingArtistasMasCobrados;

@Repository
public class RankingArtistasMasCobradosDao extends EntidadExternaDao<RankingArtistasMasCobrados> {
	
	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	public RankingArtistasMasCobradosDao() {
		super(RankingArtistasMasCobrados.class);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	@Cacheable("rankingMasCobrados")
	public List<Ranking> getAutoresMasCobrados(
			Long idPais, Integer anio,
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro) {
		
		Session session = getSessionFactory().getCurrentSession();
		
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("SELECT ");
//		
//		buffer.append(DaoUtils.getSelectClauseOrNull(trimestre, anio, idPais));
//		
//		buffer.append("ROW_NUMBER() OVER(ORDER BY SUM(montoPercibido) desc) AS ranking, ");
//		buffer.append("idAutor, nombreAutor, ");
//		buffer.append("0 as cantidadUnidades, ");//////////////////////////////////////////////////
//		buffer.append("SUM(montoPercibido) as montoPercibido ");
//		buffer.append("FROM VIEW_MoneyAmounts ");
//		
//		buffer.append("WHERE companyId = :companyId ");
//		
//		buffer.append(DaoUtils.getWhereClauseExt2(trimestre, anio, idPais, filtro));
//		
//		buffer.append("GROUP BY idAutor, nombreAutor ");		
//		buffer.append(DaoUtils.getGroupByClause(trimestre, anio, idPais));
//		
//		buffer.append("ORDER BY montoPercibido desc, nombreAutor asc");
//
//		Query query = session.createSQLQuery(buffer.toString())
//				.addScalar("trimestre")
//				.addScalar("anio")
//				.addScalar("idPais", LongType.INSTANCE)
//				.addScalar("ranking", LongType.INSTANCE)
//				.addScalar("idAutor", LongType.INSTANCE)
//				.addScalar("nombreAutor")
//				.addScalar("cantidadUnidades", LongType.INSTANCE)
//				.addScalar("montoPercibido", DoubleType.INSTANCE)
//				.setResultTransformer(Transformers.aliasToBean(Ranking.class));
//		
//		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
//		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
//		
//		query.setFirstResult(primerResultado);
//		query.setMaxResults(cantidadResultados);
//		
//		return query.list();
		
		
		Query query = session.createSQLQuery(
				"exec sp_amountsRanking :companyId, :idPais, :anio, :trimestre, :filtro, :inicioPaginacion, :finPaginacion")
				.addScalar("trimestre")
				.addScalar("anio")
				.addScalar("idPais", LongType.INSTANCE)
				.addScalar("id", LongType.INSTANCE)
				.addScalar("ranking", LongType.INSTANCE)
				.addScalar("idAutor", LongType.INSTANCE)
				.addScalar("nombreAutor")
				.addScalar("cantidadUnidades", LongType.INSTANCE)
				.addScalar("montoPercibido", DoubleType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(Ranking.class))
				
				.setParameter("companyId", Configuracion.SACM_COMPANY_ID)
				.setParameter("idPais", idPais)
				.setParameter("anio", anio)
				.setParameter("trimestre", trimestre)
				.setParameter("filtro", filtro)
				.setParameter("inicioPaginacion", primerResultado + 1)
				.setParameter("finPaginacion", primerResultado + cantidadResultados);
		
		List<Ranking> resultado = query.list();
		
		System.out.println("------------------------------------------------------- " + resultado);
		
		return resultado;
	}

	
	@Transactional(value="transactionManagerExterno")
	@Cacheable("rankingMasCobradosCount")
	public long getCantidadAutoresMasCobrados(Long idPais, Integer anio, 
			Integer trimestre, String filtro) {
		
		Session session = getSessionFactory().getCurrentSession();
		
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("select count(1) from ( ");
//		buffer.append("select 1 as item from VIEW_MoneyAmounts ");
//		
//		buffer.append("WHERE companyId = :companyId ");
//		
//		buffer.append(DaoUtils.getWhereClauseExt2(trimestre, anio, idPais, filtro));
//		
//		buffer.append("GROUP BY idAutor ");	
//		buffer.append(DaoUtils.getGroupByClause(trimestre, anio, idPais));
//		
//		buffer.append(") as tmp");
//		
//		Query query = session.createSQLQuery(buffer.toString());
//		
//		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
//		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
//
//		Integer resultado = (Integer) query.uniqueResult();
//		
//		return resultado != null ? resultado : 0;
		
		
		
		Query query = session.createSQLQuery(
				"exec sp_amountsRankingCount :companyId, :idPais, :anio, :trimestre, :filtro")
				.addScalar("cantidad", LongType.INSTANCE)
				.setParameter("companyId", Configuracion.SACM_COMPANY_ID)
				.setParameter("idPais", idPais)
				.setParameter("anio", anio)
				.setParameter("trimestre", trimestre)
				.setParameter("filtro", filtro);
		
		Long resultado = (Long) query.uniqueResult();
System.out.println("ESTA ES ALA CANTIDAD ---------------------------------------- " + resultado);		
		return resultado != null ? resultado : 0;
	}


	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactoryExterno;
	}
}
