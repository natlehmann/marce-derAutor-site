package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.Params;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FechaDestacadaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.FechaDestacada;

@Controller
@RequestMapping("/admin/fechaDestacada")
public class FechaDestacadaController {
	
	@Autowired
	private FechaDestacadaDao fechaDestacadaDao;
	
	@RequestMapping("listar")
	public String listar(ModelMap model, HttpServletRequest request) {
		return "admin/fechaDestacada_listar";
	}
	
	@ResponseBody
	@RequestMapping("/listar_ajax")
	public DataTablesResponse listar(HttpServletRequest request) {
		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = FechaDestacada.getCampoOrdenamiento( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		
		List<FechaDestacada> fechas = fechaDestacadaDao.getTodosPaginadoFiltrado(
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO),
				campoOrdenamiento,
				(String)params.get(Params.DIRECCION_ORDENAMIENTO));
		
		Long total = fechaDestacadaDao.getCantidadResultados(
				(String)params.get(Params.FILTRO));
		
		DataTablesResponse resultado = new DataTablesResponse(
				fechas, request.getParameter("sEcho"), total);
		
		return resultado;
	}

}
