package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.enums.OpenapiCacheEnum;
import com.insigma.facade.openapi.dto.SelfMachineOrgDTO;
import com.insigma.facade.openapi.enums.OpenapiSelfmachineEnum;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineFacade;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineRequestFacade;
import com.insigma.facade.openapi.po.*;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.OpenapiSelfmachineDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.insigma.mapper.OpenapiOrgMapper;
import com.insigma.mapper.OpenapiSelfmachineMapper;
import com.insigma.mapper.OpenapiSelfmachineRequestMapper;
import com.insigma.mapper.OpenapiSelfmachineTypeMapper;
import com.insigma.util.DateUtils;
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

import java.util.*;


public class OpenapiSelfmachineRequestServiceImpl implements OpenapiSelfmachineRequestFacade {

    @Autowired
    OpenapiSelfmachineRequestMapper openapiSelfmachineRequestMapper;
    @Autowired
    CachesKeyService cachesKeyService;
    @Autowired
    CachesService cachesService;
    @Autowired
    OpenapiOrgMapper openapiOrgMapper;
    @Autowired
    OpenapiSelfmachineMapper openapiSelfmachineMapper;
    @Autowired
    OpenapiSelfmachineTypeMapper openapiSelfmachineTypeMapper;
    @Autowired
    OpenapiSelfmachineFacade openapiSelfmachineFacade;

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
        example.setOrderByClause("request_time desc");
        PageHelper.startPage(openapiSelfmachineRequestListVO.getPageNum().intValue(),openapiSelfmachineRequestListVO.getPageSize().intValue());
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequestList=openapiSelfmachineRequestMapper.selectByExample(example);
        openapiSelfmachineRequestList.forEach(i->{
            OpenapiSelfmachine openapiSelfmachine=null;
            List<OpenapiSelfmachine> openapiSelfmachines=openapiSelfmachineMapper.select(new OpenapiSelfmachine().setUniqueCode(i.getUniqueCode()));
            if (!CollectionUtils.isEmpty(openapiSelfmachines)){
                openapiSelfmachine=openapiSelfmachines.get(0);
            }
            if (openapiSelfmachine!=null&&openapiSelfmachine.getMachineTypeId()!=null){
                OpenapiSelfmachineType openapiSelfmachineType=openapiSelfmachineTypeMapper.selectByPrimaryKey(openapiSelfmachine.getMachineTypeId());
                i.setMachineType(openapiSelfmachineType!=null?openapiSelfmachineType.getName():"");
            }
        });
        PageInfo<OpenapiSelfmachineRequest> openapiSelfmachineRequestPageInfo=new PageInfo<>(openapiSelfmachineRequestList);
        return openapiSelfmachineRequestPageInfo;
    }

    @Override
    public OpenapiSelfmachineRequest getOpenapiSelfmachineRequestDetail(OpenapiSelfmachineRequestDetailVO openapiSelfmachineRequestDetailVO) {
        if (openapiSelfmachineRequestDetailVO==null) {
            return null;
        };
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequest=openapiSelfmachineRequestMapper.select(JSONUtil.convert(openapiSelfmachineRequestDetailVO,OpenapiSelfmachineRequest.class));
        if (!CollectionUtils.isEmpty(openapiSelfmachineRequest)){
            return openapiSelfmachineRequest.get(0);
        }else {
            return null;
        }
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
    public OpenapiSelfmachineRequest createToken(OpenapiSelfmachineRequest openapiSelfmachineRequest, OpenapiOrg openapiOrg) {
        String token=UUID.randomUUID().toString().replaceAll("-","");
//        return new CacheKeyLock(cachesKeyService,SysCacheTimeDMO.CACHETIMEOUT_30M){
//            @Override
//            protected Object doGetList(BaseCacheEnum type, String key){
                if (cachesKeyService.getFromCache(OpenapiCacheEnum.REQUEST_TOKEN,openapiSelfmachineRequest.getToken())==null){
                openapiSelfmachineRequest.setOldToken(openapiSelfmachineRequest.getToken());
                openapiSelfmachineRequest.setToken(token);
                openapiSelfmachineRequestMapper.updateByPrimaryKeySelective(openapiSelfmachineRequest);
                cachesKeyService.putInCache(OpenapiCacheEnum.REQUEST_TOKEN,openapiSelfmachineRequest.getOldToken(),openapiSelfmachineRequest,SysCacheTimeDMO.CACHETIMEOUT_1H);
                    cachesKeyService.putInCache(OpenapiCacheEnum.REQUEST_TOKEN,openapiSelfmachineRequest.getToken(),openapiSelfmachineRequest,SysCacheTimeDMO.CACHETIMEOUT_30M);
                }
                return openapiSelfmachineRequest;
//            }
//        }.getCache(OpenapiCacheEnum.REQUEST_TOKEN, openapiSelfmachineRequest.getToken());
    }

    @Override
    public Boolean checkTokenExit(String token) {
        Boolean flag=false;
        if (StringUtils.isEmpty(token)){
            return flag;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest=null;
        Example example=new Example(OpenapiSelfmachineRequest.class);
        example.createCriteria().andCondition("token = '"+token+"' or old_token ='"+token+"'");
        List<OpenapiSelfmachineRequest> openapiSelfmachines=openapiSelfmachineRequestMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(openapiSelfmachines)){
            openapiSelfmachineRequest=openapiSelfmachines.get(0);
        }
        if (openapiSelfmachineRequest==null){
            return flag;
        }
        OpenapiSelfmachine openapiSelfmachine=openapiSelfmachineFacade.getOpenapiSelfmachineDetail(new OpenapiSelfmachineDetailVO().setUniqueCode(openapiSelfmachineRequest.getUniqueCode()));
        if (openapiSelfmachine==null|| OpenapiSelfmachineEnum.CANCEL.equals(openapiSelfmachine.getActiveStatu())){
            return flag;
        }

        if ( cachesKeyService.getFromCache(OpenapiCacheEnum.REQUEST_TOKEN,openapiSelfmachineRequest.getToken())!=null||
                cachesKeyService.getFromCache(OpenapiCacheEnum.REQUEST_TOKEN,openapiSelfmachineRequest.getOldToken())!=null){
            flag=  true;
        }
        return flag;
    }

    @Override
    public SelfMachineOrgDTO getOrgByToken(String token) {
        SelfMachineOrgDTO selfMachineOrgDTO=null;
        if (StringUtils.isEmpty(token)){
            return null;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest=null;
        List<OpenapiSelfmachineRequest> openapiSelfmachines=openapiSelfmachineRequestMapper.select(new OpenapiSelfmachineRequest().setToken(token));
        if (!CollectionUtils.isEmpty(openapiSelfmachines)){
            openapiSelfmachineRequest=openapiSelfmachines.get(0);
        }
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


    public String getInitMachineCode(OpenapiSelfmachineRequest openapiSelfmachineRequest, OpenapiOrg openapiOrg){
        Long orgId=openapiOrg.getId();
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequests=openapiSelfmachineRequestMapper.select(new OpenapiSelfmachineRequest().setOrgId(orgId));
        Optional<Integer> number=openapiSelfmachineRequests.stream().filter(i->i.getNumber()!=null).max(Comparator.comparingInt(OpenapiSelfmachineRequest::getNumber)).map(OpenapiSelfmachineRequest::getNumber);
        openapiSelfmachineRequest.setNumber(number.orElse(0)+1);
        return openapiOrg.getAbbreviation()+"00"+openapiSelfmachineRequest.getNumber();
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.parseDate(new Date(1574126664000l),"yyyy-MM-dd HH:mm:ss"));
    }
}
