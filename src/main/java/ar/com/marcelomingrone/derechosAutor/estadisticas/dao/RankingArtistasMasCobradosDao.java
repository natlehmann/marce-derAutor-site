package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class RankingArtistasMasCobradosDao {
	
	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	@Autowired
	private RankingPorAutorDao rankingPorAutorDao;
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	@Cacheable("rankingMasCobrados")
	public List<Ranking> getAutoresMasCobrados(
			Long idPais, Integer anio,
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro) {
		
		Session session = getSessionFactory().getCurrentSession();		
		
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
		
		if (!resultado.isEmpty()) {
			
			List<Long> idsAutores = new LinkedList<>();
			
			for (Ranking ranking : resultado) {				
				idsAutores.add(ranking.getAutor().getId());
			}	
			
			List<Ranking> cantidades = rankingPorAutorDao.getEjecucionesPorAutor(
					idPais, anio, trimestre, idsAutores);
			
			Map<Long, Ranking> cantidadesPorAutor = new HashMap<Long, Ranking>();
			
			for (Ranking ranking : cantidades) {
				cantidadesPorAutor.put(ranking.getAutor().getId(), ranking);
			}
			
			for (Ranking unResultado : resultado) {
				
				Ranking cantidad = cantidadesPorAutor.get(unResultado.getAutor().getId());
				if (cantidad != null) {
					
					unResultado.setCantidadUnidades(cantidad.getCantidadUnidades());
				}
			}
		}
		
		return resultado;
	}

	
	@Transactional(value="transactionManagerExterno")
	@Cacheable("rankingMasCobradosCount")
	public long getCantidadAutoresMasCobrados(Long idPais, Integer anio, 
			Integer trimestre, String filtro) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		Query query = session.createSQLQuery(
				"exec sp_amountsRankingCount :companyId, :idPais, :anio, :trimestre, :filtro")
				.addScalar("cantidad", LongType.INSTANCE)
				.setParameter("companyId", Configuracion.SACM_COMPANY_ID)
				.setParameter("idPais", idPais)
				.setParameter("anio", anio)
				.setParameter("trimestre", trimestre)
				.setParameter("filtro", filtro);
		
		Long resultado = (Long) query.uniqueResult();

		return resultado != null ? resultado : 0;
	}


	protected SessionFactory getSessionFactory() {
		return sessionFactoryExterno;
	}
}
