package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeUtilsTest {

	@Test
	public void convertirATextoCero() {		
		assertEquals("00:00:00", TimeUtils.convertirATexto(0));
	}
	
	@Test
	public void convertirATextoSegundos() {		
		assertEquals("00:00:15", TimeUtils.convertirATexto(15 * 1000));
	}
	
	@Test
	public void convertirATextoMinutos() {		
		assertEquals("00:01:25", TimeUtils.convertirATexto(85 * 1000));
	}
	
	@Test
	public void convertirATextoHoras() {		
		assertEquals("01:32:12", TimeUtils.convertirATexto((1 * 60 * 60 + 32 * 60 + 12) * 1000));
	}
	
	@Test
	public void convertirATextoUnDia() {		
		assertEquals("1 día, 21:06:09", TimeUtils.convertirATexto(
				(1 * 24 * 60 * 60 + 21 * 60 * 60 + 6 * 60 + 9) * 1000));
	}
	
	@Test
	public void convertirATextoMasDeUnDia() {		
		assertEquals("2 días, 21:06:09", TimeUtils.convertirATexto(
				(2 * 24 * 60 * 60 + 21 * 60 * 60 + 6 * 60 + 9) * 1000));
	}

}
