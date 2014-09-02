package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DerechoExternoReplica;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotalPorDerecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.DerechoExterno;

@Repository
public class DerechoExternoReplicaDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DerechoExternoDao derechoExternoDao;
	
	private List<DerechoExterno> derechos;
	
	private List<DerechoExternoReplica> derechosReplicados;
	
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	private void init() {
		
		Session session = sessionFactory.getCurrentSession();
		this.derechos = session.createQuery(
				"SELECT DISTINCT new " + DerechoExterno.class.getName() 
				+ "(dc.nombreDerechoExterno, dc.idDerechoExterno, dc.idDerechoPadre) "
				+ "from DatosCancion dc ORDER BY dc.nombreDerechoExterno").list();
	}
	
	@Transactional(value="transactionManager")
	public List<DerechoExterno> getDerechosEnUso() {
		
		if (this.derechos == null) {
			this.init();
		}
		
		return this.derechos;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<DerechoExterno> getTodosFiltrado(String filtro) {
		
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(
				"SELECT DISTINCT new " + DerechoExterno.class.getName() + "(dc.nombreDerechoExterno) "
				+ "from DatosCancion dc WHERE dc.nombreDerechoExterno LIKE :filtro "
				+ "ORDER BY dc.nombreDerechoExterno")
				.setParameter("filtro", "%" + filtro + "%")
				.list();
	}

	@Transactional(value="transactionManager")
	public DerechoExterno buscar(String nombre) {
		
		if (this.derechos == null) {
			this.init();
		}
		
		DerechoExterno derecho = null;
		Iterator<DerechoExterno> it = this.derechos.iterator();
		
		while(it.hasNext() && derecho == null) {
			DerechoExterno encontrado = it.next();
			if (encontrado.getNombre().equalsIgnoreCase(nombre)) {
				derecho = encontrado;
			}
		}
		
		return derecho;
		
	}

	public List<MontoTotalPorDerecho> ordenarDerechos(List<MontoTotalPorDerecho> montosPorDerecho) {
		
		Map<String, DerechoExternoReplica> derechos = getDerechosOrdenadosJerarquicamente();

		for (MontoTotalPorDerecho monto : montosPorDerecho) {

			DerechoExternoReplica derecho = derechos.get(monto.getDerecho().getNombre());
			derecho.setMontoPorDerecho(monto);
		}
		
		List<MontoTotalPorDerecho> resultado = new LinkedList<>();
		
		for (DerechoExternoReplica derecho : derechos.values()) {
			if (derecho.tieneMonto()) {
				
				MontoTotalPorDerecho monto = derecho.getMontoPorDerecho();
				monto.setNivel(derecho.getNivel());
				resultado.add(monto);
			}
		}
		
		return resultado;
		
		
	}

	private Map<String, DerechoExternoReplica> getDerechosOrdenadosJerarquicamente() {
		
		if (this.derechosReplicados == null) {
		
			this.derechosReplicados = buscarDerechosPadre();
			
			if (this.derechosReplicados.isEmpty()){
				// si no se tiene la copia local, se trae de la base externa
				List<DerechoExterno> derechosExternos = derechoExternoDao.getTodos();
				guardar(derechosExternos);				
				
				this.derechosReplicados = buscarDerechosPadre();
			}
		}
			
		Map<String, DerechoExternoReplica> derechosOrdenados = new LinkedHashMap<>();
		int nivel = 1;
			
		completarArbol(this.derechosReplicados, derechosOrdenados, nivel);
		
		return derechosOrdenados;
	}


	private void guardar(List<DerechoExterno> derechos) {
		
		Session session = sessionFactory.openSession();
		
		for (DerechoExterno derecho : derechos) {
			DerechoExternoReplica replica = new DerechoExternoReplica(derecho.getId(), derecho.getNombre());
			session.merge(replica);
		}
		
		for (DerechoExterno derecho : derechos) {
			
			if (derecho.getIdDerechoPadre() != null) {
				
				DerechoExternoReplica replica = 
						(DerechoExternoReplica) session.get(DerechoExternoReplica.class, derecho.getId());
				DerechoExternoReplica padre = 
						(DerechoExternoReplica) session.get(DerechoExternoReplica.class, derecho.getIdDerechoPadre());

				replica.setPadre(padre);
				session.merge(replica);
			}
		}
		
		session.flush();
		
	}

	@SuppressWarnings("unchecked")
	private List<DerechoExternoReplica> buscarDerechosPadre() {
		
		Session session = sessionFactory.openSession();
		List<DerechoExternoReplica> derechos = session.createQuery(
				"SELECT d FROM DerechoExternoReplica d WHERE d.padre is null").list();
		
		for (DerechoExternoReplica derecho : derechos) {
			if (derecho.getHijos() != null && derecho.getHijos().size() > 0) {
				inicializarHijos(derecho);
			}
		}
		
		return derechos;
	}
	

	@Transactional(value="transactionManager")
	private void inicializarHijos(DerechoExternoReplica derecho) {
		
		for (DerechoExternoReplica hijo : derecho.getHijos()) {
			if (hijo.getHijos() != null && hijo.getHijos().size() > 0) {
				inicializarHijos(hijo);
			}
		}
		
	}

	void completarArbol(List<DerechoExternoReplica> nodos,
			Map<String, DerechoExternoReplica> arbol, int nivel) {
		
		for (DerechoExternoReplica derecho : nodos) {
			
			derecho.setNivel(nivel);
			arbol.put(derecho.getNombre(), derecho);
			
			if (derecho.getHijos() != null && !derecho.getHijos().isEmpty()){
				completarArbol(derecho.getHijos(), arbol, nivel + 1);
			}
		}
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;		
	}

}
