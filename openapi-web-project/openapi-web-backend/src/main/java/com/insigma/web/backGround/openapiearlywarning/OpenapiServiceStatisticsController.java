package com.insigma.web.backGround.openapiearlywarning;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.insigma.config.RestTemplateConfig;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.dto.OpenapiEarlyWarningDto;
import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.facade.openapi.facade.OpenapiEarlyWarningFacade;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiDictionary;
import com.insigma.facade.openapi.po.OpenapiEarlyWarning;
import com.insigma.util.JSONUtil;
import com.insigma.web.BasicController;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import star.vo.result.ResultVo;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/openapiServiceStatistic")
@Api(tags ="服务监控分析相关接口")
@Slf4j
public class OpenapiServiceStatisticsController extends BasicController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OpenapiDictionaryFacade openapiDictionaryFacade;


    @ApiOperation(value = "接口调用趋势")
    @ResponseBody
    @RequestMapping(value = "/InterfaceCallTrend",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<String> getInterfaceCallTrend( @RequestParam(value = "pageNum", required = false) Long pageNum,
                                                     @RequestParam(value = "pageSize", required = false) Long pageSize){
        ResultVo resultVo=new ResultVo();
        try {
            String code= DataConstant.CODE_INTERFACE_CALLTREND;
            String testUrl=openapiDictionaryFacade.getValueByCode(code);
            Map<String,String> stringStringMap=new HashMap<>();
            stringStringMap.put("pageNum",pageNum+"");
            stringStringMap.put("pageSize",pageSize+"");
            ResponseEntity<String> result= RestTemplateConfig.getWithParamterMap(testUrl,stringStringMap,restTemplate);
            if (result.getStatusCode().is2xxSuccessful()){
                resultVo.setResult(JSONObject.parseObject(result.getBody()));
            }else {
                resultVo.setResultDes(result.getBody());
            }
        }catch (Exception e){
            resultVo.setResultDes("接口调用趋势异常");
            log.error("接口调用趋势异常",e);
        }
        return resultVo;
    }



}
