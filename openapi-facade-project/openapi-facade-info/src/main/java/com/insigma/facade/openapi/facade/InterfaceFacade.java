package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.*;

public interface InterfaceFacade {
    PageInfo<OpenapiInterface> getOpenapiInterfaceList(OpenapiInterfaceListVO buttonListVO);

    OpenapiInterfaceShowVO getOpenapiInterfaceDetail(OpenapiInterfaceDetailVO openapiInterfaceDetailVO);

    OpenapiInterfaceShowVO saveOpenapiInterface(OpenapiInterfaceSaveVO openapiInterfaceSaveVO);

    Integer deleteOpenapiInterface(OpenapiInterfaceDeleteVO openapiInterfaceDeleteVO);

    Integer updateInterfaceVersion(Long id);

    OpenapiInterface getInterfaceByCode(String code);
}
