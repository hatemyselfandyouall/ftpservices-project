 
package com.insigma.mapper;

import com.insigma.facade.openapi.po.OpenapiInterfaceRequestParam;
import com.insigma.facade.openapi.po.OpenapiInterfaceResponseParam;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;


public interface OpenapiInterfaceRequestParamMapper extends Mapper<OpenapiInterfaceRequestParam>, InsertListMapper<OpenapiInterfaceRequestParam> {


    List<OpenapiInterfaceRequestParam> selectParamterTree(@Param("id")Long id);


}
