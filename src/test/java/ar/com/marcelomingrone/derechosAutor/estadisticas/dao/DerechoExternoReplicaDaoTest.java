package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DerechoExternoReplica;

public class DerechoExternoReplicaDaoTest {

	private DerechoExternoReplica derecho;
	
	private DerechoExternoReplicaDao dao;
	
	private Session session;
	
	private Query query;
	
	@Before
	public void init() {
		
		this.derecho = new DerechoExternoReplica("A");
		this.derecho.addHijo(new DerechoExternoReplica("B"));
		this.derecho.addHijo(new DerechoExternoReplica("C"));
		this.derecho.addHijo(new DerechoExternoReplica("D"));
		
		this.derecho.getHijos().get(0).addHijo(new DerechoExternoReplica("E"));
		this.derecho.getHijos().get(1).addHijo(new DerechoExternoReplica("F"));
		
		this.derecho.getHijos().get(2).addHijo(new DerechoExternoReplica("G"));
		
		this.derecho.getHijos().get(0).addHijo(new DerechoExternoReplica("H"));
		
		
		dao = new DerechoExternoReplicaDao();
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
		
		Map<String, DerechoExternoReplica> arbol = new LinkedHashMap<>();
		int nivel = 1;
		List<DerechoExternoReplica> derechos = new LinkedList<>();
		derechos.add(derecho);
		
		dao.completarArbol(derechos, arbol, nivel);
		
		List<DerechoExternoReplica> resultado = new LinkedList<>(arbol.values());
		
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

}
