package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineRequestFacade;
import com.insigma.facade.openapi.po.OpenapiSelfmachineRequest;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.insigma.mapper.OpenapiSelfmachineRequestMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


public class OpenapiSelfmachineRequestServiceImpl implements OpenapiSelfmachineRequestFacade {

    @Autowired
    OpenapiSelfmachineRequestMapper openapiSelfmachineRequestMapper;

    @Override
    public PageInfo<OpenapiSelfmachineRequest> getOpenapiSelfmachineRequestList(OpenapiSelfmachineRequestListVO openapiSelfmachineRequestListVO) {
        if (openapiSelfmachineRequestListVO==null||openapiSelfmachineRequestListVO.getPageNum()==null||openapiSelfmachineRequestListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiSelfmachineRequestListVO.getPageNum().intValue(),openapiSelfmachineRequestListVO.getPageSize().intValue());
        OpenapiSelfmachineRequest exampleObeject=new OpenapiSelfmachineRequest();
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequestList=openapiSelfmachineRequestMapper.select(exampleObeject);
        PageInfo<OpenapiSelfmachineRequest> openapiSelfmachineRequestPageInfo=new PageInfo<>(openapiSelfmachineRequestList);
        return openapiSelfmachineRequestPageInfo;
    }

    @Override
    public OpenapiSelfmachineRequest getOpenapiSelfmachineRequestDetail(OpenapiSelfmachineRequestDetailVO openapiSelfmachineRequestDetailVO) {
        if (openapiSelfmachineRequestDetailVO==null||openapiSelfmachineRequestDetailVO.getId()==null) {
            return null;
        };
        OpenapiSelfmachineRequest openapiSelfmachineRequest=openapiSelfmachineRequestMapper.selectByPrimaryKey(openapiSelfmachineRequestDetailVO.getId());
        return openapiSelfmachineRequest;
    }

    @Override
    public Integer saveOpenapiSelfmachineRequest(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO) {
        if (openapiSelfmachineRequestSaveVO==null){
            return 0;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest= JSONUtil.convert(openapiSelfmachineRequestSaveVO,OpenapiSelfmachineRequest.class);
        if (openapiSelfmachineRequest.getId()==null){
            return openapiSelfmachineRequestMapper.insertSelective(openapiSelfmachineRequest);
        }else {
            Example example=new Example(OpenapiSelfmachineRequest.class);
            example.createCriteria().andEqualTo("id",openapiSelfmachineRequest.getId());
            return openapiSelfmachineRequestMapper.updateByExampleSelective(openapiSelfmachineRequest,example);
        }
    }

    @Override
    public Integer deleteOpenapiSelfmachineRequest(OpenapiSelfmachineRequestDeleteVO openapiSelfmachineRequestDeleteVO) {
        if (openapiSelfmachineRequestDeleteVO==null||openapiSelfmachineRequestDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest=new OpenapiSelfmachineRequest();
        Example example=new Example(OpenapiSelfmachineRequest.class);
        example.createCriteria().andEqualTo("id",openapiSelfmachineRequestDeleteVO.getId());
        return openapiSelfmachineRequestMapper.updateByExampleSelective(openapiSelfmachineRequest,example);
    }
}
