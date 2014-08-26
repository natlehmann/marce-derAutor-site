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
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Ranking;

@Repository
public class RankingPorAutorDao {
	
	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	@Cacheable("ejecucionesPorAutor")
	public List<Ranking> getEjecucionesPorAutor(Long idPais, Integer anio, 
			Integer trimestre, List<Long> idsAutores) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		
		String ids = idsAutores.toString().replace("[", "").replace("]", "");
		
		Query query = session.createSQLQuery(
				"exec sp_unitsRankingForAuthors :idPais, :anio, :trimestre, :idsAutores")
				.addScalar("idAutor", LongType.INSTANCE)
				.addScalar("cantidadUnidades", LongType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(Ranking.class))
				
				.setParameter("idPais", idPais)
				.setParameter("anio", anio)
				.setParameter("trimestre", trimestre)
				.setParameter("idsAutores", ids);
		
		List<Ranking> resultado = query.list();
		
		return resultado;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	@Cacheable("montosPorAutor")
	public List<Ranking> getMontosPorAutor(Long idPais, Integer anio, 
			Integer trimestre, List<Long> idsAutores) {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		
		String ids = idsAutores.toString().replace("[", "").replace("]", "");
		
		Query query = session.createSQLQuery(
				"exec sp_amountsRankingForAuthors :companyId, :idPais, :anio, :trimestre, :idsAutores")
				.addScalar("idAutor", LongType.INSTANCE)
				.addScalar("montoPercibido", DoubleType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(Ranking.class))
				
				.setParameter("companyId", Configuracion.SACM_COMPANY_ID)
				.setParameter("idPais", idPais)
				.setParameter("anio", anio)
				.setParameter("trimestre", trimestre)
				.setParameter("idsAutores", ids);
		
		List<Ranking> resultado = query.list();
		
		return resultado;
	}
	
}
