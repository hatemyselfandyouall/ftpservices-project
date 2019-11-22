package com.insigma.impl;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.OpenapiMonitoringDataConfigDto;
import com.insigma.facade.openapi.facade.OpenapiMonitoringDataConfigFacade;
import com.insigma.facade.openapi.po.OpenapiEarlyWarning;
import com.insigma.facade.openapi.po.OpenapiMonitoringDataConfig;
import com.insigma.mapper.OpenapiMonitoringDataConfigMapper;
import com.insigma.util.JSONUtil;
import constant.DataConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
public class OpenapiMonitoringDataConfigServiceImpl implements OpenapiMonitoringDataConfigFacade {

    @Autowired
    private OpenapiMonitoringDataConfigMapper openapiMonitoringDataConfigMapper;

    @Override
    public PageInfo<OpenapiMonitoringDataConfigDto> getOpenapiMonitoringDataConfig(String name, Integer offset, Integer limit) {
        PageInfo<OpenapiMonitoringDataConfigDto> OpenapiMonitoringDataConfigPageInfo=new PageInfo<>();
        if (offset==null||limit==null) {
            return OpenapiMonitoringDataConfigPageInfo;
        }
        int count=0;
        List<OpenapiMonitoringDataConfigDto> openapiMonitoringDataConfigDtos  = openapiMonitoringDataConfigMapper.getOpenapiMonitoringDataConfigCount(name);
        if(openapiMonitoringDataConfigDtos!=null){
            count = openapiMonitoringDataConfigDtos.size();
        }
        List<OpenapiMonitoringDataConfigDto> openapiMonitoringDataConfigDtoList=openapiMonitoringDataConfigMapper.getOpenapiMonitoringDataConfigList(name,offset-1,limit);
        OpenapiMonitoringDataConfigPageInfo.setTotal(count);
        OpenapiMonitoringDataConfigPageInfo.setList(openapiMonitoringDataConfigDtoList);
        return OpenapiMonitoringDataConfigPageInfo;
    }

    @Override
    public OpenapiMonitoringDataConfigDto getOpenapiMonitoringDataConfigDetail(Long id) {
        if (id==null) {
            return null;
        }
        return  openapiMonitoringDataConfigMapper.getOpenapiMonitoringDataConfigDetail(id);
    }

    @Override
    public OpenapiMonitoringDataConfigDto getOpenapiMonitoringDataConfigInfo(Long interfaceId) {
        return openapiMonitoringDataConfigMapper.getOpenapiMonitoringDataConfigInfo(interfaceId);
    }

    @Override
    public Integer saveOpenapiMonitoringDataConfig(OpenapiMonitoringDataConfigDto openapiMonitoringDataConfigDto) {
        if (openapiMonitoringDataConfigDto==null){
            return 0;
        }
        OpenapiMonitoringDataConfig openapiMonitoringDataConfig= JSONUtil.convert(openapiMonitoringDataConfigDto,OpenapiMonitoringDataConfig.class);
        if (openapiMonitoringDataConfig.getId()==null){
            return openapiMonitoringDataConfigMapper.insertSelective(openapiMonitoringDataConfig);
        }else {
            openapiMonitoringDataConfig.setModifyTime(new Date());
            Example example=new Example(OpenapiMonitoringDataConfig.class);
            example.createCriteria().andEqualTo("id",openapiMonitoringDataConfig.getId());
            return openapiMonitoringDataConfigMapper.updateByExampleSelective(openapiMonitoringDataConfig,example);
        }
    }

    @Override
    public Integer deleteOpenapiMonitoringDataConfig(Long id) {
        if (id==null){
            return 0;
        }
        OpenapiMonitoringDataConfig openapiMonitoringDataConfig=new OpenapiMonitoringDataConfig();
        openapiMonitoringDataConfig.setModifyTime(new Date());
        openapiMonitoringDataConfig.setIsDelete(1);
        Example example=new Example(OpenapiEarlyWarning.class);
        example.createCriteria().andEqualTo("id",id);
        return openapiMonitoringDataConfigMapper.updateByExampleSelective(openapiMonitoringDataConfig,example);
    }

    @Override
    public List<OpenapiMonitoringDataConfig> getConfigList() {
        return openapiMonitoringDataConfigMapper.select(new OpenapiMonitoringDataConfig().setIsDelete(DataConstant.NO_DELETE));
    }
}
