package com.insigma.util;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {

    public static <T> T convert(Object object,Class<T> clazz){
        return JSONObject.parseObject(JSONObject.toJSONString(object),clazz);
    }
}
