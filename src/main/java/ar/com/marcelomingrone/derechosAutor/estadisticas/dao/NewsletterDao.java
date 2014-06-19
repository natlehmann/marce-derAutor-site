package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.EnvioNewsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Newsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ReceptorNewsletter;

@Repository
public class NewsletterDao extends EntidadDao<Newsletter> {
	
	@Autowired
	private SessionFactory sessionFactory;

	protected NewsletterDao() {
		super(Newsletter.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<Newsletter> getTodosPaginadoFiltrado(int inicio, int cantidadResultados,
			String filtro, String campoOrdenamiento, String direccionOrdenamiento) {
		
		if (StringUtils.isEmpty(filtro)) {
			return super.getTodosPaginado(
					inicio, cantidadResultados, campoOrdenamiento, direccionOrdenamiento);
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "from Newsletter where subject like :filtro";
			
			if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
				query += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
			}
			
			return session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%")
					.setFirstResult(inicio)
					.setMaxResults(cantidadResultados).list();
			
		}
	}

	@Transactional(value="transactionManager")
	public Long getCantidadResultados(String filtro) {
		
		if (StringUtils.isEmpty(filtro)) {
			return super.getCantidadTotal();
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "select count(e) from Newsletter e where e.subject like :filtro";
			
			Long resultado = (Long) session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%").uniqueResult();
			
			return resultado != null ? resultado.longValue() : 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<EnvioNewsletter> getEnviosNewsletter(Long id) {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"SELECT e FROM EnvioNewsletter e WHERE e.newsletter.id = :id ORDER BY e.fechaEnvio DESC")
				.setParameter("id", id).list();
	}

	@Transactional(value="transactionManager")
	public Newsletter buscarConEnvios(Long id) {
		
		Session session = getSessionFactory().getCurrentSession();
		Newsletter newsletter = (Newsletter) session.get(Newsletter.class, id);
		
		if (newsletter.getEnvios() != null) {
			
			for (EnvioNewsletter envio : newsletter.getEnvios()) {
				
				envio.setCantidadReceptores(getCantidadReceptores(envio));
				envio.setCantidadReceptoresActivos(getCantidadReceptoresActivos(envio));
			}
		}
		
		return newsletter;
	}

	@Transactional(value="transactionManager")
	public Long getCantidadReceptoresActivos(EnvioNewsletter envioNewsletter) {
		
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
				"SELECT COUNT(r) FROM ReceptorNewsletter r WHERE r.envioNewsletter = :envio "
				+ "AND r.fechaApertura is not null")
				.setParameter("envio", envioNewsletter);
		
		Long resultado = (Long) query.uniqueResult();
		
		return (resultado != null) ? resultado.longValue() : 0;
	}
	
	@Transactional(value="transactionManager")
	public long getCantidadReceptores(EnvioNewsletter envioNewsletter) {
		
		return getCantidadReceptores(envioNewsletter.getId());
	}

	@Transactional(value="transactionManager")
	public long getCantidadReceptores(Long idEnvioNewsletter) {
		
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
				"SELECT COUNT(r) FROM ReceptorNewsletter r WHERE r.envioNewsletter.id = :idEnvio")
				.setParameter("idEnvio", idEnvioNewsletter);
		
		Long resultado = (Long) query.uniqueResult();
		
		return (resultado != null) ? resultado.longValue() : 0;
	}

	@Transactional(value="transactionManager")
	public EnvioNewsletter buscarEnvioNewsletter(Long id) {
		
		Session session = getSessionFactory().getCurrentSession();
		return (EnvioNewsletter) session.get(EnvioNewsletter.class, id);
	}

	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<ReceptorNewsletter> getReceptoresNewsletterPaginadoFiltrado(
			Long idEnvio, int inicio, int cantidadResultados, String filtro,
			String campoOrdenamiento, String direccionOrdenamiento) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		if (StringUtils.isEmpty(filtro)) {			
			
			String query = "from ReceptorNewsletter r WHERE r.envioNewsletter.id = :idEnvio";
			
			if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
				query += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
			}
			
			return session.createQuery(query)
					.setParameter("idEnvio", idEnvio)
					.setFirstResult(inicio).setMaxResults(cantidadResultados).list();
			
		} else {
			
			String query = "from ReceptorNewsletter r WHERE r.envioNewsletter.id = :idEnvio "
					+ "AND (r.usuario.nombreApellido like :filtro OR r.usuario.email like :filtro)";
			
			if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
				query += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
			}
			
			return session.createQuery(query)
					.setParameter("idEnvio", idEnvio)
					.setParameter("filtro", "%" + filtro + "%")
					.setFirstResult(inicio)
					.setMaxResults(cantidadResultados).list();
			
		}
	}

	@Transactional(value="transactionManager")
	public long getCantidadReceptores(Long idEnvio, String filtro) {
		
		if (StringUtils.isEmpty(filtro)) {
			return this.getCantidadReceptores(idEnvio);
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "SELECT COUNT(r) FROM ReceptorNewsletter r WHERE r.envioNewsletter.id = :idEnvio "
					+ "AND (r.usuario.nombreApellido like :filtro OR r.usuario.email like :filtro)";
			
			Long resultado = (Long) session.createQuery(query)
					.setParameter("idEnvio", idEnvio)
					.setParameter("filtro", "%" + filtro + "%").uniqueResult();
			
			return resultado != null ? resultado.longValue() : 0;
		}
	}

	@Transactional(value="transactionManager")
	public EnvioNewsletter guardarEnvio(EnvioNewsletter envio) {
		
		Session session = sessionFactory.getCurrentSession();
		envio.setId((Long) session.save(envio));
		return envio;
	}

	@Transactional(value="transactionManager")
	public ReceptorNewsletter getReceptorNewsletter(Long idEnvio, Long idUsuario) {
		
		Session session = sessionFactory.getCurrentSession();
		return (ReceptorNewsletter) session.createQuery(
				"SELECT r FROM ReceptorNewsletter r WHERE r.usuario.id = :idUsuario AND r.envioNewsletter.id = :idEnvio")
				.setParameter("idUsuario", idUsuario)
				.setParameter("idEnvio", idEnvio)
				.uniqueResult();
	}

	@Transactional(value="transactionManager")
	public void guardarReceptor(ReceptorNewsletter receptor) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(receptor);		
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<ReceptorNewsletter> getRecepcionesParaUsuario(Long id) {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("SELECT r FROM ReceptorNewsletter r WHERE r.usuario.id = :idUsuario")
				.setParameter("idUsuario", id).list();
	}

}
