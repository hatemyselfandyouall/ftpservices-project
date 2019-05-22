package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.constant.DataConstant;
import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.po.OpenapiInterfaceRequestParam;
import com.insigma.facade.openapi.po.OpenapiInterfaceResponseParam;
import com.insigma.facade.openapi.vo.*;
import com.insigma.facade.openapi.vo.OpenapiInterfaceRequestParam.OpenapiInterfaceRequestParamSaveVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceResponseParam.OpenapiInterfaceResponseParamSaveVO;
import com.insigma.mapper.OpenapiInterfaceMapper;
import com.insigma.mapper.OpenapiInterfaceRequestParamMapper;
import com.insigma.mapper.OpenapiInterfaceResponseParamMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class InterfaceServiceImpl implements InterfaceFacade {

    @Autowired
    OpenapiInterfaceMapper openapiInterfaceMapper;
    @Autowired
    OpenapiInterfaceResponseParamMapper openapiInterfaceResponseParamMapper;
    @Autowired
    OpenapiInterfaceRequestParamMapper openapiInterfaceRequestParamMapper;

    @Override
    public PageInfo<OpenapiInterface> getOpenapiInterfaceList(OpenapiInterfaceListVO openapiInterfaceListVO) {
        if (openapiInterfaceListVO==null||openapiInterfaceListVO.getPageNum()==null||openapiInterfaceListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiInterfaceListVO.getPageNum().intValue(),openapiInterfaceListVO.getPageSize().intValue());
        OpenapiInterface exampleObeject=new OpenapiInterface();
        List<OpenapiInterface> openapiInterfaceList=openapiInterfaceMapper.select(exampleObeject);
        PageInfo<OpenapiInterface> openapiInterfacePageInfo=new PageInfo<>(openapiInterfaceList);
        return openapiInterfacePageInfo;
    }

    @Override
    public OpenapiInterfaceShowVO getOpenapiInterfaceDetail(OpenapiInterfaceDetailVO openapiInterfaceDetailVO) {
        if (openapiInterfaceDetailVO==null||openapiInterfaceDetailVO.getId()==null) {
            return null;
        };
        OpenapiInterface openapiInterface=openapiInterfaceMapper.selectByPrimaryKey(openapiInterfaceDetailVO.getId());
        if (openapiInterface==null){
            return null;
        }
        OpenapiInterfaceShowVO openapiInterfaceShowVO=new OpenapiInterfaceShowVO();
        openapiInterfaceShowVO.setOpenapiInterface(openapiInterface);
        Example example=new Example(OpenapiInterfaceResponseParam.class);
        example.createCriteria().andEqualTo("interfaceId",openapiInterface.getId())
        .andEqualTo("isDelete",DataConstant.NO_DELETE);
        List<OpenapiInterfaceResponseParam> openapiInterfaceResponseParams=openapiInterfaceResponseParamMapper.selectByExample(example);
        example=new Example(OpenapiInterfaceRequestParam.class);
        example.createCriteria().andEqualTo("interfaceId",openapiInterface.getId())
                .andEqualTo("isDelete",DataConstant.NO_DELETE);
        List<OpenapiInterfaceRequestParam> openapiInterfaceRequestParams=openapiInterfaceRequestParamMapper.selectByExample(example);
        openapiInterfaceShowVO.setOpenapiInterfaceRequestParams(openapiInterfaceRequestParams);
        openapiInterfaceShowVO.setOpenapiInterfaceResponseParams(openapiInterfaceResponseParams);
        return openapiInterfaceShowVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveOpenapiInterface(OpenapiInterfaceSaveVO openapiInterfaceSaveVO) {
        if (openapiInterfaceSaveVO==null){
            return 0;
        }
        OpenapiInterface openapiInterface= JSONUtil.convert(openapiInterfaceSaveVO,OpenapiInterface.class);
        if (openapiInterface.getId()==null){
            openapiInterface.setVersionNumber(1);
            openapiInterfaceMapper.insertSelective(openapiInterface);
            saveRequestParams(openapiInterface.getId(),openapiInterfaceSaveVO.getOpenapiInterfaceRequestParamSaveVOList());
            saveResponseParams(openapiInterface.getId(),openapiInterfaceSaveVO.getOpenapiInterfaceResponseParamSaveVOList());
            return 1;
        }else {
            updateInterface(openapiInterface);
            deleteRequestParams(openapiInterface.getId());
            deleteResponseParams(openapiInterface.getId());
            saveRequestParams(openapiInterface.getId(),openapiInterfaceSaveVO.getOpenapiInterfaceRequestParamSaveVOList());
            saveResponseParams(openapiInterface.getId(),openapiInterfaceSaveVO.getOpenapiInterfaceResponseParamSaveVOList());
            return 1;
        }
    }

    private  void updateInterface(OpenapiInterface openapiInterface) {
        openapiInterface.setModifyTime(new Date());
        Example example=new Example(OpenapiInterface.class);
        example.createCriteria().andEqualTo("id",openapiInterface.getId());
        openapiInterfaceMapper.updateByExampleSelective(openapiInterface,example);
    }

    private void deleteResponseParams(Long id) {
        Example example=new Example(OpenapiInterfaceResponseParam.class);
        example.createCriteria().andEqualTo("interfaceId",id);
        OpenapiInterfaceResponseParam openapiInterfaceResponseParam=new OpenapiInterfaceResponseParam();
        openapiInterfaceResponseParam.setIsDelete(DataConstant.IS_DELETE);
        openapiInterfaceResponseParamMapper.updateByExampleSelective(openapiInterfaceResponseParam,example);
    }

    private void deleteRequestParams(Long id) {
        Example example=new Example(OpenapiInterfaceRequestParam.class);
        example.createCriteria().andEqualTo("interfaceId",id);
        OpenapiInterfaceRequestParam openapiInterfaceRequestParam=new OpenapiInterfaceRequestParam();
        openapiInterfaceRequestParam.setIsDelete(DataConstant.IS_DELETE);
        openapiInterfaceRequestParamMapper.updateByExampleSelective(openapiInterfaceRequestParam,example);
    }

    private void saveRequestParams(Long interfaceId,List<OpenapiInterfaceRequestParamSaveVO> openapiInterfaceRequestParamSaveVOList) {
        List<OpenapiInterfaceRequestParam> openapiInterfaceRequestParams=openapiInterfaceRequestParamSaveVOList.stream().map(i->{
            OpenapiInterfaceRequestParam openapiInterfaceRequestParam=JSONUtil.convert(i,OpenapiInterfaceRequestParam.class);
            openapiInterfaceRequestParam.setInterfaceId(interfaceId);
            openapiInterfaceRequestParam.setIsDelete(DataConstant.NO_DELETE);
            openapiInterfaceRequestParam.setCreateTime(new Date());
            openapiInterfaceRequestParam.setModifyTime(new Date());
            return openapiInterfaceRequestParam;
        }).collect(Collectors.toList());
        openapiInterfaceRequestParamMapper.insertList(openapiInterfaceRequestParams);
    }

    private void saveResponseParams(Long interfaceId,List<OpenapiInterfaceResponseParamSaveVO> openapiInterfaceResponseParamSaveVOList) {
        List<OpenapiInterfaceResponseParam> openapiInterfaceResponseParams=openapiInterfaceResponseParamSaveVOList.stream().map(i->{
            OpenapiInterfaceResponseParam openapiInterfaceResponseParam=JSONUtil.convert(i,OpenapiInterfaceResponseParam.class);
            openapiInterfaceResponseParam.setInterfaceId(interfaceId);
            openapiInterfaceResponseParam.setIsDelete(DataConstant.NO_DELETE);
            openapiInterfaceResponseParam.setCreateTime(new Date());
            openapiInterfaceResponseParam.setModifyTime(new Date());
            return openapiInterfaceResponseParam;
        }).collect(Collectors.toList());
        openapiInterfaceResponseParamMapper.insertList(openapiInterfaceResponseParams);
    }

    @Override
    public Integer deleteOpenapiInterface(OpenapiInterfaceDeleteVO openapiInterfaceDeleteVO) {
        if (openapiInterfaceDeleteVO==null||openapiInterfaceDeleteVO.getId()==null){
            return 0;
        }
        OpenapiInterface openapiInterface=new OpenapiInterface();
        openapiInterface.setModifyTime(new Date());
        openapiInterface.setIsDelete(openapiInterfaceDeleteVO.getIsDelete());
        Example example=new Example(OpenapiInterface.class);
        example.createCriteria().andEqualTo("id",openapiInterfaceDeleteVO.getId());
        return openapiInterfaceMapper.updateByExampleSelective(openapiInterface,example);
    }

    @Override
    public synchronized Integer updateInterfaceVersion(Long id) {
        if (id==null){
            return null;
        }
        OpenapiInterface openapiInterface=openapiInterfaceMapper.selectByPrimaryKey(id);
        Integer versionNumber=openapiInterface.getVersionNumber();
        versionNumber++;
        openapiInterface.setVersionNumber(versionNumber);
        return openapiInterfaceMapper.updateByPrimaryKeySelective(openapiInterface);
    }
}
