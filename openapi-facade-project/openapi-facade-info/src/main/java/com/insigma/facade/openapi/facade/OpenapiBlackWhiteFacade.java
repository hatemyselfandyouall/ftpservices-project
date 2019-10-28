 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.OpenapiBlackWhiteDto;
import com.insigma.facade.openapi.po.OpenapiBlackWhite;

import java.util.Date;


public interface OpenapiBlackWhiteFacade {

	PageInfo<OpenapiBlackWhite> getOpenapiBlackWhiteList(String name, Integer addType, Date startDate, Date endDate, Integer offset, Integer limit);

    OpenapiBlackWhite getOpenapiBlackWhiteDetail(Long id);

    Integer saveOpenapiBlackWhite(OpenapiBlackWhiteDto openapiBlackWhiteDto);

    Integer deleteOpenapiBlackWhite(Long id);

}

 
