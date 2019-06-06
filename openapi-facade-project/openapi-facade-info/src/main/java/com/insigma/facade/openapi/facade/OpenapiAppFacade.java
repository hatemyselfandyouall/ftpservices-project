 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.vo.OpenapiApp.*;

import java.util.List;


public interface OpenapiAppFacade{

	PageInfo<OpenapiApp> getOpenapiAppList(OpenapiAppListVO listVO);

    OpenapiApp getOpenapiAppDetail(OpenapiAppDetailVO detailVO);

    Integer saveOpenapiApp(OpenapiAppSaveVO saveVO);

    Integer deleteOpenapiApp(OpenapiAppDeleteVO deleteVO);


    OpenapiAppShowDetailVO getAppByAppKey(String appId);

    List<OpenapiAppShowDetailVO> getAppsByUserId(Long id);
}

 
