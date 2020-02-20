package com.insigma.web.frontGround;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.github.pagehelper.PageInfo;
import com.insigma.config.SaveLogTask;
import com.insigma.facade.facade.CdGatewayRequestDetailBdFacade;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.facade.OpenapiAppFacade;
import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.facade.openapi.facade.OpenapiUserFacade;
import com.insigma.facade.openapi.po.*;
import com.insigma.facade.openapi.vo.OpenapiApp.OpenapiAppShowDetailVO;
import com.insigma.facade.openapi.vo.openapiInterface.OpenapiInterfaceListVO;
import com.insigma.facade.vo.CdGatewayRequestBodyBd.CdGatewayRequestBodyBdSaveVO;
import com.insigma.facade.vo.CdGatewayRequestDetailBd.CdGatewayRequestDetailBdSaveVO;
import com.insigma.facade.vo.CdGatewayRequestDetailBd.CdGatewayRequestVO;
import com.insigma.util.BIUtil;
import com.insigma.util.MD5Util;
import com.insigma.util.SignUtil;
import com.insigma.util.StringUtil;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.loadBalance.LoadBalanceUtils;
import com.insigma.webtool.component.loadBalance.UpstreamCacheManager;
import com.insigma.webtool.component.loadBalance.spi.LoadBalance;
import com.insigma.webtool.util.RestTemplateUtil;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.weaver.SignatureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import star.bizbase.util.StringUtils;
import star.common.open.utils.AesUtil;
import star.fw.web.mapper.JsonObjectMapper;
import star.util.DateUtil;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executor;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("frontInterface")
@Api(tags = "接口处理及转发")
@Slf4j
public class InterfaceController extends BasicController {

    @Autowired
    private  InterfaceFacade interfaceFacade;
    @Autowired
    private  OpenapiAppFacade openapiAppFacade;
    @Autowired
    private  JsonObjectMapper jacksonObjectMapper;
    @Autowired
    private  CdGatewayRequestDetailBdFacade cdGatewayRequestDetailBdFacade;
    @Autowired
    private  Executor taskExecutor;
    @Autowired
    private  OpenapiUserFacade openapiUserFacade;
    @Autowired
    private  UpstreamCacheManager upstreamCacheManager;
    @Autowired
    private OpenapiDictionaryFacade openapiDictionaryFacade;
    @Autowired
    RestTemplate restTemplate;



