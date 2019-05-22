 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiInterfaceGroup;
import com.insigma.facade.openapi.vo.OpenapiInterfaceGroup.OpenapiInterfaceGroupDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceGroup.OpenapiInterfaceGroupDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceGroup.OpenapiInterfaceGroupListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceGroup.OpenapiInterfaceGroupSaveVO;


public interface OpenapiInterfaceGroupFacade{

	PageInfo<OpenapiInterfaceGroup> getOpenapiInterfaceGroupList(OpenapiInterfaceGroupListVO listVO);

    OpenapiInterfaceGroup getOpenapiInterfaceGroupDetail(OpenapiInterfaceGroupDetailVO detailVO);

    Integer saveOpenapiInterfaceGroup(OpenapiInterfaceGroupSaveVO saveVO);

    Integer deleteOpenapiInterfaceGroup(OpenapiInterfaceGroupDeleteVO deleteVO);

	 

}

 
