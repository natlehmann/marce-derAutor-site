package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FuenteDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.ItemAuditoriaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.PuntoAuditoriaDao;
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
	private FuenteDao fuenteDao;
	
	@Autowired
	private ItemAuditoriaDao itemAuditoriaDao;
	
	@RequestMapping("/visitasTecnicas")
	public String listar(ModelMap model) {
		
		List<Fuente> fuentes = fuenteDao.getTodos();
		
		List<VisitaTecnica> visitas = new LinkedList<>();
		
		for (Fuente fuente : fuentes) {
			
			List<PuntoAuditoria> puntos = puntoAuditoriaDao.buscarPorFuente(fuente.getId());
			double total = calcularTotal(puntos);
			
			VisitaTecnica visita = new VisitaTecnica();
			visita.setFuente(fuente);
			visita.setPuntos(total);
			
			visitas.add(visita);
		}
		
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
			
			List<PuntoAuditoria> items = puntoAuditoriaDao.buscarPorFuente(idFuente);
			
			List<ItemAuditoria> todosLosItems = itemAuditoriaDao.getTodos();
			
			verificarPuntosAuditoria(items, todosLosItems, fuente);
			
			prepararFormulario(idFuente, items, model);
		}
		
		return "admin/visitaTecnica_editar";
	}
	

	private String prepararFormulario(Long idFuente, 
			List<PuntoAuditoria> puntosAuditoria, ModelMap model) {
		
		// ordenarlos
		Collections.sort(puntosAuditoria);
		
		model.addAttribute("total", calcularTotal(puntosAuditoria));
		
		model.addAttribute("idFuente", idFuente);
		model.addAttribute("items", puntosAuditoria);
		
		return "admin/visitaTecnica_editar";
	}

	private double calcularTotal(List<PuntoAuditoria> puntosAuditoria) {
		
		double sumatoria = 0;
		for (PuntoAuditoria punto : puntosAuditoria) {
			sumatoria += punto.getPuntajePonderado() != null ? punto.getPuntajePonderado() : 0;
		}
		
		return sumatoria;
	}

	private void verificarPuntosAuditoria(List<PuntoAuditoria> puntosAuditoria,
			List<ItemAuditoria> itemsAuditoria, Fuente fuente) {
		
		// borrar los que estan de mas
		Iterator<PuntoAuditoria> it = puntosAuditoria.iterator();
		
		while (it.hasNext()) {
			
			PuntoAuditoria punto = it.next();
			if ( !itemsAuditoria.contains( punto.getItemAuditoria() ) ) {
				puntoAuditoriaDao.eliminar(punto.getId());
			}
		}
		
		// verificar que esten todos los que tienen que estar
		for (ItemAuditoria item : itemsAuditoria) {
			if ( !contiene(puntosAuditoria, item) ) {
				
				PuntoAuditoria nuevo = new PuntoAuditoria();
				nuevo.setFuente(fuente);
				nuevo.setItemAuditoria(item);
				puntosAuditoria.add(nuevo);
			}
		}
		
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
	public String aceptarEdicion(ModelMap model, HttpServletRequest request){

		List<ItemAuditoria> todosLosItems = itemAuditoriaDao.getTodos();
		
		Fuente fuente = fuenteDao.buscar(Long.valueOf(request.getParameter("idFuente")));
		
		List<PuntoAuditoria> puntosAuditoria = buildPuntosAuditoria(
				request, fuente, todosLosItems, model);
		
		
		if (StringUtils.isEmpty(model.get("msgError"))) {
			
			try {
				puntoAuditoriaDao.guardar(puntosAuditoria);
				model.addAttribute("msg", "La visita técnica se ha guardado con éxito.");
		
			} catch (Exception e) {
				log.error("Se produjo un error guardando la visita tecnica.", e);
				model.addAttribute("msg", "Se produjo un error guardando la visita técnica. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
		}
		
		return prepararFormulario(fuente.getId(), puntosAuditoria, model);
			
	}



	private List<PuntoAuditoria> buildPuntosAuditoria(
			HttpServletRequest request, Fuente fuente,
			List<ItemAuditoria> todosLosItems, ModelMap model) {
		
		List<PuntoAuditoria> puntos = new LinkedList<>();
		
		for (ItemAuditoria item : todosLosItems) {
			
			PuntoAuditoria puntoAuditoria = puntoAuditoriaDao.buscarPorItemyFuente(item, fuente);
			
			if (puntoAuditoria == null) {
				
				puntoAuditoria = new PuntoAuditoria();
				puntoAuditoria.setFuente(fuente);
				puntoAuditoria.setItemAuditoria(item);
			}
					
			puntos.add(puntoAuditoria);
			
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
			
		
		return puntos;
	}

}
