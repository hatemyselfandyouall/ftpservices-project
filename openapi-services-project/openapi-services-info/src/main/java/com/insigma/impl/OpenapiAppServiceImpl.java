package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.constant.DataConstant;
import com.insigma.facade.openapi.facade.OpenapiAppFacade;
import com.insigma.facade.openapi.po.OpenapiAppInterface;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.OpenapiApp.*;
import com.insigma.mapper.OpenapiAppInterfaceMapper;
import com.insigma.mapper.OpenapiAppMapper;
import com.insigma.mapper.OpenapiInterfaceMapper;
import com.insigma.util.JSONUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import com.insigma.facade.openapi.po.OpenapiApp;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class OpenapiAppServiceImpl implements OpenapiAppFacade {

    @Autowired
    OpenapiAppMapper openapiAppMapper;
    @Autowired
    OpenapiAppInterfaceMapper openapiAppInterfaceMapper;
    @Autowired
    OpenapiInterfaceMapper openapiInterfaceMapper;

    @Override
    public PageInfo<OpenapiApp> getOpenapiAppList(OpenapiAppListVO openapiAppListVO) {
        if (openapiAppListVO==null||openapiAppListVO.getPageNum()==null||openapiAppListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiAppListVO.getPageNum().intValue(),openapiAppListVO.getPageSize().intValue());
        OpenapiApp exampleObeject=new OpenapiApp();
        List<OpenapiApp> openapiAppList=openapiAppMapper.select(exampleObeject);
        PageInfo<OpenapiApp> openapiAppPageInfo=new PageInfo<>(openapiAppList);
        return openapiAppPageInfo;
    }

    @Override
    public OpenapiApp getOpenapiAppDetail(OpenapiAppDetailVO openapiAppDetailVO) {
        if (openapiAppDetailVO==null||openapiAppDetailVO.getId()==null) {
            return null;
        };
        OpenapiApp openapiApp=openapiAppMapper.selectByPrimaryKey(openapiAppDetailVO.getId());
        return openapiApp;
    }

    @Override
    public Integer saveOpenapiApp(OpenapiAppSaveVO openapiAppSaveVO) {
        if (openapiAppSaveVO==null){
            return 0;
        }
        OpenapiApp openapiApp= JSONUtil.convert(openapiAppSaveVO,OpenapiApp.class);
        if (openapiApp.getId()==null){
            return openapiAppMapper.insertSelective(openapiApp);
        }else {
            openapiApp.setModifyTime(new Date());
            Example example=new Example(OpenapiApp.class);
            example.createCriteria().andEqualTo("id",openapiApp.getId());
            return openapiAppMapper.updateByExampleSelective(openapiApp,example);
        }
    }

    @Override
    public Integer deleteOpenapiApp(OpenapiAppDeleteVO openapiAppDeleteVO) {
        if (openapiAppDeleteVO==null||openapiAppDeleteVO.getId()==null){
            return 0;
        }
        OpenapiApp openapiApp=new OpenapiApp();
        openapiApp.setModifyTime(new Date());
        openapiApp.setIsDelete(openapiAppDeleteVO.getIsDelete());
        Example example=new Example(OpenapiApp.class);
        example.createCriteria().andEqualTo("id",openapiAppDeleteVO.getId());
        return openapiAppMapper.updateByExampleSelective(openapiApp,example);
    }

    @Override
    public OpenapiAppShowDetailVO getAppByAppId(String appKey) {
        OpenapiApp example=new OpenapiApp();
        example.setAppKey(appKey);
        example.setIsDelete(DataConstant.NO_DELETE);
        OpenapiApp openapiApp=openapiAppMapper.selectOne(example);
        if (openapiApp==null){
            return null;
        }
        OpenapiAppShowDetailVO openapiAppShowDetailVO=JSONUtil.convert(openapiApp,OpenapiAppShowDetailVO.class);
        OpenapiAppInterface exampleAppInterface=new OpenapiAppInterface();
        exampleAppInterface.setAppId(openapiApp.getId());
        exampleAppInterface.setIsDelete(DataConstant.NO_DELETE);
        exampleAppInterface.setIsAudit(DataConstant.IS_AUDITED);
        List<OpenapiAppInterface> openapiAppInterfaces=openapiAppInterfaceMapper.select(exampleAppInterface);
        if (!CollectionUtils.isEmpty(openapiAppInterfaces)){
            List<OpenapiInterface> openapiInterfaces=openapiAppInterfaces.stream().map(i->{
                OpenapiInterface openapiInterface=openapiInterfaceMapper.selectByPrimaryKey(i.getInterfaceId());
                return openapiInterface;
            }).collect(Collectors.toList());
            openapiAppShowDetailVO.setOpenapiInterfaces(openapiInterfaces);
        }
        return openapiAppShowDetailVO;
    }
}
