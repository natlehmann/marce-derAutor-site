package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.SessionParam;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FechaDestacadaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasCobradosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasEjecutadosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.FechaDestacada;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.MontoTotal;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.dto.CeldaGrafico;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.dto.ColumnaGrafico;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.dto.FilaGrafico;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.dto.Grafico;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.dto.ColumnaGrafico.TipoColumna;

@Controller
public class HomeController {
	
	private static Log log = LogFactory.getLog(HomeController.class);
	
	@Autowired
	private DatosCancionDao datosCancionDao;
	
	@Autowired
	private FechaDestacadaDao fechaDestacadaDao;
	
	@Autowired
	private RankingArtistasMasCobradosDao rankingArtistasMasCobradosDao;
	
	@Autowired
	private RankingArtistasMasEjecutadosDao rankingArtistasMasEjecutadosDao;
	
	private SimpleDateFormat formateadorFechas = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@RequestMapping("/home")
	public String home(ModelMap model, HttpSession session) {
		
		return filtrar( (Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()), 
				model, session);
	}
	
	@RequestMapping("/home/filtrar")
	public String filtrar(@RequestParam(value="pais", required=false, defaultValue="") Long idPais,
			@RequestParam(value="anio", required=false, defaultValue="") Integer anio,
			@RequestParam(value="trimestre", required=false, defaultValue="") Integer trimestre,
			ModelMap model, HttpSession session) {
		
		
		model.addAttribute("paises", datosCancionDao.getPaises());
		model.addAttribute("anios", datosCancionDao.getAnios());
		
		session.setAttribute(SessionParam.PAIS.toString(), idPais);
		session.setAttribute(SessionParam.ANIO.toString(), anio);
		session.setAttribute(SessionParam.TRIMESTRE.toString(), trimestre);
		
		model.addAttribute("autoresMasEjecutados", rankingArtistasMasEjecutadosDao.getAutoresMasEjecutados(
				idPais, anio, trimestre, 0, 10, null));
		
		model.addAttribute("autoresMasCobrados", rankingArtistasMasCobradosDao.getAutoresMasCobrados(
				idPais, anio, trimestre, 0, 10, null));
		
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
	
	@RequestMapping("/home/grafico")
	@ResponseBody
	public Grafico getGraficoEstadisticas() {
		
		List<MontoTotal> montosSACM = datosCancionDao.getMontosTotalesSACMPorAnio(null, null);
		
		List<MontoTotal> otrosMontos = datosCancionDao.getMontosOtrosTotalesPorAnio(null, null);
		
		Grafico grafico = new Grafico();
		
		grafico.agregarColumna(new ColumnaGrafico("Año"));
		grafico.agregarColumna(new ColumnaGrafico("Distribución", TipoColumna.NUMERICO));
		grafico.agregarToolTip();
		grafico.agregarColumna(new ColumnaGrafico("SACM", TipoColumna.NUMERICO));
		grafico.agregarToolTip();
		
		for (int i = 0; i < 3; i++) {
			
			MontoTotal montoSACM = montosSACM.get(i);
			MontoTotal otroMonto = otrosMontos.get(i);
			
			double total = montoSACM.getMonto() + otroMonto.getMonto();
			
			grafico.agregarFila(new FilaGrafico(
					new CeldaGrafico<String>(String.valueOf(montoSACM.getAnio())),
					
					new CeldaGrafico<Double>(otroMonto.getMonto()),
					
					new CeldaGrafico<String>(Utils.formatear(otroMonto.getMonto()) + " (" 
							+ Utils.formatear(otroMonto.calcularPorcentaje(total)) + "%)"),
							
					new CeldaGrafico<Double>(montoSACM.getMonto()),
					
					new CeldaGrafico<String>(Utils.formatear(montoSACM.getMonto()) + " (" 
							+ Utils.formatear(montoSACM.calcularPorcentaje(total)) + "%)")));
		}
		
		return grafico;
		
	}

}
