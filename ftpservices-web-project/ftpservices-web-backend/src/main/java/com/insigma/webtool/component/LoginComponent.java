package com.insigma.webtool.component;

import com.alibaba.fastjson.JSONObject;
import com.insigma.facade.sysbase.SysUserFacade;
import com.insigma.facade.sysbase.vo.SysUserDTO;
import com.insigma.utils.FileUtil;
import com.insigma.vo.ManagerVo;
import com.insigma.webtool.struct.CookieEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import star.fw.web.util.CookieHelper;
import star.fw.web.util.CookieHelper.CookieTime;
import star.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Title: 登录帮助类
 * <p>
 * Description:
 * <p>
 * Copyright: (c) 2014
 *
 * @author haoxz11
 * @version $Id: LoginComponent.java 130377 2016-01-28 02:52:02Z zhjy $
 * @created 下午1:17:18
 */
@Component
@Slf4j
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
            long outtime = NumberUtil.parseLong(values[3]);
            if (DateUtil.dateDiff("s", outtime, System.currentTimeMillis()) > CookieTime.TIME_LONGIN.getTime()) {
                return false;
            }

            SysUserDTO sysUserDTO = sysUserFacade.getCacheByPrimaryKey(Long.parseLong(values[0]));
            if (sysUserDTO != null && StringUtil.isNotEmpty(sysUserDTO.getLogonName()) && sysUserDTO.getLogonName().equals(values[1])
//					&& StringUtil.getMD5(sysUserDTO.getPasswd()).equals(values[2])
                    && StringUtil.isNotEmpty(sysUserDTO.getPasswd()) && sysUserDTO.getPasswd().equals(values[2])
            ) {
                ManagerVo sysUser = new ManagerVo(sysUserDTO.getId(), sysUserDTO.getLogonName(), sysUserDTO.getDisplayName());
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
        values = CookieHelper.getToken4Cookie(CookieEnum.OAUTH_LOGIN.getValue());
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
        values = CookieHelper.getToken4Cookie(CookieEnum.OAUTH_LOGIN.getValue());
        if (values != null && values.length >= 2) {
            return values[1];
        }
        return null;
    }

    public void saveLogin(ManagerVo sysUser) {
        CookieHelper.saveToken2Cookie4Domains(CookieEnum.LOGIN.getValue(),
                new String[]{String.valueOf(sysUser.getId()), sysUser.getName(),
//						StringUtil.getMD5(sysUser.getPasswd()), 
                        sysUser.getPasswd(),
                        String.valueOf(System.currentTimeMillis())},
                CookieTime.TIME_LONGIN);
    }

    public boolean isLoginByH5(HttpServletRequest request, HttpServletResponse response) {
        return isLogin( request,  response) || isLoginByCas(request, response);
    }

    public boolean isLoginByCas(HttpServletRequest request, HttpServletResponse response) {
        try {
            //从cookie中获取ticket
            String value = CookieHelper.getCookie(CookieEnum.OAUTH_LOGIN.getValue());
            log.info("isLoginByCas ticket={}", value);
            if (StringUtil.isEmpty(value)) {
                if (StringUtil.isEmpty(value)) {
                    log.info("isLoginByCas getCatByUa ticket={}", value);
                    return false;
                }
            } else {
                return true;
            }

        } catch (Exception e) {
            log.error("校验异常" + e);
            return false;
        }
        return false;
    }

//    public boolean isLoginByCas(HttpServletRequest request, HttpServletResponse response) {
//        try {
//
//            //从cookie中获取ticket
//            String value = CookieHelper.getCookie(CookieEnum.OAUTH_LOGIN.getValue());
//            log.info("isLoginByCas ticket={}", value);
//            if (value == null) {
//                if (StringUtil.isEmpty(value)) {
//                    log.info("isLoginByCas getCatByUa ticket={}", value);
//                    return false;
//                }
//            } else {
//                return true;
//            }
//
//        } catch (Exception e) {
//            log.error("校验异常" + e);
//            return false;
//        }
//        return false;
//    }
public static void main(String[] args) throws Exception{
    RestTemplate template=new RestTemplate();
    JSONObject paramJson=new JSONObject();
    paramJson.put("colorEunm","YELLOW");
    paramJson.put("imgPath","http://10.85.159.203:10540/openapiApp/download?key=bizimg/rdm/158520730165694l.png");
    paramJson.put("text","虽然我可爱又迷人，但是我会带来死亡");
    ResponseEntity<Resource> resourceResponseEntity= template.postForEntity("http://10.85.94.238:10760/qrcode/textToIMG",paramJson, Resource.class);
    InputStream inputStream=resourceResponseEntity.getBody().getInputStream();
    FileUtil.getFileByUrl("tempget.jpg",inputStream);
    }
}
