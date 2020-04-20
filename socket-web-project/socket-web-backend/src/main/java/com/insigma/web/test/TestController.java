package com.insigma.web.test;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

	@RequestMapping("/ajax" )
	public String ajax(ModelMap modelMap) {
		return "test/ajax";
	}




}
