package ar.com.marcelomingrone.derechosAutor.estadisticas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

	@RequestMapping("/")
	public String home() {
		return "/admin/home";
	}
}
