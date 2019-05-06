package com.insigma.webtool.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.insigma.webtool.component.LoginComponent;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private LoginComponent loginComonent;
	
//	@Autowired
//	private PurviewFacade purviewFacade;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if (uri.equals("/") || uri.equals("")) {
			request.getRequestDispatcher("/index.html").forward(request, response);
			return false;
		}
		
		int userId = loginComonent.getLoginUserId();
//		if (!purviewFacade.getAuthByUrl(userId, uri)) {
//			throw new PermissionException("权限不足");
//		}
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

}
