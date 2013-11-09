package ar.com.marcelomingrone.derechosAutor.estadisticas.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class GeneradorArchivo {
	
	private static final String[] COMPANY_IDS = new String[]{"14778", "12345", 
		"14778", "14778", "54321", "14778", "98765", "67890","14778","14778"};
	
	private static final String[] PAISES = new String[]{"COLOMBIA", "ARGENTINA", "CHILE", "URUGUAY", 
		"BRASIL", "ECUADOR", "COSTA RICA", "NICARAGUA", "PARAGUAY", "BOLIVIA"};
	
	private static Date[] FECHAS;
	
	private static String[] CANCIONES;
	
	private static String[] AUTORES;
	
	private static final String[] SOURCES = new String[]{"SAYCO", "OTRA"};
	
	private static final SimpleDateFormat formateadorFechas = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static NumberFormat formateadorDecimales;
	
	private static final String NOMBRE_ARCHIVO_SALIDA = "target/importacion.csv";
	
	private static final long CANTIDAD_REGISTROS = 1000000;

	private static final String SEPARADOR_CAMPOS = "\",\"";
	private static final String ABRE_CIERRA_LINEA = "\"";
	
	
	public static void init() {
		
		inicializarFechas();
		inicializarCanciones();
		inicializarAutores();
		
		formateadorDecimales = NumberFormat.getNumberInstance(Locale.ENGLISH);
		formateadorDecimales.setGroupingUsed(true);
		formateadorDecimales.setMaximumFractionDigits(2);
	}


	private static void inicializarCanciones() {
		
		List<String> canciones = new LinkedList<>();
		
		for (int i = 1; i < 50000; i++) {
			canciones.add("cancion " + i);
		}
		
		CANCIONES = canciones.toArray(new String[canciones.size()]);
		
	}
	
	private static void inicializarAutores() {
		
		List<String> autores = new LinkedList<>();
		
		for (int i = 1; i < 10000; i++) {
			autores.add("autor " + i);
		}
		
		AUTORES = autores.toArray(new String[autores.size()]);
		
	}


	private static void inicializarFechas() {
		
		Calendar cal = Calendar.getInstance();
		List<Date> fechas = new LinkedList<>();
		
		fechas.add(cal.getTime());
		
		for (int i = 0; i < 12; i++) {
			cal.add(Calendar.MONTH, -3);
			fechas.add(cal.getTime());
		}
		
		FECHAS = fechas.toArray(new Date[fechas.size()]);
		
	}
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Comienza generacion de archivo de prueba con " + CANTIDAD_REGISTROS + " registros.");
		
		init();

		PrintWriter writer = new PrintWriter(NOMBRE_ARCHIVO_SALIDA);
		
		for (int i = 0; i < CANTIDAD_REGISTROS; i++) {
			
			String linea = armarLinea();
			writer.println(linea);
		}
		
		writer.close();
		
		System.out.println("Finalizado.");
	}


	private static String armarLinea() {
		
		String companyId = generarValorAlAzar(COMPANY_IDS);
		String pais = generarValorAlAzarConId(PAISES);
		String fecha = generarFecha();
		String formatId = "57";
		String cancion = generarValorAlAzarConId(CANCIONES);
		String autor = generarValorAlAzarConId(AUTORES);
		String source = generarValorAlAzarConId(SOURCES);
		String copyRightShare = generarEntero(0, 100000);
		String unidades = generarEntero(0, 500);
		String externalCode = generarEntero(0, 500);
		String currencyFactor = generarDecimal(0.01, 8.5);
		String localCurrency = generarDecimal(0.01, 9.9);
		
		StringBuffer buffer = new StringBuffer(ABRE_CIERRA_LINEA);
		buffer.append(companyId).append(SEPARADOR_CAMPOS);
		buffer.append(pais).append(SEPARADOR_CAMPOS);
		buffer.append(fecha).append(SEPARADOR_CAMPOS);
		buffer.append(formatId).append(SEPARADOR_CAMPOS);
		buffer.append(cancion).append(SEPARADOR_CAMPOS);
		buffer.append(autor).append(SEPARADOR_CAMPOS);
		buffer.append(source).append(SEPARADOR_CAMPOS);
		buffer.append(copyRightShare).append(SEPARADOR_CAMPOS);
		buffer.append(unidades).append(SEPARADOR_CAMPOS);
		buffer.append(externalCode).append(SEPARADOR_CAMPOS);
		buffer.append(currencyFactor).append(SEPARADOR_CAMPOS);
		buffer.append(localCurrency).append(ABRE_CIERRA_LINEA);
		
		return buffer.toString();
	}


	private static String generarDecimal(double limiteInferior, double limiteSuperior) {
		
		return formateadorDecimales.format(
				Math.random() * (limiteSuperior - limiteInferior) + limiteInferior);
	}


	private static String generarEntero(int limiteInferior, int limiteSuperior) {
		
		return String.valueOf( (int)Math.round( 
				(Math.random() * (limiteSuperior - limiteInferior)) + limiteInferior ) );
	}


	private static String generarFecha() {
		int indice = (int) (Math.floor(Math.random() * FECHAS.length));
		return formateadorFechas.format( FECHAS[indice] );
	}


	private static String generarValorAlAzar(String[] valores) {
		
		int indice = (int) (Math.floor(Math.random() * valores.length));
		return valores[indice];
	}
	
	private static String generarValorAlAzarConId(String[] valores) {
		
		int indice = (int) (Math.floor(Math.random() * valores.length));
		return String.valueOf(indice + 1) + SEPARADOR_CAMPOS + valores[indice];
	}
	

}
