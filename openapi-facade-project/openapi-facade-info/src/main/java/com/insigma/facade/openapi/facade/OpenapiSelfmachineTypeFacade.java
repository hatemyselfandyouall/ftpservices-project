 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiSelfmachineType;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeSaveVO;

import java.util.List;


public interface OpenapiSelfmachineTypeFacade{

	PageInfo<OpenapiSelfmachineType> getOpenapiSelfmachineTypeList(OpenapiSelfmachineTypeListVO listVO,Long userId);

    OpenapiSelfmachineType getOpenapiSelfmachineTypeDetail(OpenapiSelfmachineTypeDetailVO detailVO);

    Integer saveOpenapiSelfmachineType(OpenapiSelfmachineTypeSaveVO saveVO,Long userId,String userName);

    Integer deleteOpenapiSelfmachineType(OpenapiSelfmachineTypeDeleteVO deleteVO,Long userId);

    Integer checkDelete(OpenapiSelfmachineTypeDeleteVO openapiSelfmachineTypeDeleteVO);

    List<OpenapiSelfmachineType> getAllTypes();
}

 
