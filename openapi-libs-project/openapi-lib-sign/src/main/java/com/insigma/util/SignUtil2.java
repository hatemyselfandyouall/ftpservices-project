package com.insigma.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

public class SignUtil2 {
    public static String createSign(JSONObject parameters,String  appSecret){
        parameters.put("secret",appSecret);
        String result=parameters.toString();
        String signature = MD5Util.md5Password(result).toUpperCase();
        parameters.remove("secret");
        return signature;
    }

    /**
     * 根据参数及appSecret验证签名
     * @param appSecret
     * @return
     */
   
    public static String getSign(String nonceStr,String time,String paramString){
        String testSecret="b2566d881482431095a3fe5270756eb0";
        String testKey="915b9bda38854ffda5337bd6534c635e";
        JSONObject haeder=new JSONObject();
        haeder.put("appKey",testKey);
        haeder.put("time", time);
        haeder.put("nonceStr", nonceStr);//随机字符串
        JSONObject param=JSONObject.parseObject(paramString,Feature.OrderedField);
        param.putAll(haeder);
        String signature = createSign(param,testSecret);
        haeder.put("signature",signature);
        param=getParamWithoutsignatureParam(param);
        String testUrl="http://10.85.159.203:10480/cmd/getCommand";
        SignUtil.postTest(haeder,param,testUrl);
        HttpHelper.sendPost(testUrl,param.toJSONString());
        return signature;

   
    }

    public static JSONObject getParamWithoutsignatureParam(JSONObject params) {
        params.remove("appKey");
        params.remove("time");
        params.remove("nonceStr");
        params.remove("signatureType");
        params.remove("signature");
        return params;
    }

    public static void main(String[] args) {
     String sign=    getSign("b5be9ad3e2ac41a2aa994157528bbb11","20190731 09:28:57","{\"ver\":\"V1.0\",\"orgNo\":\"330000101006\",\"orgName\":\"浙江大学医学院附属儿童医院\",\"id\":\"\",\"inPut\":[]}");
     System.out.println(sign);
    }

}
