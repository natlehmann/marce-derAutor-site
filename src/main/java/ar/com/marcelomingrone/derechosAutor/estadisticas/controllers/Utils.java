package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class Utils {

	private static Log log = LogFactory.getLog(Utils.class);
	
	private static NumberFormat formateadorNumeros;
	
	public static enum Params {
		INICIO,
		CANTIDAD_RESULTADOS,
		FILTRO,
		DIRECCION_ORDENAMIENTO;
	}
	
	public static enum SessionParam {
		
		PAIS	("paisSeleccionado"),
		ANIO	("anioSeleccionado"),
		TRIMESTRE	("trimestreSeleccionado"), 
		AUTOR	("autorSeleccionado"),
		FUENTE	("fuenteSeleccionada");
		
		private String valor;
		
		private SessionParam(String valor) {
			this.valor = valor;
		}
		
		public String getValor() {
			return valor;
		}
		
		@Override
		public String toString() {
			return valor;
		}
	}
	
	public static int getInt(String valor, int valorDefault) {
		
		int resultado = valorDefault;
		
		if (!StringUtils.isEmpty(valor)) {
			
			try {
				resultado = Integer.parseInt(valor);
				
			} catch(NumberFormatException e) {
				log.error("Error de conversion a enteros", e);
			}
		}
		
		return resultado;
	}

	public static String getDireccionOrdenamiento(String valor) {
		
		if (!StringUtils.isEmpty(valor)) {
			if (valor.equalsIgnoreCase("asc")) {
				return "asc";
				
			} else {
				return "desc";
			}
		}
		
		return "asc";
	}
	
	public static Map<Params, Object> getParametrosDatatables(HttpServletRequest request) {
		
		int inicio = getInt(request.getParameter("iDisplayStart"), 0);
		int cantidadResultados = getInt(request.getParameter("iDisplayLength"), 10);
		
		String filtro = request.getParameter("sSearch");
		
		String direccionOrdenamiento = getDireccionOrdenamiento(
				request.getParameter("sSortDir_0"));
		
		Map<Params, Object> params = new HashMap<Utils.Params, Object>();
		
		params.put(Params.INICIO, inicio);
		params.put(Params.CANTIDAD_RESULTADOS, cantidadResultados);
		params.put(Params.FILTRO, filtro);
		params.put(Params.DIRECCION_ORDENAMIENTO, direccionOrdenamiento);
		
		return params;
	}
	
	public static String formatear(double numero) {
		
		if (formateadorNumeros == null) {
			formateadorNumeros = NumberFormat.getInstance(new Locale("es"));
			formateadorNumeros.setMaximumFractionDigits(2);
		}
		
		return formateadorNumeros.format(numero);
	}

}
