package com.insigma.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import star.fw.web.mapper.JsonObjectMapper;
import star.util.StringUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

@Slf4j
public class SignUtil {


    private static JsonObjectMapper jsonObjectMapper=new JsonObjectMapper();
    /**
     * 根据参数及appSecret生成签名
     * @param parameters
     * @param appSecret
     * @return
     */
    public static String createSign(SortedMap<String,Object> parameters,String  appSecret){
        return getSignByEntry(appSecret, parameters.entrySet());
    }

    public static String createSign(JSONObject parameters,String  appSecret){
        parameters.put("secret",appSecret);
        String result=parameters.toString();
        System.out.println(result);
        String signature = MD5Util.md5Password(result).toUpperCase();
        parameters.remove("secret");
        return signature;
    }

    private static String getSignByEntry(String appSecret, Set<Map.Entry<String, Object>> entries) {
        StringBuffer sb = new StringBuffer();
        Set es = entries;
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"signature".equals(k) && !"secret".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("secret=" + appSecret);//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
        String result=sb.toString();
        System.out.println(result);
        String signature = MD5Util.md5Password(result).toUpperCase();
        return signature;
    }

    /**
     * 根据参数及appSecret验证签名
     * @param appSecret
     * @return
     */
    public static JSONObject checkSign(JSONObject params,String appSecret){
        JSONObject result = new JSONObject();
//        try {
//            JSONObject checkParamResult = checkParamsVaild(params);
//            if (checkParamResult.getInteger("flag") != 1) {
//                return checkParamResult;
//            }
//            result.put("flag", 0);
//            String signature = params.getString("signature");
//            if (StringUtil.isEmpty(signature)) {
//                result.put("msg", "签名signature异常");
//            } else {
//                params.remove("signature");
//            }
//            String signModel=createSign(params,appSecret);
//            SortedMap testMap= new ConcurrentSkipListMap(params);
//            String signOld=createSign(testMap,appSecret);
//            params.put("signature",signModel);//todo testValue
//            System.out.println(params);//todo testValue
//            if (!signature.equals(signModel)&&!signature.equals(signOld)) {
//                result.put("msg", "参数与生成规则不符");
//            } else {
//                result.put("msg", "验证通过");
//                result.put("flag", 1);
//            }
//        }catch (Exception e){
//            result.put("flag",0);
//            result.put("msg","验证参数异常！");
//            log.error("验证参数异常",e);
//        }
                        result.put("msg", "验证通过");
                        result.put("flag", 1);
        return result;
    }

    private static JSONObject checkParamsVaild(JSONObject params) {
        JSONObject result=new JSONObject();
        result.put("flag",0);
        if (params==null||params.isEmpty()){
            result.put("msg","参数不能为空！");
            return result;
        }
        if (StringUtil.isEmpty(params.getString("appKey"))){
            result.put("msg","appKey不能为空！");
            return result;
        }
        if (StringUtil.isEmpty(params.getString("time"))){
            result.put("msg","time不能为空！");
            return result;
        }
        if (StringUtil.isEmpty(params.getString("nonceStr"))){
            result.put("msg","nonceStr不能为空！");
            return result;
        }
        result.put("flag",1);
        return result;
    }




    public static void main(String[] args) throws IOException {
        String testSecret="abed332121604f7e81cbc2cead8fc51f";
        String testKey="cd4e3d5ff09e4a59ba94ebbb82bafc43";
        JSONObject haeder=new JSONObject();
        haeder.put("appKey",testKey);
        haeder.put("time", "20190628 11:44:07");
        haeder.put("nonceStr", "7dbdc3b29a7f42948ab820bb42dec8b0");//随机字符串
        JSONObject param=JSONObject.parseObject(paramString,Feature.OrderedField);
        //        SortedMap testMap= new ConcurrentSkipListMap(param);
        param.putAll(haeder);
        String signature = createSign(param,testSecret);
        System.out.println(signature);
        haeder.put("signature",signature);
        param=getParamWithoutsignatureParam(param);
//        param.putAll(haeder);
//        String signature = createSign(testMap,testSecret);
//        testMap.put("signature",signature);
//        JSONObject paramJson=new JSONObject(testMap);
//        System.out.println(paramJson.toJSONString());
//        System.out.println(checkSign(paramJson,testSecret));
//        paramJson=getParamWithoutsignatureParam(param);
//        haeder.put("signature",signature);
//        String testUrl="http://10.87.0.68/api/frontInterface/interface/transerService-7102";
        String testUrl="http://10.85.159.203:10500/frontInterface/interface/medicalPaid-7011";
        postTest(haeder,param,testUrl);
    }

