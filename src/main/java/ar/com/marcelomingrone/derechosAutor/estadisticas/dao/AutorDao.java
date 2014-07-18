package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Autor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Autor.ComparadorPorNombre;

@Repository
public class AutorDao extends EntidadExternaDao<Autor> {

	@Autowired
	private SessionFactory sessionFactoryExterno;
	
	private Map<Long, Autor> autores;
	
	public AutorDao() {
		super(Autor.class);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManagerExterno")
	private void init() {
		
		this.autores = new LinkedHashMap<>();
		
		Session session = sessionFactoryExterno.getCurrentSession();
		
		List<Autor> resultado = session.createQuery(
				"select DISTINCT new " + Autor.class.getName() + "(idAutor,nombreAutor) "
				+ "FROM SumarizacionMontos").list();

		
		resultado.addAll( session.createQuery(
				"select DISTINCT new " + Autor.class.getName() + "(idAutor,nombreAutor) "
				+ "FROM SumarizacionUnidades").list() );
		
		Collections.sort(resultado, new ComparadorPorNombre());

		for (Autor autor : resultado) {
			this.autores.put(autor.getId(), autor);
		}
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactoryExterno;
	}
	
	@Override
	@Transactional(value="transactionManagerExterno")
	public Autor buscar(Long id) {
		
		if (this.autores == null) {
			this.init();
		}
		
		Autor autor = this.autores.get(id);
		if (autor == null) {
			
			autor = super.buscar(id);
			if (autor != null) {
				this.autores.put(id, autor);
			}
		}
		
		return autor;
	}
	
	
	@Transactional(value="transactionManagerExterno")
	@Cacheable("autores")
	public List<Autor> getAutoresLikeNombre(String nombreAutor) {
		
		if (this.autores == null) {
			this.init();
		}
		
		List<Autor> autoresFiltrados = new LinkedList<>();
		
		for (Autor autor : this.autores.values()) {
			if (autor.getNombre().toUpperCase().contains(nombreAutor.toUpperCase())) {
				autoresFiltrados.add(autor);
			}
		}
		
		return autoresFiltrados;

	}



}
