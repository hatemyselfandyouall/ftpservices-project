 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiUser;
import com.insigma.facade.openapi.vo.OpenapiUser.*;


public interface OpenapiUserFacade{

	PageInfo<OpenapiUser> getOpenapiUserList(OpenapiUserListVO listVO);

    OpenapiUserDetailShowVO getOpenapiUserDetail(OpenapiUserDetailVO detailVO);

    SaveUserBackVO saveOpenapiUser(OpenapiUserSaveVO saveVO);

    Integer deleteOpenapiUser(OpenapiUserDeleteVO deleteVO);

	 

}

 
