 
package com.insigma.mapper;

import com.insigma.facade.openapi.po.OpenapiUser;
import com.insigma.facade.openapi.vo.OpenapiUser.OpenapiUserListVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface OpenapiUserMapper extends Mapper<OpenapiUser>{


    List<OpenapiUser> getOpenapiUserList(@Param("param") OpenapiUserListVO openapiUserListVO);
}
