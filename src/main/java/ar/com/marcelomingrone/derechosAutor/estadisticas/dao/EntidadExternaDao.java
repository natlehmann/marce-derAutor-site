package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public abstract class EntidadExternaDao<T> {
	
	@SuppressWarnings("rawtypes")
	private Class claseEntidad;
	
	private String nombreEntidad;
	
	private String criterioOrdenamiento;
	
	@SuppressWarnings("rawtypes")
	protected EntidadExternaDao(Class entidad) {
		this.claseEntidad = entidad;
		this.nombreEntidad = this.claseEntidad.getName();
	}
	
	@SuppressWarnings("rawtypes")
	protected EntidadExternaDao(Class entidad, String criterioOrdenamiento) {
		this(entidad);
		this.criterioOrdenamiento = criterioOrdenamiento;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(value="transactionManagerExterno")
	public List<T> getTodos() {
		
		Session session = getSessionFactory().getCurrentSession();
		
		String query = "from " + this.nombreEntidad;
		if (this.criterioOrdenamiento != null) {
			query += " order by " + this.criterioOrdenamiento;
		}
		
		List resultado = session.createQuery(query).list();
		return resultado;
	}
	
	@Transactional(value="transactionManagerExterno")
	public T guardar(T entidad) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entidad);
		
		return entidad;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public T buscar(Long id) {
		Session session = getSessionFactory().getCurrentSession();
		return (T) session.get(this.claseEntidad, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	public List<T> getTodosPaginado(int inicio,
			int cantidadResultados, String campoOrdenamiento,
			String direccionOrdenamiento) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		String query = "from " + this.nombreEntidad;
		
		if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
			query += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
		}
		
		return session.createQuery(query)
				.setFirstResult(inicio).setMaxResults(cantidadResultados).list();
	}

	@Transactional(value="transactionManagerExterno")
	public long getCantidadTotal() {
		
		Session session = getSessionFactory().getCurrentSession();
		
		String query = "select count(e) from " + this.nombreEntidad + " e";
		
		Long resultado = (Long) session.createQuery(query).uniqueResult();
		
		return resultado != null ? resultado.longValue() : 0;
	}
	
	protected abstract SessionFactory getSessionFactory();
}
