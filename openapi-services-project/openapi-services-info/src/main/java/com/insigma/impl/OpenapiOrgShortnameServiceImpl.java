package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiOrgShortnameFacade;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.mapper.OpenapiOrgMapper;
import com.insigma.mapper.OpenapiOrgShortnameMapper;
import com.insigma.util.JSONUtil;
import constant.DataConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.util.StringUtils;
import star.vo.result.ResultVo;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import com.insigma.facade.openapi.po.OpenapiOrgShortname;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameListVO;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameSaveVO;

public class OpenapiOrgShortnameServiceImpl implements OpenapiOrgShortnameFacade {

    @Autowired
    OpenapiOrgShortnameMapper openapiOrgShortnameMapper;

    @Autowired
    OpenapiOrgMapper openapiOrgMapper;

    @Override
    public PageInfo<OpenapiOrgShortname> getOpenapiOrgShortnameList(OpenapiOrgShortnameListVO openapiOrgShortnameListVO) {
        if (openapiOrgShortnameListVO==null||openapiOrgShortnameListVO.getPageNum()==null||openapiOrgShortnameListVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(OpenapiOrgShortname.class);
        Example.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(openapiOrgShortnameListVO.getOrgCode())){
            criteria.andEqualTo("orgCode",openapiOrgShortnameListVO.getOrgCode());
        }
        if(!StringUtils.isEmpty(openapiOrgShortnameListVO.getShortName())){
            criteria.andLike("shortName",openapiOrgShortnameListVO.getShortName());
        }
        PageHelper.startPage(openapiOrgShortnameListVO.getPageNum().intValue(),openapiOrgShortnameListVO.getPageSize().intValue());
        List<OpenapiOrgShortname> openapiOrgShortnameList=openapiOrgShortnameMapper.selectByExample(example);
        PageInfo<OpenapiOrgShortname> openapiOrgShortnamePageInfo=new PageInfo<>(openapiOrgShortnameList);
        return openapiOrgShortnamePageInfo;
    }

    @Override
    public OpenapiOrgShortname getOpenapiOrgShortnameDetail(OpenapiOrgShortnameDetailVO openapiOrgShortnameDetailVO) {
        if (openapiOrgShortnameDetailVO==null) {
            return null;
        };
        List<OpenapiOrgShortname> openapiOrgShortnames=openapiOrgShortnameMapper.select(new OpenapiOrgShortname().setOrgCode(openapiOrgShortnameDetailVO.getOrgCode()).setId(openapiOrgShortnameDetailVO.getId()));
        if (!CollectionUtils.isEmpty(openapiOrgShortnames)) {
            return openapiOrgShortnames.get(0);
        }else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo saveOpenapiOrgShortname(OpenapiOrgShortnameSaveVO openapiOrgShortnameSaveVO) {
        ResultVo resultVo=new ResultVo();
        if (StringUtils.isEmpty(openapiOrgShortnameSaveVO.getShortName())){
            resultVo.setResultDes("简称不能为空");
            return resultVo;
        }
        Example example=new Example(OpenapiOrgShortname.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("shortName",openapiOrgShortnameSaveVO.getShortName());
        if (openapiOrgShortnameSaveVO.getId()!=null){
            criteria.andNotEqualTo("id",openapiOrgShortnameSaveVO.getId());
        }
        if(openapiOrgShortnameMapper.selectCountByExample(example)>0){
            resultVo.setResultDes("简称不能重复");
            return resultVo;
        }
        example.clear();
        criteria=example.createCriteria();
        criteria.andEqualTo("orgCode",openapiOrgShortnameSaveVO.getOrgCode());
        if (openapiOrgShortnameSaveVO.getId()!=null){
            criteria.andNotEqualTo("id",openapiOrgShortnameSaveVO.getId());
        }
        if(openapiOrgShortnameMapper.selectCountByExample(example)>0){
            resultVo.setResultDes("机构只能有一个简称");
            return resultVo;
        }
        OpenapiOrgShortname openapiOrgShortname= JSONUtil.convert(openapiOrgShortnameSaveVO,OpenapiOrgShortname.class);
        openapiOrgShortname.setModifyTime(new Date());
        if (openapiOrgShortname.getId()==null){
             openapiOrgShortnameMapper.insertSelective(openapiOrgShortname);
        }else {
            openapiOrgShortname.setModifyTime(new Date());
            example=new Example(OpenapiOrgShortname.class);
            example.createCriteria().andEqualTo("id",openapiOrgShortname.getId());
             openapiOrgShortnameMapper.updateByExampleSelective(openapiOrgShortname,example);
        }
        List<OpenapiOrg> openapiOrgs=openapiOrgMapper.select(new OpenapiOrg().setOrgCode(openapiOrgShortname.getOrgCode()));
        if (!CollectionUtils.isEmpty(openapiOrgs)){
            openapiOrgs.forEach(i->{
                i.setAbbreviation(openapiOrgShortname.getShortName());
                openapiOrgMapper.updateByPrimaryKeySelective(i);
            });
        }
        resultVo.setSuccess(true);
        resultVo.setResultDes("保存成功");
        return resultVo;
    }

    @Override
    public Integer deleteOpenapiOrgShortname(OpenapiOrgShortnameDeleteVO openapiOrgShortnameDeleteVO) {
        if (openapiOrgShortnameDeleteVO==null||openapiOrgShortnameDeleteVO.getId()==null){
            return 0;
        }
        Example example=new Example(OpenapiOrgShortname.class);
        example.createCriteria().andEqualTo("id",openapiOrgShortnameDeleteVO.getId());
        return openapiOrgShortnameMapper.deleteByExample(example);
    }

    @Override
    public Integer checkDelete(OpenapiOrgShortnameDeleteVO openapiOrgShortnameDeleteVO) {
        if (openapiOrgShortnameDeleteVO==null||openapiOrgShortnameDeleteVO.getId()==null){
            return 0;
        }
        OpenapiOrgShortname openapiOrgShortname=openapiOrgShortnameMapper.selectByPrimaryKey(openapiOrgShortnameDeleteVO.getId());
        if (openapiOrgMapper.selectCount(new OpenapiOrg().setIsDelete(DataConstant.NO_DELETE).setOrgCode(openapiOrgShortname.getOrgCode()))>0){
            return 0;
        }else {
            return 1;
        }
    }
}
