 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiAppInterface;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceDetailVO;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceListVO;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceSaveVO;


public interface OpenapiAppInterfaceFacade{

	PageInfo<OpenapiAppInterface> getOpenapiAppInterfaceList(OpenapiAppInterfaceListVO listVO);

    OpenapiAppInterface getOpenapiAppInterfaceDetail(OpenapiAppInterfaceDetailVO detailVO);

    Integer saveOpenapiAppInterface(OpenapiAppInterfaceSaveVO saveVO);

    Integer deleteOpenapiAppInterface(OpenapiAppInterfaceDeleteVO deleteVO);

	 

}

 
