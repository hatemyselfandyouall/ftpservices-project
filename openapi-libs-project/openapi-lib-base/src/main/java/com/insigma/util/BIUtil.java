package com.insigma.util;

import com.alibaba.fastjson.JSONObject;
import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.table.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import star.vo.result.ResultVo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BIUtil {

    public static ResultVo<TableInfo> getRequestResult(Long pageNum, Long pageSize, Map<String,Object> whereWord, String orderByWord, String code, OpenapiDictionaryFacade openapiDictionaryFacade, RestTemplate restTemplate) {
        ResultVo resultVo = new ResultVo();
        try {
            String testUrl = openapiDictionaryFacade.getValueByCode(code);
            Map<String, Object> stringStringMap = new HashMap<>();
            stringStringMap.put("pageNum", pageNum);
            stringStringMap.put("pageSize", pageSize);
            stringStringMap.put("whereWord", whereWord);
            stringStringMap.put("orderByWord", orderByWord);
            ResponseEntity<String> result = postWithParamterMap(testUrl, stringStringMap, restTemplate);
            if (result.getStatusCode().is2xxSuccessful()&&JSONObject.parseObject(result.getBody()).getBoolean("success")) {
                resultVo.setSuccess(true);
                resultVo.setResultDes("调用成功");
                resultVo.setResult(JSONUtil.convert(JSONObject.parseObject(result.getBody()).getJSONObject("result"),TableInfo.class));
            } else {
                resultVo.setSuccess(false);
                resultVo.setResultDes(result.getBody());
            }
        } catch (Exception e) {
            resultVo.setResultDes("接口调用趋势异常");
            log.error("接口调用趋势异常", e);
        }
        return resultVo;
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


    public static ResponseEntity<String> postWithParamterMap(String url, Map<String,Object> paramMap,RestTemplate restTemplate){
        return restTemplate.postForEntity(url,paramMap,String.class);
    }
}
