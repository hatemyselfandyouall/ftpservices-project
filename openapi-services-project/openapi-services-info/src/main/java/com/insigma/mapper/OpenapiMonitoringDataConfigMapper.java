 
package com.insigma.mapper;


import com.insigma.facade.openapi.dto.OpenapiMonitoringDataConfigDto;
import com.insigma.facade.openapi.po.OpenapiMonitoringDataConfig;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OpenapiMonitoringDataConfigMapper extends Mapper<OpenapiMonitoringDataConfig> {
    List<OpenapiMonitoringDataConfigDto> getOpenapiMonitoringDataConfigList(@Param("name") String name, @Param("offset")Integer offset, @Param("limit")Integer limit);

    List<OpenapiMonitoringDataConfigDto> getOpenapiMonitoringDataConfigCount(@Param("name") String name);
    OpenapiMonitoringDataConfigDto getOpenapiMonitoringDataConfigDetail(@Param("id") Long id);
    OpenapiMonitoringDataConfigDto getOpenapiMonitoringDataConfigInfo(@Param("interfaceId") Long interfaceId);
}
