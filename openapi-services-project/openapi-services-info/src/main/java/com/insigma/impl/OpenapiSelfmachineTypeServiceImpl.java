package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineTypeFacade;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.po.OpenapiOrgShortname;
import com.insigma.facade.openapi.po.OpenapiSelfmachine;
import com.insigma.mapper.OpenapiOrgMapper;
import com.insigma.mapper.OpenapiSelfmachineMapper;
import com.insigma.mapper.OpenapiSelfmachineTypeMapper;
import com.insigma.util.JSONUtil;
import com.insigma.util.StringUtil;
import constant.DataConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import com.insigma.facade.openapi.po.OpenapiSelfmachineType;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeSaveVO;

public class OpenapiSelfmachineTypeServiceImpl implements OpenapiSelfmachineTypeFacade {

    @Autowired
    OpenapiSelfmachineTypeMapper openapiSelfmachineTypeMapper;

    @Autowired
    OpenapiSelfmachineMapper openapiSelfmachineMapper;

    @Override
    public PageInfo<OpenapiSelfmachineType> getOpenapiSelfmachineTypeList(OpenapiSelfmachineTypeListVO openapiSelfmachineTypeListVO,Long userId) {
        if (openapiSelfmachineTypeListVO==null||openapiSelfmachineTypeListVO.getPageNum()==null||openapiSelfmachineTypeListVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(OpenapiSelfmachineType.class);
        Example.Criteria criteria=example.createCriteria();
        if (userId!=null){
            criteria.andEqualTo("creatorId",userId);
        }
        if (!StringUtils.isEmpty(openapiSelfmachineTypeListVO.getName())){
            criteria.andEqualTo("name",openapiSelfmachineTypeListVO.getName());
        }
        criteria.andEqualTo("isDelete", DataConstant.NO_DELETE);
        PageHelper.startPage(openapiSelfmachineTypeListVO.getPageNum().intValue(),openapiSelfmachineTypeListVO.getPageSize().intValue());
        List<OpenapiSelfmachineType> openapiSelfmachineTypeList=openapiSelfmachineTypeMapper.selectByExample(example);
        PageInfo<OpenapiSelfmachineType> openapiSelfmachineTypePageInfo=new PageInfo<>(openapiSelfmachineTypeList);
        return openapiSelfmachineTypePageInfo;
    }

    @Override
    public OpenapiSelfmachineType getOpenapiSelfmachineTypeDetail(OpenapiSelfmachineTypeDetailVO openapiSelfmachineTypeDetailVO) {
        if (openapiSelfmachineTypeDetailVO==null||openapiSelfmachineTypeDetailVO.getId()==null) {
            return null;
        };
        OpenapiSelfmachineType openapiSelfmachineType=openapiSelfmachineTypeMapper.selectByPrimaryKey(openapiSelfmachineTypeDetailVO.getId());
        return openapiSelfmachineType;
    }

    @Override
    public Integer saveOpenapiSelfmachineType(OpenapiSelfmachineTypeSaveVO openapiSelfmachineTypeSaveVO,Long userId,String userName) {
        if (openapiSelfmachineTypeSaveVO==null){
            return 0;
        }
        OpenapiSelfmachineType openapiSelfmachineType= JSONUtil.convert(openapiSelfmachineTypeSaveVO,OpenapiSelfmachineType.class);
        if (openapiSelfmachineType.getId()==null){
            openapiSelfmachineType.setCreatorId(userId).setCreatorName(userName);
            return openapiSelfmachineTypeMapper.insertSelective(openapiSelfmachineType);
        }else {
            Example example=new Example(OpenapiSelfmachineType.class);
            example.createCriteria().andEqualTo("id",openapiSelfmachineType.getId());
            return openapiSelfmachineTypeMapper.updateByExampleSelective(openapiSelfmachineType,example);
        }
    }

    @Override
    public Integer deleteOpenapiSelfmachineType(OpenapiSelfmachineTypeDeleteVO openapiSelfmachineTypeDeleteVO,Long userId) {
        if (openapiSelfmachineTypeDeleteVO==null||openapiSelfmachineTypeDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachineType openapiSelfmachineType=new OpenapiSelfmachineType();
        openapiSelfmachineType.setIsDelete(openapiSelfmachineTypeDeleteVO.getIsDelete());
        Example example=new Example(OpenapiSelfmachineType.class);
        example.createCriteria().andEqualTo("id",openapiSelfmachineTypeDeleteVO.getId()).andEqualTo("creatorId",userId);
        return openapiSelfmachineTypeMapper.updateByExampleSelective(openapiSelfmachineType,example);
    }

    @Override
    public Integer checkDelete(OpenapiSelfmachineTypeDeleteVO openapiSelfmachineTypeDeleteVO) {
        if (openapiSelfmachineTypeDeleteVO==null||openapiSelfmachineTypeDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachineType openapiSelfmachineType=openapiSelfmachineTypeMapper.selectByPrimaryKey(openapiSelfmachineTypeDeleteVO.getId());
        if (openapiSelfmachineMapper.selectCount(new OpenapiSelfmachine().setMachineTypeId(openapiSelfmachineType.getId()))>0){
            return 0;
        }else {
            return 1;
        }
    }
}
