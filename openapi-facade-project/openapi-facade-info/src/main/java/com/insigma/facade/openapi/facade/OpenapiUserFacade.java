 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiUser;
import com.insigma.facade.openapi.vo.OpenapiUser.OpenapiUserDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiUser.OpenapiUserDetailVO;
import com.insigma.facade.openapi.vo.OpenapiUser.OpenapiUserListVO;
import com.insigma.facade.openapi.vo.OpenapiUser.OpenapiUserSaveVO;


public interface OpenapiUserFacade{

	PageInfo<OpenapiUser> getOpenapiUserList(OpenapiUserListVO listVO);

    OpenapiUser getOpenapiUserDetail(OpenapiUserDetailVO detailVO);

    Integer saveOpenapiUser(OpenapiUserSaveVO saveVO);

    Integer deleteOpenapiUser(OpenapiUserDeleteVO deleteVO);

	 

}

 
