package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Configuracion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Rol;

@Repository
public class RolDao extends EntidadDao<Rol> {
	
	@Autowired
	private SessionFactory sessionFactory;

	public RolDao() {
		super(Rol.class, "nombre");
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Transactional
	public Rol buscarPorNombre(String nombre) {
		
		Session session = sessionFactory.getCurrentSession();
		return (Rol) session.createQuery("SELECT r FROM Rol r WHERE r.nombre = :nombre")
				.setParameter("nombre", nombre)
				.uniqueResult();
	}
	
	@Transactional
	public Rol getRolReceptoresNewsletter() {
		return buscarPorNombre(Configuracion.ROL_NEWSLETTER);
	}

}
