package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiOrgInterfaceFacade;
import com.insigma.facade.openapi.po.OpenapiOrgInterface;
import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceListVO;
import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceSaveVO;
import com.insigma.mapper.OpenapiOrgInterfaceMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


public class OpenapiOrgInterfaceServiceImpl implements OpenapiOrgInterfaceFacade {

    @Autowired
   OpenapiOrgInterfaceMapper OpenapiOrgInterfaceMapper;

    @Override
    public PageInfo<OpenapiOrgInterface> getOpenapiOrgInterfaceList(OpenapiOrgInterfaceListVO OpenapiOrgInterfaceListVO) {
        if (OpenapiOrgInterfaceListVO==null||OpenapiOrgInterfaceListVO.getPageNum()==null||OpenapiOrgInterfaceListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(OpenapiOrgInterfaceListVO.getPageNum().intValue(),OpenapiOrgInterfaceListVO.getPageSize().intValue());
        OpenapiOrgInterface exampleObeject=new OpenapiOrgInterface();
        List<OpenapiOrgInterface> OpenapiOrgInterfaceList=OpenapiOrgInterfaceMapper.select(exampleObeject);
        PageInfo<OpenapiOrgInterface> OpenapiOrgInterfacePageInfo=new PageInfo<>(OpenapiOrgInterfaceList);
        return OpenapiOrgInterfacePageInfo;
    }

    @Override
    public OpenapiOrgInterface getOpenapiOrgInterfaceDetail(OpenapiOrgInterfaceDetailVO OpenapiOrgInterfaceDetailVO) {
        if (OpenapiOrgInterfaceDetailVO==null||OpenapiOrgInterfaceDetailVO.getId()==null) {
            return null;
        };
        OpenapiOrgInterface OpenapiOrgInterface=OpenapiOrgInterfaceMapper.selectByPrimaryKey(OpenapiOrgInterfaceDetailVO.getId());
        return OpenapiOrgInterface;
    }

    @Override
    public Integer saveOpenapiOrgInterface(OpenapiOrgInterfaceSaveVO OpenapiOrgInterfaceSaveVO) {
        if (OpenapiOrgInterfaceSaveVO==null){
            return 0;
        }
        OpenapiOrgInterface OpenapiOrgInterface= JSONUtil.convert(OpenapiOrgInterfaceSaveVO,OpenapiOrgInterface.class);
        if (OpenapiOrgInterface.getId()==null){
            return OpenapiOrgInterfaceMapper.insertSelective(OpenapiOrgInterface);
        }else {
            OpenapiOrgInterface.setModifyTime(new Date());
            Example example=new Example(OpenapiOrgInterface.class);
            example.createCriteria().andEqualTo("id",OpenapiOrgInterface.getId());
            return OpenapiOrgInterfaceMapper.updateByExampleSelective(OpenapiOrgInterface,example);
        }
    }

    @Override
    public Integer deleteOpenapiOrgInterface(OpenapiOrgInterfaceDeleteVO OpenapiOrgInterfaceDeleteVO) {
        if (OpenapiOrgInterfaceDeleteVO==null||OpenapiOrgInterfaceDeleteVO.getId()==null){
            return 0;
        }
        OpenapiOrgInterface OpenapiOrgInterface=new OpenapiOrgInterface();
        OpenapiOrgInterface.setModifyTime(new Date());
        Example example=new Example(OpenapiOrgInterface.class);
        example.createCriteria().andEqualTo("id",OpenapiOrgInterfaceDeleteVO.getId());
        return OpenapiOrgInterfaceMapper.updateByExampleSelective(OpenapiOrgInterface,example);
    }
}
