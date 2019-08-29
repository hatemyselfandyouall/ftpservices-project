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
        String testKey="f10c86cb5804aa0d71c86d6f31f02700";
        String testSecret="2ac744922a823ef9e3dc47115de2f6f7";
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
        String testUrl="http://10.87.0.68:10500/frontInterface/interface/callBackService-7302-lishui-331100-pro";
        postTest(haeder,param,testUrl);
    }
    private static String paramString="{\n" +
            "    \"ver\": \"V1.0\",\n" +
            "    \"orgNo\": \"331100100001\",\n" +
            "    \"orgName\": \"丽水市中心医院\",\n" +
            "    \"id\": \"\",\n" +
            "    \"inPut\": [\n" +
            "        {\n" +
            "            \"COUNT\": \"1\",\n" +
            "            \"LS_DT\": [\n" +
            "                {\n" +
            "                    \"AKC190\": \"9990328535\",\n" +
            "                    \"BKE002\": \"1079Y208\",\n" +
            "                    \"AKC220\": \"1003443754\",\n" +
            "                    \"AKE003\": \"1\",\n" +
            "                    \"AKE001\": \"x120400005620005\",\n" +
            "                    \"AKE006\": \"倍他司汀片(敏使朗)\",\n" +
            "                    \"AKC225\": \"14.8100\",\n" +
            "                    \"AKC226\": \"1.0000\",\n" +
            "                    \"AKE134\": \"1.0000\",\n" +
            "                    \"AAE019\": \"14.8100\",\n" +
            "                    \"AKA067\": \"\",\n" +
            "                    \"AKA074\": \"\",\n" +
            "                    \"AKA070\": \"\",\n" +
            "                    \"AKA066\": \"0\",\n" +
            "                    \"AKE130\": \"\",\n" +
            "                    \"AKE113\": \"\",\n" +
            "                    \"AKE116\": \"\",\n" +
            "                    \"AKE118\": \"\",\n" +
            "                    \"AKE131\": \"\",\n" +
            "                    \"AKA072\": \"\",\n" +
            "                    \"AKA071\": \"\",\n" +
            "                    \"BKE344\": \"\",\n" +
            "                    \"AKC229\": \"\",\n" +
            "                    \"BKE347\": \"\",\n" +
            "                    \"AKF001\": \"\",\n" +
            "                    \"AAE386\": \"\",\n" +
            "                    \"BKE001\": \"\",\n" +
            "                    \"AKE021\": \"\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";
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
