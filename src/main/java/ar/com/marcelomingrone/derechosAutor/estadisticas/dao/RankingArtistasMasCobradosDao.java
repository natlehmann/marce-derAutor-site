package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Configuracion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Ranking;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.RankingArtistasMasCobrados;

@Repository
public class RankingArtistasMasCobradosDao extends RankingDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public RankingArtistasMasCobradosDao() {
		super(RankingArtistasMasCobrados.class);
	}
	
	
	@Transactional(value="transactionManager")
	public void borrarTodo() {
		
		Session session = sessionFactory.getCurrentSession();		
		session.createSQLQuery("drop table RankingArtistasMasCobrados").executeUpdate();
		
		session.createSQLQuery("create table RankingArtistasMasCobrados("
				+ "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "ranking BIGINT,pais_id BIGINT,trimestre int,"
				+ "anio int,autor_id BIGINT NULL,nombreAutor varchar(255) null,"
				+ "cantidadUnidades BIGINT default 0,montoPercibido DECIMAL(10,2) default 0"
				+ ")ENGINE=InnoDB;").executeUpdate();
	}



	@Transactional(value="transactionManager")
	public void importarDatosCanciones(Long idPais, Integer anio, Integer trimestre) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("INSERT INTO RankingArtistasMasCobrados(")
			.append("ranking, pais_id, trimestre, anio, autor_id, nombreAutor, cantidadUnidades, montoPercibido) ")
			.append("SELECT (@rank\\:=@rank+1) as ranking, pais_id, trimestre, anio, ")
			.append("autor_id, nombreAutor, cantidadUnidades, montoPercibido ")
			.append("FROM ( SELECT ")
			
			.append(DaoUtils.getSelectClause(trimestre, anio, idPais))
			
			.append("dc.autor_id, dc.nombreAutor, SUM(dc.cantidadUnidades) as cantidadUnidades, ")
			.append("SUM(dc.montoPercibido) as montoPercibido ")
			.append("FROM DatosCancion dc ")
			.append("WHERE (dc.companyId = :companyId OR dc.companyId is null) ")
			
			.append(DaoUtils.getWhereClauseSQL(trimestre, anio, idPais))
			
			.append("GROUP BY dc.autor_id ")
			.append("ORDER BY montoPercibido desc, dc.autor_id asc ) as tmp ")
			.append("CROSS JOIN (SELECT @rank\\:=0) b");

		Query query = session.createSQLQuery(queryStr.toString());
		query.setParameter("companyId", Configuracion.SACM_COMPANY_ID);
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, null);
		
		query.executeUpdate();
		
	}
	
	
	@Transactional(value="transactionManager")
	@Cacheable("rankingMasCobrados")
	public List<Ranking> getAutoresMasCobrados(Long idPais, Integer anio,
			Integer trimestre, int primerResultado, int cantidadResultados, String filtro) {
		
		return super.getAutoresMasCobradosOEjecutados(
				idPais, anio, trimestre, primerResultado, cantidadResultados, 
				filtro, "RankingArtistasMasCobrados");
	}

	
	@Transactional(value="transactionManager")
	@Cacheable("rankingMasCobradosCount")
	public long getCantidadAutoresMasCobrados(Long idPais, Integer anio, 
			Integer trimestre, String filtro) {
		
		return getCantidadAutoresMasEjecutados(
				idPais, anio, trimestre, filtro, "RankingArtistasMasCobrados");
	}


	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
//	@Autowired
//	private SessionFactory sessionFactoryExterno;
//	
//	@Autowired
//	private RankingPorAutorDao rankingPorAutorDao;
//	
//	
//	@SuppressWarnings("unchecked")
//	@Transactional(value="transactionManagerExterno")
//	@Cacheable("rankingMasCobrados")
//	public List<Ranking> getAutoresMasCobrados(
//			Long idPais, Integer anio,
//			Integer trimestre, int primerResultado, int cantidadResultados, String filtro) {
//		
//		Session session = getSessionFactory().getCurrentSession();		
//		
//		Query query = session.createSQLQuery(
//				"exec sp_amountsRanking :companyId, :idPais, :anio, :trimestre, :filtro, :inicioPaginacion, :finPaginacion")
//				.addScalar("trimestre")
//				.addScalar("anio")
//				.addScalar("idPais", LongType.INSTANCE)
//				.addScalar("id", LongType.INSTANCE)
//				.addScalar("ranking", LongType.INSTANCE)
//				.addScalar("idAutor", LongType.INSTANCE)
//				.addScalar("nombreAutor")
//				.addScalar("cantidadUnidades", LongType.INSTANCE)
//				.addScalar("montoPercibido", DoubleType.INSTANCE)
//				.setResultTransformer(Transformers.aliasToBean(Ranking.class))
//				
//				.setParameter("companyId", Configuracion.SACM_COMPANY_ID)
//				.setParameter("idPais", idPais)
//				.setParameter("anio", anio)
//				.setParameter("trimestre", trimestre)
//				.setParameter("filtro", filtro)
//				.setParameter("inicioPaginacion", primerResultado + 1)
//				.setParameter("finPaginacion", primerResultado + cantidadResultados);
//		
//		List<Ranking> resultado = query.list();
//		
//		if (!resultado.isEmpty()) {
//			
//			List<Long> idsAutores = new LinkedList<>();
//			
//			for (Ranking ranking : resultado) {				
//				idsAutores.add(ranking.getAutor().getId());
//			}	
//			
//			List<Ranking> cantidades = rankingPorAutorDao.getEjecucionesPorAutor(
//					idPais, anio, trimestre, idsAutores);
//			
//			Map<Long, Ranking> cantidadesPorAutor = new HashMap<Long, Ranking>();
//			
//			for (Ranking ranking : cantidades) {
//				cantidadesPorAutor.put(ranking.getAutor().getId(), ranking);
//			}
//			
//			for (Ranking unResultado : resultado) {
//				
//				Ranking cantidad = cantidadesPorAutor.get(unResultado.getAutor().getId());
//				if (cantidad != null) {
//					
//					unResultado.setCantidadUnidades(cantidad.getCantidadUnidades());
//				}
//			}
//		}
//		
//		return resultado;
//	}
//
//	
//	@Transactional(value="transactionManagerExterno")
//	@Cacheable("rankingMasCobradosCount")
//	public long getCantidadAutoresMasCobrados(Long idPais, Integer anio, 
//			Integer trimestre, String filtro) {
//		
//		Session session = getSessionFactory().getCurrentSession();
//		
//		Query query = session.createSQLQuery(
//				"exec sp_amountsRankingCount :companyId, :idPais, :anio, :trimestre, :filtro")
//				.addScalar("cantidad", LongType.INSTANCE)
//				.setParameter("companyId", Configuracion.SACM_COMPANY_ID)
//				.setParameter("idPais", idPais)
//				.setParameter("anio", anio)
//				.setParameter("trimestre", trimestre)
//				.setParameter("filtro", filtro);
//		
//		Long resultado = (Long) query.uniqueResult();
//
//		return resultado != null ? resultado : 0;
//	}
//
//
//	protected SessionFactory getSessionFactory() {
//		return sessionFactoryExterno;
//	}
}
