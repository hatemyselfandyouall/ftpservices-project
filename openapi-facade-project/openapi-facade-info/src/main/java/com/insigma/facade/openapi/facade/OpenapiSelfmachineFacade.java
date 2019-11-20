 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiSelfmachine;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.*;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;


public interface OpenapiSelfmachineFacade{

	PageInfo<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(OpenapiSelfmachineListVO listVO);

    OpenapiSelfmachine getOpenapiSelfmachineDetail(OpenapiSelfmachineDetailVO detailVO);

    Integer saveOpenapiSelfmachine(OpenapiSelfmachineSaveVO saveVO);

    Integer deleteOpenapiSelfmachine(OpenapiSelfmachineDeleteVO deleteVO);


    void saveSelfMachine(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO);

    Integer setStatu(OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO);
}

 
