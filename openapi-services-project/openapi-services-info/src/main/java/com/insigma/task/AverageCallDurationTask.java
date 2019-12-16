package com.insigma.task;

import com.alibaba.fastjson.JSONObject;
import com.insigma.facade.common.SendEmailFacade;
import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.facade.openapi.facade.OpenapiMonitoringDataConfigFacade;
import com.insigma.facade.openapi.po.OpenapiMonitoringDataConfig;
import com.insigma.table.TableInfo;
import com.insigma.util.BIUtil;
import com.taobao.diamond.extend.DynamicProperties;
import constant.DataConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import star.vo.result.ResultVo;

import java.util.*;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class AverageCallDurationTask {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OpenapiDictionaryFacade openapiDictionaryFacade;
    @Autowired
    OpenapiMonitoringDataConfigFacade openapiMonitoringDataConfigFacade;
    @Autowired
    SendEmailFacade sendEmailFacade;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void AverageCallDurationWarning() {
        //获取监控数据配置信息
        List<OpenapiMonitoringDataConfig> openapiMonitoringDataConfigs = openapiMonitoringDataConfigFacade.getConfigList();
        //获取到监控数据中所有的接口id
        Map<Long,OpenapiMonitoringDataConfig> openapiMonitoringDataConfigMap=openapiMonitoringDataConfigs.stream().collect(Collectors.toMap(i->i.getInterfaceId(),i->i));
       // Set<Long> interfaceIds=openapiMonitoringDataConfigs.stream().map(OpenapiMonitoringDataConfig::getInterfaceId).collect(Collectors.toSet());
       if(openapiMonitoringDataConfigMap.size()>0){
           String WarningStr="";
           Iterator it = openapiMonitoringDataConfigMap.entrySet().iterator();
           Map<String,Object> map = new HashMap();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Long id = (Long)entry.getKey();
                OpenapiMonitoringDataConfig dataConfig = (OpenapiMonitoringDataConfig)entry.getValue();
                if(id != null){
                   // where t.interface_id='1'conditions
                    map.put("conditions"," where t.interface_id = ' " + id +" ' ");
                }
            ResultVo  resultVo =  BIUtil.getRequestResult(1l, 90000l, map, "", DataConstant.AVERAGE_CALLDURATION_WARNING,openapiDictionaryFacade,restTemplate);
            TableInfo tableInfo = (TableInfo) resultVo.getResult();
          if(tableInfo.getTableDatas() != null && tableInfo.getTableDatas().size()>0){
             for(Object tableData : tableInfo.getTableDatas()){
                 JSONObject tableDataJson = (JSONObject)tableData;    //average_call_time
                if(tableDataJson.getDouble("avgDurationTimes") > dataConfig.getAverageCallTime()){
                    //接口前10分钟平均时长大于监控预警设置则发送邮件提醒
                    WarningStr = WarningStr+"接口id:"+tableDataJson.getLong("interface_id") + ",平均调用时长："+tableDataJson.getDouble("avgDurationTimes")+"ms <br/>";
                    }
                 }
              }
           }
           if(!"".equals(WarningStr)) {
               String addressee = DynamicProperties.staticProperties.getProperty("mail.addressee");
               String [] splitVal = addressee.split(",");
               for(String addresser : Arrays.asList(splitVal)){
                   sendEmailFacade.sendEmail(addresser, "开发平台服务监控接口平均调用时长报警", "你好!<br/>以下接口：<br/>" + WarningStr + "已达到报警指标，请及时处理。<br/> 祝：工作顺利！ <br/>" + new Date());
               }
           }
        }
     }
}
