//package com.insigma.web.frontGround;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.parser.Feature;
//import com.insigma.facade.qrcodebase.dto.SelfMachineOrgDTO;
//import com.insigma.facade.qrcodebase.enums.qrcodebaseSelfmachineEnum;
//import com.insigma.facade.qrcodebase.facade.qrcodebaseOrgFacade;
//import com.insigma.facade.qrcodebase.facade.qrcodebaseSelfmachineFacade;
//import com.insigma.facade.qrcodebase.facade.qrcodebaseSelfmachineRequestFacade;
//import com.insigma.facade.qrcodebase.po.*;
//import com.insigma.facade.qrcodebase.vo.qrcodebaseApp.qrcodebaseAppShowDetailVO;
//import com.insigma.facade.qrcodebase.vo.qrcodebaseSelfmachine.qrcodebaseSelfmachineDetailVO;
//import com.insigma.facade.qrcodebase.vo.qrcodebaseSelfmachineRequest.qrcodebaseSelfmachineDetailShowVO;
//import com.insigma.facade.qrcodebase.vo.qrcodebaseSelfmachineRequest.qrcodebaseSelfmachineRequestDetailVO;
//import com.insigma.facade.qrcodebase.vo.qrcodebaseSelfmachineRequest.qrcodebaseSelfmachineRequestSaveVO;
//import com.insigma.facade.qrcodebase.vo.qrcodebaseSelfmachineRequest.SelfMachineRequestResultVO;
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
//    qrcodebaseSelfmachineRequestFacade qrcodebaseSelfmachineRequestFacade;
//    @Autowired
//    qrcodebaseSelfmachineFacade qrcodebaseSelfmachineFacade;
//    @Autowired
//    qrcodebaseOrgFacade qrcodebaseOrgFacade;
//
//    @ApiOperation(value = "自助机请求")
//    @RequestMapping(value = {"/request"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
//    public ResultVo request(
//            @RequestParam("encodeString") String encodeString) {
//        ResultVo resultVo=new ResultVo();
//        try {
//            String tempString=Encrypt.desEncrypt(encodeString);
//            qrcodebaseSelfmachineRequestSaveVO qrcodebaseSelfmachineRequestSaveVO = JSONObject.parseObject(tempString, qrcodebaseSelfmachineRequestSaveVO.class);
//            log.info("调用获取token方法"+qrcodebaseSelfmachineRequestSaveVO);
//            String ipRoots= ServletAttributes.getRequest().getHeader("X-Forwarded-For");
//            String ip= ServletAttributes.getRequest().getHeader("X-Real-IP");
//            qrcodebaseSelfmachineRequestSaveVO.setIpSegment(ipRoots);
//            if (!StringUtils.isEmpty(ipRoots)) {
//                qrcodebaseSelfmachineRequestSaveVO.setIp(ip);
//            }
//            if (StringUtils.isEmpty(qrcodebaseSelfmachineRequestSaveVO.getCertificate())){
//                resultVo.setResultDes("自助机请求接受错误:没有携带证书");
//                return resultVo;
//            }
//            qrcodebaseOrg qrcodebaseOrg=checkCertificate(qrcodebaseSelfmachineRequestSaveVO.getCertificate(),qrcodebaseSelfmachineRequestSaveVO);
//            if (qrcodebaseOrg==null){
//                resultVo.setResultDes("自助机请求接受错误:证书无效");
//                return resultVo;
//            }
//            qrcodebaseSelfmachineRequestSaveVO.setUniqueCode(MD5Util.md5Password(qrcodebaseSelfmachineRequestSaveVO.getMacAddress()));
//            qrcodebaseSelfmachineRequest qrcodebaseSelfmachine=qrcodebaseSelfmachineRequestFacade.getqrcodebaseSelfmachineRequestDetail(new qrcodebaseSelfmachineRequestDetailVO().setUniqueCode(qrcodebaseSelfmachineRequestSaveVO.getUniqueCode()));
//            if (qrcodebaseSelfmachine==null){
//                String machineCode=qrcodebaseSelfmachineFacade.saveSelfMachine(qrcodebaseSelfmachineRequestSaveVO,qrcodebaseOrg);
//                resultVo.setResult(new SelfMachineRequestResultVO().setMachineCode(machineCode).setOrgCode(qrcodebaseOrg.getOrgCode()));
//                resultVo.setResultDes("自助机进入审核，请等待审核通过");
//                return resultVo;
//            }else {
//                qrcodebaseSelfmachineFacade.updateSelfMachine(qrcodebaseSelfmachineRequestSaveVO,qrcodebaseSelfmachine);
//            }
//            qrcodebaseSelfmachine tempMachine=qrcodebaseSelfmachineFacade.getqrcodebaseSelfmachineDetail(new qrcodebaseSelfmachineDetailVO().setUniqueCode(qrcodebaseSelfmachine.getUniqueCode()));
//            if (tempMachine==null){
//                resultVo.setResultDes("自助机请求接受异常:自助机不存在");
//                return resultVo;
//            }
//            if (qrcodebaseSelfmachineEnum.CANCEL.equals(tempMachine.getActiveStatu())){
//                if(qrcodebaseOrg.getId().equals(tempMachine.getOrgId())) {
//                    resultVo.setResultDes("自助机请求接受错误:自助机被注销");
//                    return resultVo;
//                }else {
//                    tempMachine.setOrgId(qrcodebaseOrg.getId());
//                    String machineCode=qrcodebaseSelfmachineFacade.reActivSelfMachine(tempMachine,qrcodebaseSelfmachine,qrcodebaseOrg);
//                    resultVo.setResult(new SelfMachineRequestResultVO().setMachineCode(machineCode).setOrgCode(qrcodebaseOrg.getOrgCode()));
//                    resultVo.setResultDes("自助机进入审核，请等待审核通过");
//                    return resultVo;
//                }
//            }
//            if (SelfMachineEnum.WHITE.equals(qrcodebaseSelfmachine.getStatu())){
//                qrcodebaseSelfmachineRequest tempRequest=qrcodebaseSelfmachineRequestFacade.createToken(qrcodebaseSelfmachine,qrcodebaseOrg);
//                resultVo.setResult(new SelfMachineRequestResultVO().setToken(tempRequest.getToken()).setMachineCode(tempRequest.getMachineCode()).setMachineTypeId(tempMachine.getMachineTypeId()).setOrgCode(qrcodebaseOrg.getOrgCode()));
//                resultVo.setSuccess(true);
//                log.info("自助机取得token"+qrcodebaseSelfmachineRequestSaveVO+tempRequest.getToken());
//            }
//            if (SelfMachineEnum.BLACK.equals(qrcodebaseSelfmachine.getStatu())){
//                resultVo.setResultDes("进入黑名单的自助机 不能取得token");
//            }
//            if (SelfMachineEnum.NOT_YET.equals(qrcodebaseSelfmachine.getStatu())){
//                resultVo.setResult(new SelfMachineRequestResultVO().setMachineCode(qrcodebaseSelfmachine.getMachineCode()).setMachineTypeId(tempMachine.getMachineTypeId()).setOrgCode(qrcodebaseOrg.getOrgCode()));
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
//            if (qrcodebaseSelfmachineRequestFacade.checkTokenExit(token)){
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
//            SelfMachineOrgDTO selfMachineOrgDTO=qrcodebaseSelfmachineRequestFacade.getOrgByToken(token);
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
//            qrcodebaseSelfmachineDetailShowVO qrcodebaseSelfmachineDetailShowVO=qrcodebaseSelfmachineRequestFacade.getDetailByToken(token);
//            if (qrcodebaseSelfmachineDetailShowVO!=null){
//                resultVo.setSuccess(true);
//                resultVo.setResult(qrcodebaseSelfmachineDetailShowVO);
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
//    private qrcodebaseOrg checkCertificate(String certificate, qrcodebaseSelfmachineRequestSaveVO qrcodebaseSelfmachineRequestSaveVO) {
//        return qrcodebaseOrgFacade.checkCertificate(certificate,qrcodebaseSelfmachineRequestSaveVO);
//    }
//
//    public static void main(String[] args) {
//        qrcodebaseSelfmachineRequestSaveVO qrcodebaseSelfmachineRequestSaveVO=new qrcodebaseSelfmachineRequestSaveVO();
////        qrcodebaseSelfmachineRequestSaveVO.setAppKey("123");
//        qrcodebaseSelfmachineRequestSaveVO.setCertificate("ba1a13d019e7cd98d2e2876e6697eb32");
//        qrcodebaseSelfmachineRequestSaveVO.setIp("12345116121");
//        qrcodebaseSelfmachineRequestSaveVO.setMacAddress("123");
//        qrcodebaseSelfmachineRequestSaveVO.setClientVersion("112111");
//        qrcodebaseSelfmachineRequestSaveVO.setSystemCode("windows xp");
//        qrcodebaseSelfmachineRequestSaveVO.setQtVersion("123");
//        qrcodebaseSelfmachineRequestSaveVO.setHttpVersion("123http");
////        String result=JSONObject.toJSONString(qrcodebaseSelfmachineRequestSaveVO);
////        String md5=MD5Util.md5Password(result);
////        qrcodebaseSelfmachineRequestSaveVO.setMd5Value("6f04a982dab609fde2cd2a69710eb355");
//        String result=JSONObject.toJSONString(qrcodebaseSelfmachineRequestSaveVO);
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
//        responseEntity=restTemplate.postForEntity("http://qrcodebasebk.insigma.com:6011/api/selfMachineRequest/testToken?token="+token,
//                null,String.class);
//        System.out.println(responseEntity.getBody());
//    }
//}
