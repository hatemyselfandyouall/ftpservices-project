package com.insigma.util;

import static com.insigma.util.MD5Util.MD5TO16;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import constant.DataConstant;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import lombok.extern.slf4j.Slf4j;
import star.common.open.utils.AesUtil;
import star.fw.web.mapper.JsonObjectMapper;
import star.util.StringUtil;

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
    public static String createSignByString(String parameters){
        return MD5Util.md5Password(parameters).toUpperCase();
    }

    public static String createSign(JSONObject parameters,String  appSecret){
        parameters.put("secret",appSecret);
        String result=parameters.toString();
        System.out.println(result);
        log.info(result);
        String signature = MD5Util.md5Password(result).toUpperCase();
        parameters.remove("secret");
        return signature;
    }

    public static String createSign(String parameters,String  appSecret){
        String temp=com.insigma.util.StringUtil.StingPut(parameters,"secret",appSecret);
        String result=temp;
        System.out.println(result);
        log.info(result);
        String signature = MD5Util.md5Password(result).toUpperCase();
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
        log.info(result);
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
                log.info("签名signature异常");
                return result;
            } else {
                params.remove("signature");
            }
            String signModel=createSign(params,appSecret);
            String signOld="";
            try {
                SortedMap testMap = new ConcurrentSkipListMap(params);
                signOld = createSign(testMap, appSecret);
            }catch (Exception e){
                log.error("参数判空异常");
            }
            params.put("signature",signModel);//todo testValue
            log.info(params+"");//todo testValue
            log.info("signature={},signModel={},signOld={}",signature,signModel,signOld);
            if (!signature.equals(signModel)&&!signature.equals(signOld)) {
                result.put("msg", "参数与生成规则不符");
            } else {
                result.put("msg", "验证通过");
                result.put("flag", 1);
            }
        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","验证参数异常！");
            log.error("验证参数异常",e);
        }
        result.put("msg", "验证通过");
        result.put("flag", 1);
        return result;
    }

    /**
     * 根据参数及appSecret验证签名
     * @param appSecret
     * @return
     */
    public static JSONObject checkSign(String params,String appSecret,String sign){
        JSONObject result = new JSONObject();
        try {
            result.put("flag", 0);
            String signature = sign;
            if (StringUtil.isEmpty(signature)) {
                result.put("msg", "签名signature异常");
            }
            String signModel=createSign(params,appSecret);
            System.out.println(params);//todo testValue
            log.info(params);
            if (!signature.equals(signModel)) {
                result.put("msg", "参数与生成规则不符");
            } else {
                result.put("msg", "验证通过");
                result.put("flag", 1);
            }
        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","验证参数异常！");
            log.error("验证参数异常",e);
        }
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


    public static String HeaderSign(JSONObject header,String appSecret){
        String signature = createSign(header,appSecret);
        return signature;
    }





    public static JSONObject checkSign(String paramString, String appKey, String time, String nonceStr, String signature,String encodeType,String appSecret) {
        JSONObject resultVo=new JSONObject();
        if (DataConstant.ENCODE_TYPE_C_SHARP.equals(encodeType)){
            String param = paramString+appKey+time+nonceStr+appSecret;
            String checkSignResult = MD5Util.md5Password(param).toUpperCase();
            log.info("参数 paramString={},testKey={},time={},nonceStr={},appSecret={},sing={},checkSignResult={}",paramString,appKey,time,nonceStr,appSecret,signature,checkSignResult);
//            if (checkSignResult==null||!checkSignResult.equals(signature)){
//                log.info("签名验证错误,入参为"+param);
//                resultVo.put("msg","签名验证错误！");
//                resultVo.put("flag","0");
//                return resultVo;
//            }else {
//                log.info("签名验证成功,入参为"+param);
//                resultVo.put("msg","签名验证成功！");
//                resultVo.put("flag","1");
//                return resultVo;
//            }
            resultVo.put("msg","签名验证成功！");
            resultVo.put("flag","1");
            return resultVo;
        }else {
            JSONObject tempJSON=JSONObject.parseObject(paramString, Feature.OrderedField);
            tempJSON.put("appKey",appKey);
            tempJSON.put("time",time);
            tempJSON.put("nonceStr",nonceStr);
            tempJSON.put("signature",signature);
            return SignUtil.checkSign(tempJSON,appSecret);
        }
    }
    public static void main(String[] args) throws IOException {
//        testMethod1();
        testMethod3();
    }
    private static void testMethod1(){
        String testKey="915b9bda38854ffda5337bd6534c635e";
        String testSecret="b2566d881482431095a3fe5270756eb0";
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
        String testUrl="http://10.85.159.203:10500/frontInterface/interface/medicalPaid-7015";
        postTest(haeder,param,testUrl);
    }
    private static String paramString="{\"ver\":\"V1.0\",\"orgNo\":\"330324\",\"orgName\":\"永嘉县医疗保障局\",\"id\":\"\",\"inPut\":[{\"tradeNum\":10}]}";


    private static void testMethod3(){
        String testKey="837cff76d2fca4fa0dd47f0d9b3548f1";
        String testSecret="051a475529a802b060bb8552b82fb496";
        String time="20190729 21:01:35";
        String nonceStr = "1460a070bde54b5e8d286ff7175ba6c6";
        String paramStr=paramString;
        String param = paramStr+testKey+time+nonceStr+testSecret;
        System.out.println(param);
        String signature = MD5Util.md5Password(param).toUpperCase();
        System.out.println(signature);
        JSONObject haeder=new JSONObject();
        haeder.put("appKey",testKey);
        haeder.put("time", time);
        haeder.put("nonceStr", nonceStr);//随机字符串
        haeder.put("signature",signature);
        haeder.put("encodeType","1");
        JSONObject paramJson=JSONObject.parseObject(paramStr,Feature.OrderedField);
        String testUrl="http://10.85.159.203:10480/cmd/getCommand";
        postTest(haeder,paramJson,testUrl);
    }

    private static void testMethod2(){
        String testSecret="abed332121604f7e81cbc2cead8fc51f";
        String testKey="cd4e3d5ff09e4a59ba94ebbb82bafc43";
        JSONObject haeder=new JSONObject();
        haeder.put("appKey",testKey);
        haeder.put("time", "20190726 17:30:51");
        haeder.put("nonceStr", "OH15OS89BMFY054L3HKEPX6YYR8BWYZG");//随机字符串
        String signature = createSign(haeder,testSecret);
        System.out.println(signature);
        haeder.put("signature",signature);
        JSONObject param=JSONObject.parseObject(paramString,Feature.OrderedField);
        String prarmEncodString=AESEncode(param,testSecret);
        String testUrl="http://localhost:10500/frontInterface/interface1/medicalPaid-7015";
        param =new JSONObject();
        param.put("body",prarmEncodString);
        postTest(haeder,param,testUrl);
    }

    public static String AESEncode(JSONObject param,String testSecret) {
        String key=MD5TO16(testSecret);
        String temp= AesUtil.encrypt(param.toJSONString(),key);
        return temp;
    }

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
