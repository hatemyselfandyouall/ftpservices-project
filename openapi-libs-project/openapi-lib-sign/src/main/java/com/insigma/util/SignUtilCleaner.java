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
        String testKey="e980a043c8204a1e9fbe5843bd8ebff7";
        String testSecret="bc4d5dffeb5d489390c14cf654b2b3c8";
        JSONObject haeder=new JSONObject(true);
        haeder.put("appKey",testKey);
        haeder.put("time", "20190729 21:01:35");
        haeder.put("nonceStr", "1460a070bde54b5e8d286ff7175ba6c6");//随机字符串
        JSONObject param=JSONObject.parseObject(paramString,Feature.OrderedField);
        param.putAll(haeder);
        String signature = createSign(param,testSecret);
        System.out.println(signature);
        haeder.put("signature",signature);
        param=getParamWithoutsignatureParam(param);
        String testUrl="http://localhost:10500/frontInterface/interface/testLog";
        postTest(haeder,param,testUrl);
    }
    private static String paramString="{\n" +
            "   \"AAB301\":\"330799\",\n" +
            "   \"BAB010\":\"12330000470027355W \"\n" +
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
