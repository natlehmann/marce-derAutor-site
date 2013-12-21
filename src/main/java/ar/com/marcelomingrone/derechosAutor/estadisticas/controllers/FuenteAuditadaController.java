package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.Params;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FuenteAuditadaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.FuenteAuditada;

@Controller
@RequestMapping("/admin/fuenteAuditada")
public class FuenteAuditadaController {
	
	private static Log log = LogFactory.getLog(FuenteAuditadaController.class);
	
	@Autowired
	private FuenteAuditadaDao dao;
	
	@RequestMapping("listar")
	public String listar(ModelMap model) {
		return "admin/fuenteAuditada_listar";
	}
	
	@ResponseBody
	@RequestMapping("/listar_ajax")
	public DataTablesResponse listar(HttpServletRequest request) {
		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = FuenteAuditada.getCampoOrdenamiento( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		
		List<FuenteAuditada> fuentes = dao.getTodosPaginadoFiltrado(
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
				fuentes, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	@RequestMapping("/modificar")
	public String modificar(@RequestParam("id") Long id, ModelMap model) {
		
		FuenteAuditada fuente = dao.buscar(id);
		return prepararFormulario(fuente, model);
	}
	
	@RequestMapping("/crear")
	public String crear(ModelMap model) {
		
		FuenteAuditada fuente = new FuenteAuditada();
		return prepararFormulario(fuente, model);
	}

	private String prepararFormulario(FuenteAuditada fuente, ModelMap model) {
		
		model.addAttribute("fuenteAuditada", fuente);
		return "admin/fuenteAuditada_editar";
	}
	
	@RequestMapping(value="/aceptarEdicion", method={RequestMethod.POST})
	public String aceptarEdicion(@Valid FuenteAuditada fuente, 
			BindingResult result, ModelMap model){
		
		if (!result.hasErrors()) {
			
			try {
				dao.guardar(fuente);
				model.addAttribute("msg", "La fuente se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando la fuente.", e);
				model.addAttribute("msg", "Se produjo un error guardando la fuente. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return listar(model);
		}
		
		return prepararFormulario(fuente, model);
	}
	
	@RequestMapping(value="/eliminar", method={RequestMethod.POST})
	public String eliminar(@RequestParam("id") Long id, ModelMap model) {
		
		try {
			dao.eliminar(id);
			model.addAttribute("msg", "La fuente se ha eliminado con éxito.");
			
		} catch (Exception e) {
			log.error("Error al eliminar la fuente.", e);
			model.addAttribute("msg", "No se ha podido eliminar la fuente. " 
					+ "Si el problema persiste consulte al administrador del sistema.");
		}
		
		return listar(model);
	}

}
