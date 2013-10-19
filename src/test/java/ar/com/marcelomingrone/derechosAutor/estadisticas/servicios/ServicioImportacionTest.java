package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class ServicioImportacionTest {
	
	private ServicioImportacion servicio = new ServicioImportacion();

	@Test
	public void getTrimestre1() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		assertEquals(1, servicio.getTrimestre(calendar.getTime()));
	}
	
	@Test
	public void getTrimestre2() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.MONTH, 2);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		assertEquals(1, servicio.getTrimestre(calendar.getTime()));
	}
	
	@Test
	public void getTrimestre3() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 3);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		assertEquals(2, servicio.getTrimestre(calendar.getTime()));
	}
	
	@Test
	public void getTrimestre4() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 30);
		calendar.set(Calendar.MONTH, 5);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		assertEquals(2, servicio.getTrimestre(calendar.getTime()));
	}
	
	@Test
	public void getTrimestre5() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 6);
		
		assertEquals(3, servicio.getTrimestre(calendar.getTime()));
	}
	
	@Test
	public void getTrimestre6() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 30);
		calendar.set(Calendar.MONTH, 8);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		assertEquals(3, servicio.getTrimestre(calendar.getTime()));
	}
	
	@Test
	public void getTrimestre7() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 9);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		assertEquals(4, servicio.getTrimestre(calendar.getTime()));
	}
	
	@Test
	public void getTrimestre8() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		assertEquals(4, servicio.getTrimestre(calendar.getTime()));
	}
	
	@Test(expected=ImportacionException.class)
	public void parsearLongVacio() throws ImportacionException {
		servicio.parsearLong("  ", 0, "nombreCampo");
	}
	
	@Test(expected=ImportacionException.class)
	public void parsearLongNulo() throws ImportacionException {
		servicio.parsearLong(null, 0, "nombreCampo");
	}
	
	@Test(expected=ImportacionException.class)
	public void parsearLongInvalido() throws ImportacionException {
		servicio.parsearLong("234e4r", 0, "nombreCampo");
	}
	
	@Test
	public void parsearLongOk() throws ImportacionException {
		assertEquals(1234, servicio.parsearLong("1234", 0, "nombreCampo"));
	}
	
	@Test
	public void parsearValorConComasVacio() throws ImportacionException {
		assertEquals(0.0, servicio.parsearValorConComas("", 0, "nombreCampo"), 0);
	}
	
	@Test
	public void parsearValorConComasNulo() throws ImportacionException {
		assertEquals(0.0, servicio.parsearValorConComas(null, 0, "nombreCampo"), 0);
	}
	
	@Test(expected=ImportacionException.class)
	public void parsearValorConComasInvalido() throws ImportacionException {
		servicio.parsearValorConComas("f23ee23r", 0, "nombreCampo");
	}
	
	@Test
	public void parsearValorConComasValido() throws ImportacionException {
		assertEquals(12555.43, servicio.parsearValorConComas("12,555.43", 0, "nombreCampo"), 0);
	}
	
	@Test
	public void parsearValorConComasValido2() throws ImportacionException {
		assertEquals(1912555.43, servicio.parsearValorConComas("1,912,555.43", 0, "nombreCampo"), 0);
	}
	
	@Test
	public void parsearValorConComasValido3() throws ImportacionException {
		assertEquals(191255543.00, servicio.parsearValorConComas("1,912,555,43", 0, "nombreCampo"), 0);
	}

}
