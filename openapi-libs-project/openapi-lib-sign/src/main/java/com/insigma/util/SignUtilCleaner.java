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
        String testKey="18b72f00138c91232cb28336c89595bf";
        String testSecret="2ddcaee0582930ab1836008c4ddddeff";
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
        String testUrl="http://10.87.1.152:10500/frontInterface/interface/serviceEntrance-8301";
        postTest(haeder,param,testUrl);
    }
    private static String paramString="{\"ver\":\"v1.0\",\"orgNo\":\"330800100002\",\"orgName\":\"衢州市柯城区人民医院\",\"id\":\"\",\"inPut\":[{\"COUNT\":\"1\",LS_DT1:[{\"AKC190\":\"20190904272578\",\"BKC022\":\"12523260\",\"AKA077\":\"0\",\"AAZ285\":\"\",\"AAC003\":\"童逸安\",\"AAC002\":\"330802201807255519\",\"BKE100\":\"20190904272578\",\"AAE030\":\"2019-09-04\",\"AAE031\":\"2019-09-04\",\"AKA078\":\"1\",\"AKA120\":\"Z00.100\",\"AKA121\":\"常规儿童健康检查\",\"AKE024\":\"要求查血\",\"AAE386\":\"\",\"AKE020\":\"\",\"BKE001\":\"\",\"AKE021\":\"方斌豪\",\"BKE318\":\"\",\"BKE351\":\"0\",\"BKE333\":\"0\",\"AKC264\":94.80,LS_DT2:[{\"BKA100\":\"衢州市柯城区人民医院门诊发票\",\"BKA101\":\"1\",\"BKA102\":\"20190904272578\",\"BKE100\":\"20190904272578\",\"BKA104\":\"衢州市柯城区人民医院\",\"BKA105\":\"诊间支付\",\"AAE036\":\"2019-09-04\",\"AAC003\":\"童逸安\",\"AAC002\":\"330802201807255519\",\"AKC264\":94.80}],LS_DT3:[{\"AKA120\":\"Z00.100\",\"BKA121\":\"常规儿童健康检查\"}] }]}]}\n";
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
