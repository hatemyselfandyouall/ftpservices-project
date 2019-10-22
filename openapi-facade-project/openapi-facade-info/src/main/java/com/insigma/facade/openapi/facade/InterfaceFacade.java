package com.insigma.facade.openapi.facade;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiInterfaceDetailShowVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeTreeVO;
import com.insigma.facade.openapi.vo.openapiInterface.*;

import java.util.List;

public interface InterfaceFacade {
    PageInfo<OpenapiInterfaceDetailShowVO> getOpenapiInterfaceList(OpenapiInterfaceListVO buttonListVO);

    OpenapiInterfaceShowVO getOpenapiInterfaceDetail(OpenapiInterfaceDetailVO openapiInterfaceDetailVO);

    OpenapiInterfaceShowVO saveOpenapiInterface(OpenapiInterfaceSaveVO openapiInterfaceSaveVO);

    Integer deleteOpenapiInterface(OpenapiInterfaceDeleteVO openapiInterfaceDeleteVO);

    Integer updateInterfaceVersion(Long id);

    OpenapiInterface getInterfaceByCode(String code);

    JSONObject checkSignVaild(JSONObject param);

    JSONObject checkSignVaild(String param);

    JSONObject checkSignVaild(String paramString, String appKey, String time, String nonceStr, String signature,String encodeType);

    String getAppKeyListByCommandCodeAndOrgNO(String commandCode,String orgNO) throws Exception;

    List<OpenapiInterfaceTypeTreeVO> getOpenapiInterfaceTree();

    Integer releaseOpenapiInterface(OpenapiInterfaceReleaseVO openapiInterfaceDeleteVO);

    Integer setStatusOpenapiInterface(OpenapiInterfaceSetStatusVO openapiInterfaceSetStatusVO);
}

