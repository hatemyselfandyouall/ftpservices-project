 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgListVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgSaveVO;


public interface OpenapiOrgFacade{

	PageInfo<OpenapiOrg> getOpenapiOrgList(OpenapiOrgListVO listVO);

    OpenapiOrg getOpenapiOrgDetail(OpenapiOrgDetailVO detailVO);

    Integer saveOpenapiOrg(OpenapiOrgSaveVO saveVO);

    Integer deleteOpenapiOrg(OpenapiOrgDeleteVO deleteVO);

	 

}

 
