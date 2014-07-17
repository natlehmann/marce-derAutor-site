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

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Ranking;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.RankingArtistasMasEjecutados;

@Repository
public class RankingArtistasMasEjecutadosDao extends EntidadExternaDao<RankingArtistasMasEjecutados> {
	
	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	public RankingArtistasMasEjecutadosDao() {
		super(RankingArtistasMasEjecutados.class);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	@Cacheable("rankingMasEjecutados")
	public List<Ranking> getAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro) {
		
		Session session = getSessionFactory().getCurrentSession();		
		
		Query query = session.createSQLQuery(
				"exec sp_unitsRanking :idPais, :anio, :trimestre, :filtro, :inicioPaginacion, :finPaginacion")
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
				
				.setParameter("idPais", idPais)
				.setParameter("anio", anio)
				.setParameter("trimestre", trimestre)
				.setParameter("filtro", filtro)
				.setParameter("inicioPaginacion", primerResultado + 1)
				.setParameter("finPaginacion", primerResultado + cantidadResultados);
		
		List<Ranking> resultado = query.list();
		
		return resultado;
	}
	
	
	@Transactional(value="transactionManagerExterno")
	@Cacheable("rankingMasEjecutadosCount")
	public long getCantidadAutoresMasEjecutados(Long idPais, Integer anio, 
			Integer trimestre, String filtro) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		Query query = session.createSQLQuery(
				"exec sp_unitsRankingCount :idPais, :anio, :trimestre, :filtro")
				.addScalar("cantidad", LongType.INSTANCE)
				.setParameter("idPais", idPais)
				.setParameter("anio", anio)
				.setParameter("trimestre", trimestre)
				.setParameter("filtro", filtro);
		
		Long resultado = (Long) query.uniqueResult();

		return resultado != null ? resultado : 0;
	}


	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactoryExterno;
	}
}
