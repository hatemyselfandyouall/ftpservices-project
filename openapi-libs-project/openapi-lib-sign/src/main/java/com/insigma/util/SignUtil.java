package com.insigma.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
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
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
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
        try {
            JSONObject checkParamResult = checkParamsVaild(params);
            if (checkParamResult.getInteger("flag") != 1) {
                return checkParamResult;
            }
            result.put("flag", 0);
            String signature = params.getString("signature");
            if (StringUtil.isEmpty(signature)) {
                result.put("msg", "签名signature异常");
            } else {
                params.remove("signature");
            }
            SortedMap parameters = new ConcurrentSkipListMap(params);
            StringBuffer sb = new StringBuffer();
            Set es = parameters.entrySet();
            Iterator it = es.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String k = (String) entry.getKey();
                Object v = entry.getValue();
                if (null != v && !"".equals(v)
                        && !"signature".equals(k) && !"secret".equals(k)) {
                    sb.append(k + "=" + v + "&");
                }
            }
            sb.append("secret=" + appSecret);//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
            String finalString = sb.toString();
            String sign=MD5Util.md5Password(finalString).toUpperCase();
            parameters.put("signature",sign);//todo testValue
            System.out.println(parameters);//todo testValue
            if (!signature.equals(sign)) {
                result.put("msg", "参数与生成规则不符");
            } else {
                result.put("msg", "验证通过");
                result.put("flag", 1);
            }
        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","验证参数异常！");
            log.error("验证参数异常"+e);
        }
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
        String testSecret="test";
        String testKey="test";
        JSONObject haeder=new JSONObject();
        haeder.put("appKey",testKey);
        haeder.put("time", "20180919 11:00:00");
        haeder.put("nonceStr", "b319b62bb7b34c60953257d546ab468e");//随机字符串
        JSONObject param=jsonObjectMapper.readValue(paramString,JSONObject.class);
        param.putAll(haeder);
        SortedMap testMap= new ConcurrentSkipListMap(param);
        String signature = createSign(testMap,testSecret);
        testMap.put("signature",signature);
        JSONObject paramJson=new JSONObject(testMap);
        System.out.println(paramJson.toJSONString());
        System.out.println(checkSign(paramJson,testSecret));
    }

    private static String paramString="{\n" +
            "\t\"pageNum\":\"1\",\n" +
            "\t\"pageSize\":\"1\"\n" +
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
