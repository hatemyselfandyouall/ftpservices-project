 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.SelfMachineOrgDTO;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.po.OpenapiSelfmachineRequest;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.*;


public interface OpenapiSelfmachineRequestFacade{

	PageInfo<OpenapiSelfmachineRequest> getOpenapiSelfmachineRequestList(OpenapiSelfmachineRequestListVO listVO);

    OpenapiSelfmachineRequest getOpenapiSelfmachineRequestDetail(OpenapiSelfmachineRequestDetailVO detailVO);

    Integer saveOpenapiSelfmachineRequest(OpenapiSelfmachineRequestSaveVO saveVO);

    Integer deleteOpenapiSelfmachineRequest(OpenapiSelfmachineRequestDeleteVO deleteVO);


    OpenapiSelfmachineRequest createToken(OpenapiSelfmachineRequest uniqueCode, OpenapiOrg openapiOrg);

    Boolean checkTokenExit(String token);


    SelfMachineOrgDTO getOrgByToken(String token);

    OpenapiSelfmachineDetailShowVO getDetailByToken(String token);

    String getInitMachineCode(OpenapiSelfmachineRequest openapiSelfmachineRequest, OpenapiOrg openapiOrg);

}

 
