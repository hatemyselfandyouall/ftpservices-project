package com.insigma.util;

import com.alibaba.fastjson.JSONObject;
import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.facade.operatelog.facade.SysOperatelogRecordFacade;
import com.insigma.facade.operatelog.vo.SysOperatelogRecord.SysOperatelogRecordSaveVO;
import com.insigma.table.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import star.fw.web.util.CookieHelper;
import star.fw.web.util.RequestUtil;
import star.fw.web.util.ServletAttributes;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class AddLogUtil {

    public static void addLog(String url,String ip,String userName,Long userId,String operate,String operateDetail,String systemName,String moduleName,SysOperatelogRecordFacade sysOperatelogRecordFacade) {
        try {
            SysOperatelogRecordSaveVO saveVO=new SysOperatelogRecordSaveVO().setUrl(url).
                    setIp(ip).setOperatorName(userName)
                    .setOperatorId(userId)
                    .setOperatorDate(new Date()).setOperation(operate)
                    .setOperateContent(operateDetail)
                    .setBelongSystem(systemName)
                    .setBelongModule(moduleName);
            sysOperatelogRecordFacade.saveSysOperatelogRecord(saveVO);
        }catch (Exception e){
            log.error("添加操作日志异常",e);
        }
    }


    public static void addLog(HttpServletRequest request, String userName, Long userId, String operate, String operateDetail, String systemName, String moduleName, SysOperatelogRecordFacade sysOperatelogRecordFacade) {
        String url=request.getRequestURI();
        String ip= RequestUtil.getRealIp(ServletAttributes.getRequest());
        addLog(url,ip,userName,userId,operate,operateDetail,systemName,moduleName,sysOperatelogRecordFacade);
    }


}
