 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.po.OpenapiSelfmachineRequest;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;


public interface OpenapiSelfmachineRequestFacade{

	PageInfo<OpenapiSelfmachineRequest> getOpenapiSelfmachineRequestList(OpenapiSelfmachineRequestListVO listVO);

    OpenapiSelfmachineRequest getOpenapiSelfmachineRequestDetail(OpenapiSelfmachineRequestDetailVO detailVO);

    Integer saveOpenapiSelfmachineRequest(OpenapiSelfmachineRequestSaveVO saveVO);

    Integer deleteOpenapiSelfmachineRequest(OpenapiSelfmachineRequestDeleteVO deleteVO);


    OpenapiSelfmachineRequest createToken(OpenapiSelfmachineRequest uniqueCode, OpenapiOrg openapiOrg);

    Boolean checkTokenExit(String token);
}

 
