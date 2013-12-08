package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FuenteDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.ItemAuditoriaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.PuntoAuditoriaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.VisitaTecnicaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Fuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ItemAuditoria;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.PuntoAuditoria;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.VisitaTecnica;

@Controller
public class VisitaTecnicaController {
	
	private static Log log = LogFactory.getLog(VisitaTecnicaController.class);
	
	@Autowired
	private PuntoAuditoriaDao puntoAuditoriaDao;
	
	@Autowired
	private VisitaTecnicaDao visitaTecnicaDao;
	
	@Autowired
	private FuenteDao fuenteDao;
	
	@Autowired
	private ItemAuditoriaDao itemAuditoriaDao;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping("/visitasTecnicas")
	public String listar(ModelMap model) {
		
		List<VisitaTecnica> visitas = visitaTecnicaDao.getTodos();
		
		Collections.sort(visitas);
		
		model.addAttribute("visitasTecnicas", visitas);
		
		return "visitasTecnicas";
	}
	
	@RequestMapping("/admin/visitaTecnica/cargar")
	public String cargar(ModelMap model) {
		
		model.addAttribute("fuentes", fuenteDao.getTodos());
		return "admin/visitaTecnica_seleccionarFuente";
	}
	
	@RequestMapping("/admin/visitaTecnica/seleccionarFuente")
	public String seleccionarFuente(@RequestParam(value="fuente", required=false) Long idFuente, 
			ModelMap model) {
		
		if (idFuente == null) {
			model.addAttribute("msg", "Por favor seleccione una fuente.");
			
		} else {
			
			Fuente fuente = fuenteDao.buscar(idFuente);
			
			VisitaTecnica visitaTecnica = visitaTecnicaDao.buscarPorFuente(idFuente);
			
			List<ItemAuditoria> todosLosItems = itemAuditoriaDao.getTodos();
			
			visitaTecnica = armarVisitaTecnica(visitaTecnica, todosLosItems, fuente);
			
			prepararFormulario(visitaTecnica, model);
		}
		
		return "admin/visitaTecnica_editar";
	}
	

	private String prepararFormulario(VisitaTecnica visitaTecnica, ModelMap model) {
		
		// ordenarlos
		Collections.sort(visitaTecnica.getPuntosAuditoria());
		
		model.addAttribute("visitaTecnica", visitaTecnica);
		
		return "admin/visitaTecnica_editar";
	}


	private VisitaTecnica armarVisitaTecnica(VisitaTecnica visitaTecnica,
			List<ItemAuditoria> itemsAuditoria, Fuente fuente) {
		
		if (visitaTecnica == null) {
			
			visitaTecnica = new VisitaTecnica();
			visitaTecnica.setFuente(fuente);
			visitaTecnica.setFecha(new Date());
			visitaTecnica.setPuntosAuditoria(new LinkedList<PuntoAuditoria>());
		}
		
		// borrar los que estan de mas
		Iterator<PuntoAuditoria> it = visitaTecnica.getPuntosAuditoria().iterator();
		
		while (it.hasNext()) {
			
			PuntoAuditoria punto = it.next();
			if ( !itemsAuditoria.contains( punto.getItemAuditoria() ) ) {
				puntoAuditoriaDao.eliminar(punto.getId());
			}
		}
		
		// verificar que esten todos los que tienen que estar
		for (ItemAuditoria item : itemsAuditoria) {
			if ( !contiene(visitaTecnica.getPuntosAuditoria(), item) ) {
				
				PuntoAuditoria nuevo = new PuntoAuditoria();
				nuevo.setItemAuditoria(item);
				visitaTecnica.getPuntosAuditoria().add(nuevo);
			}
		}
		
		return visitaTecnica;
		
	}

	private boolean contiene(List<PuntoAuditoria> puntosAuditoria,
			ItemAuditoria item) {
		
		boolean contiene = false;
		
		for (PuntoAuditoria punto : puntosAuditoria) {
			if (punto.getItemAuditoria().equals(item)) {
				contiene = true;
			}
		}
		
		return contiene;
	}
	
	
	@RequestMapping(value="/admin/visitaTecnica/aceptarEdicion", method={RequestMethod.POST})
	public String aceptarEdicion(ModelMap model, HttpServletRequest request,
			@RequestParam(value="id", required=false) Long id,
			@RequestParam("idFuente") Long idFuente,
			@RequestParam(value="fecha", required=false) Date fecha){
		
		
		List<ItemAuditoria> todosLosItems = itemAuditoriaDao.getTodos();
		
		VisitaTecnica visitaTecnica = null;
		
		if (id != null) {
			visitaTecnica = visitaTecnicaDao.buscar(id);
			
		} else {
			
			visitaTecnica = new VisitaTecnica();			
			Fuente fuente = fuenteDao.buscar(idFuente);
			visitaTecnica.setFuente(fuente);
			visitaTecnica.setPuntosAuditoria(new LinkedList<PuntoAuditoria>());
		}
		
		visitaTecnica.setFecha(fecha);
		
		if (fecha == null) {
			visitaTecnica = armarVisitaTecnica(visitaTecnica, todosLosItems, visitaTecnica.getFuente());
			model.addAttribute("msgError", "Por favor complete la fecha.");
			
		} else {
		
			buildPuntosAuditoria(request, visitaTecnica, todosLosItems, model);
			
			
			if (StringUtils.isEmpty(model.get("msgError"))) {
				
				try {
					visitaTecnicaDao.guardar(visitaTecnica);
					model.addAttribute("msg", "La visita técnica se ha guardado con éxito.");
			
				} catch (Exception e) {
					log.error("Se produjo un error guardando la visita tecnica.", e);
					model.addAttribute("msg", "Se produjo un error guardando la visita técnica. "
							+ "Si el problema persiste consulte al administrador del sistema.");
				}
			}
		}
		
		return prepararFormulario(visitaTecnica, model);
			
	}



	private void buildPuntosAuditoria(
			HttpServletRequest request, VisitaTecnica visitaTecnica,
			List<ItemAuditoria> todosLosItems, ModelMap model) {
		
		for (ItemAuditoria item : todosLosItems) {
			
			PuntoAuditoria puntoAuditoria = visitaTecnica.getPuntoAuditoria(item);
			
			if (puntoAuditoria == null) {
				
				puntoAuditoria = new PuntoAuditoria();
				puntoAuditoria.setItemAuditoria(item);
				
				visitaTecnica.addPuntoAuditoria(puntoAuditoria);
			}
					
			
			String valor = request.getParameter(String.valueOf(item.getId()));
			
			if ( !StringUtils.isEmpty(valor)) {
				
				try {
					puntoAuditoria.setPuntajeAsignado(Integer.parseInt(valor));
					
					if (puntoAuditoria.getPuntajeAsignado() < 0 || puntoAuditoria.getPuntajeAsignado() > 10) {
						throw new NumberFormatException();
					}
					
				} catch (NumberFormatException e) {
					if (model.get("msgError") == null) {
						model.addAttribute("msgError", "Los puntajes deben ser números enteros del 0 al 10.");
					}
				}
				
			} else {
				if (model.get("msgError") == null) {
					model.addAttribute("msgError", "Por favor complete todos los puntajes.");
				}
			}
		}
		
	}

}
