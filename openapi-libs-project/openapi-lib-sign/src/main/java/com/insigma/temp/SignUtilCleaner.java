package com.insigma.temp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.Iterator;
import java.util.Set;


public class SignUtilCleaner {



    public static String createSign(JSONObject parameters,String  appSecret){
        parameters.put("secret",appSecret);
        String result=parameters.toString();
        System.out.println(result);
      //  log.info(result);
        String signature = MD5Util.md5Password(result).toUpperCase();
        parameters.remove("secret");
        return signature;
    }

    public static void main(String[] args) throws IOException {
        testMethod1();
    }
    private static void testMethod1(){
        String testKey="6e4ea384aef1499b883a9a5682af6b15";
        String testSecret="eb6f19609ccc4f348cb88630ba351b3b";
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
        String testUrl="http://10.85.159.203:10480/cmd/getCommand";
        postTest(haeder,param,testUrl);
    }
    private static String paramString="{\n" +
            "\t\"ver\": \"V1.0\",\n" +
            "\t\"orgNo\": \"330800\",\n" +
            "\t\"orgName\": \"衢州市市本级\",\n" +
            "\t\"id\": \"\",\n" +
            "\t\"inPut\": [{\n" +
            "\n" +
            "\t\t\"tradeNum\": \"10\"\n" +
            "\t}]\n" +
            "\n" +
            "}";

    public static void postTest(JSONObject haeder, Object paramJson,String testUrl) {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        Set<String> s= haeder.keySet();
        Iterator<String> iterator = s.iterator();
        while(iterator.hasNext()){
        	 String key = iterator.next();
            requestHeaders.add(key,haeder.get(key).toString()!=null?haeder.get(key).toString():"");
        }
        requestHeaders.add("Content-Type", "application/json");
        //body
        //HttpEntity
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity(paramJson, requestHeaders);
        ResponseEntity result = restTemplate.postForEntity(testUrl,requestEntity,String.class);
        System.out.println(result.getBody());
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
