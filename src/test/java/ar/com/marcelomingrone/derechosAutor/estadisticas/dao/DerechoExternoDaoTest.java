package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.DerechoExterno;

public class DerechoExternoDaoTest {

	private DerechoExterno derecho;
	
	private DerechoExternoDao dao;
	
	private Session session;
	
	private Query query;
	
	@Before
	public void init() {
		
		this.derecho = new DerechoExterno("A");
		this.derecho.addHijo(new DerechoExterno("B"));
		this.derecho.addHijo(new DerechoExterno("C"));
		this.derecho.addHijo(new DerechoExterno("D"));
		
		this.derecho.getHijos().get(0).addHijo(new DerechoExterno("E"));
		this.derecho.getHijos().get(1).addHijo(new DerechoExterno("F"));
		
		this.derecho.getHijos().get(2).addHijo(new DerechoExterno("G"));
		
		this.derecho.getHijos().get(0).addHijo(new DerechoExterno("H"));
		
		
		dao = new DerechoExternoDao();
		SessionFactory sessionFactory = mock(SessionFactory.class);
		dao.setSessionFactory(sessionFactory);
		
		session = mock(Session.class);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		
		query = mock(Query.class);
		when(session.createQuery(Mockito.anyString())).thenReturn(query);
		when(query.setParameter(Mockito.anyString(), Mockito.anyObject())).thenReturn(query);
	}
	
	@Test
	public void completarArbol() {
		
		Map<String, DerechoExterno> arbol = new LinkedHashMap<>();
		int nivel = 1;
		List<DerechoExterno> derechos = new LinkedList<>();
		derechos.add(derecho);
		
		dao.completarArbol(derechos, arbol, nivel);
		
		List<DerechoExterno> resultado = new LinkedList<>(arbol.values());
		
		assertEquals("A", resultado.get(0).getNombre());
		assertEquals(1, resultado.get(0).getNivel());
		
		assertEquals("B", resultado.get(1).getNombre());
		assertEquals(2, resultado.get(1).getNivel());
		
		assertEquals("E", resultado.get(2).getNombre());
		assertEquals(3, resultado.get(2).getNivel());
		assertEquals("H", resultado.get(3).getNombre());
		assertEquals(3, resultado.get(3).getNivel());
		
		assertEquals("C", resultado.get(4).getNombre());
		assertEquals(2, resultado.get(4).getNivel());
		
		assertEquals("F", resultado.get(5).getNombre());
		assertEquals(3, resultado.get(5).getNivel());
		
		assertEquals("D", resultado.get(6).getNombre());
		assertEquals(2, resultado.get(6).getNivel());
		
		assertEquals("G", resultado.get(7).getNombre());
		assertEquals(3, resultado.get(7).getNivel());
	}

	
	@Test
	public void completarHijos() {
		
		List<DerechoExterno> nodos = new LinkedList<>();
		nodos.add(new DerechoExterno("A"));
		
		List<DerechoExterno> hijos1 = new LinkedList<>();
		hijos1.add(new DerechoExterno("B"));
		hijos1.add(new DerechoExterno("C"));
		
		List<DerechoExterno> hijosB = new LinkedList<>();
		hijosB.add(new DerechoExterno("D"));
		hijosB.add(new DerechoExterno("E"));
		
		List<DerechoExterno> hijosE = new LinkedList<>();
		hijosE.add(new DerechoExterno("F"));
		
		when(query.list()).thenReturn(hijos1, hijosB, new LinkedList<>(), hijosE, new LinkedList<>(), new LinkedList<>());
		
		dao.completarHijos(nodos);
		
		assertEquals("B", nodos.get(0).getHijos().get(0).getNombre());
		assertEquals("C", nodos.get(0).getHijos().get(1).getNombre());
		
		assertEquals("D", nodos.get(0).getHijos().get(0).getHijos().get(0).getNombre());
		assertEquals("E", nodos.get(0).getHijos().get(0).getHijos().get(1).getNombre());
		
		assertNull(nodos.get(0).getHijos().get(0).getHijos().get(0).getHijos());
		assertEquals("F", nodos.get(0).getHijos().get(0).getHijos().get(1).getHijos().get(0).getNombre());
		
		assertNull(nodos.get(0).getHijos().get(1).getHijos());
	}
}
