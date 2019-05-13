package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.OpenapiInterfaceDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceSaveVO;

public interface InterfaceFacade {
    PageInfo<OpenapiInterface> getOpenapiInterfaceList(OpenapiInterfaceListVO buttonListVO);

    OpenapiInterface getOpenapiInterfaceDetail(OpenapiInterfaceDetailVO openapiInterfaceDetailVO);

    Integer saveOpenapiInterface(OpenapiInterfaceSaveVO openapiInterfaceSaveVO);

    Integer deleteOpenapiInterface(OpenapiInterfaceDeleteVO openapiInterfaceDeleteVO);
}
