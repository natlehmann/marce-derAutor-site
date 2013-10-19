package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.AutorDao;

@Controller
public class HomeController {
	
	@Autowired
	private AutorDao autorDao;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("lista", autorDao.getTodos());
		return "home";
	}

}
