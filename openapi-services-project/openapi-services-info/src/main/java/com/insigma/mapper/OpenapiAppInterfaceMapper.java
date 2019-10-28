 
package com.insigma.mapper;

import com.insigma.facade.openapi.po.OpenapiAppInterface;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface OpenapiAppInterfaceMapper extends Mapper<OpenapiAppInterface>{


    List<OpenapiAppInterface> getOpenapiAppInterfaceList(@Param("keyword") String keyword, @Param("sourceType")Integer sourceType, @Param("appId")Long appId);
}
