 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiInterfaceType;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeSaveVO;


public interface OpenapiInterfaceTypeFacade{

	PageInfo<OpenapiInterfaceType> getOpenapiInterfaceTypeList(OpenapiInterfaceTypeListVO listVO);

    OpenapiInterfaceType getOpenapiInterfaceTypeDetail(OpenapiInterfaceTypeDetailVO detailVO);

    Integer saveOpenapiInterfaceType(OpenapiInterfaceTypeSaveVO saveVO);

    Integer deleteOpenapiInterfaceType(OpenapiInterfaceTypeDeleteVO deleteVO);


    boolean hasChildInterface(OpenapiInterfaceTypeDeleteVO openapiInterfaceTypeDeleteVO);
}

 
