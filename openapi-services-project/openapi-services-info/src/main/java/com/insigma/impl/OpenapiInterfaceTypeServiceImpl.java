package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiInterfaceTypeFacade;
import com.insigma.mapper.OpenapiInterfaceTypeMapper;
import com.insigma.util.JSONUtil;
import constant.DataConstant;
import org.apache.zookeeper.AsyncCallback;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import com.insigma.facade.openapi.po.OpenapiInterfaceType;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeSaveVO;

public class OpenapiInterfaceTypeServiceImpl implements OpenapiInterfaceTypeFacade {

    @Autowired
    OpenapiInterfaceTypeMapper openapiInterfaceTypeMapper;

    @Override
    public PageInfo<OpenapiInterfaceType> getOpenapiInterfaceTypeList(OpenapiInterfaceTypeListVO openapiInterfaceTypeListVO) {
        if (openapiInterfaceTypeListVO==null||openapiInterfaceTypeListVO.getPageNum()==null||openapiInterfaceTypeListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiInterfaceTypeListVO.getPageNum().intValue(),openapiInterfaceTypeListVO.getPageSize().intValue());
        OpenapiInterfaceType exampleObeject=new OpenapiInterfaceType();
        exampleObeject.setIsDelete(DataConstant.NO_DELETE);
        exampleObeject.setType(openapiInterfaceTypeListVO.getType());
        List<OpenapiInterfaceType> openapiInterfaceTypeList=openapiInterfaceTypeMapper.select(exampleObeject);
        PageInfo<OpenapiInterfaceType> openapiInterfaceTypePageInfo=new PageInfo<>(openapiInterfaceTypeList);
        return openapiInterfaceTypePageInfo;
    }

    @Override
    public OpenapiInterfaceType getOpenapiInterfaceTypeDetail(OpenapiInterfaceTypeDetailVO openapiInterfaceTypeDetailVO) {
        if (openapiInterfaceTypeDetailVO==null||openapiInterfaceTypeDetailVO.getId()==null) {
            return null;
        };
        OpenapiInterfaceType openapiInterfaceType=openapiInterfaceTypeMapper.selectByPrimaryKey(openapiInterfaceTypeDetailVO.getId());
        return openapiInterfaceType;
    }

    @Override
    public Integer saveOpenapiInterfaceType(OpenapiInterfaceTypeSaveVO openapiInterfaceTypeSaveVO) {
        if (openapiInterfaceTypeSaveVO==null){
            return 0;
        }
        OpenapiInterfaceType openapiInterfaceType= JSONUtil.convert(openapiInterfaceTypeSaveVO,OpenapiInterfaceType.class);
        if (openapiInterfaceType.getId()==null){
            return openapiInterfaceTypeMapper.insertSelective(openapiInterfaceType);
        }else {
            openapiInterfaceType.setModifyTime(new Date());
            Example example=new Example(OpenapiInterfaceType.class);
            example.createCriteria().andEqualTo("id",openapiInterfaceType.getId());
            return openapiInterfaceTypeMapper.updateByExampleSelective(openapiInterfaceType,example);
        }
    }

    @Override
    public Integer deleteOpenapiInterfaceType(OpenapiInterfaceTypeDeleteVO openapiInterfaceTypeDeleteVO) {
        if (openapiInterfaceTypeDeleteVO==null||openapiInterfaceTypeDeleteVO.getId()==null){
            return 0;
        }
        OpenapiInterfaceType openapiInterfaceType=new OpenapiInterfaceType();
        openapiInterfaceType.setModifyTime(new Date());
        openapiInterfaceType.setIsDelete(openapiInterfaceTypeDeleteVO.getIsDelete());
        Example example=new Example(OpenapiInterfaceType.class);
        example.createCriteria().andEqualTo("id",openapiInterfaceTypeDeleteVO.getId());
        return openapiInterfaceTypeMapper.updateByExampleSelective(openapiInterfaceType,example);
    }
}
