package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotalPorDerecho;

public class DerechoExternoTest {
	
	private DerechoExterno derecho;
	
	@Before
	public void init() {
		
		this.derecho = new DerechoExterno("A");
		this.derecho.addHijo(new DerechoExterno("B"));
		this.derecho.addHijo(new DerechoExterno("C"));
		this.derecho.addHijo(new DerechoExterno("D"));
		
		this.derecho.getHijos().get(0).addHijo(new DerechoExterno("E"));
		this.derecho.getHijos().get(0).addHijo(new DerechoExterno("F"));
		
		this.derecho.getHijos().get(2).addHijo(new DerechoExterno("G"));
	}

	@Test
	public void noTieneMonto() {
		assertFalse(this.derecho.tieneMonto());
	}
	
	@Test
	public void raizTieneMonto() {
		this.derecho.setMontoPorDerecho(new MontoTotalPorDerecho());
		assertTrue(this.derecho.tieneMonto());
	}
	
	@Test
	public void tieneMontoEnPrimerHijo() {
		this.derecho.getHijos().get(0).setMontoPorDerecho(new MontoTotalPorDerecho());
		assertTrue(this.derecho.tieneMonto());
	}
	
	@Test
	public void tieneMontoEnSegundoHijo() {
		this.derecho.getHijos().get(1).setMontoPorDerecho(new MontoTotalPorDerecho());
		assertTrue(this.derecho.tieneMonto());
	}
	
	@Test
	public void tieneMontoEnTercerHijo() {
		this.derecho.getHijos().get(2).setMontoPorDerecho(new MontoTotalPorDerecho());
		assertTrue(this.derecho.tieneMonto());
	}
	
	@Test
	public void tieneMontoEnPrimerNieto() {
		this.derecho.getHijos().get(0).getHijos().get(0).setMontoPorDerecho(new MontoTotalPorDerecho());
		assertTrue(this.derecho.tieneMonto());
	}
	
	@Test
	public void tieneMontoEnSegundoNieto() {
		this.derecho.getHijos().get(0).getHijos().get(1).setMontoPorDerecho(new MontoTotalPorDerecho());
		assertTrue(this.derecho.tieneMonto());
	}
	
	@Test
	public void tieneMontoEnTercerNieto() {
		this.derecho.getHijos().get(2).getHijos().get(0).setMontoPorDerecho(new MontoTotalPorDerecho());
		assertTrue(this.derecho.tieneMonto());
	}

}
