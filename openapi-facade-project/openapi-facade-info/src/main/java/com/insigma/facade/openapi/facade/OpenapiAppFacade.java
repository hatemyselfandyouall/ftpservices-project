 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.OpenapiApp.*;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceListVO;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceSaveVO;
import star.vo.result.ResultVo;

import java.util.List;


public interface OpenapiAppFacade{

	PageInfo<OpenapiAppListShowVO> getOpenapiAppList(OpenapiAppListVO listVO);

    OpenapiApp getOpenapiAppDetail(OpenapiAppDetailVO detailVO);

    Integer saveOpenapiApp(OpenapiAppSaveVO saveVO, Long userId, String userName, String orgName);

    Integer deleteOpenapiApp(OpenapiAppDeleteVO deleteVO);


    OpenapiAppShowDetailVO getAppByAppKey(String appId);

    List<OpenapiAppShowDetailVO> getAppsByUserId(Long id);

    Long getInstitutionCount();

    ResultVo checkSave(OpenapiAppSaveVO openapiAppSaveVO);

    PageInfo<OpenapiAppInterfaceShowVO> getOpenapiAppInterfaceList(OpenapiAppInterfaceListVO openapiAppInterfaceListVO);

    ResultVo checkAppInterfaceSave(OpenapiAppInterfaceSaveVO openapiAppSaveVO);

    Integer saveAppInterface(OpenapiAppInterfaceSaveVO openapiAppSaveVO);

    Integer changeAppBlackStatus(ChangeAppBlackStatusVO changeAppBlackStatusVO);

    OpenapiApp resetAppSecret(ResetAppSecretVO resetAppSecretVO);

    List<OpenapiInterface> getUnUsedInterfaceList(UnUsedInterfaceListVO unUsedInterfaceListVO);
}

 
