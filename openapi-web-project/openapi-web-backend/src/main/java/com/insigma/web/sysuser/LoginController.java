package com.insigma.web.sysuser;

import com.insigma.facade.sysbase.SysUserFacade;
import com.insigma.facade.sysbase.vo.SysUserDTO;
import com.insigma.vo.ManagerVo;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import com.insigma.webtool.struct.CookieEnum;
import com.insigma.util.DateUtils;
import com.insigma.webtool.util.Encrypt;
import com.insigma.webtool.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import star.bizbase.vo.result.Results;
import star.fw.web.util.CookieHelper;
import star.modules.cache.JedisService;
import star.util.StringUtil;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 登录页
 * 
 * @author Administrator
 *
 */
@Controller
public class LoginController extends BasicController {

	/**
	 * 管理员用户接口对象
	 */
	@Autowired
	private SysUserFacade sysUserFacade;
	@Autowired
	private LoginComponent loginComponent;
	@Autowired
	private JedisService jedisService;

	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = { "/doLogin" })
	public ResultVo<SysUserDTO> doLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "loginName") String loginName,
                                        @RequestParam(name = "passWord")  String passWord) {
		ResultVo<SysUserDTO> result = Results.newResultVo();
		result.setSuccess(false);
//		if (StringUtil.isNotEmpty(code)) {				
//			String sessionId = request.getSession().getId();
//			String captcha = ImageCodeUtil.getImageCode(jedisService, sessionId);	
//			if (code.toUpperCase().equals(captcha)) {					
//				ImageCodeUtil.clearImageCode(jedisService, sessionId);
				SysUserDTO sysUser = sysUserFacade.getByLoginName(loginName);
				if (sysUser != null) {
					if(!"1".equals(sysUser.getUserType()) && !"3".equals(sysUser.getUserType())){
						return result = Results.setResult(false, "此用户没有权限");
					}
					if(!"1".equals(sysUser.getUserState())){
						return result = Results.setResult(false, "用户状态异常");
					}
					//前端加密，解密。。。		
					String desPassword = Encrypt.desEncrypt2(passWord);
					String hashPassword = StringUtil.getMD5(desPassword);
					if (null == hashPassword || !hashPassword.equalsIgnoreCase(sysUser.getPasswd())) {
						return Results.setResult(false, "密码不正确");
					}
					//用户过期时间
					Date userExpireDate = sysUser.getUserExpireDate();
					//当前时间
					int newTime = Integer.parseInt(DateUtils.getStringCurrentDate());
					if(null != userExpireDate){
						int userTime = Integer.parseInt(DateUtils.parseDate(userExpireDate, DateUtils.date3));
						if(!(userTime>newTime)){
							return Results.setResult(false, "用户已过期");
						}
					}
					//密码过期时间
					Date pwExpireDate = sysUser.getPwEditDate();
					if(null != pwExpireDate){
						int pwdTime = Integer.parseInt(DateUtils.parseDate(pwExpireDate, DateUtils.date3));
						String pwdType = sysUser.getPwExpireType();
						if(!StringUtil.isEmpty(pwdType)){
							if(pwdType.equals("3")){//指定日期
								if(!(pwdTime>newTime)){
									return Results.setResult(false, "密码已过期");
								}
								int tim3 = pwdTime-newTime;
								if(!(tim3>7)){//小于等于7天密码修改提醒
									result.setCode("0");
									result.setResult(sysUser);
									result.setSuccess(true);
									result.setResultDes("登录成功，密码即将到期请及时修改！");
								}
							}
						}
					}
					ManagerVo managerVo =  new ManagerVo(sysUser.getId(), sysUser.getLogonName(), sysUser.getPasswd(),sysUser.getDisplayName()) ;
					result.setCode("0");
					result.setResult(sysUser);
					result.setSuccess(true);
					result.setResultDes("登录成功");
					
					loginComponent.saveLogin(managerVo);
				} else {
					result = Results.setResult(false, "用户信息不存在");
				}

//			} else {
//				result = Results.setResult(false, "验证码错误");
//			}
//		}
		
		return result;
	}

	/**
	 * 跳登录页
	 */
	@ResponseBody
	@RequestMapping(value = { "/login" })
	public ResultVo<String> login(HttpServletRequest request, Model model, String cmd, String url) {
		ResultVo<String> result = Results.newResultVo();
		if ("logout".equals(cmd)) {
			CookieHelper.cancelCookie4Domains(CookieEnum.LOGIN.getValue());
			CookieHelper.cancelCookie4Domains(CookieEnum.OAUTH_LOGIN.getValue());
			ImageCodeUtil.clearImageCode(jedisService, request.getSession().getId());
			result.setCode("0");
	        result.setSuccess(true);
	        result.setResultDes("退出成功");
		}
		return result;
	}

}
