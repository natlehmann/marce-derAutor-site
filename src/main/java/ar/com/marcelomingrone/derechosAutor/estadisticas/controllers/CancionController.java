package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.Params;
import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils.SessionParam;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.AutorDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.DatosCancionExternoDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Autor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.RankingCancion;

@Controller
public class CancionController {
	
	@Autowired
	private DatosCancionExternoDao datosCancionDao;
	
	@Autowired
	private AutorDao autorDao;
	
	
	@RequestMapping("/canciones")
	public String canciones(ModelMap model, HttpSession session) {
		
		return filtrar( (Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()),
				(Long) session.getAttribute(SessionParam.AUTOR.toString()),
				model, session);
	}
	
	@RequestMapping("/canciones/{autorId}")
	public String canciones(@PathVariable("autorId") Long idAutor, 
			ModelMap model, HttpSession session) {
		
		return filtrar( (Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()),
				idAutor, model, session);
	}
	
	@RequestMapping("/canciones/filtrar")
	public String filtrar(@RequestParam(value="pais", required=false, defaultValue="") Long idPais,
			@RequestParam(value="anio", required=false, defaultValue="") Integer anio,
			@RequestParam(value="trimestre", required=false, defaultValue="") Integer trimestre,
			@RequestParam(value="autor", required=false, defaultValue="") Long idAutor,
			ModelMap model, HttpSession session) {
		
		session.setAttribute(SessionParam.PAIS.toString(), idPais);
		session.setAttribute(SessionParam.ANIO.toString(), anio);
		session.setAttribute(SessionParam.TRIMESTRE.toString(), trimestre);
		session.setAttribute(SessionParam.AUTOR.toString(), idAutor);
		
		model.addAttribute("paises", datosCancionDao.getPaises());
		model.addAttribute("anios", datosCancionDao.getAnios());
		
		if (idAutor != null) {
			Autor autor = autorDao.buscar(idAutor);
			model.addAttribute("nombreAutor", autor != null ? autor.getNombre() : "");
		}
		
		return "canciones";
	}
	
	
	@RequestMapping("canciones_ajax")
	@ResponseBody
	public DataTablesResponse cancionesAjax(HttpServletRequest request, HttpSession session) {		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		List<RankingCancion> listado = datosCancionDao.getCanciones(
				(Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()),
				(Long)session.getAttribute(SessionParam.AUTOR.toString()), 
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO));
		
		long totalFiltrados = datosCancionDao.getCantidadCanciones(
				(Long)session.getAttribute(SessionParam.PAIS.toString()), 
				(Integer)session.getAttribute(SessionParam.ANIO.toString()), 
				(Integer)session.getAttribute(SessionParam.TRIMESTRE.toString()),
				(Long)session.getAttribute(SessionParam.AUTOR.toString()), 
				(String)params.get(Params.FILTRO));
		
		long total = datosCancionDao.getCantidadCanciones(
				null, null, null, (Long)session.getAttribute(SessionParam.AUTOR.toString()), null);
		
		DataTablesResponse resultado = new DataTablesResponse(
				listado, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}

}
