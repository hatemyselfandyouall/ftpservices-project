 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiSelfmachine;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineSaveVO;


public interface OpenapiSelfmachineFacade{

	PageInfo<OpenapiSelfmachine> getOpenapiSelfmachineList(OpenapiSelfmachineListVO listVO);

    OpenapiSelfmachine getOpenapiSelfmachineDetail(OpenapiSelfmachineDetailVO detailVO);

    Integer saveOpenapiSelfmachine(OpenapiSelfmachineSaveVO saveVO);

    Integer deleteOpenapiSelfmachine(OpenapiSelfmachineDeleteVO deleteVO);

	 

}

 
