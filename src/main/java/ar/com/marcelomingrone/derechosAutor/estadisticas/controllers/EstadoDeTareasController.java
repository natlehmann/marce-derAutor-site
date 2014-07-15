package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.AutorDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.EstadoDeTareasDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FuenteDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.EstadoDeTareas;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.EstadoDeTareas.Estado;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.EstadoDeTareas.Prioridad;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Autor;

@Controller
public class EstadoDeTareasController {
	
	private static Log log = LogFactory.getLog(EstadoDeTareasController.class);
	
	@Autowired
	private EstadoDeTareasDao estadoDeTareasDao;
	
	@Autowired
	private FuenteDao fuenteDao;
	
	@Autowired
	private AutorDao autorDao;
	
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping("/estadoDeTareas")
	public String listar(ModelMap model) {
		
		return buscar(null, null, null, null, null, null, model);
	}
	
	@RequestMapping("/estadoDeTareas/buscar")
	public String buscar(@RequestParam(value="autor", required=false) Long idAutor,
			@RequestParam(value="asunto", required=false) String asunto,
			@RequestParam(value="fuente", required=false) Long idFuente,
			@RequestParam(value="estado", required=false) String estado,
			@RequestParam(value="prioridad", required=false) String prioridad,
			@RequestParam(value="nombreAutor", required=false) String nombreAutor,
			ModelMap model) {
		
		List<EstadoDeTareas> listado = estadoDeTareasDao.filtrar(
				idAutor, idFuente, asunto, estado, prioridad);
		
		for (EstadoDeTareas estadoTarea : listado) {
			if (estadoTarea.getIdAutor() != null && estadoTarea.getNombreAutor() == null) {
				
				Autor autor = autorDao.buscar(estadoTarea.getIdAutor());
				if (autor != null) {
					estadoTarea.setNombreAutor(autor.getNombre());
				}
			}
		}
		
		model.addAttribute("listado", listado);
		
		model.addAttribute("filtro_asunto", asunto);
		model.addAttribute("filtro_autor", idAutor);
		model.addAttribute("filtro_fuente", idFuente);
		model.addAttribute("filtro_estado", estado);
		model.addAttribute("filtro_prioridad", prioridad);
		model.addAttribute("nombreAutor", nombreAutor);
		
		model.addAttribute("fuentes", fuenteDao.getTodos());
		model.addAttribute("estados", Estado.values());
		model.addAttribute("prioridades", Prioridad.values());
		
		return "estadoDeTareas_listar";
		
	}

	
	@RequestMapping("/admin/estadoDeTareas/modificar")
	public String modificar(@RequestParam("id") Long id, ModelMap model) {
		
		EstadoDeTareas estadoDeTareas = estadoDeTareasDao.buscar(id);
		return prepararFormulario(estadoDeTareas, model);
	}
	
	@RequestMapping("/admin/estadoDeTareas/crear")
	public String crear(ModelMap model) {
		
		EstadoDeTareas estadoDeTareas = new EstadoDeTareas();
		return prepararFormulario(estadoDeTareas, model);
	}

	private String prepararFormulario(EstadoDeTareas estadoDeTareas, ModelMap model) {
		
		model.addAttribute("fuentes", fuenteDao.getTodos());
		model.addAttribute("estados", Estado.values());
		model.addAttribute("prioridades", Prioridad.values());
		
		if (estadoDeTareas.getIdAutor() != null && model.get("nombreAutor") == null) {
			
			Autor autor = autorDao.buscar(estadoDeTareas.getIdAutor());
			if (autor != null) {
				model.addAttribute("nombreAutor", autor.getNombre());
			}
		}
		
		model.addAttribute("estadoDeTareas", estadoDeTareas);
		return "admin/estadoDeTareas_editar";
	}
	
	@RequestMapping(value="/admin/estadoDeTareas/aceptarEdicion", method={RequestMethod.POST})
	public String aceptarEdicion(@Valid EstadoDeTareas estadoDeTareas, 
			BindingResult result, ModelMap model, 
			@RequestParam(value="nombreAutor", required=false) String nombreAutor){
		
		model.addAttribute("nombreAutor", nombreAutor);
		
		if (!result.hasErrors()) {
			
			try {
//				if (estadoDeTareas.getAutor() != null && StringUtils.isEmpty(estadoDeTareas.getAutor().getId())) {
//					estadoDeTareas.setAutor(null);
//				}
				
				if (estadoDeTareas.getFuente() != null && StringUtils.isEmpty(estadoDeTareas.getFuente().getId())) {
					estadoDeTareas.setFuente(null);
				}
				
				estadoDeTareasDao.guardar(estadoDeTareas);
				model.addAttribute("msg", "El estado de tareas se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el estado de tareas.", e);
				model.addAttribute("msg", "Se produjo un error guardando el estado de tareas. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return listar(model);
		}
		
		return prepararFormulario(estadoDeTareas, model);
	}
	
	@RequestMapping(value="/admin/estadoDeTareas/eliminar", method={RequestMethod.POST})
	public String eliminar(@RequestParam("id") Long id, ModelMap model) {
		
		try {
			estadoDeTareasDao.eliminar(id);
			model.addAttribute("msg", "El estado de tareas se ha eliminado con éxito.");
			
		} catch (Exception e) {
			log.error("Error al eliminar el estado de tareas.", e);
			model.addAttribute("msg", "No se ha podido eliminar el estado de tareas. " 
					+ "Si el problema persiste consulte al administrador del sistema.");
		}
		return listar(model);
	}
	
	@RequestMapping("/estadoDeTareas/verDescripcion") 
	@ResponseBody
	public String verDescripcion(@RequestParam("id") Long id) {
		
		EstadoDeTareas estadoDeTareas = estadoDeTareasDao.buscar(id);
		return estadoDeTareas.getDescripcion() + estadoDeTareas.getLinkReducirDescripcion();
	}
	
	@RequestMapping("/estadoDeTareas/reducirDescripcion") 
	@ResponseBody
	public String reducirDescripcion(@RequestParam("id") Long id) {
		
		EstadoDeTareas estadoDeTareas = estadoDeTareasDao.buscar(id);
		return estadoDeTareas.getDescripcionCorta();
	}

}
