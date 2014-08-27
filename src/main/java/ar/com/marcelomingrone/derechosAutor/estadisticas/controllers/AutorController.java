package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.Params;
import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.SessionParam;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.AutorDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasCobradosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.RankingArtistasMasEjecutadosDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Ranking;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Autor;

@Controller
public class AutorController {
	
	@Autowired
	private DatosCancionDao datosCancionDao;
	
	@Autowired
	private RankingArtistasMasCobradosDao rankingArtistasMasCobradosDao;
	
	@Autowired
	private RankingArtistasMasEjecutadosDao rankingArtistasMasEjecutadosDao;
	
	@Autowired
	private AutorDao autorDao;
	

	@RequestMapping("/autoresMasEjecutados")
	public String autoresMasEjecutados(ModelMap model, HttpSession session) {
		
		return filtrar( (Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()), 
				model, session);
	}
	
	@RequestMapping("/autoresMasEjecutados/filtrar")
	public String filtrar(@RequestParam(value="pais", required=false, defaultValue="") Long idPais,
			@RequestParam(value="anio", required=false, defaultValue="") Integer anio,
			@RequestParam(value="trimestre", required=false, defaultValue="") Integer trimestre,
			ModelMap model, HttpSession session) {
		
		session.setAttribute(SessionParam.PAIS.toString(), idPais);
		session.setAttribute(SessionParam.ANIO.toString(), anio);
		session.setAttribute(SessionParam.TRIMESTRE.toString(), trimestre);
		
		model.addAttribute("paises", datosCancionDao.getPaises());
		model.addAttribute("anios", datosCancionDao.getAnios());
		
		return "autores_mas_ejecutados";
	}
	
	
	@RequestMapping("autoresMasEjecutados_ajax")
	@ResponseBody
	public DataTablesResponse autoresMasEjecutadosAjax(HttpServletRequest request, HttpSession session) {		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		List<Ranking> listado = rankingArtistasMasEjecutadosDao.getAutoresMasEjecutados(
				(Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()),
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO));
		
		long totalFiltrados = rankingArtistasMasEjecutadosDao.getCantidadAutoresMasEjecutados(
				(Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()),
				(String)params.get(Params.FILTRO));
		
		long total = rankingArtistasMasEjecutadosDao.getCantidadAutoresMasEjecutados(
				null, null, null, null);
		
		DataTablesResponse resultado = new DataTablesResponse(
				listado, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	
	
	
	@RequestMapping("/autoresMasCobrados")
	public String autoresMasCobrados(ModelMap model, HttpSession session) {
		
		return filtrarMasCobrados( (Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()), 
				model, session);
	}
	
	@RequestMapping("/autoresMasCobrados/filtrar")
	public String filtrarMasCobrados(
			@RequestParam(value="pais", required=false, defaultValue="") Long idPais,
			@RequestParam(value="anio", required=false, defaultValue="") Integer anio,
			@RequestParam(value="trimestre", required=false, defaultValue="") Integer trimestre,
			ModelMap model, HttpSession session) {
		
		session.setAttribute(SessionParam.PAIS.toString(), idPais);
		session.setAttribute(SessionParam.ANIO.toString(), anio);
		session.setAttribute(SessionParam.TRIMESTRE.toString(), trimestre);
		
		model.addAttribute("paises", datosCancionDao.getPaises());
		model.addAttribute("anios", datosCancionDao.getAnios());
		
		return "autores_mas_cobrados";
	}
	
	
	@RequestMapping("autoresMasCobrados_ajax")
	@ResponseBody
	public DataTablesResponse autoresMasCobradosAjax(HttpServletRequest request, HttpSession session) {		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		List<Ranking> listado = rankingArtistasMasCobradosDao.getAutoresMasCobrados(
				(Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()),
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO));
		
		long totalFiltrados = rankingArtistasMasCobradosDao.getCantidadAutoresMasCobrados(
				(Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()),
				(String)params.get(Params.FILTRO));
		
		long total = rankingArtistasMasCobradosDao.getCantidadAutoresMasCobrados(
				null, null, null, null);
		
		DataTablesResponse resultado = new DataTablesResponse(
				listado, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	
	@RequestMapping("/buscarAutorPorNombre")
	@ResponseBody
	public List<Autor> buscarAutorPorNombre(@RequestParam("autor")String nombreAutor) {
		
		return autorDao.getAutoresLikeNombre(nombreAutor);
		
	}
	
}
