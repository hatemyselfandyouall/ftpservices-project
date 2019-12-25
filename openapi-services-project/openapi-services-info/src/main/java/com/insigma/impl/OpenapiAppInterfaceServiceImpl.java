package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.enums.OpenapiCacheEnum;
import com.insigma.facade.openapi.facade.OpenapiAppInterfaceFacade;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.mapper.OpenapiAppInterfaceMapper;
import com.insigma.mapper.OpenapiAppMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import star.modules.cache.CachesKeyService;
import tk.mybatis.mapper.entity.Example;
import com.insigma.facade.openapi.po.OpenapiAppInterface;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceDetailVO;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceListVO;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceSaveVO;

import java.util.Date;
import java.util.List;


public class OpenapiAppInterfaceServiceImpl implements OpenapiAppInterfaceFacade {

    @Autowired
    OpenapiAppInterfaceMapper openapiAppInterfaceMapper;
    @Autowired
    CachesKeyService cachesKeyService;
    @Autowired
    OpenapiAppMapper openapiAppMapper;

    @Override
    public PageInfo<OpenapiAppInterface> getOpenapiAppInterfaceList(OpenapiAppInterfaceListVO openapiAppInterfaceListVO) {
        if (openapiAppInterfaceListVO==null||openapiAppInterfaceListVO.getPageNum()==null||openapiAppInterfaceListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiAppInterfaceListVO.getPageNum().intValue(),openapiAppInterfaceListVO.getPageSize().intValue());
        OpenapiAppInterface exampleObeject=new OpenapiAppInterface();
        List<OpenapiAppInterface> openapiAppInterfaceList=openapiAppInterfaceMapper.select(exampleObeject);
        PageInfo<OpenapiAppInterface> openapiAppInterfacePageInfo=new PageInfo<>(openapiAppInterfaceList);
        return openapiAppInterfacePageInfo;
    }

    @Override
    public OpenapiAppInterface getOpenapiAppInterfaceDetail(OpenapiAppInterfaceDetailVO openapiAppInterfaceDetailVO) {
        if (openapiAppInterfaceDetailVO==null||openapiAppInterfaceDetailVO.getId()==null) {
            return null;
        };
        OpenapiAppInterface openapiAppInterface=openapiAppInterfaceMapper.selectByPrimaryKey(openapiAppInterfaceDetailVO.getId());
        return openapiAppInterface;
    }

    @Override
    public Integer saveOpenapiAppInterface(OpenapiAppInterfaceSaveVO openapiAppInterfaceSaveVO) {
        if (openapiAppInterfaceSaveVO==null){
            return 0;
        }
        OpenapiAppInterface openapiAppInterface= JSONUtil.convert(openapiAppInterfaceSaveVO,OpenapiAppInterface.class);
        if (openapiAppInterface.getId()==null){
            return openapiAppInterfaceMapper.insertSelective(openapiAppInterface);
        }else {
            openapiAppInterface.setModifyTime(new Date());
            Example example=new Example(OpenapiAppInterface.class);
            example.createCriteria().andEqualTo("id",openapiAppInterface.getId());
            return openapiAppInterfaceMapper.updateByExampleSelective(openapiAppInterface,example);
        }
    }

    @Override
    public Integer deleteOpenapiAppInterface(OpenapiAppInterfaceDeleteVO openapiAppInterfaceDeleteVO) {
        if (openapiAppInterfaceDeleteVO==null||openapiAppInterfaceDeleteVO.getId()==null){
            return 0;
        }
        OpenapiAppInterface openapiAppInterface=new OpenapiAppInterface();
        openapiAppInterface.setModifyTime(new Date());
        openapiAppInterface.setIsDelete(openapiAppInterfaceDeleteVO.getIsDelete());
        Example example=new Example(OpenapiAppInterface.class);
        example.createCriteria().andEqualTo("id",openapiAppInterfaceDeleteVO.getId());
        OpenapiAppInterface temp=openapiAppInterfaceMapper.selectByPrimaryKey(openapiAppInterfaceDeleteVO.getId());
        OpenapiApp openapiApp=openapiAppMapper.selectByPrimaryKey(temp.getAppId());
        if (openapiApp!=null){
            cachesKeyService.deleteCache(OpenapiCacheEnum.OPENAPI_BY_APPID,openapiApp.getId().toString());
            cachesKeyService.deleteCache(OpenapiCacheEnum.OPENAPI_BY_APPKEY,openapiApp.getAppKey());
        }
        return openapiAppInterfaceMapper.updateByExampleSelective(openapiAppInterface,example);
    }
}
