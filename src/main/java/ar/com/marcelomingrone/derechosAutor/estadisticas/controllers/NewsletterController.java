package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.Params;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.NewsletterDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.EnvioNewsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Newsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ReceptorNewsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.servicios.ServicioEnvioMail;

@Controller
public class NewsletterController {
	
	private static Log log = LogFactory.getLog(NewsletterController.class);
	
	@Autowired
	private ServicioEnvioMail servicioEnvioMail;
	
	@Autowired
	private NewsletterDao newsletterDao;
	
	@Value("${newsletter.home}")
	private String NEWSLETTER_HOME;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {

	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	

	@RequestMapping("/admin/newsletter/enviar")
	public String enviar(@RequestParam("id") Long id, ModelMap model) {
		
		Newsletter newsletter = newsletterDao.buscarConEnvios(id);

		servicioEnvioMail.enviarNewsletter(newsletter);			
	
		model.addAttribute("msg", 
				"Se ha iniciado el envío del newsletter. Podrá ver sus resultados cuando haya finalizado el proceso.");
			
		return "admin/newsletter_listar";
	}
	
	@RequestMapping("/admin/newsletter/listar")
	public String listar(ModelMap model) {
		return "admin/newsletter_listar";
	}
	
	@ResponseBody
	@RequestMapping("/admin/newsletter/listar_ajax")
	public DataTablesResponse listar(HttpServletRequest request) {
		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = Newsletter.getCampoOrdenamiento( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		
		List<Newsletter> newsletters = newsletterDao.getTodosPaginadoFiltrado(
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO),
				campoOrdenamiento,
				(String)params.get(Params.DIRECCION_ORDENAMIENTO));
		
		long totalFiltrados = newsletterDao.getCantidadResultados(
				(String)params.get(Params.FILTRO));
		
		long total = totalFiltrados;
		if (!StringUtils.isEmpty((String)params.get(Params.FILTRO))) {
			total = newsletterDao.getCantidadResultados(null);
		}
		
		DataTablesResponse resultado = new DataTablesResponse(
				newsletters, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	@RequestMapping("/admin/newsletter/modificar")
	public String modificar(@RequestParam("id") Long id, ModelMap model) {
		
		Newsletter newsletter = newsletterDao.buscar(id);
		return prepararFormulario(newsletter, model);
	}
	
	@RequestMapping("/admin/newsletter/duplicar")
	public String duplicar(@RequestParam("id") Long id, ModelMap model) {
		
		Newsletter newsletterDuplicado = newsletterDao.buscar(id);
		Newsletter nuevo = new Newsletter();
		
		nuevo.setContenido(newsletterDuplicado.getContenido());
		nuevo.setSubject(newsletterDuplicado.getSubject());
		nuevo.setFechaCreacion(new Date());
		
		return prepararFormulario(nuevo, model);
	}
	
	@RequestMapping("/admin/newsletter/crear")
	public String crear(ModelMap model) {
		
		Newsletter newsletter = new Newsletter();
		newsletter.setFechaCreacion(new Date());
		return prepararFormulario(newsletter, model);
	}

	private String prepararFormulario(Newsletter newsletter, ModelMap model) {
		
		model.addAttribute("newsletter", newsletter);
		return "admin/newsletter_editar";
	}
	
	@RequestMapping(value="/admin/newsletter/aceptarEdicion", method={RequestMethod.POST})
	public String aceptarEdicion(@Valid Newsletter newsletter, 
			BindingResult result, ModelMap model){
		
		if (!result.hasErrors()) {
			
			try {
				newsletterDao.guardar(newsletter);
				model.addAttribute("msg", "El newsletter se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el newsletter.", e);
				model.addAttribute("msg", "Se produjo un error guardando el newsletter. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return listar(model);
		}
		
		return prepararFormulario(newsletter, model);
	}
	
	@RequestMapping(value="/admin/newsletter/eliminar", method={RequestMethod.POST})
	public String eliminar(@RequestParam("id") Long id, ModelMap model) {
		
		try {
			newsletterDao.eliminar(id);
			model.addAttribute("msg", "El newsletter se ha eliminado con éxito.");
			
		} catch (Exception e) {
			log.error("Error al eliminar el newsletter.", e);
			model.addAttribute("msg", "No se ha podido eliminar el newsletter. " 
					+ "Si el problema persiste consulte al administrador del sistema.");
		}
		return listar(model);
	}
	
	@RequestMapping("/admin/newsletter/validarEnvio")
	@ResponseBody
	public String validarEnvio(@RequestParam("id") Long id, ModelMap model) {
		
		String msg = "¿Está seguro que desea enviar este newsletter a todos los usuarios registrados?";
		
		List<EnvioNewsletter> envios = newsletterDao.getEnviosNewsletter(id);
		if (!envios.isEmpty()) {
			msg = "Este newsletter ya fue enviado " + envios.size() 
					+ " veces. El último envío se produjo el día " 
					+ dateFormat.format(envios.get(0).getFechaEnvio()) 
					+ ". ¿Está seguro que desea enviarlo de nuevo?";
		}
		
		return msg;
	}
	
	@RequestMapping("/admin/newsletter/verInfo")
	public String verInfo(@RequestParam("id") Long id, ModelMap model) {
		
		Newsletter newsletter = newsletterDao.buscarConEnvios(id);

		model.addAttribute("newsletter", newsletter);
			
		return "admin/newsletter_info";
	}
	
	@RequestMapping("/admin/newsletter/probarEnvio")
	public String probarEnvio(@RequestParam("id") Long id, ModelMap model) {
		
		Newsletter newsletter = newsletterDao.buscar(id);

		model.addAttribute("newsletter", newsletter);
			
		return "admin/newsletter_probarEnvio";
	}
	
	@RequestMapping("/admin/newsletter/verReceptores/{idEnvio}")
	public String verReceptores(@PathVariable("idEnvio") Long id, ModelMap model) {
		
		EnvioNewsletter envio = newsletterDao.buscarEnvioNewsletter(id);
		
		model.addAttribute("idEnvio", id);
		model.addAttribute("idNewsletter", envio.getNewsletter().getId());
		
		return "admin/newsletter_receptores_listar";
	}
	
	@ResponseBody
	@RequestMapping("/admin/newsletter/verReceptores_ajax/{idEnvio}")
	public DataTablesResponse verReceptoresAjax(
			@PathVariable("idEnvio") Long idEnvio, HttpServletRequest request) {
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = ReceptorNewsletter.getCampoOrdenamiento( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		
		List<ReceptorNewsletter> receptores = newsletterDao.getReceptoresNewsletterPaginadoFiltrado(
				idEnvio,
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO),
				campoOrdenamiento,
				(String)params.get(Params.DIRECCION_ORDENAMIENTO));
		
		long totalFiltrados = newsletterDao.getCantidadReceptores(
				idEnvio, (String)params.get(Params.FILTRO));
		
		long total = totalFiltrados;
		if (!StringUtils.isEmpty((String)params.get(Params.FILTRO))) {
			total = newsletterDao.getCantidadReceptores(idEnvio, null);
		}
		
		DataTablesResponse resultado = new DataTablesResponse(
				receptores, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
		
	}
	
	@RequestMapping("/newsletter/desuscribir/{id}")
	public String desuscribir(@PathVariable("id") Long id) {
		
		throw new IllegalArgumentException("AUN NO IMPLEMENTADO");
	}
	
	@ResponseBody
	@RequestMapping(value="/newsletter/**")
	public byte[] verImagenNewsletter(HttpServletRequest request) 
			throws IOException {
		
		String restOfTheUrl = (String) request.getAttribute(
		        HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		InputStream in = new FileInputStream(new File(NEWSLETTER_HOME
				+ restOfTheUrl.substring("/newsletter".length())));
		
	    return IOUtils.toByteArray(in);
	}
}
