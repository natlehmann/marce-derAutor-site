package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.PaisDao;

@Controller
public class HomeController {
	
	@Autowired
	private DatosCancionDao datosCancionDao;
	
	@Autowired
	private PaisDao paisDao;
	
	@RequestMapping("/home")
	public String home(ModelMap model) {
		
		return filtrar(null, null, null, model);
	}
	
	@RequestMapping("/home/filtrar")
	public String filtrar(@RequestParam(value="pais", required=false, defaultValue="") Long idPais,
			@RequestParam(value="anio", required=false, defaultValue="") Integer anio,
			@RequestParam(value="trimestre", required=false, defaultValue="") Integer trimestre,
			ModelMap model) {
		
		
		model.addAttribute("paises", paisDao.getTodos());
		model.addAttribute("anios", datosCancionDao.getAnios());
		
		model.addAttribute("paisSeleccionado", idPais);
		model.addAttribute("anioSeleccionado", anio);
		model.addAttribute("trimestreSeleccionado", trimestre);
		
		model.addAttribute("lista", datosCancionDao.getAutoresMasEjecutados(
				idPais, anio, trimestre, 10));
		
		return "home";
		
	}

}
