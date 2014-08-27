package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PaisDao {

	@Autowired
	private SessionFactory sessionFactory;
	

	@Transactional(value="transactionManager")
	public String buscarNombrePais(Long idPais) {

		Session session = sessionFactory.getCurrentSession();
		return (String) session.createQuery(
				"SELECT dc.nombrePais FROM DatosCancion dc WHERE dc.idPais = :idPais GROUP BY dc.idPais")
				.setParameter("idPais", idPais).uniqueResult();
	}

}
