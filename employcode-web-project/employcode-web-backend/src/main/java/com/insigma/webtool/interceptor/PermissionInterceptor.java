package com.insigma.webtool.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.facade.sysbase.SysUserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.insigma.webtool.component.LoginComponent;
import star.bizbase.exception.BizRuleException;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private LoginComponent loginComonent;

	@Autowired
	private SysUserFacade sysUserFacade;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if (uri.equals("/") || uri.equals("")) {
			request.getRequestDispatcher("/index.html").forward(request, response);
			return false;
		}

		Long userId = loginComonent.getLoginUserId();
//		if (!sysUserFacade.getAuthByUrlAndUserId(uri, userId)) {
//			throw new BizRuleException("权限不足");
//		}
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

}
