package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.Params;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.HistorialImportacionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.HistorialImportacion;

@Controller
@RequestMapping("/admin/historialImportacion")
public class HistorialImportacionController {
	
	@Autowired
	private HistorialImportacionDao dao;
	
	@RequestMapping("/listar")
	public String listar(ModelMap model) {
		return "admin/historialImportacion_listar";
	}
	
	@ResponseBody
	@RequestMapping("/listar_ajax")
	public DataTablesResponse listar(HttpServletRequest request) {
		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = HistorialImportacion.getCampoOrdenamiento( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		
		List<HistorialImportacion> fechas = dao.getTodosPaginadoFiltrado(
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO),
				campoOrdenamiento,
				(String)params.get(Params.DIRECCION_ORDENAMIENTO));
		
		long totalFiltrados = dao.getCantidadResultados(
				(String)params.get(Params.FILTRO));
		
		long total = totalFiltrados;
		if (!StringUtils.isEmpty((String)params.get(Params.FILTRO))) {
			total = dao.getCantidadResultados(null);
		}
		
		DataTablesResponse resultado = new DataTablesResponse(
				fechas, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
}
