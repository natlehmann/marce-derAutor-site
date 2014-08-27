package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Configuracion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Autor;

@Repository
public class AutorDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Map<Long, Autor> autores;
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	private void init() {
		
		this.autores = new LinkedHashMap<>();
		
		Session session = sessionFactory.getCurrentSession();
		
		List<Autor> resultado = session.createQuery(
				"select DISTINCT new " + Autor.class.getName() + "(idAutor,nombreAutor) "
				+ "FROM DatosCancion ORDER BY nombreAutor ASC").list();

		for (Autor autor : resultado) {
			this.autores.put(autor.getId(), autor);
		}
	}

	
	@Transactional(value="transactionManager")
	public Autor buscar(Long id) {
		
		if (this.autores == null) {
			this.init();
		}
		
		Autor autor = this.autores.get(id);
		return autor;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	@Cacheable("autores")
	public List<Autor> getAutoresLikeNombre(String nombreAutor) {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"select DISTINCT new " + Autor.class.getName() + "(idAutor, nombreAutor) from DatosCancion dc "
				+ "WHERE (dc.companyId = :companyId OR dc.companyId is null) AND dc.nombreAutor LIKE :nombreAutor order by dc.nombreAutor asc")
				.setParameter("companyId", Configuracion.SACM_COMPANY_ID)
				.setParameter("nombreAutor", "%" + nombreAutor + "%").list();

	}



}
