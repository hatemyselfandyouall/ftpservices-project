package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiOrgFacade;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgListVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgSaveVO;
import com.insigma.mapper.OpenapiOrgMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


public class OpenapiOrgServiceImpl implements OpenapiOrgFacade {

    @Autowired
    OpenapiOrgMapper OpenapiOrgMapper;

    @Override
    public PageInfo<OpenapiOrg> getOpenapiOrgList(OpenapiOrgListVO OpenapiOrgListVO) {
        if (OpenapiOrgListVO==null||OpenapiOrgListVO.getPageNum()==null||OpenapiOrgListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(OpenapiOrgListVO.getPageNum().intValue(),OpenapiOrgListVO.getPageSize().intValue());
        OpenapiOrg exampleObeject=new OpenapiOrg();
        List<OpenapiOrg> OpenapiOrgList=OpenapiOrgMapper.select(exampleObeject);
        PageInfo<OpenapiOrg> OpenapiOrgPageInfo=new PageInfo<>(OpenapiOrgList);
        return OpenapiOrgPageInfo;
    }

    @Override
    public OpenapiOrg getOpenapiOrgDetail(OpenapiOrgDetailVO OpenapiOrgDetailVO) {
        if (OpenapiOrgDetailVO==null||OpenapiOrgDetailVO.getId()==null) {
            return null;
        };
        OpenapiOrg OpenapiOrg=OpenapiOrgMapper.selectByPrimaryKey(OpenapiOrgDetailVO.getId());
        return OpenapiOrg;
    }

    @Override
    public Integer saveOpenapiOrg(OpenapiOrgSaveVO OpenapiOrgSaveVO) {
        if (OpenapiOrgSaveVO==null){
            return 0;
        }
        OpenapiOrg OpenapiOrg= JSONUtil.convert(OpenapiOrgSaveVO,OpenapiOrg.class);
        if (OpenapiOrg.getId()==null){
            return OpenapiOrgMapper.insertSelective(OpenapiOrg);
        }else {
            OpenapiOrg.setModifyTime(new Date());
            Example example=new Example(OpenapiOrg.class);
            example.createCriteria().andEqualTo("id",OpenapiOrg.getId());
            return OpenapiOrgMapper.updateByExampleSelective(OpenapiOrg,example);
        }
    }

    @Override
    public Integer deleteOpenapiOrg(OpenapiOrgDeleteVO OpenapiOrgDeleteVO) {
        if (OpenapiOrgDeleteVO==null||OpenapiOrgDeleteVO.getId()==null){
            return 0;
        }
        OpenapiOrg OpenapiOrg=new OpenapiOrg();
        OpenapiOrg.setModifyTime(new Date());
        Example example=new Example(OpenapiOrg.class);
        example.createCriteria().andEqualTo("id",OpenapiOrgDeleteVO.getId());
        return OpenapiOrgMapper.updateByExampleSelective(OpenapiOrg,example);
    }
}
