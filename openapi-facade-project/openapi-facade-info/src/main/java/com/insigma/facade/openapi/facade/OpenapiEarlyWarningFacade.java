 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.OpenapiEarlyWarningDto;
import com.insigma.facade.openapi.po.OpenapiEarlyWarning;



public interface OpenapiEarlyWarningFacade {

	PageInfo<OpenapiEarlyWarning> getOpenapiEarlyWarningList(String name,Integer offset,Integer limit);

    OpenapiEarlyWarning getOpenapiEarlyWarningDetail(Long id);

    Integer saveOpenapiEarlyWarning(OpenapiEarlyWarningDto openapiEarlyWarningDto);

    Integer deleteOpenapiEarlyWarning(Long id);

}

 
