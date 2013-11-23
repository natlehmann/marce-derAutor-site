package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.ItemAuditoriaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ItemAuditoria;

@Controller
@RequestMapping("/admin/itemAuditoria")
public class ItemAuditoriaController {
	
	private static Log log = LogFactory.getLog(ItemAuditoriaController.class);
	
	@Autowired
	private ItemAuditoriaDao itemAuditoriaDao;
	
	@RequestMapping("listar")
	public String listar(ModelMap model) {
		
		model.addAttribute("items", itemAuditoriaDao.getTodos());
		return "admin/itemAuditoria_listar";
	}
	
	
	@RequestMapping("/modificar")
	public String modificar(@RequestParam("id") Long id, ModelMap model) {
		
		ItemAuditoria item = itemAuditoriaDao.buscar(id);
		return prepararFormulario(item, model);
	}
	
	@RequestMapping("/crear")
	public String crear(ModelMap model) {
		
		ItemAuditoria item = new ItemAuditoria();
		return prepararFormulario(item, model);
	}

	private String prepararFormulario(ItemAuditoria item, ModelMap model) {
		
		model.addAttribute("itemAuditoria", item);
		return "admin/itemAuditoria_editar";
	}
	
	@RequestMapping(value="/aceptarEdicion", method={RequestMethod.POST})
	public String aceptarEdicion(@Valid ItemAuditoria item, 
			BindingResult result, ModelMap model){
		
		if (!result.hasErrors()) {
			
			try {
				itemAuditoriaDao.guardar(item);
				model.addAttribute("msg", "El item se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el item.", e);
				model.addAttribute("msg", "Se produjo un error guardando el item. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return listar(model);
		}
		
		return prepararFormulario(item, model);
	}
	
	@RequestMapping(value="/eliminar", method={RequestMethod.POST})
	public String eliminar(@RequestParam("id") Long id, ModelMap model) {
		
		try {
			itemAuditoriaDao.eliminar(id);
			model.addAttribute("msg", "El item se ha eliminado con éxito.");
			
		} catch (Exception e) {
			log.error("Error al eliminar el item.", e);
			model.addAttribute("msg", "No se ha podido eliminar el item. " 
					+ "Si el problema persiste consulte al administrador del sistema.");
		}
		return listar(model);
	}

}
