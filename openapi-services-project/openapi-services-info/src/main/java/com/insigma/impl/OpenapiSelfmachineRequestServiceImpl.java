package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.enums.OpenapiCacheEnum;
import com.insigma.facade.openapi.dto.SelfMachineOrgDTO;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineRequestFacade;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.po.OpenapiSelfmachineRequest;
import com.insigma.facade.openapi.po.SelfMachineEnum;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.insigma.mapper.OpenapiOrgMapper;
import com.insigma.mapper.OpenapiSelfmachineRequestMapper;
import com.insigma.util.JSONUtil;
import com.insigma.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import star.bizbase.util.StringUtils;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.CachesService;
import star.modules.cache.enumerate.BaseCacheEnum;
import tk.mybatis.mapper.entity.Example;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class OpenapiSelfmachineRequestServiceImpl implements OpenapiSelfmachineRequestFacade {

    @Autowired
    OpenapiSelfmachineRequestMapper openapiSelfmachineRequestMapper;
    @Autowired
    CachesKeyService cachesKeyService;
    @Autowired
    CachesService cachesService;
    @Autowired
    OpenapiOrgMapper openapiOrgMapper;

    @Override
    public PageInfo<OpenapiSelfmachineRequest> getOpenapiSelfmachineRequestList(OpenapiSelfmachineRequestListVO openapiSelfmachineRequestListVO) {
        if (openapiSelfmachineRequestListVO==null||openapiSelfmachineRequestListVO.getPageNum()==null||openapiSelfmachineRequestListVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(OpenapiSelfmachineRequest.class);
        Example.Criteria criteria=example.createCriteria();
        if (!StringUtils.isEmpty(openapiSelfmachineRequestListVO.getKeyWord())){
            criteria.andCondition("( ip like '%"+openapiSelfmachineRequestListVO.getKeyWord()+"%' or unique_code like '%"
                    +openapiSelfmachineRequestListVO.getKeyWord()+"%' or mac_address like '%"+openapiSelfmachineRequestListVO.getKeyWord()+"%' )");
        }
        if (!StringUtils.isEmpty(openapiSelfmachineRequestListVO.getMachineType())){
            criteria.andEqualTo("machineType",openapiSelfmachineRequestListVO.getMachineType());
        }
        if (openapiSelfmachineRequestListVO.getPreliminaryCalibration()!=null){
            criteria.andEqualTo("preliminaryCalibration",openapiSelfmachineRequestListVO.getPreliminaryCalibration());
        }
        criteria.andEqualTo("statu", SelfMachineEnum.NOT_YET);
        PageHelper.startPage(openapiSelfmachineRequestListVO.getPageNum().intValue(),openapiSelfmachineRequestListVO.getPageSize().intValue());
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequestList=openapiSelfmachineRequestMapper.selectByExample(example);
        PageInfo<OpenapiSelfmachineRequest> openapiSelfmachineRequestPageInfo=new PageInfo<>(openapiSelfmachineRequestList);
        return openapiSelfmachineRequestPageInfo;
    }

    @Override
    public OpenapiSelfmachineRequest getOpenapiSelfmachineRequestDetail(OpenapiSelfmachineRequestDetailVO openapiSelfmachineRequestDetailVO) {
        if (openapiSelfmachineRequestDetailVO==null) {
            return null;
        };
        OpenapiSelfmachineRequest openapiSelfmachineRequest=openapiSelfmachineRequestMapper.selectOne(JSONUtil.convert(openapiSelfmachineRequestDetailVO,OpenapiSelfmachineRequest.class));
        return openapiSelfmachineRequest;
    }

    @Override
    public Integer saveOpenapiSelfmachineRequest(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO) {
        if (openapiSelfmachineRequestSaveVO==null){
            return 0;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest= JSONUtil.convert(openapiSelfmachineRequestSaveVO,OpenapiSelfmachineRequest.class);
        if (openapiSelfmachineRequest.getId()==null){
            return openapiSelfmachineRequestMapper.insertSelective(openapiSelfmachineRequest);
        }else {
            Example example=new Example(OpenapiSelfmachineRequest.class);
            example.createCriteria().andEqualTo("id",openapiSelfmachineRequest.getId());
            return openapiSelfmachineRequestMapper.updateByExampleSelective(openapiSelfmachineRequest,example);
        }
    }

    @Override
    public Integer deleteOpenapiSelfmachineRequest(OpenapiSelfmachineRequestDeleteVO openapiSelfmachineRequestDeleteVO) {
        if (openapiSelfmachineRequestDeleteVO==null|| CollectionUtils.isEmpty(openapiSelfmachineRequestDeleteVO.getIds())){
            return 0;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest=new OpenapiSelfmachineRequest();
        openapiSelfmachineRequest.setStatu(openapiSelfmachineRequestDeleteVO.getStatu());
        Example example=new Example(OpenapiSelfmachineRequest.class);
        example.createCriteria().andIn("id",openapiSelfmachineRequestDeleteVO.getIds());
        return openapiSelfmachineRequestMapper.updateByExampleSelective(openapiSelfmachineRequest,example);
    }

    @Override
    public OpenapiSelfmachineRequest createToken(OpenapiSelfmachineRequest uniqueCode, OpenapiOrg openapiOrg) {
        return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_30M){
            @Override
            protected Object doGetList(BaseCacheEnum type, String key){
                String token=UUID.randomUUID().toString().replaceAll("-","");
                uniqueCode.setToken(token);
                if (uniqueCode.getMachineCode()==null){
                    uniqueCode.setMachineCode(getInitMachineCode(uniqueCode,openapiOrg));
                }
                openapiSelfmachineRequestMapper.updateByPrimaryKeySelective(uniqueCode);
                return uniqueCode;
            }
        }.getCache(OpenapiCacheEnum.REQUEST_TOKEN, uniqueCode.getUniqueCode());
    }

    @Override
    public Boolean checkTokenExit(String token) {
        Boolean flag=false;
        if (StringUtils.isEmpty(token)){
            return flag;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest=openapiSelfmachineRequestMapper.selectOne(new OpenapiSelfmachineRequest().setToken(token));
        if (openapiSelfmachineRequest==null){
            return flag;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest1=cachesKeyService.getFromCache(OpenapiCacheEnum.REQUEST_TOKEN,openapiSelfmachineRequest.getUniqueCode());
        if (openapiSelfmachineRequest1!=null&&token.equals(openapiSelfmachineRequest1.getToken())){
            flag=true;
        }
        return flag;
    }

    @Override
    public SelfMachineOrgDTO getOrgByToken(String token) {
        SelfMachineOrgDTO selfMachineOrgDTO=null;
        if (StringUtils.isEmpty(token)){
            return null;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest=openapiSelfmachineRequestMapper.selectOne(new OpenapiSelfmachineRequest().setToken(token));
        if (openapiSelfmachineRequest==null){
            return null;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest1=cachesKeyService.getFromCache(OpenapiCacheEnum.REQUEST_TOKEN,openapiSelfmachineRequest.getUniqueCode());
        if (openapiSelfmachineRequest1!=null&&token.equals(openapiSelfmachineRequest1.getToken())){
            Long orgId=openapiSelfmachineRequest1.getOrgId();
            OpenapiOrg openapiOrg=openapiOrgMapper.selectByPrimaryKey(orgId);
            if (openapiOrg==null){
                return null;
            }
            selfMachineOrgDTO=new SelfMachineOrgDTO().setOrgCode(openapiOrg.getOrgCode()).setOrgName(openapiOrg.getOrgName());
        }
        return selfMachineOrgDTO;
    }


    private String getInitMachineCode(OpenapiSelfmachineRequest openapiSelfmachineRequest, OpenapiOrg openapiOrg){
        Long orgId=openapiSelfmachineRequest.getOrgId();
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequests=openapiSelfmachineRequestMapper.select(new OpenapiSelfmachineRequest().setOrgId(orgId));
        Optional<Integer> number=openapiSelfmachineRequests.stream().filter(i->i.getNumber()!=null).max(Comparator.comparingInt(OpenapiSelfmachineRequest::getNumber)).map(OpenapiSelfmachineRequest::getNumber);
        openapiSelfmachineRequest.setNumber(number.orElse(0)+1);
        return openapiOrg.getAbbreviation()+"00"+openapiSelfmachineRequest.getNumber();
    }
}
