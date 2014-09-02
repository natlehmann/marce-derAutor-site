package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.DerechoExterno;

@Repository
public class DerechoExternoDao {

	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public List<DerechoExterno> getTodos() {
		
		Session session = sessionFactoryExterno.getCurrentSession();
		return session.createQuery("Select d from DerechoExterno d order by d.idDerechoPadre").list();
		
	}

}
