 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiAudit;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditDetailVO;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditListVO;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditSaveVO;


public interface OpenapiAuditFacade{

	PageInfo<OpenapiAudit> getOpenapiAuditList(OpenapiAuditListVO listVO);

    OpenapiAudit getOpenapiAuditDetail(OpenapiAuditDetailVO detailVO);

    Integer saveOpenapiAudit(OpenapiAuditSaveVO saveVO);

    Integer deleteOpenapiAudit(OpenapiAuditDeleteVO deleteVO);

	 

}

 
