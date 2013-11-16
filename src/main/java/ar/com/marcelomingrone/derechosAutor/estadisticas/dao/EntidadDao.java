package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public abstract class EntidadDao<T> {
	
	@SuppressWarnings("rawtypes")
	private Class claseEntidad;
	
	private String nombreEntidad;
	
	private String criterioOrdenamiento;
	
	@SuppressWarnings("rawtypes")
	protected EntidadDao(Class entidad) {
		this.claseEntidad = entidad;
		this.nombreEntidad = this.claseEntidad.getName();
	}
	
	@SuppressWarnings("rawtypes")
	protected EntidadDao(Class entidad, String criterioOrdenamiento) {
		this(entidad);
		this.criterioOrdenamiento = criterioOrdenamiento;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List<T> getTodos() {
		
		Session session = getSessionFactory().getCurrentSession();
		
		String query = "from " + this.nombreEntidad;
		if (this.criterioOrdenamiento != null) {
			query += " order by " + this.criterioOrdenamiento;
		}
		
		List resultado = session.createQuery(query).list();
		return resultado;
	}
	
	@Transactional
	public T guardar(T entidad) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entidad);
		
		return entidad;
	}
	
	@Transactional
	public void borrarTodo() {
		
		Session session = getSessionFactory().getCurrentSession();		
		session.createSQLQuery("delete from " + this.nombreEntidad).executeUpdate();		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public T buscar(Long id) {
		Session session = getSessionFactory().getCurrentSession();
		return (T) session.get(this.claseEntidad, id);
	}
	
	
	protected abstract SessionFactory getSessionFactory();

}
