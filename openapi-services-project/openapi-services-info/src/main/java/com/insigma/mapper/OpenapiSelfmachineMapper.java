 
package com.insigma.mapper;

import com.insigma.facade.openapi.po.OpenapiSelfmachine;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineShowVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface OpenapiSelfmachineMapper extends Mapper<OpenapiSelfmachine>{


    List<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(@Param("param") OpenapiSelfmachineListVO openapiSelfmachineListVO);
}
