 
package com.insigma.mapper;

import com.insigma.facade.openapi.po.OpenapiInterfaceResponseParam;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.special.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface OpenapiInterfaceResponseParamMapper extends Mapper<OpenapiInterfaceResponseParam>, InsertListMapper<OpenapiInterfaceResponseParam> {


    List<OpenapiInterfaceResponseParam> selectParamterTree(@Param("id")Long id);
}
