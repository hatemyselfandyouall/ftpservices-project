package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.GetSelfMachineDTO;
import com.insigma.facade.openapi.dto.SelfMachineOrgDTO;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.vo.OpenapiApp.ResetAppSecretVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgListVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgSaveVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.insigma.facade.openapi.vo.root.PageVO;
import com.insigma.mapper.OpenapiOrgMapper;
import com.insigma.util.HttpUtil;
import com.insigma.util.JSONUtil;
import com.insigma.util.MD5Util;
import com.taobao.diamond.extend.DynamicProperties;
import constant.DataConstant;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import com.insigma.facade.openapi.facade.OpenapiOrgFacade;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class OpenapiOrgServiceImpl implements OpenapiOrgFacade {

    @Autowired
    OpenapiOrgMapper OpenapiOrgMapper;

    public static final String URL= DynamicProperties.staticProperties.getProperty("oss.download.http.txt.url");
    @Override
    public PageInfo<OpenapiOrg> getOpenapiOrgList(OpenapiOrgListVO OpenapiOrgListVO) {
        if (OpenapiOrgListVO==null||OpenapiOrgListVO.getPageNum()==null||OpenapiOrgListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(OpenapiOrgListVO.getPageNum().intValue(),OpenapiOrgListVO.getPageSize().intValue());
        OpenapiOrg exampleObeject=new OpenapiOrg().setOrgId(OpenapiOrgListVO.getOrgId()).setAreaId(OpenapiOrgListVO.getAreaId()).setIsDelete(DataConstant.NO_DELETE);
        List<OpenapiOrg> openapiOrgList=OpenapiOrgMapper.select(exampleObeject);
        openapiOrgList.forEach(i->{
            i.setCertificateKey(URL+i.getCertificateKey());
        });
        PageInfo<OpenapiOrg> OpenapiOrgPageInfo=new PageInfo<>(openapiOrgList);
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
            String certificate= MD5Util.MD5Encode(appKey+openapiOrg.getOrgId()+openapiOrg.getCreateTime(),"UTF-8");
            String key= HttpUtil.upload(certificate);
            openapiOrg.setCreatorId(userId);
            openapiOrg.setModifyId(userId);
            openapiOrg.setCreatorName(userName);
            openapiOrg.setModifyName(userName);
            openapiOrg.setCertificate(certificate);
            openapiOrg.setCertificateKey(key);
            openapiOrg.setAppKey(key);
            openapiOrg.setAppKey(appKey);
            openapiOrg.setAppSecret(appSecret);
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
        PageInfo<SelfMachineOrgDTO> OpenapiOrgPageInfo=new PageInfo<>(selfMachineOrgDTOS);
        return OpenapiOrgPageInfo;
    }

    @Override
    public OpenapiOrg checkCertificate(String certificate, OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO) {
        OpenapiOrg openapiOrg=OpenapiOrgMapper.selectOne(new OpenapiOrg().setCertificate(certificate).setIsDelete(DataConstant.NO_DELETE));
        return openapiOrg;
    }
}
