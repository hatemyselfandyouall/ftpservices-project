package com.insigma.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

@Configurable
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    public static ResponseEntity<String> getWithParamterMap(String url, Map<String,String> paramMap,RestTemplate restTemplate){
        Set<String> keyset= paramMap.keySet();
        if (!keyset.isEmpty()){
            url=url+"?";
            for (String key:keyset){
                url=url+key+"="+paramMap.get(key)+"&";
            }
            url=url.substring(0,url.length()-1);
        }
        return restTemplate.getForEntity(url,String.class);
    }

}