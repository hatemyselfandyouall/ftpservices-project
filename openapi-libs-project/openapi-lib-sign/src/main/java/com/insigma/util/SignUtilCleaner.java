package com.insigma.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;


@Slf4j
public class SignUtilCleaner {



    public static String createSign(JSONObject parameters,String  appSecret){
        parameters.put("secret",appSecret);
        String result=parameters.toString();
        System.out.println(result);
        log.info(result);
        String signature = MD5Util.md5Password(result).toUpperCase();
        parameters.remove("secret");
        return signature;
    }

    public static void main(String[] args) throws IOException {
        testMethod1();
    }
    private static void testMethod1(){
        String testKey="d8e16daa40848f30c48262d0113cc96e";
        String testSecret="f1b74f1dee465cc4d22753d024196375";
        JSONObject haeder=new JSONObject(true);
        haeder.put("appKey",testKey);
        haeder.put("time", "20190729 21:01:35");
        haeder.put("nonceStr", "W29FR0D03QIZPN8UU3Z0OY8VR39KKLZ1");//随机字符串
        JSONObject param=JSONObject.parseObject(paramString,Feature.OrderedField);
        param.putAll(haeder);
        String signature = createSign(param,testSecret);
        System.out.println(signature);
        haeder.put("signature",signature);
        param=getParamWithoutsignatureParam(param);
        String testUrl="http://10.85.159.203:10500/frontInterface/interface/matters-9017";
        postTest(haeder,param,testUrl);
    }
    private static String paramString="{\"matterCode\":\"公共服务-00512-002\",\"projId\":\"190920000023890611489\",\"approveType\":\"01\",\"recvUserName\":\"杨琛\",\"bizType\":\"0\",\"affairType\":\"01\",\"recvDeptName\":\"浙江省医疗保障局\",\"execDeptOrgCode\":\"331099\",\"applyOrigin\":\"1\",\"msgid\":\"99661a5869424abc821420a9be04624d\",\"recvUserType\":\"1\",\"projectNature\":\"99\",\"areaCode\":\"330000\",\"trade\":\"9017\",\"recvDeptCode\":\"001003148\",\"applicantVO\":{\"applyUid\":\"32\",\"applyName\":\"杨琛\",\"applyCardType\":\"31\",\"applyCardNo\":\"32\",\"isAgent\":\"0\",\"contactUid\":\"32\",\"contactName\":\"杨琛\",\"contactCardType\":\"31\",\"contactCardNo\":\"32\",\"contactTelNo\":\"13000000000\",\"address\":\"浙江省\",\"applyType\":\"00\"},\"recvUserId\":\"32\",\"gmtApply\":\"2019-09-20\",\"projectName\":\"基本医疗保险关系接续\",\"deptCode\":\"001003148\"}";
    public static void postTest(JSONObject haeder, Object paramJson,String testUrl) {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        haeder.keySet().forEach(
                i->{
                    requestHeaders.add(i,haeder.get(i).toString()!=null?haeder.get(i).toString():"");
                }
        );
        requestHeaders.add("Content-Type", "application/json");
        //body
        //HttpEntity
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity(paramJson, requestHeaders);
        ResponseEntity result= restTemplate.postForEntity(testUrl,requestEntity,String.class);
        System.out.println(result);
    }






    public static JSONObject getParamWithoutsignatureParam(JSONObject params) {
        params.remove("appKey");
        params.remove("time");
        params.remove("nonceStr");
        params.remove("signatureType");
        params.remove("signature");
        return params;
    }



}
