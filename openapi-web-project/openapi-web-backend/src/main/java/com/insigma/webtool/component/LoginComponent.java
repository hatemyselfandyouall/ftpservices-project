package com.insigma.webtool.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.insigma.webtool.struct.CookieEnum;

import star.fw.web.util.CookieHelper;
import star.fw.web.util.CookieHelper.CookieTime;
import star.util.DateUtil;
import star.util.NumberUtil;

/**
 * 
 * 
 * 
 * Title: 登录帮助类
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 下午1:17:18
 * @version $Id: LoginComponent.java 130377 2016-01-28 02:52:02Z zhjy $
 */
@Component
public class LoginComponent {

//	@Autowired
//	private ManagerFacade sysUserFacade;

	/**
	 * 判断是否为登录状态
	 * 
	 * @return
	 */
	public boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
		String[] values = CookieHelper.getToken4Cookie(CookieEnum.LOGIN.getValue());
		if (values.length == 4) {
//			TODO 加一层保护，如果Cookie泄漏，那么这个cookie如果没有继续操作的话，有效期与登入时常一致，By zhujy
			long outtime=NumberUtil.parseLong(values[3]);
			if(DateUtil.dateDiff("s", outtime, System.currentTimeMillis())>CookieTime.TIME_LONGIN.getTime()){
				return false;
			}
			
//			ManagerVo uservo = sysUserFacade.getManagerById(Integer.parseInt(values[0]));
//
//			if (uservo != null && uservo.getName().equals(values[1])
//					&& StringUtil.getMD5(uservo.getPasswd()).equals(values[2])) {
//				request.setAttribute("sysUser", uservo);
//				saveLogin(uservo);
//				return true;
//			}
		}
		return false;
	}

	public Integer getLoginUserId() {
		String[] values = CookieHelper.getToken4Cookie(CookieEnum.LOGIN.getValue());
		if (values != null && values.length > 0) {
			return Integer.parseInt(values[0]);
		}
		return null;
	}
	
	public String getLoginUserName() {
		String[] values = CookieHelper.getToken4Cookie(CookieEnum.LOGIN.getValue());
		if (values != null && values.length >= 2) {
			return values[1];
		}
		return null;
	}


//	public void saveLogin(ManagerVo sysUser) {
//		CookieHelper.saveToken2Cookie(CookieEnum.LOGIN.getValue(),
//				new String[] { String.valueOf(sysUser.getId()), sysUser.getName(),
//						StringUtil.getMD5(sysUser.getPasswd()), String.valueOf(System.currentTimeMillis()) },
//				CookieTime.TIME_LONGIN);
//	}

}
