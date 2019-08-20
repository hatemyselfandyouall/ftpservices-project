package com.insigma.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
        String testKey="b0dcb609aa8a475f87f659fc2309980c";
        String testSecret="eb5dc42ccac041fca9f841c597056b69";
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
        String testUrl="http://10.85.159.203:10500/frontInterface/interface/getSelfPaidTreatment-7209";
        postTest(haeder,param,testUrl);
    }
    private static String paramString="{\"ver\":\"V1.0\",\"orgNo\":\"330900\",\"trade\":\"7200\",\"orgName\":\"舟山市医保局\",\"id\":\"339900190625128064373\",\"inPut\":[{\"AGA001\":\"339900190625128064373\"}]}";

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
        Object result= restTemplate.postForEntity(testUrl,requestEntity,String.class);
        System.out.println(result.toString());
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
