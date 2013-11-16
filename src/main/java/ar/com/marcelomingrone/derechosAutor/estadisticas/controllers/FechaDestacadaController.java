package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.Params;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FechaDestacadaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.FechaDestacada;

@Controller
@RequestMapping("/admin/fechaDestacada")
public class FechaDestacadaController {
	
	private static Log log = LogFactory.getLog(FechaDestacadaController.class);
	
	@Autowired
	private FechaDestacadaDao fechaDestacadaDao;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping("listar")
	public String listar(ModelMap model) {
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
	
	@RequestMapping("/modificar")
	public String modificar(@RequestParam("id") Long id, ModelMap model) {
		
		FechaDestacada fecha = fechaDestacadaDao.buscar(id);
		return prepararFormulario(fecha, model);
	}
	
	@RequestMapping("/crear")
	public String crear(ModelMap model) {
		
		FechaDestacada fecha = new FechaDestacada();
		return prepararFormulario(fecha, model);
	}

	private String prepararFormulario(FechaDestacada fecha, ModelMap model) {
		
		model.addAttribute("fechaDestacada", fecha);
		return "admin/fechaDestacada_editar";
	}
	
	@RequestMapping(value="aceptarEdicion", method={RequestMethod.POST})
	public String aceptarEdicion(@Valid FechaDestacada fechaDestacada, 
			BindingResult result, ModelMap model){
		
		if (!result.hasErrors()) {
			
			try {
				fechaDestacadaDao.guardar(fechaDestacada);
				model.addAttribute("msg", "La fecha se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando la fecha destacada.", e);
				model.addAttribute("msg", "Se produjo un error guardando la fecha. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return listar(model);
		}
		
		return prepararFormulario(fechaDestacada, model);
	}

}
