package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Configuracion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Usuario;

@Repository
public class UsuarioDao extends EntidadDao<Usuario> {
	
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
		return session.createQuery(
				"SELECT u FROM Usuario u JOIN u.roles r WHERE u.fechaBaja is null AND r.nombre = :rolNewsletter")
				.setParameter("rolNewsletter", Configuracion.ROL_NEWSLETTER).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Usuario> getTodosPaginadoFiltrado(int inicio, int cantidadResultados, String filtro,
			String campoOrdenamiento, String direccionOrdenamiento) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		if (StringUtils.isEmpty(filtro)) {
			
			String query = "SELECT u from Usuario u join u.roles r where r.nombre = :rolNewsletter";
			
			if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
				query += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
			}
			
			return session.createQuery(query)
					.setParameter("rolNewsletter", Configuracion.ROL_NEWSLETTER)
					.setFirstResult(inicio).setMaxResults(cantidadResultados).list();
			
		} else {
			
			String query = "SELECT u from Usuario u join u.roles r where "
					+ "(u.nombreApellido like :filtro OR u.email like :filtro) "
					+ "AND r.nombre = :rolNewsletter";
			
			if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
				query += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
			}
			
			return session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%")
					.setParameter("rolNewsletter", Configuracion.ROL_NEWSLETTER)
					.setFirstResult(inicio)
					.setMaxResults(cantidadResultados).list();
			
		}
	}

	@Transactional
	public long getCantidadResultados(String filtro) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		if (StringUtils.isEmpty(filtro)) {
			
			String query = "select count(e) from Usuario e join e.roles r WHERE r.nombre = :rolNewsletter";
			
			Long resultado = (Long) session.createQuery(query)
					.setParameter("rolNewsletter", Configuracion.ROL_NEWSLETTER)
					.uniqueResult();
			
			return resultado != null ? resultado.longValue() : 0;
			
		} else {
			
			String query = "select count(e) from Usuario e join e.roles r "
					+ "where (e.nombreApellido like :filtro OR e.email like :filtro) "
					+ "AND r.nombre = :rolNewsletter";
			
			Long resultado = (Long) session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%")
					.setParameter("rolNewsletter", Configuracion.ROL_NEWSLETTER)
					.uniqueResult();
			
			return resultado != null ? resultado.longValue() : 0;
		}
	}

}
