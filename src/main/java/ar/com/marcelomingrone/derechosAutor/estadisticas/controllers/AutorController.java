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
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.UnidadesVendidasPorAutor;

@Controller
public class AutorController {
	
	@Autowired
	private DatosCancionDao datosCancionDao;

	@RequestMapping("/autoresMasEjecutados")
	public String autoresMasEjecutados(ModelMap model) {
		return "autores_mas_ejecutados";
	}
	
	@RequestMapping("autoresMasEjecutados_ajax")
	@ResponseBody
	public DataTablesResponse autoresMasEjecutadosAjax(HttpServletRequest request) {		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		List<UnidadesVendidasPorAutor> listado = datosCancionDao.getAutoresMasEjecutados(
				null, //TODO: pais
				null, // TODO: anio
				null, //TODO: trimestre
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO));
		
		Long total = datosCancionDao.getCantidadAutoresMasEjecutados(
				null, //TODO: pais
				null, // TODO: anio
				null, //TODO: trimestre
				(String)params.get(Params.FILTRO));
		
		DataTablesResponse resultado = new DataTablesResponse(
				listado, request.getParameter("sEcho"), total);
		
		return resultado;
	}
	
}
