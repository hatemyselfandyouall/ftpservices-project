 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.OpenapiBlackWhiteDto;
import com.insigma.facade.openapi.po.OpenapiBlackWhite;

import java.util.Date;
import java.util.List;


public interface OpenapiBlackWhiteFacade {

	PageInfo<OpenapiBlackWhite> getOpenapiBlackWhiteList(String name, Integer addType, Date startDate, Date endDate, Integer offset, Integer limit);

    OpenapiBlackWhite getOpenapiBlackWhiteDetail(Long id);

    void saveOpenapiBlackWhite(OpenapiBlackWhiteDto openapiBlackWhiteDto);

    Integer deleteOpenapiBlackWhite(Long id);

    /**
     *安全监控中用到ip和黑白名单的关联关系
     * @return
     */
    List<OpenapiBlackWhite> getBlackWhiteList();
}

 
