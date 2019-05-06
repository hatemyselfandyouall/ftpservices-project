package com.insigma.web.root;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insigma.web.BasicController;

@Controller
@RequestMapping("/page")
public class PageIndexController extends BasicController {
	
	@RequestMapping(value = "/index.html")
	public String list(Model model) {
		return "/page/index";

	}
	
}
