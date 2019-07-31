package com.insigma.facade.openapi.facade;

import com.alibaba.fastjson.JSONObject;
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

    JSONObject checkSignVaild(JSONObject param);

    JSONObject checkSignVaild(String param);

    JSONObject checkSignVaild(String paramString, String appKey, String time, String nonceStr, String signature,String encodeType);

    String getAppKeyListByCommandCodeAndOrgNO(String commandCode,String orgNO) throws Exception;

    JSONObject insertMatters();
}

