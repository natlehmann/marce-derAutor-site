package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.RankingArtistasMasEjecutados;

@Repository
public class RankingArtistasMasEjecutadosDao extends EntidadDao<RankingArtistasMasEjecutados> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public RankingArtistasMasEjecutadosDao() {
		super(RankingArtistasMasEjecutados.class);
	}
	
	
	@Transactional
	public void borrarTodo() {
		
		Session session = sessionFactory.getCurrentSession();		
		session.createSQLQuery("drop table RankingArtistasMasEjecutados").executeUpdate();
		
		session.createSQLQuery("create table RankingArtistasMasEjecutados("
				+ "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "ranking BIGINT,pais_id BIGINT,trimestre int,"
				+ "anio int,autor_id BIGINT NULL,"
				+ "cantidadUnidades BIGINT default 0,montoPercibido DECIMAL(10,2) default 0,"
				+ "FOREIGN KEY (pais_id) REFERENCES Pais(id),"
				+ "FOREIGN KEY (autor_id) REFERENCES Autor(id))ENGINE=InnoDB;").executeUpdate();
	}



	@Transactional
	public void importarDatosCanciones(Long idPais, Integer anio, Integer trimestre) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("INSERT INTO RankingArtistasMasEjecutados(")
			.append("ranking, pais_id, trimestre, anio, autor_id, cantidadUnidades, montoPercibido) ")
			.append("SELECT (@rank\\:=@rank+1) as ranking, pais_id, trimestre, anio, ")
			.append("autor_id, cantidadUnidades, montoPercibido ")
			.append("FROM ( SELECT ")
			
			.append(getSelectClause(trimestre, anio, idPais))
			
			.append("dc.autor_id, SUM(dc.cantidadUnidades) as cantidadUnidades, ")
			.append("SUM(dc.montoPercibido) as montoPercibido ")
			.append("FROM DatosCancion dc ")
			
			.append(DaoUtils.getWhereClauseSQL(trimestre, anio, idPais))
			
			.append("GROUP BY dc.autor_id ")
			.append("ORDER BY cantidadUnidades desc, dc.autor_id asc ) as tmp ")
			.append("CROSS JOIN (SELECT @rank\\:=0) b");

		Query query = session.createSQLQuery(queryStr.toString());
		
		DaoUtils.setearParametros(query, idPais, anio, trimestre, null);
		
		query.executeUpdate();
		
	}

	private String getSelectClause(Integer trimestre, Integer anio, Long idPais) {
		
		String valorTrimestre = StringUtils.isEmpty(trimestre) ? "null" : String.valueOf(trimestre);
		String valorAnio = StringUtils.isEmpty(anio) ? "null" : String.valueOf(anio);
		String valorPais = StringUtils.isEmpty(idPais) ? "null" : String.valueOf(idPais);
		
		return valorPais + " as pais_id, " + valorTrimestre + " as trimestre, " + valorAnio + " as anio, ";
	}


	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
