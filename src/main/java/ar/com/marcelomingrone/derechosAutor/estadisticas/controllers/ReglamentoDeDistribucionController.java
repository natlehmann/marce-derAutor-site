package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.Params;
import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.SessionParam;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DerechoDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FuenteDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.ReglamentoDeDistribucionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ReglamentoDeDistribucion;

@Controller
public class ReglamentoDeDistribucionController {
	
	private static Log log = LogFactory.getLog(ReglamentoDeDistribucionController.class);
	
	@Autowired
	private ReglamentoDeDistribucionDao reglamentoDeDistribucionDao;
	
	@Autowired
	private FuenteDao fuenteDao;
	
	@Autowired
	private DerechoDao derechoDao;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping("/reglamentoDeDistribucion")
	public String listar(ModelMap model, HttpSession session) {
		
		model.addAttribute("fuentes", fuenteDao.getTodos());
		session.setAttribute(SessionParam.FUENTE.toString(), null);
		
		return "reglamentoDeDistribucion_listar";
	}
	
	@ResponseBody
	@RequestMapping("/reglamentoDeDistribucion/listar_ajax")
	public DataTablesResponse listar(HttpServletRequest request, HttpSession session) {
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = ReglamentoDeDistribucion.getCampoOrdenamiento( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		session.setAttribute(SessionParam.FUENTE.toString(), (String)params.get(Params.FILTRO));
		
		
		List<ReglamentoDeDistribucion> reglamentos = reglamentoDeDistribucionDao.getTodosPaginadoFiltrado(
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO),
				campoOrdenamiento,
				(String)params.get(Params.DIRECCION_ORDENAMIENTO));
		
		long totalFiltrados = reglamentoDeDistribucionDao.getCantidadResultados(
				(String)params.get(Params.FILTRO));
		
		long total = totalFiltrados;
		if (!StringUtils.isEmpty((String)params.get(Params.FILTRO))) {
			total = reglamentoDeDistribucionDao.getCantidadResultados(null);
		}
		
		DataTablesResponse resultado = new DataTablesResponse(
				reglamentos, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	@RequestMapping("/admin/reglamentoDeDistribucion/modificar")
	public String modificar(@RequestParam("id") Long id, ModelMap model) {
		
		ReglamentoDeDistribucion reglamento = reglamentoDeDistribucionDao.buscar(id);
		return prepararFormulario(reglamento, model);
	}
	
	@RequestMapping("/admin/reglamentoDeDistribucion/crear")
	public String crear(ModelMap model) {
		
		ReglamentoDeDistribucion reglamento = new ReglamentoDeDistribucion();
		return prepararFormulario(reglamento, model);
	}

	private String prepararFormulario(ReglamentoDeDistribucion reglamento, ModelMap model) {
		
		model.addAttribute("fuentes", fuenteDao.getTodos());
		model.addAttribute("derechos", derechoDao.getTodos());
		
		model.addAttribute("reglamentoDeDistribucion", reglamento);
		return "admin/reglamentoDeDistribucion_editar";
	}
	
	@RequestMapping(value="/admin/reglamentoDeDistribucion/aceptarEdicion", method={RequestMethod.POST})
	public String aceptarEdicion(@Valid ReglamentoDeDistribucion reglamento, 
			BindingResult result, ModelMap model, HttpSession session){
		
		if (!result.hasErrors()) {
			
			try {
				reglamentoDeDistribucionDao.guardar(reglamento);
				model.addAttribute("msg", "El reglamento de distribución se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el reglamento de distribución.", e);
				model.addAttribute("msg", "Se produjo un error guardando el reglamento de distribución. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return listar(model, session);
		}
		
		return prepararFormulario(reglamento, model);
	}
	
	@RequestMapping(value="/admin/reglamentoDeDistribucion/eliminar", method={RequestMethod.POST})
	public String eliminar(@RequestParam("id") Long id, ModelMap model, HttpSession session) {
		
		try {
			reglamentoDeDistribucionDao.eliminar(id);
			model.addAttribute("msg", "El reglamento de distribución se ha eliminado con éxito.");
			
		} catch (Exception e) {
			log.error("Error al eliminar el reglamento de distribución.", e);
			model.addAttribute("msg", "No se ha podido eliminar el reglamento de distribución. " 
					+ "Si el problema persiste consulte al administrador del sistema.");
		}
		return listar(model, session);
	}
	
	@RequestMapping("/reglamentoDeDistribucion/verDescripcion")
	@ResponseBody
	public String verDescripcion(@RequestParam("id") Long id, ModelMap model) {
		
		ReglamentoDeDistribucion reglamento = reglamentoDeDistribucionDao.buscar(id);
		return reglamento.getDescripcion() + reglamento.getLinkReducirDescripcion();
	}
	
	@RequestMapping("/reglamentoDeDistribucion/reducirDescripcion")
	@ResponseBody
	public String reducirDescripcion(@RequestParam("id") Long id, ModelMap model) {
		
		ReglamentoDeDistribucion reglamento = reglamentoDeDistribucionDao.buscar(id);
		return reglamento.getDescripcionCorta();
	}

}
