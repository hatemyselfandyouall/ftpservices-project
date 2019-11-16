package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineFacade;
import com.insigma.mapper.OpenapiSelfmachineMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import com.insigma.facade.openapi.po.OpenapiSelfmachine;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineSaveVO;
import java.util.Date;
import java.util.List;


public class OpenapiSelfmachineServiceImpl implements OpenapiSelfmachineFacade {

    @Autowired
    OpenapiSelfmachineMapper openapiSelfmachineMapper;

    @Override
    public PageInfo<OpenapiSelfmachine> getOpenapiSelfmachineList(OpenapiSelfmachineListVO openapiSelfmachineListVO) {
        if (openapiSelfmachineListVO==null||openapiSelfmachineListVO.getPageNum()==null||openapiSelfmachineListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiSelfmachineListVO.getPageNum().intValue(),openapiSelfmachineListVO.getPageSize().intValue());
        OpenapiSelfmachine exampleObeject=new OpenapiSelfmachine();
        List<OpenapiSelfmachine> openapiSelfmachineList=openapiSelfmachineMapper.select(exampleObeject);
        PageInfo<OpenapiSelfmachine> openapiSelfmachinePageInfo=new PageInfo<>(openapiSelfmachineList);
        return openapiSelfmachinePageInfo;
    }

    @Override
    public OpenapiSelfmachine getOpenapiSelfmachineDetail(OpenapiSelfmachineDetailVO openapiSelfmachineDetailVO) {
        if (openapiSelfmachineDetailVO==null||openapiSelfmachineDetailVO.getId()==null) {
            return null;
        };
        OpenapiSelfmachine openapiSelfmachine=openapiSelfmachineMapper.selectByPrimaryKey(openapiSelfmachineDetailVO.getId());
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
}
