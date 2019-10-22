package com.insigma.webtool.component;

import com.insigma.facade.sysbase.SysUserFacade;
import com.insigma.facade.sysbase.vo.SysUserDTO;
import com.insigma.vo.ManagerVo;
import com.insigma.webtool.struct.CookieEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import star.fw.web.util.CookieHelper;
import star.fw.web.util.CookieHelper.CookieTime;
import star.util.DateUtil;
import star.util.NumberUtil;
import star.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@Autowired
	private SysUserFacade sysUserFacade;

	/**
	 * 判断是否为登录状态
	 * 
	 * @return
	 */
	public boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
		String[] values = CookieHelper.getToken4Cookie(CookieEnum.LOGIN.getValue());
		if (values.length == 4) {
//			 加一层保护，如果Cookie泄漏，那么这个cookie如果没有继续操作的话，有效期与登入时常一致，By zhujy
			long outtime=NumberUtil.parseLong(values[3]);
			if(DateUtil.dateDiff("s", outtime, System.currentTimeMillis())>CookieTime.TIME_LONGIN.getTime()){
				return false;
			}
			
			SysUserDTO sysUserDTO = sysUserFacade.getCacheByPrimaryKey(Long.parseLong(values[0]));
			if (sysUserDTO != null && StringUtil.isNotEmpty(sysUserDTO.getLogonName()) &&sysUserDTO.getLogonName().equals(values[1])
//					&& StringUtil.getMD5(sysUserDTO.getPasswd()).equals(values[2])
					&& StringUtil.isNotEmpty(sysUserDTO.getPasswd()) && sysUserDTO.getPasswd().equals(values[2])
					) {
				ManagerVo sysUser = new ManagerVo(sysUserDTO.getId(), sysUserDTO.getLogonName(), sysUserDTO.getDisplayName()) ;
				sysUser.setPasswd(sysUserDTO.getPasswd());
				request.setAttribute("sysUser", sysUser);
				saveLogin(sysUser);
				return true;
			}
		}
		return false;
	}

	public Long getLoginUserId() {
		String[] values = CookieHelper.getToken4Cookie(CookieEnum.LOGIN.getValue());
		if (values != null && values.length > 0) {
			return Long.parseLong(values[0]);
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

	public void saveLogin(ManagerVo sysUser) {
		CookieHelper.saveToken2Cookie4Domains(CookieEnum.LOGIN.getValue(),
				new String[] { String.valueOf(sysUser.getId()), sysUser.getName(),
//						StringUtil.getMD5(sysUser.getPasswd()), 
						sysUser.getPasswd(), 
						String.valueOf(System.currentTimeMillis()) },
				CookieTime.TIME_LONGIN);
	}

}
