//package com.insigma.web.frontGround;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.parser.Feature;
//import com.insigma.facade.employcode.dto.SelfMachineOrgDTO;
//import com.insigma.facade.employcode.enums.employcodeSelfmachineEnum;
//import com.insigma.facade.employcode.facade.employcodeOrgFacade;
//import com.insigma.facade.employcode.facade.employcodeSelfmachineFacade;
//import com.insigma.facade.employcode.facade.employcodeSelfmachineRequestFacade;
//import com.insigma.facade.employcode.po.*;
//import com.insigma.facade.employcode.vo.employcodeApp.employcodeAppShowDetailVO;
//import com.insigma.facade.employcode.vo.employcodeSelfmachine.employcodeSelfmachineDetailVO;
//import com.insigma.facade.employcode.vo.employcodeSelfmachineRequest.employcodeSelfmachineDetailShowVO;
//import com.insigma.facade.employcode.vo.employcodeSelfmachineRequest.employcodeSelfmachineRequestDetailVO;
//import com.insigma.facade.employcode.vo.employcodeSelfmachineRequest.employcodeSelfmachineRequestSaveVO;
//import com.insigma.facade.employcode.vo.employcodeSelfmachineRequest.SelfMachineRequestResultVO;
//import com.insigma.facade.vo.CdGatewayRequestBodyBd.CdGatewayRequestBodyBdSaveVO;
//import com.insigma.facade.vo.CdGatewayRequestDetailBd.CdGatewayRequestDetailBdSaveVO;
//import com.insigma.facade.vo.CdGatewayRequestDetailBd.CdGatewayRequestVO;
//import com.insigma.util.MD5Util;
//import com.insigma.util.SignUtil;
//import com.insigma.util.StringUtil;
//import com.insigma.webtool.component.loadBalance.LoadBalanceUtils;
//import com.insigma.webtool.util.Encrypt;
//import com.insigma.webtool.util.RestTemplateUtil;
//import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
//import constant.DataConstant;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import star.bizbase.util.StringUtils;
//import star.fw.web.util.ServletAttributes;
//import star.vo.result.ResultVo;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("selfMachineRequest")
//@Api(tags = "自助机请求")
//@Slf4j
//public class SelfMachineRequestController {
//
//    @Autowired
//    employcodeSelfmachineRequestFacade employcodeSelfmachineRequestFacade;
//    @Autowired
//    employcodeSelfmachineFacade employcodeSelfmachineFacade;
//    @Autowired
//    employcodeOrgFacade employcodeOrgFacade;
//
//    @ApiOperation(value = "自助机请求")
//    @RequestMapping(value = {"/request"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
//    public ResultVo request(
//            @RequestParam("encodeString") String encodeString) {
//        ResultVo resultVo=new ResultVo();
//        try {
//            String tempString=Encrypt.desEncrypt(encodeString);
//            employcodeSelfmachineRequestSaveVO employcodeSelfmachineRequestSaveVO = JSONObject.parseObject(tempString, employcodeSelfmachineRequestSaveVO.class);
//            log.info("调用获取token方法"+employcodeSelfmachineRequestSaveVO);
//            String ipRoots= ServletAttributes.getRequest().getHeader("X-Forwarded-For");
//            String ip= ServletAttributes.getRequest().getHeader("X-Real-IP");
//            employcodeSelfmachineRequestSaveVO.setIpSegment(ipRoots);
//            if (!StringUtils.isEmpty(ipRoots)) {
//                employcodeSelfmachineRequestSaveVO.setIp(ip);
//            }
//            if (StringUtils.isEmpty(employcodeSelfmachineRequestSaveVO.getCertificate())){
//                resultVo.setResultDes("自助机请求接受错误:没有携带证书");
//                return resultVo;
//            }
//            employcodeOrg employcodeOrg=checkCertificate(employcodeSelfmachineRequestSaveVO.getCertificate(),employcodeSelfmachineRequestSaveVO);
//            if (employcodeOrg==null){
//                resultVo.setResultDes("自助机请求接受错误:证书无效");
//                return resultVo;
//            }
//            employcodeSelfmachineRequestSaveVO.setUniqueCode(MD5Util.md5Password(employcodeSelfmachineRequestSaveVO.getMacAddress()));
//            employcodeSelfmachineRequest employcodeSelfmachine=employcodeSelfmachineRequestFacade.getemploycodeSelfmachineRequestDetail(new employcodeSelfmachineRequestDetailVO().setUniqueCode(employcodeSelfmachineRequestSaveVO.getUniqueCode()));
//            if (employcodeSelfmachine==null){
//                String machineCode=employcodeSelfmachineFacade.saveSelfMachine(employcodeSelfmachineRequestSaveVO,employcodeOrg);
//                resultVo.setResult(new SelfMachineRequestResultVO().setMachineCode(machineCode).setOrgCode(employcodeOrg.getOrgCode()));
//                resultVo.setResultDes("自助机进入审核，请等待审核通过");
//                return resultVo;
//            }else {
//                employcodeSelfmachineFacade.updateSelfMachine(employcodeSelfmachineRequestSaveVO,employcodeSelfmachine);
//            }
//            employcodeSelfmachine tempMachine=employcodeSelfmachineFacade.getemploycodeSelfmachineDetail(new employcodeSelfmachineDetailVO().setUniqueCode(employcodeSelfmachine.getUniqueCode()));
//            if (tempMachine==null){
//                resultVo.setResultDes("自助机请求接受异常:自助机不存在");
//                return resultVo;
//            }
//            if (employcodeSelfmachineEnum.CANCEL.equals(tempMachine.getActiveStatu())){
//                if(employcodeOrg.getId().equals(tempMachine.getOrgId())) {
//                    resultVo.setResultDes("自助机请求接受错误:自助机被注销");
//                    return resultVo;
//                }else {
//                    tempMachine.setOrgId(employcodeOrg.getId());
//                    String machineCode=employcodeSelfmachineFacade.reActivSelfMachine(tempMachine,employcodeSelfmachine,employcodeOrg);
//                    resultVo.setResult(new SelfMachineRequestResultVO().setMachineCode(machineCode).setOrgCode(employcodeOrg.getOrgCode()));
//                    resultVo.setResultDes("自助机进入审核，请等待审核通过");
//                    return resultVo;
//                }
//            }
//            if (SelfMachineEnum.WHITE.equals(employcodeSelfmachine.getStatu())){
//                employcodeSelfmachineRequest tempRequest=employcodeSelfmachineRequestFacade.createToken(employcodeSelfmachine,employcodeOrg);
//                resultVo.setResult(new SelfMachineRequestResultVO().setToken(tempRequest.getToken()).setMachineCode(tempRequest.getMachineCode()).setMachineTypeId(tempMachine.getMachineTypeId()).setOrgCode(employcodeOrg.getOrgCode()));
//                resultVo.setSuccess(true);
//                log.info("自助机取得token"+employcodeSelfmachineRequestSaveVO+tempRequest.getToken());
//            }
//            if (SelfMachineEnum.BLACK.equals(employcodeSelfmachine.getStatu())){
//                resultVo.setResultDes("进入黑名单的自助机 不能取得token");
//            }
//            if (SelfMachineEnum.NOT_YET.equals(employcodeSelfmachine.getStatu())){
//                resultVo.setResult(new SelfMachineRequestResultVO().setMachineCode(employcodeSelfmachine.getMachineCode()).setMachineTypeId(tempMachine.getMachineTypeId()).setOrgCode(employcodeOrg.getOrgCode()));
//                resultVo.setResultDes("自助机审核中，请等待审核通过");
//            }
//        }catch (Exception e){
//            log.error("自助机请求接受异常",e);
//            resultVo.setResultDes("自助机请求接受异常");
//        }
//        return resultVo;
//    }
//
//
//    @ApiOperation(value = "自助机请求")
//    @RequestMapping(value = {"/testToken"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
//    public ResultVo testToken(@RequestParam("token") String token) {
//        ResultVo resultVo=new ResultVo();
//        try {
//            if (employcodeSelfmachineRequestFacade.checkTokenExit(token)){
//                resultVo.setSuccess(true);
//                resultVo.setResultDes("token验证通过");
//            }else {
//                resultVo.setResultDes("token验证未通过");
//            }
//        }catch (Exception e){
//            log.error("自助机请求接受异常",e);
//            resultVo.setResultDes("自助机请求接受异常");
//        }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "自助机请求")
//    @RequestMapping(value = {"/getOrgByToken"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
//    public ResultVo getOrgByToken(
//            @RequestParam("token") String token) {
//        ResultVo resultVo=new ResultVo();
//        try {
//            SelfMachineOrgDTO selfMachineOrgDTO=employcodeSelfmachineRequestFacade.getOrgByToken(token);
//            if (selfMachineOrgDTO!=null){
//                resultVo.setSuccess(true);
//                resultVo.setResult(selfMachineOrgDTO);
//                resultVo.setResultDes("token验证通过");
//            }else {
//                resultVo.setResultDes("token验证未通过");
//            }
//        }catch (Exception e){
//            log.error("自助机请求接受异常",e);
//            resultVo.setResultDes("自助机请求接受异常");
//        }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "自助机请求")
//    @RequestMapping(value = {"/getDetailByToken"},  produces = {"application/json;charset=UTF-8"})
//    public ResultVo getDetailByToken(
//            @RequestParam("token") String token) {
//        ResultVo resultVo=new ResultVo();
//        try {
//            employcodeSelfmachineDetailShowVO employcodeSelfmachineDetailShowVO=employcodeSelfmachineRequestFacade.getDetailByToken(token);
//            if (employcodeSelfmachineDetailShowVO!=null){
//                resultVo.setSuccess(true);
//                resultVo.setResult(employcodeSelfmachineDetailShowVO);
//                resultVo.setResultDes("token取得数据");
//            }else {
//                resultVo.setResultDes("未取得数据");
//            }
//        }catch (Exception e){
//            log.error("自助机请求接受异常",e);
//            resultVo.setResultDes("自助机请求接受异常");
//        }
//        return resultVo;
//    }
//
//    private employcodeOrg checkCertificate(String certificate, employcodeSelfmachineRequestSaveVO employcodeSelfmachineRequestSaveVO) {
//        return employcodeOrgFacade.checkCertificate(certificate,employcodeSelfmachineRequestSaveVO);
//    }
//
//    public static void main(String[] args) {
//        employcodeSelfmachineRequestSaveVO employcodeSelfmachineRequestSaveVO=new employcodeSelfmachineRequestSaveVO();
////        employcodeSelfmachineRequestSaveVO.setAppKey("123");
//        employcodeSelfmachineRequestSaveVO.setCertificate("ba1a13d019e7cd98d2e2876e6697eb32");
//        employcodeSelfmachineRequestSaveVO.setIp("12345116121");
//        employcodeSelfmachineRequestSaveVO.setMacAddress("123");
//        employcodeSelfmachineRequestSaveVO.setClientVersion("112111");
//        employcodeSelfmachineRequestSaveVO.setSystemCode("windows xp");
//        employcodeSelfmachineRequestSaveVO.setQtVersion("123");
//        employcodeSelfmachineRequestSaveVO.setHttpVersion("123http");
////        String result=JSONObject.toJSONString(employcodeSelfmachineRequestSaveVO);
////        String md5=MD5Util.md5Password(result);
////        employcodeSelfmachineRequestSaveVO.setMd5Value("6f04a982dab609fde2cd2a69710eb355");
//        String result=JSONObject.toJSONString(employcodeSelfmachineRequestSaveVO);
////        result="{\"certificate\":\"c6ae6cdcad6a859a447a3aca0f46d31f\",\"clientVersion\":\"0.0.4\",\"ip\":\"192.168.1.103\",\"macAddress\":\"1C-1B-0D-C7-6E-B7\",\"systemCode\":\"Windows 7\"}";
//        System.out.println(result);
//        System.out.println(Encrypt.encrypt(result));
//        RestTemplate restTemplate=new RestTemplate();
//        ResponseEntity<String> responseEntity=restTemplate.postForEntity("http://10.85.94.57:16679/selfMachineRequest/request?encodeString="+Encrypt.encrypt(result),
//                null,String.class);
//        System.out.println(responseEntity.getBody());
//        JSONObject resultJSON=JSONObject.parseObject(responseEntity.getBody());
//        String token=resultJSON.getJSONObject("result").getString("token");
////        String token="d942365d82cfdb999834199a3ca0f7";
//        responseEntity=restTemplate.postForEntity("http://employcodebk.insigma.com:6011/api/selfMachineRequest/testToken?token="+token,
//                null,String.class);
//        System.out.println(responseEntity.getBody());
//    }
//}
