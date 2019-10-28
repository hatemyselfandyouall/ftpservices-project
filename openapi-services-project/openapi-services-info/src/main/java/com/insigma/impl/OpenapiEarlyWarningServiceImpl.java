package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.OpenapiEarlyWarningDto;
import com.insigma.facade.openapi.facade.OpenapiEarlyWarningFacade;
import com.insigma.facade.openapi.po.OpenapiEarlyWarning;
import com.insigma.mapper.OpenapiEarlyWarningMapper;
import com.insigma.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
public class OpenapiEarlyWarningServiceImpl implements OpenapiEarlyWarningFacade {

    @Autowired
    private OpenapiEarlyWarningMapper openapiEarlyWarningMapper;

    @Override
    public PageInfo<OpenapiEarlyWarning> getOpenapiEarlyWarningList(String name,Integer offset,Integer limit) {
        if (offset==null||limit==null) {
            return null;
        }
        PageHelper.startPage(offset,limit);
        Example example=new Example(OpenapiEarlyWarning.class);
        Example.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotEmpty(name)) {
            criteria.andLike("name","%"+name+"%");
        }
        List<OpenapiEarlyWarning> openapiEarlyWarningList=openapiEarlyWarningMapper.selectByExample(example);
        PageInfo<OpenapiEarlyWarning> openapiEarlyWarningPageInfo=new PageInfo<>(openapiEarlyWarningList);
        return openapiEarlyWarningPageInfo;
    }

    @Override
    public OpenapiEarlyWarning getOpenapiEarlyWarningDetail(Long id) {
        if (id==null) {
            return null;
        };
        OpenapiEarlyWarning openapiEarlyWarning=openapiEarlyWarningMapper.selectByPrimaryKey(id);
        return openapiEarlyWarning;
    }

    @Override
    public Integer saveOpenapiEarlyWarning(OpenapiEarlyWarningDto openapiEarlyWarningDto) {
        if (openapiEarlyWarningDto==null){
            return 0;
        }
        OpenapiEarlyWarning openapiEarlyWarning= JSONUtil.convert(openapiEarlyWarningDto,OpenapiEarlyWarning.class);
        if (openapiEarlyWarning.getId()==null){
            return openapiEarlyWarningMapper.insertSelective(openapiEarlyWarning);
        }else {
            openapiEarlyWarning.setModifyTime(new Date());
            Example example=new Example(OpenapiEarlyWarning.class);
            example.createCriteria().andEqualTo("id",openapiEarlyWarning.getId());
            return openapiEarlyWarningMapper.updateByExampleSelective(openapiEarlyWarning,example);
        }
    }

    @Override
    public Integer deleteOpenapiEarlyWarning(Long id) {
        if (id==null){
            return 0;
        }
        OpenapiEarlyWarning openapiEarlyWarning=new OpenapiEarlyWarning();
        openapiEarlyWarning.setModifyTime(new Date());
        openapiEarlyWarning.setIsDelete(1);
        Example example=new Example(OpenapiEarlyWarning.class);
        example.createCriteria().andEqualTo("id",id);
        return openapiEarlyWarningMapper.updateByExampleSelective(openapiEarlyWarning,example);
    }
}
