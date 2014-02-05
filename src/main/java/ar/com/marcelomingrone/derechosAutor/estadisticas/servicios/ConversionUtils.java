package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

public class ConversionUtils {

	public static String convertirATexto(long milisegundos) {
		
		Date ahora = new Date();
		Date despues = new Date(ahora.getTime() + milisegundos);
		
		DateTime inicio = new DateTime(ahora);
		DateTime fin = new DateTime(despues);
		
		int dias = Days.daysBetween(inicio, fin).getDays();
		int horas = Hours.hoursBetween(inicio, fin).getHours() % 24;
		int minutos = Minutes.minutesBetween(inicio, fin).getMinutes() % 60;
		int segundos = Seconds.secondsBetween(inicio, fin).getSeconds() % 60;
		
		String resultado = paddingAIzq(horas) + ":" + paddingAIzq(minutos) + ":" + paddingAIzq(segundos);
		
		if (dias > 0) {
			
			if (dias > 1) {
				resultado = dias + " días, " + resultado;
				
			} else {
				resultado = dias + " día, " + resultado;
			}
		}
			
		
		return resultado;
			
	}

	private static String paddingAIzq(int numero) {
		
		if (numero > 9) {
			return String.valueOf(numero);
		}
		
		return "0" + numero;
	}

	public static String humanReadableByteCount(long tamanioArchivo) {
		return humanReadableByteCount(tamanioArchivo, false);
	}
	
	public static String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

}
