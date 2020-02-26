package com.insigma.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/ajax" )
	public String ajax(ModelMap modelMap) {
		return "test/ajax";
	}

	public static void main(String[] args) {
		for (int i=0;i<=3;i++){
			System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
		}
	}
}
