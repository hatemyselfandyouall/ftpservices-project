package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiInterfaceHistoryFacade;
import com.insigma.mapper.OpenapiInterfaceHistoryMapper;
import com.insigma.mapper.OpenapiInterfaceMapper;
import com.insigma.util.JSONUtil;
import com.insigma.util.StringUtil;
import constant.DataConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

import com.insigma.facade.openapi.po.OpenapiInterfaceHistory;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistorySaveVO;

public class OpenapiInterfaceHistoryServiceImpl implements OpenapiInterfaceHistoryFacade {

    @Autowired
    OpenapiInterfaceHistoryMapper openapiInterfaceHistoryMapper;

    @Autowired
    OpenapiInterfaceMapper openapiInterfaceMapper;

    @Override
    public PageInfo<OpenapiInterfaceHistory> getOpenapiInterfaceHistoryList(OpenapiInterfaceHistoryListVO openapiInterfaceHistoryListVO) {
        if (openapiInterfaceHistoryListVO==null||openapiInterfaceHistoryListVO.getPageNum()==null||openapiInterfaceHistoryListVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(OpenapiInterfaceHistory.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("interfaceId", openapiInterfaceHistoryListVO.getInterfaceId());
        List<OpenapiInterfaceHistory> openapiInterfaceHistoryList=openapiInterfaceHistoryMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(openapiInterfaceHistoryList)){
            openapiInterfaceHistoryList.get(0).setIsAvaliable(openapiInterfaceMapper.selectByPrimaryKey(openapiInterfaceHistoryList.get(0).getInterfaceId()).getIsAvaliable());
        }
        if (openapiInterfaceHistoryListVO.getIsAvaliable()!=null){
            openapiInterfaceHistoryList=openapiInterfaceHistoryList.stream().filter(i->i.getIsAvaliable().equals(openapiInterfaceHistoryListVO.getIsAvaliable())).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(openapiInterfaceHistoryListVO.getKeyword())){
            openapiInterfaceHistoryList=openapiInterfaceHistoryList.stream().filter(i->i.getUpdateDescription().contains(openapiInterfaceHistoryListVO.getKeyword())).collect(Collectors.toList());
        }
        Integer total=openapiInterfaceHistoryList.size();
        int start=(int)((long)(openapiInterfaceHistoryListVO.getPageNum()>0?0:openapiInterfaceHistoryListVO.getPageNum()-1)*openapiInterfaceHistoryListVO.getPageSize());
        int end=start+(int)((long)openapiInterfaceHistoryListVO.getPageSize());
        if (end>openapiInterfaceHistoryList.size()){
        }else {
            openapiInterfaceHistoryList=openapiInterfaceHistoryList.subList(start,end);
        }
        PageInfo<OpenapiInterfaceHistory> openapiInterfaceHistoryPageInfo=new PageInfo<>(openapiInterfaceHistoryList);
        openapiInterfaceHistoryPageInfo.setTotal(total);
        return openapiInterfaceHistoryPageInfo;
    }

    @Override
    public OpenapiInterfaceHistory getOpenapiInterfaceHistoryDetail(OpenapiInterfaceHistoryDetailVO openapiInterfaceHistoryDetailVO) {
        if (openapiInterfaceHistoryDetailVO==null||openapiInterfaceHistoryDetailVO.getId()==null) {
            return null;
        }
        OpenapiInterfaceHistory openapiInterfaceHistory=openapiInterfaceHistoryMapper.selectByPrimaryKey(openapiInterfaceHistoryDetailVO.getId());
        return openapiInterfaceHistory;
    }

    @Override
    public Integer saveOpenapiInterfaceHistory(OpenapiInterfaceHistorySaveVO openapiInterfaceHistorySaveVO, Long userId, String userName) {
        if (openapiInterfaceHistorySaveVO==null){
            return 0;
        }
        OpenapiInterfaceHistory openapiInterfaceHistory= JSONUtil.convert(openapiInterfaceHistorySaveVO,OpenapiInterfaceHistory.class);
        if (openapiInterfaceHistory.getId()==null){
            openapiInterfaceHistory.setCreatorId(userId);
            openapiInterfaceHistory.setCreatorName(userName);
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
