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
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        exampleObeject.setGroupId(openapiInterfaceListVO.getGroupId());
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
    public OpenapiInterfaceShowVO saveOpenapiInterface(OpenapiInterfaceSaveVO openapiInterfaceSaveVO) {
        if (openapiInterfaceSaveVO==null||openapiInterfaceSaveVO.getGroupId()==null){
            return null;
        }
        OpenapiInterface openapiInterface= JSONUtil.convert(openapiInterfaceSaveVO,OpenapiInterface.class);
        openapiInterface.setVersionNumber(getMaxVersion(openapiInterfaceSaveVO.getGroupId()));
        if (openapiInterface.getId()==null){
            openapiInterfaceMapper.insertSelective(openapiInterface);
            return getInterfaceDetail(openapiInterfaceSaveVO, openapiInterface);
        }else {
            updateInterface(openapiInterface);
            deleteRequestParams(openapiInterface.getId());
            deleteResponseParams(openapiInterface.getId());
            return getInterfaceDetail(openapiInterfaceSaveVO, openapiInterface);
        }
    }

    private Integer getMaxVersion(Integer groupId){
        Example example=new Example(OpenapiInterface.class);
        example.createCriteria().andEqualTo("groupId",groupId);
        example.setOrderByClause("version_number desc");
        List<OpenapiInterface> openapiInterfaces=openapiInterfaceMapper.selectByExample(example);
        if (openapiInterfaces!=null&&!openapiInterfaces.isEmpty()){
            return openapiInterfaces.get(0).getVersionNumber()+1;
        }else {
            return 1;
        }
    }

    private OpenapiInterfaceShowVO getInterfaceDetail(OpenapiInterfaceSaveVO openapiInterfaceSaveVO, OpenapiInterface openapiInterface) {
        saveRequestParams(openapiInterface.getId(),openapiInterfaceSaveVO.getOpenapiInterfaceRequestParamSaveVOList());
        saveResponseParams(openapiInterface.getId(),openapiInterfaceSaveVO.getOpenapiInterfaceResponseParamSaveVOList());
        OpenapiInterfaceDetailVO openapiInterfaceDetailVO=new OpenapiInterfaceDetailVO();
        openapiInterfaceDetailVO.setId(openapiInterface.getId());
        OpenapiInterfaceShowVO openapiInterfaceShowVO=getOpenapiInterfaceDetail(openapiInterfaceDetailVO);
        return openapiInterfaceShowVO;
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
        if (CollectionUtils.isEmpty(openapiInterfaceRequestParamSaveVOList)){
            return;
        }
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
        if (CollectionUtils.isEmpty(openapiInterfaceResponseParamSaveVOList)){
            return;
        }
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

    @Override
    public OpenapiInterface getInterfaceByCode(String code){
        OpenapiInterface example=new OpenapiInterface();
        example.setCode(code);
        example.setIsDelete(DataConstant.NO_DELETE);
        OpenapiInterface openapiInterface=openapiInterfaceMapper.selectOne(example);
        return openapiInterface;
    }
}
