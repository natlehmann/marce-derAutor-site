package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FechaDestacadaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.PaisDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.FechaDestacada;

@Controller
public class HomeController {
	
	private static Log log = LogFactory.getLog(HomeController.class);
	
	@Autowired
	private DatosCancionDao datosCancionDao;
	
	@Autowired
	private PaisDao paisDao;
	
	@Autowired
	private FechaDestacadaDao fechaDestacadaDao;
	
	private SimpleDateFormat formateadorFechas = new SimpleDateFormat("dd/MM/yyyy");
	
	
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
		
		model.addAttribute("autoresMasEjecutados", datosCancionDao.getAutoresMasEjecutados(
				idPais, anio, trimestre, 1, 10, null));
		
		model.addAttribute("autoresMasCobrados", datosCancionDao.getAutoresMasCobrados(
				idPais, anio, trimestre, 1, 10, null));
		
		List<FechaDestacada> fechasDestacadas = fechaDestacadaDao.getTodos();
		
		List<String> diasDestacados = new LinkedList<>();
		List<String> textoDiasDestacados = new LinkedList<>();
		
		for (FechaDestacada fecha : fechasDestacadas) {
			diasDestacados.add(formateadorFechas.format(fecha.getFecha()));
			textoDiasDestacados.add("'" + fecha.getDescripcion() + "'");
		}
		
		model.addAttribute("diasDestacados", diasDestacados);
		model.addAttribute("textoDiasDestacados", textoDiasDestacados);
			
		
		return "home";
		
	}

}
