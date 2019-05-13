package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.OpenapiInterfaceDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceSaveVO;
import com.insigma.mapper.OpenapiInterfaceMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


public class InterfaceServiceImpl implements InterfaceFacade {

    @Autowired
    OpenapiInterfaceMapper openapiInterfaceMapper;

    @Override
    public PageInfo<OpenapiInterface> getOpenapiInterfaceList(OpenapiInterfaceListVO openapiInterfaceListVO) {
        if (openapiInterfaceListVO==null||openapiInterfaceListVO.getPageNum()==null||openapiInterfaceListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiInterfaceListVO.getPageNum().intValue(),openapiInterfaceListVO.getPageSize().intValue());
        OpenapiInterface exampleObeject=new OpenapiInterface();
        List<OpenapiInterface> openapiInterfaceList=openapiInterfaceMapper.select(exampleObeject);
        PageInfo<OpenapiInterface> openapiInterfacePageInfo=new PageInfo<>(openapiInterfaceList);
        return openapiInterfacePageInfo;
    }

    @Override
    public OpenapiInterface getOpenapiInterfaceDetail(OpenapiInterfaceDetailVO openapiInterfaceDetailVO) {
        if (openapiInterfaceDetailVO==null||openapiInterfaceDetailVO.getId()==null) {
            return null;
        };
        OpenapiInterface openapiInterface=openapiInterfaceMapper.selectByPrimaryKey(openapiInterfaceDetailVO.getId());
        return openapiInterface;
    }

    @Override
    public Integer saveOpenapiInterface(OpenapiInterfaceSaveVO openapiInterfaceSaveVO) {
        if (openapiInterfaceSaveVO==null){
            return 0;
        }
        OpenapiInterface openapiInterface= JSONUtil.convert(openapiInterfaceSaveVO,OpenapiInterface.class);
        if (openapiInterface.getId()==null){
            return openapiInterfaceMapper.insertSelective(openapiInterface);
        }else {
            openapiInterface.setModifyTime(new Date());
            Example example=new Example(OpenapiInterface.class);
            example.createCriteria().andEqualTo("id",openapiInterface.getId());
            return openapiInterfaceMapper.updateByExampleSelective(openapiInterface,example);
        }
    }

    @Override
    public Integer deleteOpenapiInterface(OpenapiInterfaceDeleteVO openapiInterfaceDeleteVO) {
        if (openapiInterfaceDeleteVO==null||openapiInterfaceDeleteVO.getId()==null){
            return 0;
        }
        OpenapiInterface openapiInterface=new OpenapiInterface();
        openapiInterface.setModifyTime(new Date());
        openapiInterface.setIsDelete(openapiInterfaceDeleteVO.getIsDelete());
        Example example=new Example(OpenapiInterface.class);
        example.createCriteria().andEqualTo("id",openapiInterfaceDeleteVO.getId());
        return openapiInterfaceMapper.updateByExampleSelective(openapiInterface,example);
    }
}
