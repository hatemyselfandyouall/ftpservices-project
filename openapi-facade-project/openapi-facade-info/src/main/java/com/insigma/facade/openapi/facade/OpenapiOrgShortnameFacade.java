 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiOrgShortname;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameListVO;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameSaveVO;
import star.vo.result.ResultVo;


public interface OpenapiOrgShortnameFacade{

	PageInfo<OpenapiOrgShortname> getOpenapiOrgShortnameList(OpenapiOrgShortnameListVO listVO);

    OpenapiOrgShortname getOpenapiOrgShortnameDetail(OpenapiOrgShortnameDetailVO detailVO);

    ResultVo saveOpenapiOrgShortname(OpenapiOrgShortnameSaveVO saveVO);

    Integer deleteOpenapiOrgShortname(OpenapiOrgShortnameDeleteVO deleteVO);

    Integer checkDelete(OpenapiOrgShortnameDeleteVO openapiOrgShortnameDeleteVO);
}

 
