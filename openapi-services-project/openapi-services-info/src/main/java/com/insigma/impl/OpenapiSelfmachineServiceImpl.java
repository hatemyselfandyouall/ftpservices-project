package com.insigma.impl;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineFacade;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineRequestFacade;
import com.insigma.facade.openapi.po.*;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.*;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.insigma.facade.sysbase.SysOrgFacade;
import com.insigma.facade.sysbase.SysUserFacade;
import com.insigma.facade.sysbase.vo.SysUserOrgDTO;
import com.insigma.mapper.OpenapiSelfmachineAddressMapper;
import com.insigma.mapper.OpenapiSelfmachineMapper;
import com.insigma.mapper.OpenapiSelfmachineRequestMapper;
import com.insigma.mapper.OpenapiSelfmachineTypeMapper;
import com.insigma.util.JSONUtil;
import constant.DataConstant;
import lombok.experimental.Accessors;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;


public class OpenapiSelfmachineServiceImpl implements OpenapiSelfmachineFacade {

    @Autowired
    OpenapiSelfmachineMapper openapiSelfmachineMapper;
    @Autowired
    OpenapiSelfmachineAddressMapper openapiSelfmachineAddressMapper;
    @Autowired
    OpenapiSelfmachineRequestMapper openapiSelfmachineRequestMapper;
    @Autowired
    OpenapiSelfmachineRequestFacade openapiSelfmachineRequestFacade;
    @Autowired
    SysUserFacade sysUserFacade;
    @Autowired
    SysOrgFacade sysOrgFacade;
    @Autowired
    OpenapiSelfmachineTypeMapper openapiSelfmachineTypeMapper;


    @Override
    public PageInfo<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(OpenapiSelfmachineListVO openapiSelfmachineListVO, Long userId) {
        if (openapiSelfmachineListVO==null||openapiSelfmachineListVO.getPageNum()==null||openapiSelfmachineListVO.getPageSize()==null) {
            return null;
        }
        String orgCode=null;
        if (userId!=null){
            List<SysUserOrgDTO> orgList=sysUserFacade.queryUserOrg(userId);
            if (orgList!=null&&!orgList.isEmpty()){
                orgCode=sysOrgFacade.getByPrimaryKey(orgList.get(0).getOrgId()).getOrgenterCode();
                openapiSelfmachineListVO.setOrgCode(orgCode);
            }
        }
        List<OpenapiSelfmachineShowVO> openapiSelfmachineRequestList=openapiSelfmachineMapper.getOpenapiSelfmachineList(openapiSelfmachineListVO);
        openapiSelfmachineRequestList.forEach(i->{
            if (i.getMachineTypeId()!=null){
                OpenapiSelfmachineType openapiSelfmachineType=openapiSelfmachineTypeMapper.selectByPrimaryKey(i.getMachineTypeId());
                i.setMachineType(openapiSelfmachineType!=null?openapiSelfmachineType.getName():"");
            }
        });
        PageInfo<OpenapiSelfmachineShowVO> openapiSelfmachineRequestPageInfo=new PageInfo<>(openapiSelfmachineRequestList);
        return openapiSelfmachineRequestPageInfo;
    }

