package com.insigma.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiAppTypeFacade;
import com.insigma.mapper.OpenapiAppTypeMapper;
import com.insigma.util.JSONUtil;
import constant.DataConstant;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import com.insigma.facade.openapi.po.OpenapiAppType;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeDetailVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeListVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeSaveVO;

import javax.xml.crypto.Data;

public class OpenapiAppTypeServiceImpl implements OpenapiAppTypeFacade {

    @Autowired
    OpenapiAppTypeMapper openapiAppTypeMapper;

    @Override
    public PageInfo<OpenapiAppType> getOpenapiAppTypeList(OpenapiAppTypeListVO openapiAppTypeListVO) {
        if (openapiAppTypeListVO==null||openapiAppTypeListVO.getPageNum()==null||openapiAppTypeListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiAppTypeListVO.getPageNum().intValue(),openapiAppTypeListVO.getPageSize().intValue());
        Example example=new Example(OpenapiAppType.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDelete",DataConstant.NO_DELETE);
        if (openapiAppTypeListVO.getKeyWord()!=null){
            criteria.andLike("name",openapiAppTypeListVO.getKeyWord());
        }
        Page<OpenapiAppType> openapiAppTypeList= (Page<OpenapiAppType>) openapiAppTypeMapper.selectByExample(example);
        PageInfo<OpenapiAppType> openapiAppTypePageInfo=new PageInfo<>(openapiAppTypeList);
        openapiAppTypePageInfo.setTotal(openapiAppTypeList.getTotal());
        return openapiAppTypePageInfo;
    }

    @Override
    public OpenapiAppType getOpenapiAppTypeDetail(OpenapiAppTypeDetailVO openapiAppTypeDetailVO) {
        if (openapiAppTypeDetailVO==null||openapiAppTypeDetailVO.getId()==null) {
            return null;
        };
        OpenapiAppType openapiAppType=openapiAppTypeMapper.selectByPrimaryKey(openapiAppTypeDetailVO.getId());
        return openapiAppType;
    }

    @Override
    public Integer saveOpenapiAppType(OpenapiAppTypeSaveVO openapiAppTypeSaveVO) {
        if (openapiAppTypeSaveVO==null){
            return 0;
        }
        OpenapiAppType openapiAppType= JSONUtil.convert(openapiAppTypeSaveVO,OpenapiAppType.class);
        if (openapiAppType.getId()==null){
            return openapiAppTypeMapper.insertSelective(openapiAppType);
        }else {
            openapiAppType.setModifyTime(new Date());
            Example example=new Example(OpenapiAppType.class);
            example.createCriteria().andEqualTo("id",openapiAppType.getId());
            return openapiAppTypeMapper.updateByExampleSelective(openapiAppType,example);
        }
    }

    @Override
    public Integer deleteOpenapiAppType(OpenapiAppTypeDeleteVO openapiAppTypeDeleteVO) {
        if (openapiAppTypeDeleteVO==null||openapiAppTypeDeleteVO.getId()==null){
            return 0;
        }
        OpenapiAppType openapiAppType=new OpenapiAppType();
        openapiAppType.setModifyTime(new Date());
        openapiAppType.setIsDelete(openapiAppTypeDeleteVO.getIsDelete());
        Example example=new Example(OpenapiAppType.class);
        example.createCriteria().andEqualTo("id",openapiAppTypeDeleteVO.getId());
        return openapiAppTypeMapper.updateByExampleSelective(openapiAppType,example);
    }
}
