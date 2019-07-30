package com.insigma.web.frontGround;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.facade.OpenapiAppFacade;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.OpenapiApp.OpenapiAppShowDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceListVO;
import com.insigma.util.MD5Util;
import com.insigma.util.SignUtil;
import com.insigma.util.StringUtil;
import com.insigma.web.BasicController;
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
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("frontInterface")
@Api(tags ="接口处理及转发")
@Slf4j
public class InterfaceController extends BasicController {

    @Autowired
    InterfaceFacade interfaceFacade;
    @Autowired
    OpenapiAppFacade openapiAppFacade;
    @Autowired
    JsonObjectMapper jacksonObjectMapper;


    @ApiOperation(value = "接口转发")
    @RequestMapping(value = {"/interface/{code}"},method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public Object getOpenapiInterfaceList(
            @PathVariable String code,
            @RequestBody String paramString,HttpServletRequest httpServletRequest){
        ResultVo resultVo=new ResultVo();
        try {
            Enumeration<String> headerNames=httpServletRequest.getHeaderNames();
            log.info("请求头为");
            while (headerNames.hasMoreElements()){
                String headerName=headerNames.nextElement();
                log.info("请求头："+headerName+"value:"+httpServletRequest.getHeader(headerName));
            }
            log.info("入参"+paramString+"目标code为"+code);
            String appKey=httpServletRequest.getHeader("appKey");
            String time=httpServletRequest.getHeader("time");
            String nonceStr = httpServletRequest.getHeader("nonceStr");
            String signature=httpServletRequest.getHeader("signature");
            String encodeType=httpServletRequest.getHeader("encodeType");
            if (StringUtils.isEmpty(appKey)){
                log.info("appKey未提供,参数"+paramString);
                resultVo.setResultDes("appKey未提供！");
                return resultVo;
            }
            OpenapiAppShowDetailVO openapiApp=openapiAppFacade.getAppByAppKey(appKey);
            if (openapiApp==null){
                log.info("openapiApp不存在"+paramString);
                resultVo.setResultDes("openapiApp不存在！");
                return resultVo;
            }
            if (CollectionUtils.isEmpty(openapiApp.getOpenapiInterfaces())){
                resultVo.setResultDes("接口不存在！");
                log.info("接口不存在"+paramString);
                return resultVo;
            }
            Map<String,OpenapiInterface> openapiInterfaceMap=openapiApp.getOpenapiInterfaces().stream().collect(Collectors.toMap(i->i.getCode(),i->i));
            OpenapiInterface openapiInterface;
            if (openapiInterfaceMap.get(code)==null){
                resultVo.setResultDes("应用无此接口权限！");
                log.info("应用无此接口权限"+paramString);
                return resultVo;
            }else {
                openapiInterface=openapiInterfaceMap.get(code);
            }
            if (openapiInterface==null){
                resultVo.setResultDes("接口不存在！");
                log.info("接口不存在"+paramString);
                return resultVo;
            }
            String appSecret=openapiApp.getAppSecret();
            JSONObject checkSignResult=checkSign(paramString,appKey,time,nonceStr,signature,encodeType,appSecret);
            if(checkSignResult.getInteger("flag")!=1){
                resultVo.setResultDes(checkSignResult.getString("msg"));
                log.info(checkSignResult.getString("msg")+paramString);
                return resultVo;
            }
            String innerUrl=openapiInterface.getInnerUrl();
            JSONObject paramsJSON=JSONObject.parseObject(paramString, Feature.OrderedField);
            log.info("开始进行接口转发，目标url为"+innerUrl+",参数为"+paramsJSON);
            ResponseEntity result= RestTemplateUtil.postByMap(innerUrl,paramsJSON,String.class);
            log.info("开始进行接口转发，返回值为"+result);
            return sendMessageBack(resultVo, result);
        }catch (Exception e){
            resultVo.setResultDes("接口转发功能异常!原因为:"+e.getMessage());
            resultVo.setResult("接口转发功能异常!原因为:"+e.getMessage());
            log.error("获取接口列表异常",e);
        }
        log.info("返回参数为"+resultVo);
        return resultVo;
    }

    private JSONObject checkSign(String paramString, String appKey, String time, String nonceStr, String signature,String encodeType,String appSecret) {
        JSONObject resultVo=new JSONObject();
        if (DataConstant.ENCODE_TYPE_C_SHARP.equals(encodeType)){
            String param = paramString+appKey+time+nonceStr+appSecret;
            String checkSignResult = MD5Util.md5Password(param).toUpperCase();
            logger.info("参数 paramString={},testKey={},time={},nonceStr={},appSecret={},sing={},checkSignResult={}",paramString,appKey,time,nonceStr,appSecret,signature,checkSignResult);
            if (checkSignResult==null||!checkSignResult.equals(signature)){
                logger.info("签名验证错误,入参为"+param);
                resultVo.put("msg","签名验证错误！");
                resultVo.put("flag","0");
                return resultVo;
            }else {
                logger.info("签名验证成功,入参为"+param);
                resultVo.put("msg","签名验证成功！");
                resultVo.put("flag","1");
                return resultVo;
            }
        }else {
            JSONObject tempJSON=JSONObject.parseObject(paramString, Feature.OrderedField);
            tempJSON.put("appKey",appKey);
            tempJSON.put("time",time);
            tempJSON.put("nonceStr",nonceStr);
            tempJSON.put("signature",signature);
            return SignUtil.checkSign(tempJSON,appSecret);
        }
    }

    private Object sendMessageBack(ResultVo resultVo, ResponseEntity result) {
        if (result==null||!HttpStatus.OK.equals(result.getStatusCode())){
            resultVo.setResultDes("获得了异常的返回码！返回信息为："+result);
            resultVo.setResult("获得了异常的返回码！返回信息为："+result);
            resultVo.setSuccess(false);
            return resultVo;
        }else {
            return result;
        }
    }





    @ApiOperation(value = "接口转发")
    @RequestMapping(value = "/checkSignVaild",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo getOpenapiInterfaceList(@RequestBody JSONObject params) {
        ResultVo resultVo=new ResultVo();
        try {
            JSONObject resultParam=interfaceFacade.checkSignVaild(params);
            if (resultParam!=null&&resultParam.getInteger("flag")==1){
                resultVo.setSuccess(true);
                resultVo.setResult(resultParam);
            }else {
                resultVo.setResult(resultParam);
            }
        }catch (Exception e){
            log.error("验签异常",e);
            resultVo.setResultDes("验签过程异常");
        }
        return resultVo;
    }

    @ApiOperation(value = "接口转发")
    @RequestMapping(value = "/insertMatters",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo insertMatters() {
        ResultVo resultVo=new ResultVo();
        try {
            JSONObject resultParam=interfaceFacade.insertMatters();
            if (resultParam!=null&&resultParam.getInteger("flag")==1){
                resultVo.setSuccess(true);
                resultVo.setResult(resultParam);
            }else {
                resultVo.setResult(resultParam);
            }
        }catch (Exception e){
            log.error("验签异常",e);
            resultVo.setResultDes("验签过程异常");
        }
        return resultVo;
    }
}
