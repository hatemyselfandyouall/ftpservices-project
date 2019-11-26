 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.GetSelfMachineDTO;
import com.insigma.facade.openapi.dto.SelfMachineOrgDTO;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.vo.OpenapiApp.ResetAppSecretVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.*;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.insigma.facade.openapi.vo.root.PageVO;


public interface OpenapiOrgFacade {

    PageInfo<OpenapiOrgShowVO> getOpenapiOrgList(OpenapiOrgListVO listVO);

    OpenapiOrg getOpenapiOrgDetail(OpenapiOrgDetailVO detailVO);

    Integer saveOpenapiOrg(OpenapiOrgSaveVO saveVO, Long userId, String userName) throws Exception;

    Integer deleteOpenapiOrg(OpenapiOrgDeleteVO deleteVO);

    OpenapiOrg resetAppSecret(ResetAppSecretVO resetAppSecretVO);

    PageInfo<SelfMachineOrgDTO> getSelfMachine(GetSelfMachineDTO getSelfMachineDTO);

    OpenapiOrg checkCertificate(String certificate, OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO);

    /**
     *机构下一共有多少自助机
     * @param orgCode
     * @return
     */
    Integer getSelfMachineCountByOrgCode(String orgCode);

    /**
     *根据机器码 获取机构信息
     * @return
     */
    OpenapiOrg getOrgByMachineCode(String machineCode);

}

 