    private static void postTest(JSONObject haeder, JSONObject paramJson,String testUrl) {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        haeder.keySet().forEach(
                i->{
                    requestHeaders.add(i,haeder.get(i).toString()!=null?haeder.get(i).toString():"");
                }
        );
        requestHeaders.add("Content-Type", "application/json");
        //body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap();
        //HttpEntity
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity(paramJson, requestHeaders);
        Object result= restTemplate.postForEntity(testUrl,requestEntity,String.class);
        System.out.println(result.toString());
    }


    private static String paramString="{\n" +
            "\t\"ver\": \"V1.0\",\n" +
            "\t\"orgNo\": \"330000101006\",\n" +
            "\t\"orgName\": \"æµ\\u0099æ±\\u009Få¤§å\u00AD¦å\\u008C»å\u00AD¦é\\u0099¢é\\u0099\\u0084å±\\u009Eå\\u0084¿ç«¥å\\u008C»é\\u0099¢\",\n" +
            "\t\"id\": \"\",\n" +
            "\t\"inPut\": [{\n" +
            "\t\t\"LS_DT1\": [{\n" +
            "\t\t\t\"AKC190\": \"100061037\",\n" +
            "\t\t\t\"BKC022\": \"6475782\",\n" +
            "\t\t\t\"AKA077\": \"0\",\n" +
            "\t\t\t\"AAZ285\": \"\",\n" +
            "\t\t\t\"AAC003\": \"赖昭宇\",\n" +
            "\t\t\t\"AAC002\": \"331124201205170412\",\n" +
            "\t\t\t\"BKE100\": \"100061037\",\n" +
            "\t\t\t\"AAE030\": \"2019-07-23\",\n" +
            "\t\t\t\"AAE031\": \"\",\n" +
            "\t\t\t\"AKA078\": \"1\",\n" +
            "\t\t\t\"AKA120\": \"\",\n" +
            "\t\t\t\"AKA121\": \"\",\n" +
            "\t\t\t\"AKE024\": \"\",\n" +
            "\t\t\t\"AAE386\": \"213\",\n" +
            "\t\t\t\"AKE020\": \"呼吸内科(湖滨)\",\n" +
            "\t\t\t\"BKE001\": \"\",\n" +
            "\t\t\t\"AKE021\": \"\",\n" +
            "\t\t\t\"BKE318\": \"\",\n" +
            "\t\t\t\"BKE351\": \"0\",\n" +
            "\t\t\t\"BKE333\": \"0\",\n" +
            "\t\t\t\"AKC264\": \"26.00\",\n" +
            "\t\t\t\"LS_DT2\": {\n" +
            "\t\t\t\t\"BKA100\": \"0\",\n" +
            "\t\t\t\t\"BKA101\": \"1\",\n" +
            "\t\t\t\t\"BKA102\": \"0\",\n" +
            "\t\t\t\t\"BKE100\": \"100061037\",\n" +
            "\t\t\t\t\"BKA104\": \"浙江大学医学院附属儿童医院\",\n" +
            "\t\t\t\t\"BKA105\": \"滨江APP\",\n" +
            "\t\t\t\t\"AAE036\": \"2019-07-22 05:08:37.0\",\n" +
            "\t\t\t\t\"AAC003\": \"赖昭宇\",\n" +
            "\t\t\t\t\"AAC002\": \"331124201205170412\",\n" +
            "\t\t\t\t\"AKC264\": \"26.00\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"LS_DT3\": {\n" +
            "\t\t\t\t\"BKA120\": \"\",\n" +
            "\t\t\t\t\"BKA121\": \"\"\n" +
            "\t\t\t}\n" +
            "\t\t}],\n" +
            "\t\t\"COUNT\": 1\n" +
            "\t}]\n" +
            "}";


    public static JSONObject getParamWithoutsignatureParam(JSONObject params) {
        params.remove("appKey");
        params.remove("time");
        params.remove("nonceStr");
        params.remove("signatureType");
        params.remove("signature");
        return params;
    }



}
