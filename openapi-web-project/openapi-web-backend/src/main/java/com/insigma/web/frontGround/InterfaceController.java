package com.insigma.web.frontGround;

import com.alibaba.fastjson.JSONObject;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.weaver.SignatureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import star.bizbase.util.StringUtils;
import star.vo.result.ResultVo;

import java.util.Map;
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


    @ApiOperation(value = "接口转发")
    @RequestMapping(value = "/interface/{code}",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public Object getOpenapiInterfaceList(
            @PathVariable String code,
            @RequestBody JSONObject params){
        ResultVo resultVo=new ResultVo();
        try {
            if (StringUtils.isEmpty(params.getString("appKey"))){
                resultVo.setResultDes("appKey未提供");
                return resultVo;
            }
            String appKey=params.getString("appKey");
            OpenapiAppShowDetailVO openapiApp=openapiAppFacade.getAppByAppId(appKey);
            if (openapiApp==null){
                resultVo.setResultDes("应用不存在或已删除！");
                return resultVo;
            }
            if (CollectionUtils.isEmpty(openapiApp.getOpenapiInterfaces())){
                resultVo.setResultDes("接口不存在！");
                return resultVo;
            }
            Map<String,OpenapiInterface> openapiInterfaceMap=openapiApp.getOpenapiInterfaces().stream().collect(Collectors.toMap(i->i.getCode(),i->i));
            OpenapiInterface openapiInterface;
            if (openapiInterfaceMap.get(code)==null){
                resultVo.setResultDes("应用无此接口权限！");
                return resultVo;
            }else {
                openapiInterface=openapiInterfaceMap.get(code);
            }
            String appSecret=openapiApp.getAppSecret();
            JSONObject checkSignResult= SignUtil.checkSign(params,appSecret);
            if(checkSignResult.getInteger("flag")!=1){
                resultVo.setResultDes(checkSignResult.getString("msg"));
                return resultVo;
            }
            if (openapiInterface==null){
                resultVo.setResultDes("接口不存在！");
                return resultVo;
            }
            String innerUrl=openapiInterface.getInnerUrl();
            params=SignUtil.getParamWithoutSignParam(params);
            ResponseEntity<JSONObject> result= RestTemplateUtil.postByMap(innerUrl,params,JSONObject.class);
            if (result==null||result.getStatusCode()!= HttpStatus.OK){
                resultVo.setResultDes("接口转发异常");
                return resultVo;
            }else {
                return result.getBody();
            }
        }catch (Exception e){
            resultVo.setResultDes("接口转发功能异常");
            log.error("获取接口列表异常",e);
        }
        return resultVo;
    }

    private boolean checkInterfaceCanBeUse(OpenapiAppShowDetailVO openapiApp,String code) {
        if (openapiApp==null|| CollectionUtils.isEmpty(openapiApp.getOpenapiInterfaces())){
            return false;
        }
        Map<String,OpenapiInterface> openapiInterfaceMap=openapiApp.getOpenapiInterfaces().stream().collect(Collectors.toMap(i->i.getCode(),i->i));
        if (openapiInterfaceMap.get(code)==null){
            return false;
        }else {
            return true;
        }
    }
}