    @ApiOperation(value = "接口转发")
    @RequestMapping(value = {"/interface/{code}"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Object getOpenapiInterfaceList(
            @PathVariable String code,
            @RequestBody String paramString, HttpServletRequest httpServletRequest) {
        ResultVo resultVo = new ResultVo();
        CdGatewayRequestVO cdGatewayRequestVO = new CdGatewayRequestVO(new CdGatewayRequestDetailBdSaveVO(), new CdGatewayRequestBodyBdSaveVO());
        try {
            Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
            String ip = httpServletRequest.getHeader("X-Real-IP");
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setRequesterIp(ip);
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setInterfaceCode(code);
            JSONObject headerJSON = new JSONObject();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headerJSON.put(headerName, httpServletRequest.getHeader(headerName));
            }
            cdGatewayRequestVO.getCdGatewayRequestBodyBdSaveVO().setRequestHeader(headerJSON.toJSONString());
            cdGatewayRequestVO.getCdGatewayRequestBodyBdSaveVO().setRequestBody(paramString);
            String appKey = httpServletRequest.getHeader("appKey");
            String time = httpServletRequest.getHeader("time");
            String nonceStr = httpServletRequest.getHeader("nonceStr");
            String signature = httpServletRequest.getHeader("signature");
            String encodeType = httpServletRequest.getHeader("encodeType");
            if (StringUtils.isEmpty(appKey)) {
                log.error("appKey未提供,参数" + paramString);
                resultVo.setResultDes("appKey未提供！");
                saveFailRequestLog("appKey未提供", cdGatewayRequestVO);
                return resultVo;
            }
            OpenapiAppShowDetailVO openapiApp = openapiAppFacade.getAppByAppKey(appKey);
            if (openapiApp == null) {
                log.error("openapiApp不存在" + paramString);
                resultVo.setResultDes("openapiApp不存在！");
                saveFailRequestLog("openapiApp不存在", cdGatewayRequestVO);
                return resultVo;
            }
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setRequesterName(openapiApp.getName());
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setOrgNo(openapiApp.getOrgId()+"");
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setOrgName(openapiApp.getOrgName());
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setArea(openapiApp.getArea());
            if (CollectionUtils.isEmpty(openapiApp.getOpenapiInterfaces())){
                log.error("该应用没有任何接口" + paramString);
                resultVo.setResultDes("该应用没有任何接口！");
                saveFailRequestLog("该应用没有任何接口", cdGatewayRequestVO);
                return resultVo;
            }
            Map<String, OpenapiInterface> openapiInterfaceMap = openapiApp.getOpenapiInterfaces().stream().filter(i -> i != null && i.getCode() != null).collect(Collectors.toMap(i -> i.getCode(), i -> i));
            OpenapiInterface openapiInterface;
            if (openapiInterfaceMap.get(code) == null) {
                resultVo.setResultDes("接口code错误或无此接口权限！");
                log.error("接口code错误或无此接口权限" + paramString);
                saveFailRequestLog("接口code错误或无此接口权限", cdGatewayRequestVO);
                return resultVo;
            } else {
                openapiInterface = openapiInterfaceMap.get(code);
            }
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setInterfaceProducerName(openapiInterface.getProviderName());
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setInterfaceProducerType(openapiInterface.getProviderType());
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setTradeNo(openapiInterface.getCommandCode());
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setInterfaceId(openapiInterface.getId());
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setInterfaceName(openapiInterface.getName());
            String appSecret = openapiApp.getAppSecret();
            JSONObject checkSignResult = SignUtil.checkSign(paramString, appKey, time, nonceStr, signature, encodeType, appSecret);
            if (checkSignResult.getInteger("flag") != 1) {
                resultVo.setResultDes(checkSignResult.getString("msg"));
                log.error(checkSignResult.getString("msg") + paramString);
                saveFailRequestLog(checkSignResult.getString("msg"), cdGatewayRequestVO);
                return resultVo;
            }
            JSONObject paramsJSON = JSONObject.parseObject(paramString, Feature.OrderedField);
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setHasForward(1);
            List<OpenapiInterfaceInnerUrl>  innerUrls=upstreamCacheManager.findUpstreamListBySelectorId(openapiInterface.getId());
            if(CollectionUtils.isEmpty(innerUrls)){
                resultVo.setResultDes("没有通过心跳检测的内部url");
                log.error("没有通过心跳检测的内部url" + paramString);
                saveFailRequestLog("没有通过心跳检测的内部url", cdGatewayRequestVO);
                return resultVo;
            }
            String  randomWay=openapiDictionaryFacade.getValueByCode(DataConstant.RANDOM_ALGORITHM);
            OpenapiInterfaceInnerUrl openapiInterfaceInnerUrl= LoadBalanceUtils.selector(innerUrls,randomWay,ip);
            String innerurl=openapiInterfaceInnerUrl.getInnerUrl();
            log.info("开始进行接口转发，目标url为" + innerurl + ",参数为" + paramsJSON);
//            ResponseEntity result = RestTemplateUtil.postByMap(openapiInterfaceInnerUrl.getInnerUrl(), paramsJSON, String.class);
            Date start=new Date();
            if (!StringUtils.isEmpty(innerurl)){
                innerurl=innerurl.trim();
            }
            ResponseEntity result = BIUtil.postWithUrlParam(innerurl, paramsJSON, restTemplate);
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setRequestUseTime((int)(long)(new Date().getTime()-start.getTime()));
            cdGatewayRequestVO.getCdGatewayRequestBodyBdSaveVO().setResponseBody(result.toString());
            log.info("开始进行接口转发，返回值为" + result);
            return sendMessageBack(resultVo, result, cdGatewayRequestVO);
        } catch (Exception e) {
            resultVo.setResultDes("接口转发功能异常!原因为:" + e.getMessage());
            resultVo.setResult("接口转发功能异常!原因为:" + e.getMessage());
            log.error("获取接口列表异常", e);
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setIsForwardSuccess(0);
            saveLog(cdGatewayRequestVO);
        }
//        log.info("返回参数为" + resultVo);
        return resultVo;
    }

    private void saveFailRequestLog(String reason, CdGatewayRequestVO cdGatewayRequestVO) {
        cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setIsForwardSuccess(0);
        cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setHasForward(0);
        cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setNoForwardReason(reason);
        saveLog(cdGatewayRequestVO);
    }


    private Object sendMessageBack(ResultVo resultVo, ResponseEntity result, CdGatewayRequestVO cdGatewayRequestVO) {
        cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setResultCode(result.getStatusCode().value());
        if (result == null || !HttpStatus.OK.equals(result.getStatusCode())) {
            if (HttpStatus.GATEWAY_TIMEOUT.equals(result.getStatusCode())||HttpStatus.REQUEST_TIMEOUT.equals(result.getStatusCode())){
                cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setIsOvertime(1);
            }else {
                cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setIsOvertime(0);
            }
                cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setIsForwardSuccess(0);
            resultVo.setResultDes("获得了异常的返回码！返回信息为：" + result);
            resultVo.setResult("获得了异常的返回码！返回信息为：" + result);
            resultVo.setSuccess(false);
            saveLog(cdGatewayRequestVO);
            return resultVo;
        } else {
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setIsOvertime(0);
            cdGatewayRequestVO.getCdGatewayRequestDetailBdSaveVO().setIsForwardSuccess(1);
            saveLog(cdGatewayRequestVO);
            return result;
        }
    }

    private void saveLog(CdGatewayRequestVO cdGatewayRequestVO) {
        try {
            taskExecutor.execute(new SaveLogTask(cdGatewayRequestDetailBdFacade, cdGatewayRequestVO));
        }catch (Exception e){
            log.error("保存日志功能异常",e);
        }
    }

    @ApiOperation(value = "接口转发")
    @RequestMapping(value = "/checkSignVaild", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo getOpenapiInterfaceList(@RequestBody JSONObject params) {
        ResultVo resultVo = new ResultVo();
        try {
            JSONObject resultParam = interfaceFacade.checkSignVaild(params);
            if (resultParam != null && resultParam.getInteger("flag") == 1) {
                resultVo.setSuccess(true);
                resultVo.setResult(resultParam);
            } else {
                resultVo.setResult(resultParam);
            }
        } catch (Exception e) {
            log.error("验签异常", e);
            resultVo.setResultDes("验签过程异常");
        }
        return resultVo;
    }


}
