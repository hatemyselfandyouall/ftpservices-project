package com.insigma.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.insigma.util.SignUtil.createSign;
import static com.insigma.util.SignUtil.getParamWithoutsignatureParam;

@Slf4j
public class StringUtil {

    private static String paramString="{\"ver\":\"V1.0\",\"orgNo\":\"330000101011\",\"orgName\":\"浙江省立同德医院\",\"id\":\"\",\"inPut\":[{\"tradeNum\":\"\"}]}";


    public static void main(String[] args) {
        String testSecret="abed332121604f7e81cbc2cead8fc51f";
        String testKey="cd4e3d5ff09e4a59ba94ebbb82bafc43";
        JSONObject haeder=new JSONObject();
        haeder.put("appKey",testKey);
        haeder.put("time", "20190726 17:30:51");
        haeder.put("nonceStr", "OH15OS89BMFY054L3HKEPX6YYR8BWYZG");//随机字符串
//        JSONObject param=JSONObject.parseObject(paramString, Feature.OrderedField);
        paramString=StringPutAll(paramString,haeder);
        System.out.println(paramString);
//        System.out.println(StingGet(paramString,"time"));
//        String signature = createSign(param,testSecret);
//        System.out.println(signature);
//        haeder.put("signature",signature);
//        param=getParamWithoutsignatureParam(param);
//        String testUrl="http://10.85.159.203:10500/frontInterface/interface/medicalPaid-7011";
//        postTest(haeder,param,testUrl);
    }

    public static String StingPut(String paramString,String key,String param){
        try {
            String temp = paramString.substring(0, paramString.lastIndexOf("}"));
            StringBuilder stringBuilder = new StringBuilder(temp);
            stringBuilder.append(",").append("\"").append(key).append("\"").append(":").append("\"").append(param).append("\"").append("}");
            paramString=stringBuilder.toString();
            return paramString;
        }catch (Exception e){
            log.error("拼装字符串异常",e);
            return "";
        }
    }

    public static String StringPutAll(String paramString,JSONObject params){
        try {
        Set<String> keySet=params.keySet();
        Iterator<String> iterator=keySet.iterator();
        while (iterator.hasNext()){
            String key=iterator.next();
            paramString=StingPut(paramString,key, params.getString(key));
        }
        return paramString;
        }catch (Exception e){
            log.error("拼装字符串异常1",e);
            return "";
        }
    }

//    public static String StingGet(String paramString,String key){
//        try {
//            if (!paramString.contains(key)){
//                return null;
//            }else {
//                Pattern r = Pattern.compile("\""+key+"\":.*[,}]");
//                // 现在创建 matcher 对象
//                Matcher m = r.matcher(paramString);
//                if (m.find()){
//                    return m.group(0);
//                }else {
//                    return null;
//                }
//            }
//        }catch (Exception e){
//            log.error("拼装字符串异常",e);
//            return "";
//        }
//    }

}
