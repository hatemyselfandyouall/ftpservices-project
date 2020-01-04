package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.GetSelfMachineDTO;
import com.insigma.facade.openapi.dto.SelfMachineOrgDTO;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.po.OpenapiSelfmachine;
import com.insigma.facade.openapi.po.OpenapiSelfmachineRequest;
import com.insigma.facade.openapi.po.SelfMachineEnum;
import com.insigma.facade.openapi.vo.OpenapiApp.ResetAppSecretVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.*;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.insigma.facade.sysbase.SysOrgFacade;
import com.insigma.facade.sysbase.SysUserFacade;
import com.insigma.facade.sysbase.vo.SysUserOrgDTO;
import com.insigma.mapper.OpenapiOrgMapper;
import com.insigma.mapper.OpenapiSelfmachineMapper;
import com.insigma.mapper.OpenapiSelfmachineRequestMapper;
import com.insigma.util.*;
import com.taobao.diamond.extend.DynamicProperties;
import constant.DataConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import com.insigma.facade.openapi.facade.OpenapiOrgFacade;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class OpenapiOrgServiceImpl implements OpenapiOrgFacade {

    @Autowired
    OpenapiOrgMapper OpenapiOrgMapper;
    @Autowired
    OpenapiSelfmachineMapper openapiSelfmachineMapper;
    @Autowired
    OpenapiSelfmachineRequestMapper openapiSelfmachineRequestMapper;
    @Autowired
    SysUserFacade sysUserFacade;
    @Autowired
    SysOrgFacade sysOrgFacade;
    public static final String URL= DynamicProperties.staticProperties.getProperty("oss.download.http.txt.url");
    @Override
    public PageInfo<OpenapiOrgShowVO> getOpenapiOrgList(OpenapiOrgListVO OpenapiOrgListVO, Long userId) {
        if (OpenapiOrgListVO==null||OpenapiOrgListVO.getPageNum()==null||OpenapiOrgListVO.getPageSize()==null) {
            return null;
        }
        OpenapiOrg exampleObeject=new OpenapiOrg().setAreaId(OpenapiOrgListVO.getAreaId()).setOrgCode(OpenapiOrgListVO.getOrgCode()).setIsDelete(DataConstant.NO_DELETE);
        String orgCode=null;
        if (userId!=null){
            List<SysUserOrgDTO> orgList=sysUserFacade.queryUserOrg(userId);
            if (orgList!=null&&!orgList.isEmpty()){
                orgCode=sysOrgFacade.getByPrimaryKey(orgList.get(0).getOrgId()).getOrgenterCode();
            }
            exampleObeject.setOrgCode(orgCode);
        }
        PageHelper.startPage(OpenapiOrgListVO.getPageNum().intValue(),OpenapiOrgListVO.getPageSize().intValue());
        List<OpenapiOrg> openapiOrgList=OpenapiOrgMapper.select(exampleObeject);
        List<OpenapiOrgShowVO> openapiOrgShowVOS=openapiOrgList.stream().map(i->{
            OpenapiOrgShowVO openapiOrgShowVO=JSONUtil.convert(i,OpenapiOrgShowVO.class);
            openapiOrgShowVO.setCertificateKey(URL+i.getCertificateKey());
            openapiOrgShowVO.setMachineCount(openapiSelfmachineRequestMapper.selectCount(new OpenapiSelfmachineRequest().setOrgId(i.getId()).setStatu(SelfMachineEnum.WHITE)));
            return openapiOrgShowVO;
        }).collect(Collectors.toList());
        PageInfo<OpenapiOrgShowVO> OpenapiOrgPageInfo=new PageInfo<>(openapiOrgShowVOS);
        return OpenapiOrgPageInfo;
    }

    @Override
    public OpenapiOrg getOpenapiOrgDetail(OpenapiOrgDetailVO OpenapiOrgDetailVO) {
        if (OpenapiOrgDetailVO==null||OpenapiOrgDetailVO.getId()==null) {
            return null;
        };
        OpenapiOrg OpenapiOrg=OpenapiOrgMapper.selectByPrimaryKey(OpenapiOrgDetailVO.getId());
        return OpenapiOrg;
    }

    @Override
    public Integer saveOpenapiOrg(OpenapiOrgSaveVO OpenapiOrgSaveVO, Long userId, String userName)throws Exception {
        if (OpenapiOrgSaveVO==null){
            return 0;
        }

        OpenapiOrg openapiOrg= JSONUtil.convert(OpenapiOrgSaveVO,OpenapiOrg.class);
        if (openapiOrg.getId()==null){
            String appKey= UUID.randomUUID().toString().replaceAll("-","");
            String appSecret= UUID.randomUUID().toString().replaceAll("-","");
            String certificate= MD5Util.MD5Encode(appKey+openapiOrg.getOrgCode()+openapiOrg.getCreateTime(),"UTF-8");
            String key= HttpUtil.upload(certificate);
            openapiOrg.setCreatorId(userId);
            openapiOrg.setModifyId(userId);
            openapiOrg.setCreatorName(userName);
            openapiOrg.setModifyName(userName);
            openapiOrg.setCertificate(certificate);
            openapiOrg.setCertificateKey(key);
            openapiOrg.setAppKey(appKey);
            openapiOrg.setAppSecret(appSecret);
            openapiOrg.setSortNumber(OpenapiOrgMapper.selectCount(new OpenapiOrg().setOrgCode(openapiOrg.getOrgCode()))+1);
            openapiOrg.setCertificateCode(openapiOrg.getOrgCode()+ DateUtils.getStringCurrentDate()+ NumbersUtil.getSortNumber(openapiOrg.getSortNumber(),2));
//            openapiOrg.setCertCodeNumber(getCertCodeNumber())
            return OpenapiOrgMapper.insertSelective(openapiOrg);
        }else {
            openapiOrg.setModifyId(userId);
            openapiOrg.setModifyName(userName);
            openapiOrg.setModifyTime(new Date());
            Example example=new Example(OpenapiOrg.class);
            example.createCriteria().andEqualTo("id",openapiOrg.getId());
            return OpenapiOrgMapper.updateByExampleSelective(openapiOrg,example);
        }
    }

    @Override
    public Integer deleteOpenapiOrg(OpenapiOrgDeleteVO OpenapiOrgDeleteVO) {
        if (OpenapiOrgDeleteVO==null||OpenapiOrgDeleteVO.getId()==null){
            return 0;
        }
        OpenapiOrg OpenapiOrg=new OpenapiOrg();
        OpenapiOrg.setModifyTime(new Date());
        OpenapiOrg.setIsDelete(OpenapiOrgDeleteVO.getIsDelete());
        Example example=new Example(OpenapiOrg.class);
        example.createCriteria().andEqualTo("id",OpenapiOrgDeleteVO.getId());
        return OpenapiOrgMapper.updateByExampleSelective(OpenapiOrg,example);
    }

    @Override
    public Integer getSelfMachineCountByOrgCode(String orgCode) {
        List<OpenapiOrg> openapiOrgs= OpenapiOrgMapper.select(new OpenapiOrg().setOrgCode(orgCode).setIsDelete(DataConstant.NO_DELETE));
        List<Long> ids=openapiOrgs.stream().map(OpenapiOrg::getId).collect(Collectors.toList());
        if (ids==null||ids.isEmpty()){
            return 0;
        }else {
            Example example=new Example(OpenapiSelfmachine.class);
            example.createCriteria().andIn("orgId",ids).andEqualTo("idDelete",DataConstant.NO_DELETE);
            return openapiSelfmachineMapper.selectCountByExample(example);
        }
    }

    @Override
    public OpenapiOrg getOrgByMachineCode(String machineCode) {
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequest=openapiSelfmachineRequestMapper.select(new OpenapiSelfmachineRequest().setMachineCode(machineCode));
        if (openapiSelfmachineRequest==null||openapiSelfmachineRequest.isEmpty()) {
            return null;
        }
        Long id=openapiSelfmachineRequest.get(0).getOrgId();
        OpenapiOrg openapiOrg=OpenapiOrgMapper.selectByPrimaryKey(id);
        return openapiOrg;
    }

    @Override
    public OpenapiOrg resetAppSecret(ResetAppSecretVO resetAppSecretVO) {
        if (resetAppSecretVO==null||resetAppSecretVO.getId()==null){
            return null;
        }
        OpenapiOrg openapiOrg=new OpenapiOrg();
        openapiOrg.setModifyTime(new Date());
        openapiOrg.setAppSecret(UUID.randomUUID().toString().replaceAll("-",""));
        Example example=new Example(OpenapiOrg.class);
        example.createCriteria().andEqualTo("id",resetAppSecretVO.getId());
        OpenapiOrgMapper.updateByExampleSelective(openapiOrg,example);
        return OpenapiOrgMapper.selectByPrimaryKey(resetAppSecretVO.getId());
    }

    @Override
    public PageInfo<SelfMachineOrgDTO> getSelfMachine(GetSelfMachineDTO getSelfMachineDTO) {
        if (getSelfMachineDTO==null||getSelfMachineDTO.getPageNum()==null||getSelfMachineDTO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(getSelfMachineDTO.getPageNum().intValue(),getSelfMachineDTO.getPageSize().intValue());
        List<SelfMachineOrgDTO> selfMachineOrgDTOS=OpenapiOrgMapper.getSelfMachine(getSelfMachineDTO.getName());
        selfMachineOrgDTOS.forEach(i->{
          String orgCode=i.getOrgCode();
          List<Long> ids=OpenapiOrgMapper.select(new OpenapiOrg().setOrgCode(orgCode).setIsDelete(DataConstant.NO_DELETE)).stream().map(OpenapiOrg::getId).collect(Collectors.toList());
          Integer count;
          if (CollectionUtils.isEmpty(ids)){
              count=0;
          }else {
              Example example=new Example(OpenapiSelfmachineRequest.class);
              example.createCriteria().andIn("orgId", ids).andEqualTo("statu", SelfMachineEnum.WHITE);
              count=openapiSelfmachineRequestMapper.selectCountByExample(example);
          }
          i.setCount(count);
        });
        PageInfo<SelfMachineOrgDTO> OpenapiOrgPageInfo=new PageInfo<>(selfMachineOrgDTOS);
        return OpenapiOrgPageInfo;
    }

    @Override
    public OpenapiOrg checkCertificate(String certificate, OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO) {
        OpenapiOrg openapiOrg=OpenapiOrgMapper.selectOne(new OpenapiOrg().setCertificate(certificate).setIsDelete(DataConstant.NO_DELETE));
        return openapiOrg;
    }
}
