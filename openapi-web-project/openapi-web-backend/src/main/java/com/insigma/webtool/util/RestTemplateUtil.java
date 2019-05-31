package com.insigma.webtool.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

public class RestTemplateUtil {
    /**
     * 拼接字符串形式发送post
     * @param url
     * @param param
     * @return
     */
    public static <T> T postByQuery(String url, JSONObject param, Class<T> clazz){
        RestTemplate restTemplate=new RestTemplate();
        url=concatUrlFromParam(url,param);
        T result=restTemplate.postForObject(url,null,clazz);
        return result;
    }

    /**
     * map形式发送post
     * @param url
     * @param param
     * @return
     */
    public static <T> ResponseEntity<T> postByMap(String url, Object param, Class<T> clazz){
        RestTemplate restTemplate=new RestTemplate();
//        url=concatUrlFromParam(url,param);
        ResponseEntity<T> result=restTemplate.postForEntity(url,param,clazz);
        return result;
    }


    /**
     * 拼接url用的方法
     * @param url
     * @param param
     * @return
     */
    private static String concatUrlFromParam(String url, JSONObject param){
        StringBuilder sb=new StringBuilder(url);
        int count=0;
        for(Map.Entry<String, Object> entry : param.entrySet()) {
            if(!StringUtils.isEmpty(entry.getValue())){
                if(count==0){
                    sb.append("?");
                }else {
                    sb.append("&");
                }
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                count++;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String url="https://console.tim.qq.com/v4/openim/querystate?usersig=USERSIG&identifier=IDENTIFIER&sdkappid=SDKAPPID&random=RANDOM&contenttype=json";
        url=url.replace("USERSIG","eJx1kMFugkAURfd8BWFL08xjQKA7g9JibBtajNENGWCQqTjQmYGqTf9dg03Kpm97T*65ed*arutGsny-J3nedFyl6tRSQ3-QDexjQMbdH9C2rEiJSrEoBgBshMAG8OwRRY8tEzQlpaJioGxsoXENKyhXrGS-cVV-VWfwXMeznAn4zoiUxT4dpP-bJNsN4fN8E0Tx7ChrZnqJCFHY92W14tk22Jp4QTK*nr352au77uK8f7L2cVRNF-CRJUt16qbEFKFEUUCZ*fkoq3DF493mEMJLgmg995pspFTscPsPONjF19mT8eaeCskaPgAWAgcAfHQ9Q-vRLs17Ye0_");
        url=url.replace("IDENTIFIER","admin").replace("IDENTIFIER","hlwhz18758256195").replace("RANDOM",UUID.randomUUID().toString().replace("-", ""));
        url=url.replace("SDKAPPID","1400141184");
        MultiValueMap multiValueMap=new LinkedMultiValueMap();
        JSONObject To_Account=new JSONObject();
        System.out.println(postByMap(url,multiValueMap,String.class));
    }
}
