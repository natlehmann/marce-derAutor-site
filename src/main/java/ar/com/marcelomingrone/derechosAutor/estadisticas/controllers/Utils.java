package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class Utils {

	private static Log log = LogFactory.getLog(Utils.class);
	
	public static enum Params {
		INICIO,
		CANTIDAD_RESULTADOS,
		FILTRO,
		DIRECCION_ORDENAMIENTO;
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

}
