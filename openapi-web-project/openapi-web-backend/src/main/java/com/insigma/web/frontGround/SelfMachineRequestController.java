package com.insigma.web.frontGround;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.insigma.facade.openapi.facade.OpenapiOrgFacade;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineFacade;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineRequestFacade;
import com.insigma.facade.openapi.po.*;
import com.insigma.facade.openapi.vo.OpenapiApp.OpenapiAppShowDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.SelfMachineRequestResultVO;
import com.insigma.facade.vo.CdGatewayRequestBodyBd.CdGatewayRequestBodyBdSaveVO;
import com.insigma.facade.vo.CdGatewayRequestDetailBd.CdGatewayRequestDetailBdSaveVO;
import com.insigma.facade.vo.CdGatewayRequestDetailBd.CdGatewayRequestVO;
import com.insigma.util.MD5Util;
import com.insigma.util.SignUtil;
import com.insigma.util.StringUtil;
import com.insigma.webtool.component.loadBalance.LoadBalanceUtils;
import com.insigma.webtool.util.Encrypt;
import com.insigma.webtool.util.RestTemplateUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import star.bizbase.util.StringUtils;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("selfMachineRequest")
@Api(tags = "自助机请求")
@Slf4j
public class SelfMachineRequestController {

    @Autowired
    OpenapiSelfmachineRequestFacade openapiSelfmachineRequestFacade;
    @Autowired
    OpenapiSelfmachineFacade openapiSelfmachineFacade;
    @Autowired
    OpenapiOrgFacade openapiOrgFacade;

    @ApiOperation(value = "自助机请求")
    @RequestMapping(value = {"/request"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo request(
            @RequestParam("encodeString") String encodeString) {
        ResultVo resultVo=new ResultVo();
        try {
            String tempString=Encrypt.desEncrypt(encodeString);
            OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO = JSONObject.parseObject(tempString, OpenapiSelfmachineRequestSaveVO.class);
            if (StringUtils.isEmpty(openapiSelfmachineRequestSaveVO.getCertificate())){
                resultVo.setResultDes("自助机请求接受错误:没有携带证书");
                return resultVo;
            }
            OpenapiOrg openapiOrg=checkCertificate(openapiSelfmachineRequestSaveVO.getCertificate(),openapiSelfmachineRequestSaveVO);
            if (openapiOrg==null){
                resultVo.setResultDes("自助机请求接受错误:证书无效");
                return resultVo;
            }
            openapiSelfmachineRequestSaveVO.setUniqueCode(MD5Util.MD5Encode(openapiSelfmachineRequestSaveVO.getIp()+openapiSelfmachineRequestSaveVO.getMacAddress(),"UTF-8"));
            OpenapiSelfmachineRequest openapiSelfmachine=openapiSelfmachineRequestFacade.getOpenapiSelfmachineRequestDetail(new OpenapiSelfmachineRequestDetailVO().setUniqueCode(openapiSelfmachineRequestSaveVO.getUniqueCode()));
            if (openapiSelfmachine==null){
                openapiSelfmachineFacade.saveSelfMachine(openapiSelfmachineRequestSaveVO);
                resultVo.setResultDes("自助机审核中，请等待审核通过");
                return resultVo;
            }
            if (SelfMachineEnum.WHITE.equals(openapiSelfmachine.getStatu())){
                OpenapiSelfmachineRequest tempRequest=openapiSelfmachineRequestFacade.createToken(openapiSelfmachine,openapiOrg);
                resultVo.setResult(new SelfMachineRequestResultVO().setToken(tempRequest.getToken()).setMachineCode(tempRequest.getMachineCode()));
                resultVo.setSuccess(true);
            }
            if (SelfMachineEnum.BLACK.equals(openapiSelfmachine.getStatu())){
                resultVo.setResultDes("进入黑名单的自助机 不能取得token");
            }
            if (SelfMachineEnum.NOT_YET.equals(openapiSelfmachine.getStatu())){
                resultVo.setResultDes("自助机审核中，请等待审核通过");
            }
        }catch (Exception e){
            log.error("自助机请求接受异常",e);
            resultVo.setResultDes("自助机请求接受异常");
        }
        return resultVo;
    }

    private OpenapiOrg checkCertificate(String certificate, OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO) {
        return openapiOrgFacade.checkCertificate(certificate,openapiSelfmachineRequestSaveVO);
    }

    public static void main(String[] args) {
        OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO=new OpenapiSelfmachineRequestSaveVO();
        openapiSelfmachineRequestSaveVO.setAppKey("123");
        openapiSelfmachineRequestSaveVO.setCertificate("123");
        openapiSelfmachineRequestSaveVO.setIp("12345");
        openapiSelfmachineRequestSaveVO.setMacAddress("123");
        openapiSelfmachineRequestSaveVO.setMachineType("123");
        String result=JSONObject.toJSONString(openapiSelfmachineRequestSaveVO);
        System.out.println(result);
        System.out.println(Encrypt.encrypt(result));
    }
}