package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.ApplicationServiceDto;
import com.insigma.facade.openapi.dto.OpenapiBlackWhiteDto;
import com.insigma.facade.openapi.facade.OpenapiBlackWhiteFacade;
import com.insigma.facade.openapi.po.OpenapiBlackWhite;
import com.insigma.mapper.OpenapiBlackWhiteMapper;
import com.insigma.util.JSONUtil;
import constant.DataConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class OpenapiBlackWhiteServiceImpl implements OpenapiBlackWhiteFacade {

    @Autowired
    private OpenapiBlackWhiteMapper openapiBlackWhiteMapper;

    @Override
    public PageInfo<OpenapiBlackWhite> getOpenapiBlackWhiteList(String name, Integer addType, Date startDate, Date endDate,Integer offset, Integer limit) {
        if (offset==null||limit==null) {
            return null;
        }
        PageHelper.startPage(offset,limit);
        Example example=new Example(OpenapiBlackWhite.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        if(StringUtils.isNotEmpty(name)) {
            criteria.andLike("ipAddress","%"+name+"%");
            criteria.orLike("applicationName","%"+name+"%");
        }
        if(addType!=null){
            criteria.andEqualTo("addType",addType);
        }
        if(startDate!=null&&endDate!=null){
            criteria.andBetween("modifyTime",startDate,endDate);
        }
        List<OpenapiBlackWhite> openapiBlackWhiteList=openapiBlackWhiteMapper.selectByExample(example);
        PageInfo<OpenapiBlackWhite> openapiBlackWhitePageInfo=new PageInfo<>(openapiBlackWhiteList);
        return openapiBlackWhitePageInfo;
    }

    @Override
    public OpenapiBlackWhite getOpenapiBlackWhiteDetail(Long id) {
        if (id==null) {
            return null;
        };
        OpenapiBlackWhite openapiBlackWhite=openapiBlackWhiteMapper.selectByPrimaryKey(id);
        return openapiBlackWhite;
    }

    @Override
    public String saveOpenapiBlackWhite(OpenapiBlackWhiteDto openapiBlackWhiteDto) {
        Date date = new Date();
        StringBuilder sbu = new StringBuilder();
        List<ApplicationServiceDto> applicationServiceDtos = openapiBlackWhiteDto.getApplicationServiceDtos();
        if(applicationServiceDtos!=null&&applicationServiceDtos.size()>0) {
            for (ApplicationServiceDto applicationServiceDto : applicationServiceDtos) {
                OpenapiBlackWhite openapiBlackWhite= JSONUtil.convert(openapiBlackWhiteDto,OpenapiBlackWhite.class);
                openapiBlackWhite.setApplicationId(applicationServiceDto.getApplicationId());
                openapiBlackWhite.setApplicationName(applicationServiceDto.getApplicationName());
                openapiBlackWhite.setCreateTime(date);
                openapiBlackWhite.setModifyTime(date);
                openapiBlackWhiteMapper.insertSelective(openapiBlackWhite);
            }
        }else{
            List<String> ipAddressList = openapiBlackWhiteDto.getIpAddress();
            if(ipAddressList!=null&&ipAddressList.size()>0) {
                for(String ipAddress:ipAddressList) {
                    OpenapiBlackWhite openapiBlackWhite = JSONUtil.convert(openapiBlackWhiteDto, OpenapiBlackWhite.class);
                    Integer addType = openapiBlackWhiteDto.getAddType();
                    openapiBlackWhite.setIpAddress(ipAddress);
                    openapiBlackWhite.setCreateTime(date);
                    openapiBlackWhite.setModifyTime(date);
                    //如果当前ip不同类型的名单已存在，必须作废当前条，添加新的。
                    OpenapiBlackWhite openapiBlackWhiteUpd=new OpenapiBlackWhite();
                    openapiBlackWhiteUpd.setModifyTime(date);
                    openapiBlackWhiteUpd.setIsDelete(1);
                    openapiBlackWhiteUpd.setModifyBy(openapiBlackWhiteDto.getModifyBy());
                    Example exampleQuery=new Example(OpenapiBlackWhite.class);
                    Example.Criteria criteriaQuery=exampleQuery.createCriteria();
                    criteriaQuery.andEqualTo("ipAddress",ipAddress);
                    criteriaQuery.andEqualTo("addType",addType);
                    criteriaQuery.andEqualTo("isDelete",0);
                    List<OpenapiBlackWhite> openapiBlackWhiteList = openapiBlackWhiteMapper.selectByExample(exampleQuery);
                    if(openapiBlackWhiteList!=null&&openapiBlackWhiteList.size()>0){
                        sbu.append(ipAddress);
                        sbu.append(",");
                    }else {
                        Example example=new Example(OpenapiBlackWhite.class);
                        Example.Criteria criteria=example.createCriteria();
                        criteria.andEqualTo("ipAddress",ipAddress);
                        criteria.andEqualTo("isDelete",0);
                        if (addType == 1) {
                            criteria.andEqualTo("addType", 2);
                        } else {
                            criteria.andEqualTo("addType", 1);
                        }
                        openapiBlackWhiteMapper.updateByExampleSelective(openapiBlackWhiteUpd, example);
                        openapiBlackWhiteMapper.insertSelective(openapiBlackWhite);
                    }
                }
            }
        }
        String ipAddressStr = sbu.toString();
        if(ipAddressStr.indexOf(",")>=0){
            ipAddressStr = ipAddressStr.substring(0,ipAddressStr.length()-1);
        }
        return ipAddressStr;
    }

    @Override
    public void updateOpenapiBlackWhite(OpenapiBlackWhite openapiBlackWhite) {
        openapiBlackWhite.setModifyTime(new Date());
        Example example=new Example(OpenapiBlackWhite.class);
        example.createCriteria().andEqualTo("id",openapiBlackWhite.getId());
        openapiBlackWhiteMapper.updateByExampleSelective(openapiBlackWhite,example);
    }

    @Override
    public List<String> verificationOpenapiBlackWhite(OpenapiBlackWhiteDto openapiBlackWhiteDto) {
        List<String> ipAddressList = openapiBlackWhiteDto.getIpAddress();
        List<String> ipAddressListRepeat = new ArrayList<>();
        for(String ipAddress:ipAddressList){
            Example example=new Example(OpenapiBlackWhite.class);
            Example.Criteria criteria=example.createCriteria();
            criteria.andEqualTo("ipAddress",ipAddress);
            criteria.andEqualTo("addType",openapiBlackWhiteDto.getAddType());
            criteria.andEqualTo("isDelete",0);
            List<OpenapiBlackWhite> openapiBlackWhiteList= openapiBlackWhiteMapper.selectByExample(example);
            if(openapiBlackWhiteList!=null&&openapiBlackWhiteList.size()>0){
                ipAddressListRepeat.add(ipAddress);
            }
        }
        return ipAddressListRepeat;
    }

    @Override
    public Integer deleteOpenapiBlackWhite(Long id) {
        if (id==null){
            return 0;
        }
        OpenapiBlackWhite openapiBlackWhite=new OpenapiBlackWhite();
        openapiBlackWhite.setModifyTime(new Date());
        openapiBlackWhite.setIsDelete(1);
        Example example=new Example(OpenapiBlackWhite.class);
        example.createCriteria().andEqualTo("id",id);
        return openapiBlackWhiteMapper.updateByExampleSelective(openapiBlackWhite,example);
    }

    @Override
    public List<OpenapiBlackWhite> getBlackWhiteList() {
        return openapiBlackWhiteMapper.select(new OpenapiBlackWhite().setIsDelete(DataConstant.NO_DELETE).setAddType(1));
    }

}
