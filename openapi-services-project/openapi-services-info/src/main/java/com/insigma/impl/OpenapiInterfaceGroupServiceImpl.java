package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.constant.DataConstant;
import com.insigma.facade.openapi.facade.OpenapiInterfaceGroupFacade;
import com.insigma.facade.openapi.po.OpenapiFtpAccount;
import com.insigma.facade.openapi.vo.OpenapiUser.OpenapiUserDetailShowVO;
import com.insigma.mapper.OpenapiInterfaceGroupMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.insigma.facade.openapi.po.OpenapiInterfaceGroup;
import com.insigma.facade.openapi.vo.OpenapiInterfaceGroup.OpenapiInterfaceGroupDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceGroup.OpenapiInterfaceGroupDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceGroup.OpenapiInterfaceGroupListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceGroup.OpenapiInterfaceGroupSaveVO;

public class OpenapiInterfaceGroupServiceImpl implements OpenapiInterfaceGroupFacade {


    @Autowired
    OpenapiInterfaceGroupMapper openapiInterfaceGroupMapper;

    @Override
    public PageInfo<OpenapiInterfaceGroup> getOpenapiInterfaceGroupList(OpenapiInterfaceGroupListVO openapiInterfaceGroupListVO) {
        if (openapiInterfaceGroupListVO==null||openapiInterfaceGroupListVO.getPageNum()==null||openapiInterfaceGroupListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiInterfaceGroupListVO.getPageNum().intValue(),openapiInterfaceGroupListVO.getPageSize().intValue());
        OpenapiInterfaceGroup exampleObeject=new OpenapiInterfaceGroup();
        exampleObeject.setIsDelete(DataConstant.NO_DELETE);
        List<OpenapiInterfaceGroup> openapiInterfaceGroupList=openapiInterfaceGroupMapper.select(exampleObeject);
        PageInfo<OpenapiInterfaceGroup> openapiInterfaceGroupPageInfo=new PageInfo<>(openapiInterfaceGroupList);
        return openapiInterfaceGroupPageInfo;
    }

    @Override
    public OpenapiInterfaceGroup getOpenapiInterfaceGroupDetail(OpenapiInterfaceGroupDetailVO openapiInterfaceGroupDetailVO) {
        if (openapiInterfaceGroupDetailVO==null||openapiInterfaceGroupDetailVO.getId()==null) {
            return null;
        };
        OpenapiInterfaceGroup openapiInterfaceGroup=openapiInterfaceGroupMapper.selectByPrimaryKey(openapiInterfaceGroupDetailVO.getId());
        return openapiInterfaceGroup;
    }

    @Override
    public Integer saveOpenapiInterfaceGroup(OpenapiInterfaceGroupSaveVO openapiInterfaceGroupSaveVO) {
        if (openapiInterfaceGroupSaveVO==null){
            return 0;
        }
        OpenapiInterfaceGroup openapiInterfaceGroup= JSONUtil.convert(openapiInterfaceGroupSaveVO,OpenapiInterfaceGroup.class);
        if (openapiInterfaceGroup.getId()==null){
            openapiInterfaceGroup.setCode(UUID.randomUUID().toString().replaceAll("-",""));
            return openapiInterfaceGroupMapper.insertSelective(openapiInterfaceGroup);
        }else {
            openapiInterfaceGroup.setModifyTime(new Date());
            Example example=new Example(OpenapiInterfaceGroup.class);
            example.createCriteria().andEqualTo("id",openapiInterfaceGroup.getId());
            return openapiInterfaceGroupMapper.updateByExampleSelective(openapiInterfaceGroup,example);
        }
    }

    @Override
    public Integer deleteOpenapiInterfaceGroup(OpenapiInterfaceGroupDeleteVO openapiInterfaceGroupDeleteVO) {
        if (openapiInterfaceGroupDeleteVO==null||openapiInterfaceGroupDeleteVO.getId()==null){
            return 0;
        }
        OpenapiInterfaceGroup openapiInterfaceGroup=new OpenapiInterfaceGroup();
        openapiInterfaceGroup.setModifyTime(new Date());
        openapiInterfaceGroup.setIsDelete(openapiInterfaceGroupDeleteVO.getIsDelete());
        Example example=new Example(OpenapiInterfaceGroup.class);
        example.createCriteria().andEqualTo("id",openapiInterfaceGroupDeleteVO.getId());
        return openapiInterfaceGroupMapper.updateByExampleSelective(openapiInterfaceGroup,example);
    }
}
