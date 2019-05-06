package com.insigma.web.root;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insigma.web.BasicController;

/**
 * 
 * 
 * 
 * Title: 首页
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 上午10:41:10
 * @version $Id: IndexController.java 89113 2015-06-13 10:17:14Z zhjy $
 */
@Controller
public class IndexController extends BasicController {

	@RequestMapping(value = { "/", "index" })
	public String index(ModelMap modelMap) {
		return "root/index";
	}

}
