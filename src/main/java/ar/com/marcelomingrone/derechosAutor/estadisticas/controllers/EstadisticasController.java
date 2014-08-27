package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.SessionParam;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.PaisDao;

@Controller
public class EstadisticasController {
	
	@Autowired
	private DatosCancionDao datosCancionDao;
	
	@Autowired
	private PaisDao paisDao;
	
	
	@RequestMapping("/estadisticas")
	public String home(ModelMap model, HttpSession session) {
		
		return filtrar( (Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				model, session);
	}
	
	@RequestMapping("/estadisticas/filtrar")
	public String filtrar(@RequestParam(value="pais", required=false, defaultValue="") Long idPais,
			@RequestParam(value="anio", required=false, defaultValue="") Integer anio,
			ModelMap model, HttpSession session) {
		
		
		model.addAttribute("paises", datosCancionDao.getPaises());
		model.addAttribute("anios", datosCancionDao.getAnios());
		
		session.setAttribute(SessionParam.PAIS.toString(), idPais);
		session.setAttribute(SessionParam.ANIO.toString(), anio);
		
		model.addAttribute("totalesPorFuente", datosCancionDao.getTotalesPorFuente(idPais, anio));
		model.addAttribute("nombrePais", idPais != null ? paisDao.buscarNombrePais(idPais) : null);
		
		return "estadisticas";
		
	}

}
