 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.OpenapiMonitoringDataConfigDto;
import com.insigma.facade.openapi.po.OpenapiMonitoringDataConfig;

import java.util.List;


public interface OpenapiMonitoringDataConfigFacade {

	PageInfo<OpenapiMonitoringDataConfigDto> getOpenapiMonitoringDataConfig(String name, Integer offset, Integer limit);

    OpenapiMonitoringDataConfigDto getOpenapiMonitoringDataConfigDetail(Long id);

    OpenapiMonitoringDataConfigDto getOpenapiMonitoringDataConfigInfo(Long interfaceId);

    Integer saveOpenapiMonitoringDataConfig(OpenapiMonitoringDataConfigDto openapiMonitoringDataConfigDto);

    Integer deleteOpenapiMonitoringDataConfig(Long id);

    List<OpenapiMonitoringDataConfig> getConfigList();

}

 
