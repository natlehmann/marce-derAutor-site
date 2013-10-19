package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.UnidadesVendidasPorAutor;

@Repository
public class UnidadesVendidasPorAutorDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public UnidadesVendidasPorAutor guardar(UnidadesVendidasPorAutor unidades) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(unidades);
		
		return unidades;
	}
	
	@Transactional
	public void guardar(List<UnidadesVendidasPorAutor> unidades) {
		Session session = sessionFactory.getCurrentSession();
		
		for (UnidadesVendidasPorAutor unidad : unidades) {
			session.saveOrUpdate(unidad);
		}
	}

}
