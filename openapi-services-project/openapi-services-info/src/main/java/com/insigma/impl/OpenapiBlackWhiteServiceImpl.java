package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.ApplicationServiceDto;
import com.insigma.facade.openapi.dto.OpenapiBlackWhiteDto;
import com.insigma.facade.openapi.facade.OpenapiBlackWhiteFacade;
import com.insigma.facade.openapi.po.OpenapiBlackWhite;
import com.insigma.mapper.OpenapiBlackWhiteMapper;
import com.insigma.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

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
            criteria.andBetween("createTime",startDate,endDate);
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
    public void saveOpenapiBlackWhite(OpenapiBlackWhiteDto openapiBlackWhiteDto) {
        List<ApplicationServiceDto> applicationServiceDtos = openapiBlackWhiteDto.getApplicationServiceDtos();
        if(applicationServiceDtos!=null&&applicationServiceDtos.size()>0) {
            for (ApplicationServiceDto applicationServiceDto : applicationServiceDtos) {
                OpenapiBlackWhite openapiBlackWhite= JSONUtil.convert(openapiBlackWhiteDto,OpenapiBlackWhite.class);
                openapiBlackWhite.setApplicationId(applicationServiceDto.getApplicationId());
                openapiBlackWhite.setApplicationName(applicationServiceDto.getApplicationName());
                openapiBlackWhiteMapper.insertSelective(openapiBlackWhite);
            }
        }else{
            List<String> ipAddressList = openapiBlackWhiteDto.getIpAddress();
            if(ipAddressList!=null&&ipAddressList.size()>0) {
                for(String ipAddress:ipAddressList) {
                    OpenapiBlackWhite openapiBlackWhite = JSONUtil.convert(openapiBlackWhiteDto, OpenapiBlackWhite.class);
                    openapiBlackWhite.setIpAddress(ipAddress);
                    openapiBlackWhiteMapper.insertSelective(openapiBlackWhite);
                }
            }
        }
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
}
