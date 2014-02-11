package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.util.Date;
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
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.NewsletterDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RolDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.UsuarioDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ReceptorNewsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Rol;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Usuario;

@Controller
@RequestMapping("/admin/usuario")
public class UsuarioController {
	
	private static Log log = LogFactory.getLog(UsuarioController.class);
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private RolDao rolDao;
	
	@Autowired
	private NewsletterDao newsletterDao;
	
	@RequestMapping("listar")
	public String listar(ModelMap model) {
		return "admin/usuario_listar";
	}
	
	@ResponseBody
	@RequestMapping("/listar_ajax")
	public DataTablesResponse listar(HttpServletRequest request) {		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = Usuario.getCampoOrdenamiento( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		
		List<Usuario> usuarios = usuarioDao.getTodosPaginadoFiltrado(
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO),
				campoOrdenamiento,
				(String)params.get(Params.DIRECCION_ORDENAMIENTO));
		
		long totalFiltrados = usuarioDao.getCantidadResultados(
				(String)params.get(Params.FILTRO));
		
		long total = totalFiltrados;
		if (!StringUtils.isEmpty((String)params.get(Params.FILTRO))) {
			total = usuarioDao.getCantidadResultados(null);
		}
		
		DataTablesResponse resultado = new DataTablesResponse(
				usuarios, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	@RequestMapping("/modificar")
	public String modificar(@RequestParam("id") Long id, ModelMap model) {
		
		Usuario usuario = usuarioDao.buscar(id);
		return prepararFormulario(usuario, model);
	}
	
	@RequestMapping("/crear")
	public String crear(ModelMap model) {
		
		Usuario usuario = new Usuario();
		return prepararFormulario(usuario, model);
	}

	private String prepararFormulario(Usuario usuario, ModelMap model) {
		
		model.addAttribute("usuario", usuario);
		return "admin/usuario_editar";
	}
	
	@RequestMapping(value="/aceptarEdicion", method={RequestMethod.POST})
	public String aceptarEdicion(@Valid Usuario usuario, 
			BindingResult result, ModelMap model){
		
		if (!result.hasErrors()) {
			
			try {
				Rol rol = rolDao.getRolReceptoresNewsletter();
				usuario.agregarRol(rol);
				
				usuarioDao.guardar(usuario);
				model.addAttribute("msg", "El usuario se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el usuario.", e);
				model.addAttribute("msg", "Se produjo un error guardando el usuario. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return listar(model);
		}
		
		return prepararFormulario(usuario, model);
	}
	
	@RequestMapping(value="/eliminar", method={RequestMethod.POST})
	public String eliminar(@RequestParam("id") Long id, ModelMap model) {
		
		try {
			Usuario usuario = usuarioDao.buscar(id);
			usuario.setFechaEliminacion(new Date());
			usuarioDao.guardar(usuario);
			
			model.addAttribute("msg", "El usuario se ha eliminado con éxito.");
			
		} catch (Exception e) {
			log.error("Error al eliminar el usuario.", e);
			model.addAttribute("msg", "No se ha podido eliminar el usuario. " 
					+ "Si el problema persiste consulte al administrador del sistema.");
		}
		return listar(model);
	}
	
	@ResponseBody
	@RequestMapping(value="/confirmarEliminar", method={RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String confirmarEliminar(@RequestParam("id") Long id, ModelMap model) {
		
		String mensaje = "¿Está seguro que desea eliminar este usuario?";
		
		List<ReceptorNewsletter> receptores = newsletterDao.getRecepcionesParaUsuario(id);
		
		if (!receptores.isEmpty()) {
			mensaje = "Este usuario ha recibido newsletters. ¿Está seguro que desea eliminarlo?";
		}
		
		return mensaje;
	}

}
