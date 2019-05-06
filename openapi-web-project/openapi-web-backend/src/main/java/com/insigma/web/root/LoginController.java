package com.insigma.web.root;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import com.insigma.webtool.struct.CookieEnum;

import star.config.Config;
import star.fw.web.util.CookieHelper;
//import com.guang.webtool.struct.CookieEnum;
//import star.fw.web.util.CookieHelper;
//import star.fw.web.util.RequestUtil;
import star.modules.cache.JedisService;

/**
 * 
 * 
 * 
 * Title: 登录页
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 上午11:20:51
 * @version $Id: LoginController.java 86424 2015-03-19 09:20:26Z yes $
 */
@Controller
public class LoginController extends BasicController {
	
	private final static String ENVIRONMENT = Config.getString("sys.env.tag");
	
//	/**
//	 * 管理员用户接口对象
//	 */
//	@Autowired
//	private ManagerFacade sysUserFacade;
	@Autowired
	private LoginComponent loginComponent;
	@Autowired
	private JedisService jedisService;

	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = { "/doLogin"})
	public Model doLogin(HttpServletRequest request, Model model, HttpServletResponse response, 
			String username, String password, String code) {
//		ResultVo<ManagerVo> result=Results.newResultVo();	
//		
//		if ("dev".equals(ENVIRONMENT) || StringUtil.isNotEmpty(code)) {				
//			String sessionId = request.getSession().getId();
//			String captcha = ImageCodeUtil.getImageCode(jedisService, sessionId);	
//			if ("dev".equals(ENVIRONMENT) || code.toUpperCase().equals(captcha)) {					
//				ImageCodeUtil.clearImageCode(jedisService, sessionId);
//				ManagerVo sysUser = sysUserFacade.getManagerByUserName(username);
//				if (sysUser != null) {
//					if (sysUser.getDel().equals(SysUserEnum.DEL_DELETED.value())) {
//						result = Results.setResult(false, "用户信息已经被删除");
//						model.addAttribute("ret", result);
//						return model;
//					}				
//					String desPassword = Encrypt.desEncrypt2(password);
//					String hashPassword = StringUtil.getMD5(desPassword);
//					if (null == hashPassword || !hashPassword.equalsIgnoreCase(sysUser.getPasswd())) {
//						result = Results.setResult(false, "密码不正确");
//						model.addAttribute("ret", result);
//						return model;
//					}
//					
//					ManagerVo managerVo = new ManagerVo();
//					managerVo.setRealname(sysUser.getRealname());
//					result = Results.setResult(true,managerVo);
//					
//					model.addAttribute("ret", result);
//					loginComponent.saveLogin(sysUser);
//				} else {
//					result = Results.setResult(false, "用户信息不存在");
//					model.addAttribute("ret", result);
//				}
//
//			} else {
//				result = Results.setResult(false, "验证码错误");
//				model.addAttribute("ret", result);
//			}
//		}
		
		return model;
	}
	
	/**
	 * 跳登录页
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request, Model model,String cmd,String url){
		
		if ("logout".equals(cmd)) {
			CookieHelper.cancelCookie(CookieEnum.LOGIN.getValue());
			ImageCodeUtil.clearImageCode(jedisService, request.getSession().getId());
		}
		
		return "/root/login";
	}
}
