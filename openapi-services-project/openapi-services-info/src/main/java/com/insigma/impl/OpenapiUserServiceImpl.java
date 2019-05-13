package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiUserFacade;
import com.insigma.facade.openapi.po.OpenapiUser;
import com.insigma.facade.openapi.vo.OpenapiUser.OpenapiUserDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiUser.OpenapiUserDetailVO;
import com.insigma.facade.openapi.vo.OpenapiUser.OpenapiUserListVO;
import com.insigma.facade.openapi.vo.OpenapiUser.OpenapiUserSaveVO;
import com.insigma.mapper.OpenapiUserMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


public class OpenapiUserServiceImpl implements OpenapiUserFacade {

    @Autowired
    OpenapiUserMapper OpenapiUserMapper;

    @Override
    public PageInfo<OpenapiUser> getOpenapiUserList(OpenapiUserListVO OpenapiUserListVO) {
        if (OpenapiUserListVO==null||OpenapiUserListVO.getPageNum()==null||OpenapiUserListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(OpenapiUserListVO.getPageNum().intValue(),OpenapiUserListVO.getPageSize().intValue());
        OpenapiUser exampleObeject=new OpenapiUser();
        List<OpenapiUser> OpenapiUserList=OpenapiUserMapper.select(exampleObeject);
        PageInfo<OpenapiUser> OpenapiUserPageInfo=new PageInfo<>(OpenapiUserList);
        return OpenapiUserPageInfo;
    }

    @Override
    public OpenapiUser getOpenapiUserDetail(OpenapiUserDetailVO OpenapiUserDetailVO) {
        if (OpenapiUserDetailVO==null||OpenapiUserDetailVO.getId()==null) {
            return null;
        };
        OpenapiUser OpenapiUser=OpenapiUserMapper.selectByPrimaryKey(OpenapiUserDetailVO.getId());
        return OpenapiUser;
    }

    @Override
    public Integer saveOpenapiUser(OpenapiUserSaveVO OpenapiUserSaveVO) {
        if (OpenapiUserSaveVO==null){
            return 0;
        }
        OpenapiUser OpenapiUser= JSONUtil.convert(OpenapiUserSaveVO,OpenapiUser.class);
        if (OpenapiUser.getId()==null){
            return OpenapiUserMapper.insertSelective(OpenapiUser);
        }else {
            OpenapiUser.setModifyTime(new Date());
            Example example=new Example(OpenapiUser.class);
            example.createCriteria().andEqualTo("id",OpenapiUser.getId());
            return OpenapiUserMapper.updateByExampleSelective(OpenapiUser,example);
        }
    }

    @Override
    public Integer deleteOpenapiUser(OpenapiUserDeleteVO OpenapiUserDeleteVO) {
        if (OpenapiUserDeleteVO==null||OpenapiUserDeleteVO.getId()==null){
            return 0;
        }
        OpenapiUser OpenapiUser=new OpenapiUser();
        OpenapiUser.setModifyTime(new Date());
        Example example=new Example(OpenapiUser.class);
        example.createCriteria().andEqualTo("id",OpenapiUserDeleteVO.getId());
        return OpenapiUserMapper.updateByExampleSelective(OpenapiUser,example);
    }
}
