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
		
		Grafico grafico = new Grafico();
		
		grafico.agregarColumna(new ColumnaGrafico("Year"));
		grafico.agregarColumna(new ColumnaGrafico("Sales", TipoColumna.NUMERICO));
		grafico.agregarToolTip();
		grafico.agregarColumna(new ColumnaGrafico("Expenses", TipoColumna.NUMERICO));
		grafico.agregarToolTip();
		
		grafico.agregarFila(new FilaGrafico(
				new CeldaGrafico<String>("2004"), 
				new CeldaGrafico<Integer>(1000),
				new CeldaGrafico<String>(1000 + " (60%)"),
				new CeldaGrafico<Integer>(400),
				new CeldaGrafico<String>(400 + " (40%)")));
		
		grafico.agregarFila(new FilaGrafico(
				new CeldaGrafico<String>("2005"), 
				new CeldaGrafico<Integer>(876),
				new CeldaGrafico<String>(1000 + " (30%)"),
				new CeldaGrafico<Integer>(123),
				new CeldaGrafico<String>(1000 + " (60%)")));
		
		grafico.agregarFila(new FilaGrafico(
				new CeldaGrafico<String>("2006"), 
				new CeldaGrafico<Integer>(986),
				new CeldaGrafico<String>(1000 + " (43%)"),
				new CeldaGrafico<Integer>(345),
				new CeldaGrafico<String>(1000 + " (43%)")));
		
		return grafico;
		
	}

}
