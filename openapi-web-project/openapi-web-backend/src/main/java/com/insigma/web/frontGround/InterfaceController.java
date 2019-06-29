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
import com.insigma.util.SignUtil;
import com.insigma.web.BasicController;
import com.insigma.webtool.util.RestTemplateUtil;
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
    @RequestMapping(value = "/interface/{code}",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public Object getOpenapiInterfaceList(
            @PathVariable@ApiParam("code") String code,
            @RequestBody Object paramString, HttpServletRequest httpServletRequest){
        ResultVo resultVo=new ResultVo();
        try {
            Enumeration<String> headerNames=httpServletRequest.getHeaderNames();
            log.info("请求头为");
            while (headerNames.hasMoreElements()){
                String headerName=headerNames.nextElement();
                log.info("请求头："+headerName+"value:"+httpServletRequest.getHeader(headerName));
            }
            log.info("入参"+paramString+"目标code为"+code);
            String temp=JSONObject.toJSONString(paramString);
            JSONObject params=JSONObject.parseObject(temp, Feature.OrderedField);
            params.put("appKey",httpServletRequest.getHeader("appKey"));
            params.put("time", httpServletRequest.getHeader("time"));
            params.put("nonceStr",httpServletRequest.getHeader("nonceStr"));
            params.put("signature",httpServletRequest.getHeader("signature"));
            String appKey=httpServletRequest.getHeader("appKey");
            if (StringUtils.isEmpty(appKey)){
                log.info("appKey未提供,参数"+params);
                resultVo.setResultDes("appKey未提供！");
                return resultVo;
            }
            OpenapiAppShowDetailVO openapiApp=openapiAppFacade.getAppByAppKey(appKey);
            if (openapiApp==null){
                log.info("openapiApp不存在"+params);
                resultVo.setResultDes("openapiApp不存在！");
                return resultVo;
            }
            if (CollectionUtils.isEmpty(openapiApp.getOpenapiInterfaces())){
                resultVo.setResultDes("接口不存在！");
                log.info("接口不存在"+params);
                return resultVo;
            }
            Map<String,OpenapiInterface> openapiInterfaceMap=openapiApp.getOpenapiInterfaces().stream().collect(Collectors.toMap(i->i.getCode(),i->i));
            OpenapiInterface openapiInterface;
            if (openapiInterfaceMap.get(code)==null){
                resultVo.setResultDes("应用无此接口权限！");
                log.info("应用无此接口权限"+params);
                return resultVo;
            }else {
                openapiInterface=openapiInterfaceMap.get(code);
            }
            String appSecret=openapiApp.getAppSecret();
            JSONObject checkSignResult= SignUtil.checkSign(params,appSecret);
            if(checkSignResult.getInteger("flag")!=1){
                resultVo.setResultDes(checkSignResult.getString("msg"));
                log.info(checkSignResult.getString("msg")+params);
                return resultVo;
            }
            if (openapiInterface==null){
                resultVo.setResultDes("接口不存在！");
                log.info("接口不存在"+params);
                return resultVo;
            }
            String innerUrl=openapiInterface.getInnerUrl();
            params=SignUtil.getParamWithoutsignatureParam(params);
            log.info("开始进行接口转发，目标url为"+innerUrl+",参数为"+params);
            ResponseEntity result= RestTemplateUtil.postByMap(innerUrl,params,String.class);
            log.info("开始进行接口转发，返回值为"+result);
            if (result==null||!HttpStatus.OK.equals(result.getStatusCode())){
                resultVo.setResultDes("获得了异常的返回码！返回信息为："+result);
                resultVo.setResult("获得了异常的返回码！返回信息为："+params);
                resultVo.setSuccess(false);
                return resultVo;
            }else {
                return result;
            }
        }catch (Exception e){
            resultVo.setResultDes("接口转发功能异常!原因为:"+e.getMessage());
            resultVo.setResult("接口转发功能异常!原因为:"+e.getMessage());
            log.error("获取接口列表异常",e);
        }
        log.info("返回参数为"+resultVo);
        return resultVo;
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
}
