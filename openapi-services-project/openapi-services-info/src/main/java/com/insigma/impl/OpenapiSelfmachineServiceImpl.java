package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineFacade;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineRequestFacade;
import com.insigma.facade.openapi.po.OpenapiSelfmachineRequest;
import com.insigma.facade.openapi.po.SelfMachineEnum;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.*;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.insigma.mapper.OpenapiSelfmachineMapper;
import com.insigma.mapper.OpenapiSelfmachineRequestMapper;
import com.insigma.util.JSONUtil;
import lombok.experimental.Accessors;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import com.insigma.facade.openapi.po.OpenapiSelfmachine;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class OpenapiSelfmachineServiceImpl implements OpenapiSelfmachineFacade {

    @Autowired
    OpenapiSelfmachineMapper openapiSelfmachineMapper;
    @Autowired
    OpenapiSelfmachineRequestMapper openapiSelfmachineRequestMapper;
    @Autowired
    OpenapiSelfmachineRequestFacade openapiSelfmachineRequestFacade;

    @Override
    public PageInfo<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(OpenapiSelfmachineListVO openapiSelfmachineListVO) {
        if (openapiSelfmachineListVO==null||openapiSelfmachineListVO.getPageNum()==null||openapiSelfmachineListVO.getPageSize()==null) {
            return null;
        }
        List<OpenapiSelfmachineShowVO> openapiSelfmachineRequestList=openapiSelfmachineMapper.getOpenapiSelfmachineList(openapiSelfmachineListVO);
        PageInfo<OpenapiSelfmachineShowVO> openapiSelfmachineRequestPageInfo=new PageInfo<>(openapiSelfmachineRequestList);
        return openapiSelfmachineRequestPageInfo;
    }

    @Override
    public OpenapiSelfmachine getOpenapiSelfmachineDetail(OpenapiSelfmachineDetailVO openapiSelfmachineDetailVO) {
        if (openapiSelfmachineDetailVO==null) {
            return null;
        };
        OpenapiSelfmachine openapiSelfmachine=openapiSelfmachineMapper.selectOne(JSONUtil.convert(openapiSelfmachineDetailVO,OpenapiSelfmachine.class));
        return openapiSelfmachine;
    }

    @Override
    public Integer saveOpenapiSelfmachine(OpenapiSelfmachineSaveVO openapiSelfmachineSaveVO) {
        if (openapiSelfmachineSaveVO==null){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine= JSONUtil.convert(openapiSelfmachineSaveVO,OpenapiSelfmachine.class);
        if (openapiSelfmachine.getId()==null){
            return openapiSelfmachineMapper.insertSelective(openapiSelfmachine);
        }else {
            Example example=new Example(OpenapiSelfmachine.class);
            example.createCriteria().andEqualTo("id",openapiSelfmachine.getId());
            return openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
        }
    }

    @Override
    public Integer deleteOpenapiSelfmachine(OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO) {
        if (openapiSelfmachineDeleteVO==null||openapiSelfmachineDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine();
        Example example=new Example(OpenapiSelfmachine.class);
        example.createCriteria().andEqualTo("id",openapiSelfmachineDeleteVO.getId());
        return openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSelfMachine(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO) {
        OpenapiSelfmachine openapiSelfmachine=JSONUtil.convert(openapiSelfmachineRequestSaveVO,OpenapiSelfmachine.class);
        openapiSelfmachineMapper.insertSelective(openapiSelfmachine);
        OpenapiSelfmachineRequest openapiSelfmachineRequest=JSONUtil.convert(openapiSelfmachineRequestSaveVO,OpenapiSelfmachineRequest.class);
        openapiSelfmachineRequestMapper.insertSelective(openapiSelfmachineRequest);
    }

    @Override
    public Integer setStatu(OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO) {
        if (openapiSelfmachineDeleteVO==null|| openapiSelfmachineDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine=openapiSelfmachineMapper.selectByPrimaryKey(openapiSelfmachineDeleteVO.getId());
        if (openapiSelfmachine==null){
            return 0;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest=openapiSelfmachineRequestMapper.selectOne(new OpenapiSelfmachineRequest().setUniqueCode(openapiSelfmachine.getUniqueCode()));
        if (openapiSelfmachineRequest==null){
            return 0;
        }
        return openapiSelfmachineRequestFacade.deleteOpenapiSelfmachineRequest(new OpenapiSelfmachineRequestDeleteVO().setIds(Arrays.asList(openapiSelfmachineRequest.getId())).setStatu(openapiSelfmachineDeleteVO.getStatu()));
    }
}
