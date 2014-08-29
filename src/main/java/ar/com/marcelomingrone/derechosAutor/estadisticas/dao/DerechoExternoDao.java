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

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotalPorDerecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.DerechoExterno;

@Repository
public class DerechoExternoDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private List<DerechoExterno> derechos;
	
	private Map<String, DerechoExterno> derechosOrdenados;
	
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
		
		Map<String, DerechoExterno> derechos = getDerechosOrdenadosJerarquicamente();
System.out.println("------------- DERECHOS " + derechos);		
		for (MontoTotalPorDerecho monto : montosPorDerecho) {
System.out.println("BUSCANDO " + monto.getDerecho().getNombre());
			DerechoExterno derecho = derechos.get(monto.getDerecho().getNombre());
			derecho.setMontoPorDerecho(monto);
		}
		
		List<MontoTotalPorDerecho> resultado = new LinkedList<>();
		
		for (DerechoExterno derecho : derechos.values()) {
			if (derecho.tieneMonto()) {
				resultado.add(derecho.getMontoPorDerecho());
			}
		}
		
		return resultado;
		
		
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	private Map<String, DerechoExterno> getDerechosOrdenadosJerarquicamente() {
		
		if (this.derechosOrdenados == null) {
		
			Session session = sessionFactory.getCurrentSession();
			List<DerechoExterno> raices = session.createQuery(
					"SELECT DISTINCT new " + DerechoExterno.class.getName()
					+ "(dc.nombreDerechoExterno, dc.idDerechoExterno, dc.idDerechoPadre) "
					+ "FROM DatosCancion dc WHERE dc.idDerechoPadre is null").list();
			
			completarHijos(raices);
System.out.println("------------------ ACA VA EL ARBOL ENETERO " + raices);			
			this.derechosOrdenados = new LinkedHashMap<>();
			int nivel = 1;
			
			completarArbol(raices, this.derechosOrdenados, nivel);
System.out.println("ASI QUEDO ------------------------- " + this.derechosOrdenados);
		}
		
		return this.derechosOrdenados;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	void completarHijos(List<DerechoExterno> nodos) {
		
		Session session = sessionFactory.getCurrentSession();
		
		for (DerechoExterno derecho : nodos) {
			
			List<DerechoExterno> hijos = session.createQuery(
					"SELECT DISTINCT new " + DerechoExterno.class.getName() 
					+ "(dc.nombreDerechoExterno, dc.idDerechoExterno, dc.idDerechoPadre) "
					+ "FROM DatosCancion dc WHERE dc.idDerechoPadre = :idDerechoPadre")
					.setParameter("idDerechoPadre", derecho.getIdDerechoPadre()).list();
System.out.println("LOS HIJOS DE " + derecho.getIdDerechoPadre() + "  SON " + hijos);			
			if (!hijos.isEmpty()) {
				derecho.setHijos(hijos);
				completarHijos(hijos);
			}
		}
		
	}

	void completarArbol(List<DerechoExterno> nodos,
			Map<String, DerechoExterno> arbol, int nivel) {
		
		for (DerechoExterno derecho : nodos) {
			
			derecho.setNivel(nivel);
			arbol.put(derecho.getNombre(), derecho);
			
			if (derecho.getHijos() != null){
				completarArbol(derecho.getHijos(), arbol, nivel + 1);
			}
		}
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;		
	}

}
