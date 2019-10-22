package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiInterfaceHistoryFacade;
import com.insigma.mapper.OpenapiInterfaceHistoryMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import com.insigma.facade.openapi.po.OpenapiInterfaceHistory;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistorySaveVO;

public class OpenapiInterfaceHistoryServiceImpl implements OpenapiInterfaceHistoryFacade {

    @Autowired
    OpenapiInterfaceHistoryMapper openapiInterfaceHistoryMapper;

    @Override
    public PageInfo<OpenapiInterfaceHistory> getOpenapiInterfaceHistoryList(OpenapiInterfaceHistoryListVO openapiInterfaceHistoryListVO) {
        if (openapiInterfaceHistoryListVO==null||openapiInterfaceHistoryListVO.getPageNum()==null||openapiInterfaceHistoryListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiInterfaceHistoryListVO.getPageNum().intValue(),openapiInterfaceHistoryListVO.getPageSize().intValue());
        OpenapiInterfaceHistory exampleObeject=new OpenapiInterfaceHistory().setInterfaceId(openapiInterfaceHistoryListVO.getInterfaceId());
        List<OpenapiInterfaceHistory> openapiInterfaceHistoryList=openapiInterfaceHistoryMapper.select(exampleObeject);
        PageInfo<OpenapiInterfaceHistory> openapiInterfaceHistoryPageInfo=new PageInfo<>(openapiInterfaceHistoryList);
        return openapiInterfaceHistoryPageInfo;
    }

    @Override
    public OpenapiInterfaceHistory getOpenapiInterfaceHistoryDetail(OpenapiInterfaceHistoryDetailVO openapiInterfaceHistoryDetailVO) {
        if (openapiInterfaceHistoryDetailVO==null||openapiInterfaceHistoryDetailVO.getId()==null) {
            return null;
        };
        OpenapiInterfaceHistory openapiInterfaceHistory=openapiInterfaceHistoryMapper.selectByPrimaryKey(openapiInterfaceHistoryDetailVO.getId());
        return openapiInterfaceHistory;
    }

    @Override
    public Integer saveOpenapiInterfaceHistory(OpenapiInterfaceHistorySaveVO openapiInterfaceHistorySaveVO) {
        if (openapiInterfaceHistorySaveVO==null){
            return 0;
        }
        OpenapiInterfaceHistory openapiInterfaceHistory= JSONUtil.convert(openapiInterfaceHistorySaveVO,OpenapiInterfaceHistory.class);
        if (openapiInterfaceHistory.getId()==null){
            return openapiInterfaceHistoryMapper.insertSelective(openapiInterfaceHistory);
        }else {
            Example example=new Example(OpenapiInterfaceHistory.class);
            example.createCriteria().andEqualTo("id",openapiInterfaceHistory.getId());
            return openapiInterfaceHistoryMapper.updateByExampleSelective(openapiInterfaceHistory,example);
        }
    }

    @Override
    public Integer deleteOpenapiInterfaceHistory(OpenapiInterfaceHistoryDeleteVO openapiInterfaceHistoryDeleteVO) {
        if (openapiInterfaceHistoryDeleteVO==null||openapiInterfaceHistoryDeleteVO.getId()==null){
            return 0;
        }
        OpenapiInterfaceHistory openapiInterfaceHistory=new OpenapiInterfaceHistory();
        Example example=new Example(OpenapiInterfaceHistory.class);
        example.createCriteria().andEqualTo("id",openapiInterfaceHistoryDeleteVO.getId());
        return openapiInterfaceHistoryMapper.updateByExampleSelective(openapiInterfaceHistory,example);
    }
}
