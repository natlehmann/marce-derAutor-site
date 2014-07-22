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
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DerechoDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Derecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DerechoEditable;

@Controller
@RequestMapping("/admin/derecho")
public class DerechoController {
	
	private static Log log = LogFactory.getLog(DerechoController.class);
	
	@Autowired
	private DerechoDao derechoDao;
	
	@RequestMapping("/listar")
	public String listar(ModelMap model) {
		return "admin/derecho_listar";
	}
	
	@ResponseBody
	@RequestMapping("/listar_ajax")
	public DataTablesResponse listar(HttpServletRequest request) {
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		List<Derecho> derechos = derechoDao.getTodosPaginadoFiltrado(
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO));
		
		long totalFiltrados = derechoDao.getCantidadResultados(
				(String)params.get(Params.FILTRO));
		
		long total = totalFiltrados;
		if (!StringUtils.isEmpty((String)params.get(Params.FILTRO))) {
			total = derechoDao.getCantidadResultados(null);
		}
		
		DataTablesResponse resultado = new DataTablesResponse(
				derechos, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	@RequestMapping("/modificar")
	public String modificar(@RequestParam("nombre") String nombre, ModelMap model) {
		
		Derecho derecho = derechoDao.buscar(nombre);
		return prepararFormulario(derecho, model);
	}
	
	@RequestMapping("/crear")
	public String crear(ModelMap model) {
		
		Derecho derecho = new DerechoEditable();
		return prepararFormulario(derecho, model);
	}

	private String prepararFormulario(Derecho derecho, ModelMap model) {
		
		model.addAttribute("derecho", derecho);
		return "admin/derecho_editar";
	}
	
	@RequestMapping(value="/aceptarEdicion", method={RequestMethod.POST})
	public String aceptarEdicion(@Valid DerechoEditable derecho, 
			BindingResult result, ModelMap model){
		
		if (!result.hasErrors()) {
			
			try {
				derecho.setNombre(derecho.getNombre().toUpperCase());
				
				derechoDao.guardar(derecho);
				model.addAttribute("msg", "El derecho se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el derecho.", e);
				model.addAttribute("msg", "Se produjo un error guardando el derecho. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return listar(model);
		}
		
		return prepararFormulario(derecho, model);
	}
	
	@RequestMapping(value="/eliminar", method={RequestMethod.POST})
	public String eliminar(@RequestParam("id") String nombre, ModelMap model) {
		
		try {
			
			Derecho derecho = derechoDao.buscar(nombre);
			
			if (!derecho.isModificable()) {
				model.addAttribute("msg", "No se puede eliminar el derecho porque está vinculado a datos importados.");
				
			} else {
				
				try {
					derechoDao.eliminar((DerechoEditable) derecho);
					model.addAttribute("msg", "El derecho se ha eliminado con éxito.");
					
				} catch(IllegalArgumentException e){
					model.addAttribute("msg", e.getMessage());
				}
			}
			
		} catch (Exception e) {
			log.error("Error al eliminar el derecho.", e);
			model.addAttribute("msg", "No se ha podido eliminar el derecho. " 
					+ "Si el problema persiste consulte al administrador del sistema.");
		}
		return listar(model);
	}

}
