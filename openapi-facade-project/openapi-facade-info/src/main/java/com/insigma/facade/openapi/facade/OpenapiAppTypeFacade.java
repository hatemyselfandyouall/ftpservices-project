 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiAppType;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeDetailVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeListVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeSaveVO;


public interface OpenapiAppTypeFacade{

	PageInfo<OpenapiAppType> getOpenapiAppTypeList(OpenapiAppTypeListVO listVO);

    OpenapiAppType getOpenapiAppTypeDetail(OpenapiAppTypeDetailVO detailVO);

    Integer saveOpenapiAppType(OpenapiAppTypeSaveVO saveVO,Long userId,String userName);

    Integer deleteOpenapiAppType(OpenapiAppTypeDeleteVO deleteVO);

	 

}

 
