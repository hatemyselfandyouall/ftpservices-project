package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.mapper.OpenapiDictionaryMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import com.insigma.facade.openapi.po.OpenapiDictionary;
import com.insigma.facade.openapi.vo.OpenapiDictionary.OpenapiDictionaryDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiDictionary.OpenapiDictionaryDetailVO;
import com.insigma.facade.openapi.vo.OpenapiDictionary.OpenapiDictionaryListVO;
import com.insigma.facade.openapi.vo.OpenapiDictionary.OpenapiDictionarySaveVO;

public class OpenapiDictionaryServiceImpl implements OpenapiDictionaryFacade {

    @Autowired
    OpenapiDictionaryMapper openapiDictionaryMapper;

    @Override
    public PageInfo<OpenapiDictionary> getOpenapiDictionaryList(OpenapiDictionaryListVO openapiDictionaryListVO) {
        if (openapiDictionaryListVO==null||openapiDictionaryListVO.getPageNum()==null||openapiDictionaryListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiDictionaryListVO.getPageNum().intValue(),openapiDictionaryListVO.getPageSize().intValue());
        OpenapiDictionary exampleObeject=new OpenapiDictionary();
        List<OpenapiDictionary> openapiDictionaryList=openapiDictionaryMapper.select(exampleObeject);
        PageInfo<OpenapiDictionary> openapiDictionaryPageInfo=new PageInfo<>(openapiDictionaryList);
        return openapiDictionaryPageInfo;
    }

    @Override
    public OpenapiDictionary getOpenapiDictionaryDetail(OpenapiDictionaryDetailVO openapiDictionaryDetailVO) {
        if (openapiDictionaryDetailVO==null||openapiDictionaryDetailVO.getId()==null) {
            return null;
        };
        OpenapiDictionary openapiDictionary=openapiDictionaryMapper.selectByPrimaryKey(openapiDictionaryDetailVO.getId());
        return openapiDictionary;
    }

    @Override
    public Integer saveOpenapiDictionary(OpenapiDictionarySaveVO openapiDictionarySaveVO) {
        if (openapiDictionarySaveVO==null){
            return 0;
        }
        OpenapiDictionary openapiDictionary= JSONUtil.convert(openapiDictionarySaveVO,OpenapiDictionary.class);
        if (openapiDictionary.getId()==null){
            return openapiDictionaryMapper.insertSelective(openapiDictionary);
        }else {
            Example example=new Example(OpenapiDictionary.class);
            example.createCriteria().andEqualTo("id",openapiDictionary.getId());
            return openapiDictionaryMapper.updateByExampleSelective(openapiDictionary,example);
        }
    }

    @Override
    public Integer deleteOpenapiDictionary(OpenapiDictionaryDeleteVO openapiDictionaryDeleteVO) {
        if (openapiDictionaryDeleteVO==null||openapiDictionaryDeleteVO.getId()==null){
            return 0;
        }
        OpenapiDictionary openapiDictionary=new OpenapiDictionary();
        Example example=new Example(OpenapiDictionary.class);
        example.createCriteria().andEqualTo("id",openapiDictionaryDeleteVO.getId());
        return openapiDictionaryMapper.updateByExampleSelective(openapiDictionary,example);
    }

    @Override
    public String getValueByCode(String code) {
        OpenapiDictionary openapiDictionary=openapiDictionaryMapper.selectOne(new OpenapiDictionary().setCode(code));
        return  openapiDictionary!=null?openapiDictionary.getValue():null;
    }

}
