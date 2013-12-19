package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Usuario;

@Repository
public class UsuarioDao extends EntidadDao<Usuario> {
	
	private static final String ROL_NEWSLETTER = "newsletter";
	@Autowired
	private SessionFactory sessionFactory;

	protected UsuarioDao() {
		super(Usuario.class, "nombre");
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Usuario> getReceptoresNewsletter() {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("SELECT r.usuarios FROM Rol r WHERE r.nombre = :rolNewsletter")
				.setParameter("rolNewsletter", ROL_NEWSLETTER).list();
	}

}
