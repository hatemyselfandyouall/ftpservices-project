 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.OpenapiMonitoringDataConfigDto;
import com.insigma.facade.openapi.po.OpenapiMonitoringDataConfig;


public interface OpenapiMonitoringDataConfigFacade {

	PageInfo<OpenapiMonitoringDataConfigDto> getOpenapiMonitoringDataConfig(String name, Integer offset, Integer limit);

    OpenapiMonitoringDataConfigDto getOpenapiMonitoringDataConfigDetail(Long id);

    Integer saveOpenapiMonitoringDataConfig(OpenapiMonitoringDataConfigDto openapiMonitoringDataConfigDto);

    Integer deleteOpenapiMonitoringDataConfig(Long id);

}

 