    @Override
    public OpenapiSelfmachine getOpenapiSelfmachineDetail(OpenapiSelfmachineDetailVO openapiSelfmachineDetailVO) {
        if (openapiSelfmachineDetailVO==null) {
            return null;
        };
        OpenapiSelfmachine openapiSelfmachine=openapiSelfmachineMapper.selectOne(JSONUtil.convert(openapiSelfmachineDetailVO,OpenapiSelfmachine.class));
        return openapiSelfmachine;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OpenapiSelfmachine saveOpenapiSelfmachine(OpenapiSelfmachineSaveVO openapiSelfmachineSaveVO) {
        if (openapiSelfmachineSaveVO==null){
            return null;
        }
        OpenapiSelfmachine openapiSelfmachine= JSONUtil.convert(openapiSelfmachineSaveVO,OpenapiSelfmachine.class);
        if (openapiSelfmachine.getId()==null){
            openapiSelfmachineMapper.insertSelective(openapiSelfmachine);
        }else {
            Example example=new Example(OpenapiSelfmachine.class);
            example.createCriteria().andEqualTo("id",openapiSelfmachine.getId());
            openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
        }
        if (openapiSelfmachine.getMachineAddressId()!=null){
            Example example=new Example(OpenapiSelfmachineAddress.class);
            example.createCriteria().andEqualTo("userId",0);
            openapiSelfmachineAddressMapper.updateByExampleSelective(new OpenapiSelfmachineAddress().setIsLastUsed(DataConstant.NOT_LAST_USED),example);
            example.clear();
            example.createCriteria().andEqualTo("id",openapiSelfmachine.getMachineAddressId());
            openapiSelfmachineAddressMapper.updateByExampleSelective(new OpenapiSelfmachineAddress().setIsLastUsed(DataConstant.IS_LAST_USED),example);
        }
        return getOpenapiSelfmachineDetail(new OpenapiSelfmachineDetailVO().setId(openapiSelfmachine.getId()));
    }

    @Override
    public Integer deleteOpenapiSelfmachine(OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO) {
        if (openapiSelfmachineDeleteVO==null||openapiSelfmachineDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine();
        Example example=new Example(OpenapiSelfmachine.class);
        example.createCriteria().andEqualTo("id",openapiSelfmachineDeleteVO.getId());
        return openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveSelfMachine(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO, OpenapiOrg openapiOrg) {
        OpenapiSelfmachine openapiSelfmachine=JSONUtil.convert(openapiSelfmachineRequestSaveVO,OpenapiSelfmachine.class);
        openapiSelfmachine.setOrgName(openapiOrg.getOrgName());
        openapiSelfmachineMapper.insertSelective(openapiSelfmachine.setOrgId(openapiOrg.getId()));
        OpenapiSelfmachineRequest openapiSelfmachineRequest=JSONUtil.convert(openapiSelfmachineRequestSaveVO,OpenapiSelfmachineRequest.class);
        if (openapiSelfmachineRequest.getMachineCode()==null){
            openapiSelfmachineRequest.setMachineCode(openapiSelfmachineRequestFacade.getInitMachineCode(openapiSelfmachineRequest,openapiOrg));
        }
        openapiSelfmachineRequest.setIpSegment(openapiOrg.getIpSegment()).setOrgName(openapiOrg.getOrgName());
        openapiSelfmachineRequestMapper.insertSelective(openapiSelfmachineRequest.setOrgId(openapiOrg.getId()));
        return openapiSelfmachineRequest.getMachineCode();
    }

    @Override
    public Integer setStatu(OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO) {
        if (openapiSelfmachineDeleteVO==null|| openapiSelfmachineDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine=openapiSelfmachineMapper.selectByPrimaryKey(openapiSelfmachineDeleteVO.getId());
        if (openapiSelfmachine==null){
            return 0;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest=openapiSelfmachineRequestMapper.selectOne(new OpenapiSelfmachineRequest().setUniqueCode(openapiSelfmachine.getUniqueCode()));
        if (openapiSelfmachineRequest==null){
            return 0;
        }
        return openapiSelfmachineRequestFacade.deleteOpenapiSelfmachineRequest(new OpenapiSelfmachineRequestDeleteVO().setIds(Arrays.asList(openapiSelfmachineRequest.getId())).setStatu(openapiSelfmachineDeleteVO.getStatu()));
    }

    @Override
    public Integer setActiveStatus(OpenapiSelfmachineSetActiveStatusVO openapiSelfmachineSetActiveStatusVO) {
        if (openapiSelfmachineSetActiveStatusVO==null|| CollectionUtils.isEmpty(openapiSelfmachineSetActiveStatusVO.getIds())){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine().setActiveStatu(openapiSelfmachineSetActiveStatusVO.getActiveStatu()).setRemark(openapiSelfmachineSetActiveStatusVO.getRemark());
        Example example=new Example(OpenapiSelfmachine.class);
        example.createCriteria().andIn("id",openapiSelfmachineSetActiveStatusVO.getIds());
        return openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
    }

    @Override
    public Integer setOrg(OpenapiSelfmachineSetOrgVO openapiSelfmachineSetOrgVO) {
        if (openapiSelfmachineSetOrgVO==null|| CollectionUtils.isEmpty(openapiSelfmachineSetOrgVO.getIds())){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine().setOrgId(openapiSelfmachineSetOrgVO.getOrgId()).setRemark(openapiSelfmachineSetOrgVO.getRemark());
        Example example=new Example(OpenapiSelfmachine.class);
        example.createCriteria().andIn("id",openapiSelfmachineSetOrgVO.getIds());
        return openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
    }
}
