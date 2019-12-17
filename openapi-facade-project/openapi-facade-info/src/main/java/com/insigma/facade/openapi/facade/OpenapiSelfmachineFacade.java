 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.po.OpenapiSelfmachine;
import com.insigma.facade.openapi.po.OpenapiSelfmachineRequest;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.*;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;


public interface OpenapiSelfmachineFacade{

	PageInfo<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(OpenapiSelfmachineListVO listVO, Long userId);

    OpenapiSelfmachine getOpenapiSelfmachineDetail(OpenapiSelfmachineDetailVO detailVO);

    OpenapiSelfmachine saveOpenapiSelfmachine(OpenapiSelfmachineSaveVO saveVO);

    Integer deleteOpenapiSelfmachine(OpenapiSelfmachineDeleteVO deleteVO);


    String saveSelfMachine(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO, OpenapiOrg id);

    Integer setStatu(OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO);

    Integer setActiveStatus(OpenapiSelfmachineSetActiveStatusVO openapiSelfmachineSetActiveStatusVO);

    Integer setOrg(OpenapiSelfmachineSetOrgVO openapiSelfmachineSetOrgVO);

    void updateSelfMachine(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO, OpenapiSelfmachineRequest openapiSelfmachine);
}

 
