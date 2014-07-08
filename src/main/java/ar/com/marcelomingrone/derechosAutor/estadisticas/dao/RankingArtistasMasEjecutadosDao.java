package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Ranking;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.RankingArtistasMasEjecutados;

@Repository
public class RankingArtistasMasEjecutadosDao extends EntidadDao<RankingArtistasMasEjecutados> {
	
	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	public RankingArtistasMasEjecutadosDao() {
		super(RankingArtistasMasEjecutados.class);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public List<Ranking> getAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro) {
		
		
		Session session = getSessionFactory().getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ");
		
		buffer.append(DaoUtils.getSelectClauseOrNull(trimestre, anio, idPais));
		
		buffer.append("ROW_NUMBER() OVER(ORDER BY SUM(cantidadUnidades) desc) AS ranking, ");
		buffer.append("idAutor, nombreAutor, ");
		buffer.append("SUM(cantidadUnidades) as cantidadUnidades, ");
		buffer.append("0 as montoPercibido "); ////////////////////////////////////////////
		buffer.append("FROM VIEW_Units ");
		
		buffer.append("WHERE 1 = 1 ");
		
		buffer.append(DaoUtils.getWhereClauseExt2(trimestre, anio, idPais, filtro));
		
		buffer.append("GROUP BY idAutor, nombreAutor ");		
		buffer.append(DaoUtils.getGroupByClause(trimestre, anio, idPais));
		
		buffer.append("ORDER BY cantidadUnidades desc, nombreAutor asc");

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
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);
		
		query.setFirstResult(primerResultado);
		query.setMaxResults(cantidadResultados);
		
		return query.list();
	}
	
	
	@Transactional(value="transactionManagerExterno")
	public long getCantidadAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, String filtro) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(1) from ( ");
		buffer.append("select 1 as item from VIEW_Units ");
		
		buffer.append("WHERE 1 = 1 ");
		
		buffer.append(DaoUtils.getWhereClauseExt2(trimestre, anio, idPais, filtro));
		
		buffer.append("GROUP BY idAutor ");	
		buffer.append(DaoUtils.getGroupByClause(trimestre, anio, idPais));
		
		buffer.append(") as tmp");
		
		Query query = session.createSQLQuery(buffer.toString());
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, filtro);

		Integer resultado = (Integer) query.uniqueResult();
		
		return resultado != null ? resultado : 0;
	}


	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactoryExterno;
	}
}
