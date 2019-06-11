package com.insigma.util;

import com.alibaba.fastjson.JSONObject;
import star.util.StringUtil;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class SignUtil {



    /**
     * 根据参数及appkey生成签名
     * @param parameters
     * @param appKey
     * @return
     */
    public static String createSign(SortedMap<String,Object> parameters,String  appKey){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"signature".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + appKey);//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
        String result=sb.toString();
        System.out.println(result);
        String signature = MD5Util.md5Password(result).toUpperCase();
        return signature;
    }

    /**
     * 根据参数及appkey验证签名
     * @param appKey
     * @return
     */
    public static JSONObject checkSign(JSONObject params,String appKey){
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
            SortedMap<String, String> parameters = new ConcurrentSkipListMap(params);
            StringBuffer sb = new StringBuffer();
            Set es = parameters.entrySet();
            Iterator it = es.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String k = (String) entry.getKey();
                Object v = entry.getValue();
                if (null != v && !"".equals(v)
                        && !"signature".equals(k) && !"key".equals(k)) {
                    sb.append(k + "=" + v + "&");
                }
            }
            sb.append("key=" + appKey);//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
            String finalString = sb.toString();
            if (!signature.equals(MD5Util.md5Password(finalString).toUpperCase())) {
                result.put("msg", "参数与生成规则不符");
            } else {
                result.put("msg", "验证通过");
                result.put("flag", 1);
            }
        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","验证参数异常！");
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

    public static void main(String[] args) {
        String testSecret="test";
        String testKey="test";
        SortedMap<String, Object> param = new ConcurrentSkipListMap<>() ;
        param.put("appKey",testKey);
        param.put("time", "20180919");
        param.put("nonceStr", UUID.randomUUID().toString().replaceAll("-",""));//随机字符串
        param.put("AAC002","3");
        param.put("trade","7300");
        String signature = createSign(param,testKey);
        param.put("signature",signature);
        JSONObject paramJson=new JSONObject(param);
        System.out.println(paramJson.toJSONString());
        System.out.println(checkSign(paramJson,testSecret));
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
