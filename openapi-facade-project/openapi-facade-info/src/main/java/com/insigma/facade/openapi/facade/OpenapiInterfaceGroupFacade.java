 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiInterfaceGroup;
import com.insigma.facade.openapi.vo.OpenapiInterfaceGroup.*;


public interface OpenapiInterfaceGroupFacade{

	PageInfo<OpenapiInterfaceGroupDetailShowVO> getOpenapiInterfaceGroupList(OpenapiInterfaceGroupListVO listVO);

    OpenapiInterfaceGroup getOpenapiInterfaceGroupDetail(OpenapiInterfaceGroupDetailVO detailVO);

    Integer saveOpenapiInterfaceGroup(OpenapiInterfaceGroupSaveVO saveVO);

    Integer deleteOpenapiInterfaceGroup(OpenapiInterfaceGroupDeleteVO deleteVO);

	 

}

 
