package com.insigma.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/ajax" )
	public String ajax(ModelMap modelMap) {
		return "test/ajax";
	}

}
