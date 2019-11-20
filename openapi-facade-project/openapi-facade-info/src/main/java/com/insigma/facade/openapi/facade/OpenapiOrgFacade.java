 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.GetSelfMachineDTO;
import com.insigma.facade.openapi.dto.SelfMachineOrgDTO;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.vo.OpenapiApp.ResetAppSecretVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgListVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgSaveVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.insigma.facade.openapi.vo.root.PageVO;


public interface OpenapiOrgFacade{

	PageInfo<OpenapiOrg> getOpenapiOrgList(OpenapiOrgListVO listVO);

    OpenapiOrg getOpenapiOrgDetail(OpenapiOrgDetailVO detailVO);

    Integer saveOpenapiOrg(OpenapiOrgSaveVO saveVO, Long userId, String userName)throws Exception;

    Integer deleteOpenapiOrg(OpenapiOrgDeleteVO deleteVO);


    OpenapiOrg resetAppSecret(ResetAppSecretVO resetAppSecretVO);

    PageInfo<SelfMachineOrgDTO> getSelfMachine(GetSelfMachineDTO getSelfMachineDTO);

    OpenapiOrg checkCertificate(String certificate, OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO);
}

 
