package com.insigma.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import constant.DataConstant;
import com.insigma.enums.OpenapiCacheEnum;
import com.insigma.facade.openapi.facade.OpenapiAppFacade;
import com.insigma.facade.openapi.po.OpenapiAppInterface;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.OpenapiApp.*;
import com.insigma.mapper.OpenapiAppInterfaceMapper;
import com.insigma.mapper.OpenapiAppMapper;
import com.insigma.mapper.OpenapiInterfaceMapper;
import com.insigma.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;
import tk.mybatis.mapper.entity.Example;
import com.insigma.facade.openapi.po.OpenapiApp;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class OpenapiAppServiceImpl implements OpenapiAppFacade {

    @Autowired
    OpenapiAppMapper openapiAppMapper;
    @Autowired
    OpenapiAppInterfaceMapper openapiAppInterfaceMapper;
    @Autowired
    OpenapiInterfaceMapper openapiInterfaceMapper;
    @Autowired
    CachesKeyService cachesKeyService;



    @Override
    public PageInfo<OpenapiAppListShowVO> getOpenapiAppList(OpenapiAppListVO openapiAppListVO) {
        if (openapiAppListVO==null||openapiAppListVO.getPageNum()==null||openapiAppListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiAppListVO.getPageNum().intValue(),openapiAppListVO.getPageSize().intValue());
       Example example=new Example(OpenapiApp.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDelete",DataConstant.NO_DELETE);
        if (openapiAppListVO.getChannelSource()!=null){
            criteria.andEqualTo("channelSource",openapiAppListVO.getChannelSource());
        }
        if (openapiAppListVO.getOrgName()!=null){
            criteria.andEqualTo("orgName",openapiAppListVO.getOrgName());
        }
        if (openapiAppListVO.getTypeId()!=null){
            criteria.andEqualTo("typeId",openapiAppListVO.getTypeId());
        }
        if (openapiAppListVO.getName()!=null){
            criteria.andLike("orgName",openapiAppListVO.getOrgName());
        }
        Page<OpenapiApp> openapiAppList=(Page<OpenapiApp>)openapiAppMapper.selectByExample(example);;
        List<OpenapiAppListShowVO> openapiAppListShowVOS=openapiAppList.stream().map(i->{
            OpenapiAppListShowVO openapiAppListShowVO=JSONUtil.convert(i,OpenapiAppListShowVO.class);
            OpenapiAppInterface openapiInterface=new OpenapiAppInterface().setIsDelete(DataConstant.NO_DELETE).setAppId(i.getId());
            openapiAppListShowVO.setInterfaceCount(openapiAppInterfaceMapper.selectCount(openapiInterface));
            return openapiAppListShowVO;
        }).collect(Collectors.toList());
        PageInfo<OpenapiAppListShowVO> openapiAppListShowVOPageInfo=new PageInfo<>(openapiAppListShowVOS);
        openapiAppListShowVOPageInfo.setTotal(openapiAppList.getTotal());
        return openapiAppListShowVOPageInfo;
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
    public OpenapiAppShowDetailVO getAppByAppKey(String appKey) {
        String cacheKey=appKey;
        return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_30M){
            @Override
            protected Object doGetList(BaseCacheEnum type, String key){
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
        }.getCache(OpenapiCacheEnum.OPENAPI_BY_APPKEY, cacheKey);
    }

    @Override
    public List<OpenapiAppShowDetailVO> getAppsByUserId(Long id) {
        log.info("开始调用AppKeyListByCommandCodeAndOrgNO方法,id+"+id);
        String cacheKey = id.toString();
        return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_30M) {
            @Override
            protected Object doGetList(BaseCacheEnum type, String key) {
                OpenapiApp example = new OpenapiApp();
                example.setUserId(id);
                example.setIsDelete(DataConstant.NO_DELETE);
                List<OpenapiApp> openapiApp = openapiAppMapper.select(example);
                if (openapiApp == null) {
                    return null;
                }
                List<OpenapiAppShowDetailVO> openapiAppShowDetailVOs = openapiApp.stream().map(i -> {
                    OpenapiAppShowDetailVO openapiAppShowDetailVO = JSONUtil.convert(i, OpenapiAppShowDetailVO.class);
                    OpenapiAppInterface exampleAppInterface = new OpenapiAppInterface();
                    exampleAppInterface.setAppId(i.getId());
                    exampleAppInterface.setIsDelete(DataConstant.NO_DELETE);
                    exampleAppInterface.setIsAudit(DataConstant.IS_AUDITED);
                    List<OpenapiAppInterface> openapiAppInterfaces = openapiAppInterfaceMapper.select(exampleAppInterface);
                    if (!CollectionUtils.isEmpty(openapiAppInterfaces)) {
                        List<OpenapiInterface> openapiInterfaces = openapiAppInterfaces.stream().map(j -> {
                            OpenapiInterface openapiInterface = openapiInterfaceMapper.selectByPrimaryKey(j.getInterfaceId());
                            return openapiInterface;
                        }).collect(Collectors.toList());
                        openapiAppShowDetailVO.setOpenapiInterfaces(openapiInterfaces);
                    }
                    return openapiAppShowDetailVO;
                }).collect(Collectors.toList());
                return openapiAppShowDetailVOs;
            }
        }.getCache(OpenapiCacheEnum.OPENAPI_BY_APPID, cacheKey);
    }

    @Override
    public Long getInstitutionCount() {
        OpenapiApp openapiApp=new OpenapiApp();
        openapiApp.setIsDelete(DataConstant.NO_DELETE);
        return (long)openapiAppMapper.selectCount(openapiApp);
    }

}
