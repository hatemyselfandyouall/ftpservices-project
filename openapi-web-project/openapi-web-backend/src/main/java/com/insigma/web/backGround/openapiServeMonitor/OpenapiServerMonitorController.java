package com.insigma.web.backGround.openapiServeMonitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.facade.openapi.facade.OpenapiMonitoringDataConfigFacade;
import com.insigma.facade.openapi.po.OpenapiMonitoringDataConfig;
import com.insigma.facade.openapi.vo.InterfaceDetailVO;
import com.insigma.table.TableInfo;
import com.insigma.util.BIUtil;
import com.insigma.util.JSONUtil;
import com.insigma.web.BasicController;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import star.vo.result.ResultVo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/serverMonitor")
@Api(tags = "服务监控接口")
@Slf4j
public class OpenapiServerMonitorController extends BasicController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OpenapiDictionaryFacade openapiDictionaryFacade;
    @Autowired
    OpenapiMonitoringDataConfigFacade openapiMonitoringDataConfigFacade;


    @ApiOperation(value = "服务监控统计接口查询")
    @ResponseBody
    @RequestMapping(value = "/listCount", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo getStatisticCount(@RequestBody InterfaceDetailVO interfaceDetailVO) {
        ResultVo resultVo = new ResultVo();
        ResultVo temp1=BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.SERVER_MONITOR1, openapiDictionaryFacade, restTemplate);
        if (temp1.isSuccess()){
            ResultVo temp2=BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.SERVER_MONITOR2, openapiDictionaryFacade, restTemplate);
            if(temp2.isSuccess()){
                resultVo.setSuccess(true);
                resultVo.setResultDes("调用成功！");
                resultVo.setResult(Arrays.asList(temp1.getResult(),temp2.getResult()));
            }
        }
        return resultVo;
    }
    @ApiOperation(value = "服务监控列表查询")
    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo getStatisticList(@RequestBody InterfaceDetailVO interfaceDetailVO) {
        ResultVo resultVo = new ResultVo();
        //where r.interface_name like '%rr%'
        if(!interfaceDetailVO.getWhereWord().isEmpty()){
            Map<String,Object> map = new HashMap();
            map.put("interface_name","where r.interface_name like '%" + interfaceDetailVO.getWhereWord().get("interface_name")+"%'");
            interfaceDetailVO.setWhereWord(map);
        }
        resultVo =  BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.SERVER_MONITOR_LIST, openapiDictionaryFacade, restTemplate);
        //获取接口中的预警信息
        List<OpenapiMonitoringDataConfig> openapiMonitoringDataConfigs=openapiMonitoringDataConfigFacade.getConfigList();
        //字段转map,key为interfaceId   interface_id : 1
        Map<Long,OpenapiMonitoringDataConfig> openapiMonitoringDataConfigMap=openapiMonitoringDataConfigs.
                stream().collect(Collectors.toMap(i->i.getInterfaceId(),i->i));
        //遍历，在resultVO的json中添加字段
        TableInfo tableInfo = (TableInfo)resultVo.getResult();
        OpenapiMonitoringDataConfig openapiMonitoringDataConfig = new   OpenapiMonitoringDataConfig ();
        for(Object tableData : tableInfo.getTableDatas()){
            JSONObject tableDataJson = (JSONObject)tableData;
             openapiMonitoringDataConfig = openapiMonitoringDataConfigMap.get(tableDataJson.getLong("interface_id"));
            if(null!=openapiMonitoringDataConfig) {
                tableDataJson.put("averageCallTime", openapiMonitoringDataConfig.getAverageCallTime());          //平均调用时长大于
                tableDataJson.put("singleCallDuration", openapiMonitoringDataConfig.getSingleCallDuration());   //单次调用时长
                tableDataJson.put("warningInterval", openapiMonitoringDataConfig.getWarningInterval());       //预警时间间隔
                tableDataJson.put("alarmInterval", openapiMonitoringDataConfig.getAlarmInterval());          //报警时间间隔
                tableDataJson.put("numberOfFailures", openapiMonitoringDataConfig.getNumberOfFailures());   //一小时失败次数大于
            }
        }
       // resultVo.setResult(tableInfo);
        return resultVo;
    }
    @ApiOperation(value = "接口调用详情")
    @ResponseBody
    @RequestMapping(value = "/getDetailbyId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo getDetailById(@RequestBody InterfaceDetailVO interfaceDetailVO) {
        ResultVo resultVo = new ResultVo();
        //where r.interface_id='3' and r.requester_name like '%等待%'
        if(!interfaceDetailVO.getWhereWord().isEmpty()){
            if(interfaceDetailVO.getWhereWord().get("interface_id") != null){
                Map<String,Object> map = new HashMap();
                if(interfaceDetailVO.getWhereWord().get("requester_name") != null){
                    map.put("condition","where r.interface_id = '" + interfaceDetailVO.getWhereWord().get("interface_id")+"' and r.requester_name like '%" + interfaceDetailVO.getWhereWord().get("requester_name")+"%'");
                }
                map.put("condition","where r.interface_id = '" + interfaceDetailVO.getWhereWord().get("interface_id")+"'");
                interfaceDetailVO.setWhereWord(map);
                resultVo =  BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.INTERFACE_DETAIL, openapiDictionaryFacade, restTemplate);
            }
        }
        return resultVo;
    }
    @ApiOperation(value = "应用查看详情")
    @ResponseBody
    @RequestMapping(value = "/getDetailbyOrgNo", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo getDetailByOrgNo(@RequestBody InterfaceDetailVO interfaceDetailVO) {
        ResultVo resultVo = new ResultVo();
        //where t.org_no='330000101007'
        if(!interfaceDetailVO.getWhereWord().isEmpty()){
            if(interfaceDetailVO.getWhereWord().get("org_no") != null){
                Map<String,Object> map = new HashMap();
                map.put("condition","where t.org_no = '" + interfaceDetailVO.getWhereWord().get("org_no")+"'");
                interfaceDetailVO.setWhereWord(map);
            }
            resultVo =  BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.CALL_DETAIL, openapiDictionaryFacade, restTemplate);
        }
        return resultVo;
    }

    @ApiOperation(value = "服务监控折线统计")
    @ResponseBody
    @RequestMapping(value = "/getLineStatisticList", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo getLineStatisticList(@RequestBody InterfaceDetailVO interfaceDetailVO) {
        ResultVo resultVo = new ResultVo();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -11);
        resultVo = BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.SERVER_MONITOR_LINE, openapiDictionaryFacade, restTemplate);
        TableInfo tableInfo = (TableInfo) resultVo.getResult();
        List<JSONObject>  list = new ArrayList<>();
        if(tableInfo != null){
        for (int i = 0; i < 12; i++) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
            String nowStr = df.format(calendar.getTime());
            JSONObject data = new JSONObject();
            data.put("createHour",nowStr);
            data.put("callInHour",0);
            for (Object tableData : tableInfo.getTableDatas()) {
                JSONObject tableDataJson = (JSONObject) tableData;
                if(nowStr.equals(tableDataJson.getString("createHour"))){
                    data.put("callInHour",tableDataJson.getInteger("callInHour"));
                    break;
                }
            }
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            list.add(data);
         }
        }
        resultVo.setResult(list);
        return resultVo;
    }

    @ApiOperation(value = "服务监控柱状统计")
    @ResponseBody
    @RequestMapping(value = "/getColumnarStatisticList", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo getColumnarStatisticList(@RequestBody InterfaceDetailVO interfaceDetailVO) {
        ResultVo resultVo = new ResultVo();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -15);
        resultVo = BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.SERVER_MONITOR_COLUMNAR, openapiDictionaryFacade, restTemplate);
        TableInfo tableInfo = (TableInfo) resultVo.getResult();
        List<JSONObject>  list = new ArrayList<>();
        if(tableInfo != null) {
            for (int i = 0; i < 16; i++) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
                String nowStr = df.format(calendar.getTime());
                JSONObject data = new JSONObject();
                data.put("createHour", nowStr);
                data.put("failureInhour", 0);
                for (Object tableData : tableInfo.getTableDatas()) {
                    JSONObject tableDataJson = (JSONObject) tableData;
//                if(!nowStr.equals(tableDataJson.getString("createHour"))){
//                    tableDataJson.put(nowStr,0);
//                    break;
//                }
                    if (nowStr.equals(tableDataJson.getString("createHour"))) {
                        data.put("failureInhour", tableDataJson.getInteger("failureInhour"));
                        break;
                    }
                }
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                list.add(data);
            }
        }
        resultVo.setResult(list);
        return resultVo;
    }

}
