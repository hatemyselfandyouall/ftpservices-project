package com.insigma.web.backGround.openapiSafeMonitor;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.insigma.facade.openapi.facade.OpenapiBlackWhiteFacade;
import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.facade.openapi.po.OpenapiBlackWhite;
import com.insigma.facade.openapi.vo.InterfaceDetailVO;
import com.insigma.facade.openapi.vo.OpenapiDictionary.OpenapiDictionaryUpdateVO;
import com.insigma.table.TableInfo;
import com.insigma.util.BIUtil;
import com.insigma.web.BasicController;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import star.util.StringUtil;
import star.vo.result.ResultVo;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/safeMonitor")
@Api(tags = "安全管控接口")
@Slf4j
public class OpenapiSafeMonitorController extends BasicController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OpenapiDictionaryFacade openapiDictionaryFacade;
    @Autowired
    OpenapiBlackWhiteFacade openapiBlackWhiteFacade;

    @ApiOperation(value = "更新访问次数")
    @ResponseBody
    @RequestMapping(value = "/updateVisitNum", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo updateVisitNum(@RequestBody OpenapiDictionaryUpdateVO updateVO) {
        ResultVo resultVo = new ResultVo();
      try{
          Integer flag =  openapiDictionaryFacade.updateOpenapiDictionary(updateVO);
          if (1 == flag) {
              resultVo.setResultDes("更新成功");
              resultVo.setSuccess(true);
          } else {
              resultVo.setResultDes("更新失败");
          }
      }catch (Exception e) {
          resultVo.setResultDes("更新异常");
          log.error("更新异常", e);
      }
        return resultVo;
    }

    @ApiOperation(value = "安全监控查询")
    @ResponseBody
    @RequestMapping(value = "/getSafeMonitorList", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo getSafeMonitorList(@RequestBody InterfaceDetailVO interfaceDetailVO) {
       ResultVo resultVo = new ResultVo();
        //获取所有ip是否为黑白名单
        List<OpenapiBlackWhite> list = openapiBlackWhiteFacade.getBlackWhiteList();
        List<OpenapiBlackWhite> list1 = new ArrayList<>();
        list1=list.stream().filter(i->i.getAddType().equals(Integer.valueOf(interfaceDetailVO.getWhereWord().get("is_black").toString()))).collect(Collectors.toList());
        if(!interfaceDetailVO.getWhereWord().isEmpty()){   //根据ip，是否加入黑白名单查询
            Map<String, Object> map = new HashMap();
            //根据ip地址模糊查询
            if(interfaceDetailVO.getWhereWord().get("requester_ip") != null) { //根据ip条件查询
                //where r.requester_ip='10.85.13.126'
                map.put("condition", "where r.requester_ip like '%" + interfaceDetailVO.getWhereWord().get("requester_ip") + "%'");
                if (interfaceDetailVO.getWhereWord().get("is_black") != null) { //根据ip条件查询   1白2黑
                    if (!CollectionUtils.isEmpty(list1)) {
                        String ips = list1.stream().map(
                                i -> {
                                    return "'" + i.getIpAddress() + "'";
                                }
                        ).reduce("", (a, b) -> a + "," + b);
                        ips = ips.substring(1, ips.length());
                        String ipWhere = " and r.requester_ip in (" + ips + ")";
                        map.put("condition", "where r.requester_ip like '%" + interfaceDetailVO.getWhereWord().get("requester_ip") + "%'" + ipWhere);
                    }
                }
            }else{
                if (interfaceDetailVO.getWhereWord().get("is_black") != null) {
                    if (!CollectionUtils.isEmpty(list1)) {
                        String ips =list1.stream().map(
                                i -> {
                                    return "'" + i.getIpAddress() + "'";
                                }
                        ).reduce("", (a, b) -> a + "," + b);
                        ips = ips.substring(1, ips.length());
                        String ipWhere = "where r.requester_ip in (" + ips + ")";
                        logger.info("打印："+ipWhere);
                        // map.put("condition","where r.request_ip in ("+list.stream().filter(i->i.getAddType().equals(interfaceDetailVO.getWhereWord().get("is_black"))).map(  i->{
                        //  return "'"+i.getIpAddress()+"'";
                        // }).reduce("",(a,b)->a+","+b)+")");
                        map.put("condition",ipWhere);
                    }

                }
            }
             interfaceDetailVO.setWhereWord(map);
            }
        //从BI中获取安全管控相关数据
        resultVo=BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.SAFE_MONITOR, openapiDictionaryFacade, restTemplate);
        //String ipWhere="and request_ip in("+list.stream().filter(i->i.getAddType().equals(2)).map(OpenapiBlackWhite::getIpAddress).reduce("",(a,b)->a+","+b)+")";
        //字段转map,key为ip
       // Map<Long,OpenapiBlackWhite> openapiMonitoringDataConfigMap=list.stream().collect(Collectors.toMap(i->i.getIpAddress(), i->i));
        Set<OpenapiBlackWhite> blackWhiteSet = new HashSet(list);
        TableInfo tableInfo = (TableInfo) resultVo.getResult();
        for (Object tableData : tableInfo.getTableDatas()) {
            JSONObject tableDataJson = (JSONObject) tableData;
            if( tableDataJson.getInteger("dayCallTimes") >= Integer.parseInt(openapiDictionaryFacade.getValueByCode(DataConstant.VISIT_NUM))){
                tableDataJson.put("is_abnormal","0");  //异常
            }else{
                tableDataJson.put("is_abnormal","1");  //没有异常
            }
            if (StringUtil.isNotEmpty(tableDataJson.getString("requester_ip"))) {
                for (OpenapiBlackWhite blackWhite : blackWhiteSet) {
                if (tableDataJson.getString("requester_ip").equals(blackWhite.getIpAddress())) {
                    tableDataJson.put("is_black",blackWhite.getAddType());   //1白2黑
                }
            }
         }
        }
        return resultVo;
    }

}
