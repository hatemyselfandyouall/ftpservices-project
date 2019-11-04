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
import com.insigma.facade.openapi.vo.InterfaceDetailVO;
import com.insigma.util.BIUtil;
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
@Api(tags = "BI接口转发接口")
@Slf4j
public class OpenapiServiceStatisticsController extends BasicController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OpenapiDictionaryFacade openapiDictionaryFacade;


    @ApiOperation(value = "BI接口转发接口")
    @ResponseBody
    @RequestMapping(value = "/InterfaceCallTrend/{code}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo<String> getInterfaceCallTrend(@PathVariable String code,@RequestBody InterfaceDetailVO interfaceDetailVO) {
        return BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), code, openapiDictionaryFacade, restTemplate);
    }


}
