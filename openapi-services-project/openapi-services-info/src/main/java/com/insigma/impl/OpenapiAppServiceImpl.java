package com.insigma.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.po.OpenapiAppType;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceListVO;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceSaveVO;
import com.insigma.mapper.OpenapiAppTypeMapper;
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
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.util.StringUtils;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;
import star.vo.result.ResultVo;
import tk.mybatis.mapper.entity.Example;
import com.insigma.facade.openapi.po.OpenapiApp;

import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    @Autowired
    InterfaceFacade  interfaceFacade;
    @Autowired
    OpenapiAppTypeMapper openapiAppTypeMapper;



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
        if (!StringUtils.isEmpty(openapiAppListVO.getOrgName())){
            criteria.andEqualTo("orgName",openapiAppListVO.getOrgName());
        }
        if (openapiAppListVO.getTypeId()!=null){
            criteria.andEqualTo("typeId",openapiAppListVO.getTypeId());
        }
        if (!StringUtils.isEmpty(openapiAppListVO.getName())){
            criteria.andLike("name","%"+openapiAppListVO.getName()+"%");
        }
        Page<OpenapiApp> openapiAppList=(Page<OpenapiApp>)openapiAppMapper.selectByExample(example);;
        List<OpenapiAppListShowVO> openapiAppListShowVOS=openapiAppList.stream().map(i->{
            OpenapiAppListShowVO openapiAppListShowVO=JSONUtil.convert(i,OpenapiAppListShowVO.class);
            openapiAppListShowVO.setInterfaceCount(openapiAppInterfaceMapper.getOpenapiAppInterfaceList(null,null,i.getId()).size());
            if (openapiAppListShowVO.getTypeId()!=null) {
                OpenapiAppType openapiAppType = openapiAppTypeMapper.selectByPrimaryKey(openapiAppListShowVO.getTypeId());
                openapiAppListShowVO.setTypeName(openapiAppType != null ? "0" : openapiAppType.getName());
            }
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
        if(openapiApp!=null&&openapiApp.getTypeId()!=null) {
            OpenapiAppType openapiAppType = openapiAppTypeMapper.selectByPrimaryKey(openapiApp.getTypeId());
            openapiApp.setTypeName(openapiAppType != null ? "0" : openapiAppType.getName());
        }
        return openapiApp;
    }

    @Override
    public Integer saveOpenapiApp(OpenapiAppSaveVO openapiAppSaveVO, Long userId, String userName, String orgName) {
        if (openapiAppSaveVO==null){
            return 0;
        }
        OpenapiApp openapiApp= JSONUtil.convert(openapiAppSaveVO,OpenapiApp.class);
        if (openapiApp.getId()==null){
            openapiApp.setAppKey(UUID.randomUUID().toString().replaceAll("-",""));
            openapiApp.setAppSecret(UUID.randomUUID().toString().replaceAll("-",""));
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

    @Override
    public ResultVo checkSave(OpenapiAppSaveVO openapiAppSaveVO) {
        ResultVo resultVo=new ResultVo();
        resultVo.setSuccess(true);
        return resultVo;
    }

    @Override
    public PageInfo<OpenapiAppInterfaceShowVO> getOpenapiAppInterfaceList(OpenapiAppInterfaceListVO openapiAppInterfaceListVO) {
        if (openapiAppInterfaceListVO==null||openapiAppInterfaceListVO.getPageNum()==null||openapiAppInterfaceListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiAppInterfaceListVO.getPageNum().intValue(),openapiAppInterfaceListVO.getPageSize().intValue());
        Page<OpenapiAppInterface> openapiAppInterfaces= (Page<OpenapiAppInterface>) openapiAppInterfaceMapper.getOpenapiAppInterfaceList(openapiAppInterfaceListVO.getKeyword(),openapiAppInterfaceListVO.getSourceType(),openapiAppInterfaceListVO.getAppId());
        List<OpenapiAppInterfaceShowVO> openapiAppInterfaceShowVOS=openapiAppInterfaces.stream().map(i->{
            OpenapiAppInterfaceShowVO openapiAppInterfaceShowVO=JSONUtil.convert(i,OpenapiAppInterfaceShowVO.class);
            openapiAppInterfaceShowVO.setOpenapiInterface(openapiInterfaceMapper.selectByPrimaryKey(i.getInterfaceId()));
            return openapiAppInterfaceShowVO;
        }).collect(Collectors.toList());
        PageInfo<OpenapiAppInterfaceShowVO> openapiAppInterfaceShowVOPageInfo=new PageInfo<>(openapiAppInterfaceShowVOS);
        openapiAppInterfaceShowVOPageInfo.setTotal(openapiAppInterfaces.getTotal());
        return openapiAppInterfaceShowVOPageInfo;
    }

    @Override
    public ResultVo checkAppInterfaceSave(OpenapiAppInterfaceSaveVO openapiAppSaveVO) {
        ResultVo resultVo=new ResultVo();
        if (openapiAppSaveVO==null){
            resultVo.setResultDes("参数必须传递");
            return resultVo;
        }
        Long appId=openapiAppSaveVO.getAppId();
        List<Long> interfaceIds=openapiAppSaveVO.getInterfaceIds();
        if (appId==null||interfaceIds==null||interfaceIds.isEmpty()){
            resultVo.setResultDes("参数必须传递");
            return resultVo;
        }
        Example example=new Example(OpenapiAppInterface.class);
        example.createCriteria().andEqualTo("appId",appId).andEqualTo("isDelete",DataConstant.NO_DELETE).andEqualTo("isAudit",DataConstant.IS_AUDITED).andIn("interfaceId",interfaceIds);
        if (openapiAppInterfaceMapper.selectCountByExample(example)>0){
            resultVo.setResultDes("不允许重复授权");
            return resultVo;
        }
        resultVo.setSuccess(true);
        return resultVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveAppInterface(OpenapiAppInterfaceSaveVO openapiAppSaveVO) {
        OpenapiAppInterface openapiAppInterface=JSONUtil.convert(openapiAppSaveVO,OpenapiAppInterface.class);
        List<Long> interrfaceIds=openapiAppSaveVO.getInterfaceIds();
        interrfaceIds.forEach(i->{
            openapiAppInterface.setId(null);
            openapiAppInterface.setInterfaceId(i);
            openapiAppInterface.setIsDelete(DataConstant.NO_DELETE);
            openapiAppInterface.setIsAudit(DataConstant.IS_AUDITED);
            openapiAppInterfaceMapper.insertSelective(openapiAppInterface);
        });
        return 1;
    }

    @Override
    public Integer changeAppBlackStatus(ChangeAppBlackStatusVO changeAppBlackStatusVO) {
        if (changeAppBlackStatusVO==null||changeAppBlackStatusVO.getId()==null){
            return 0;
        }
        OpenapiApp openapiApp=new OpenapiApp();
        openapiApp.setModifyTime(new Date());
        openapiApp.setIsBlacked(changeAppBlackStatusVO.getIsBlacked());
        Example example=new Example(OpenapiApp.class);
        example.createCriteria().andEqualTo("id",changeAppBlackStatusVO.getId());
        return openapiAppMapper.updateByExampleSelective(openapiApp,example);
    }

    @Override
    public OpenapiApp resetAppSecret(ResetAppSecretVO resetAppSecretVO) {
        if (resetAppSecretVO==null||resetAppSecretVO.getId()==null){
            return null;
        }
        OpenapiApp openapiApp=new OpenapiApp();
        openapiApp.setModifyTime(new Date());
        openapiApp.setAppSecret(UUID.randomUUID().toString().replaceAll("-",""));
        Example example=new Example(OpenapiApp.class);
        example.createCriteria().andEqualTo("id",resetAppSecretVO.getId());
        openapiAppMapper.updateByExampleSelective(openapiApp,example);
        return openapiAppMapper.selectByPrimaryKey(resetAppSecretVO.getId());
    }

    @Override
    public List<OpenapiInterface> getUnUsedInterfaceList(UnUsedInterfaceListVO unUsedInterfaceListVO) {
        List<OpenapiAppInterface> openapiAppInterfaces=openapiAppInterfaceMapper.select(new OpenapiAppInterface().setAppId(unUsedInterfaceListVO.getId()).setIsDelete(DataConstant.NO_DELETE));
        List<Long> ids=openapiAppInterfaces.stream().map(i->i.getInterfaceId()).collect(Collectors.toList());
        Example example=new Example(OpenapiInterface.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDelete",DataConstant.NO_DELETE).andEqualTo("typeId",unUsedInterfaceListVO.getTypeId());
        if (!CollectionUtils.isEmpty(ids)){
            criteria.andNotIn("id",ids);
        }
        return openapiInterfaceMapper.selectByExample(example);
    }

}
