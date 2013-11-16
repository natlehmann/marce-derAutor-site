package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.FechaDestacadaDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DataTablesResponse;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.FechaDestacada;

@Controller
@RequestMapping("/admin/fechaDestacada")
public class FechaDestacadaController {
	
	@Autowired
	private FechaDestacadaDao fechaDestacadaDao;
	
	@RequestMapping("listar")
	public String listar(ModelMap model, HttpServletRequest request) {
		
//		model.addAttribute("listado", fechaDestacadaDao.getTodos());
		return "admin/fechaDestacada_listar";
	}
	
	@ResponseBody
	@RequestMapping("/listar_ajax")
	public DataTablesResponse listar(HttpServletRequest request) {
		
		
		System.out.println("iDisplayStart " + request.getParameter("iDisplayStart"));
		System.out.println("iDisplayLength " + request.getParameter("iDisplayLength"));
		System.out.println("iSortingCols " + request.getParameter("iSortingCols"));
		
		System.out.println("iSortCol_0 " + request.getParameter("iSortCol_0"));
		System.out.println("sSearch " + request.getParameter("sSearch"));
		
		
		List<FechaDestacada> fechas = fechaDestacadaDao.getTodos();
		
		DataTablesResponse resultado = new DataTablesResponse(fechas, 1L, fechas.size());
		
		return resultado;
	}

}
