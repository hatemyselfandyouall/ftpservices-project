 
package com.insigma.mapper;

import com.insigma.facade.openapi.dto.SelfMachineOrgDTO;
import com.insigma.facade.openapi.po.OpenapiOrg;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface OpenapiOrgMapper extends Mapper<OpenapiOrg>{


    List<SelfMachineOrgDTO> getSelfMachine(@Param("name") String name);
}
