package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.EnvioNewsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Newsletter;

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
	@Transactional
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

	@Transactional
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
	@Transactional
	public List<EnvioNewsletter> getEnviosNewsletter(Long id) {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"SELECT e FROM EnvioNewsletter e WHERE e.newsletter.id = :id ORDER BY e.fechaEnvio DESC")
				.setParameter("id", id).list();
	}

	@Transactional
	public Newsletter buscarConEnvios(Long id) {
		
		Session session = getSessionFactory().getCurrentSession();
		Newsletter newsletter = (Newsletter) session.get(Newsletter.class, id);
		
		if (newsletter.getEnvios() != null) {
			newsletter.getEnvios().size();
		}
		
		return newsletter;
	}

}
