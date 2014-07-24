package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

	@RequestMapping("/")
	public String home() {
		return "/admin/home";
	}
	
	@RequestMapping("/cache/borrar")
	@CacheEvict(allEntries=true, value={"rankingMasCobrados", "rankingMasCobradosCount", "montosPorAutor",
			"rankingMasEjecutados", "rankingMasEjecutadosCount", "ejecucionesPorAutor", "paises",
			"fuentes", "derechos", "derechosExternos", "anios", "autores", "canciones", "cancionesCount",
			"montosSACMPorAnio", "montosOtrosPorAnio", "montosSACMPorTrimestre", "montosOtrosPorTrimestre",
			"montosSACMPorPais", "montosOtrosPorPais", "montosPorFuente"})
	public String borrarCache(ModelMap model) {
		model.addAttribute("msg", "El cache ha sido borrado exitosamente.");
		return "/admin/home";
	}
}
