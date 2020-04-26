package com.insigma.util;

import org.apache.commons.collections.CollectionUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public class MybatisUtil {

    public   static <T> T SelectOne(T t, Mapper<T> mapper){
        List<T> list=mapper.select(t);
        if (!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }else {
            return null;
        }
    }


    public static <T> T SelectOne(Example example, Mapper mapper){
        List<T> list=mapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }else {
            return null;
        }
    }
}
