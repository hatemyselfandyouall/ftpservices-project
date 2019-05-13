 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiOrgInterface;
import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceListVO;
import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceSaveVO;


public interface OpenapiOrgInterfaceFacade{

	PageInfo<OpenapiOrgInterface> getOpenapiOrgInterfaceList(OpenapiOrgInterfaceListVO listVO);

    OpenapiOrgInterface getOpenapiOrgInterfaceDetail(OpenapiOrgInterfaceDetailVO detailVO);

    Integer saveOpenapiOrgInterface(OpenapiOrgInterfaceSaveVO saveVO);

    Integer deleteOpenapiOrgInterface(OpenapiOrgInterfaceDeleteVO deleteVO);

	 

}

 
