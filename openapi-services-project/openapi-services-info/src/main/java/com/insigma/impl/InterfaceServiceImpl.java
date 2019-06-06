package com.insigma.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.constant.DataConstant;
import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.facade.OpenapiAppFacade;
import com.insigma.facade.openapi.po.*;
import com.insigma.facade.openapi.vo.*;
import com.insigma.facade.openapi.vo.OpenapiApp.OpenapiAppShowDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceRequestParam.OpenapiInterfaceRequestParamSaveVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceResponseParam.OpenapiInterfaceResponseParamSaveVO;
import com.insigma.mapper.OpenapiInterfaceMapper;
import com.insigma.mapper.OpenapiInterfaceRequestParamMapper;
import com.insigma.mapper.OpenapiInterfaceResponseParamMapper;
import com.insigma.mapper.OpenapiUserMapper;
import com.insigma.util.JSONUtil;
import com.insigma.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class InterfaceServiceImpl implements InterfaceFacade {

    @Autowired
    OpenapiInterfaceMapper openapiInterfaceMapper;
    @Autowired
    OpenapiInterfaceResponseParamMapper openapiInterfaceResponseParamMapper;
    @Autowired
    OpenapiInterfaceRequestParamMapper openapiInterfaceRequestParamMapper;
    @Autowired
    OpenapiAppFacade openapiAppFacade;
    @Autowired
    OpenapiUserMapper openapiUserMapper;

    @Override
    public PageInfo<OpenapiInterface> getOpenapiInterfaceList(OpenapiInterfaceListVO openapiInterfaceListVO) {
        if (openapiInterfaceListVO == null || openapiInterfaceListVO.getPageNum() == null || openapiInterfaceListVO.getPageSize() == null) {
            return null;
        }
        PageHelper.startPage(openapiInterfaceListVO.getPageNum().intValue(), openapiInterfaceListVO.getPageSize().intValue());
        OpenapiInterface exampleObeject = new OpenapiInterface();
        exampleObeject.setGroupId(openapiInterfaceListVO.getGroupId());
        List<OpenapiInterface> openapiInterfaceList = openapiInterfaceMapper.select(exampleObeject);
        PageInfo<OpenapiInterface> openapiInterfacePageInfo = new PageInfo<>(openapiInterfaceList);
        return openapiInterfacePageInfo;
    }

    @Override
    public OpenapiInterfaceShowVO getOpenapiInterfaceDetail(OpenapiInterfaceDetailVO openapiInterfaceDetailVO) {
        if (openapiInterfaceDetailVO == null || openapiInterfaceDetailVO.getId() == null) {
            return null;
        }
        OpenapiInterface openapiInterface = openapiInterfaceMapper.selectByPrimaryKey(openapiInterfaceDetailVO.getId());
        if (openapiInterface == null) {
            return null;
        }
        OpenapiInterfaceShowVO openapiInterfaceShowVO = new OpenapiInterfaceShowVO();
        openapiInterfaceShowVO.setOpenapiInterface(openapiInterface);
        List<OpenapiInterfaceResponseParam> openapiInterfaceResponseParams = openapiInterfaceResponseParamMapper.selectParamterTree(openapiInterface.getId());
        List<OpenapiInterfaceRequestParam> openapiInterfaceRequestParams = openapiInterfaceRequestParamMapper.selectParamterTree(openapiInterface.getId());
        openapiInterfaceShowVO.setOpenapiInterfaceRequestParams(openapiInterfaceRequestParams);
        openapiInterfaceShowVO.setOpenapiInterfaceResponseParams(openapiInterfaceResponseParams);
        return openapiInterfaceShowVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OpenapiInterfaceShowVO saveOpenapiInterface(OpenapiInterfaceSaveVO openapiInterfaceSaveVO) {
        if (openapiInterfaceSaveVO == null || openapiInterfaceSaveVO.getGroupId() == null) {
            return null;
        }
        OpenapiInterface openapiInterface = JSONUtil.convert(openapiInterfaceSaveVO, OpenapiInterface.class);
        openapiInterface.setVersionNumber(getMaxVersion(openapiInterfaceSaveVO.getGroupId()));
        if (openapiInterface.getId() == null) {
            openapiInterfaceMapper.insertSelective(openapiInterface);
            return getInterfaceDetail(openapiInterfaceSaveVO, openapiInterface);
        } else {
            updateInterface(openapiInterface);
            deleteRequestParams(openapiInterface.getId());
            deleteResponseParams(openapiInterface.getId());
            return getInterfaceDetail(openapiInterfaceSaveVO, openapiInterface);
        }
    }

    private Integer getMaxVersion(Integer groupId) {
        Example example = new Example(OpenapiInterface.class);
        example.createCriteria().andEqualTo("groupId", groupId);
        example.setOrderByClause("version_number desc");
        List<OpenapiInterface> openapiInterfaces = openapiInterfaceMapper.selectByExample(example);
        if (openapiInterfaces != null && !openapiInterfaces.isEmpty()) {
            return openapiInterfaces.get(0).getVersionNumber() + 1;
        } else {
            return 1;
        }
    }

    private OpenapiInterfaceShowVO getInterfaceDetail(OpenapiInterfaceSaveVO openapiInterfaceSaveVO, OpenapiInterface openapiInterface) {
        saveRequestParams(openapiInterface.getId(), openapiInterfaceSaveVO.getOpenapiInterfaceRequestParamSaveVOList());
        saveResponseParams(openapiInterface.getId(), openapiInterfaceSaveVO.getOpenapiInterfaceResponseParamSaveVOList());
        OpenapiInterfaceDetailVO openapiInterfaceDetailVO = new OpenapiInterfaceDetailVO();
        openapiInterfaceDetailVO.setId(openapiInterface.getId());
        OpenapiInterfaceShowVO openapiInterfaceShowVO = getOpenapiInterfaceDetail(openapiInterfaceDetailVO);
        return openapiInterfaceShowVO;
    }

    private void updateInterface(OpenapiInterface openapiInterface) {
        openapiInterface.setModifyTime(new Date());
        Example example = new Example(OpenapiInterface.class);
        example.createCriteria().andEqualTo("id", openapiInterface.getId());
        openapiInterfaceMapper.updateByExampleSelective(openapiInterface, example);
    }

    private void deleteResponseParams(Long id) {
        Example example = new Example(OpenapiInterfaceResponseParam.class);
        example.createCriteria().andEqualTo("interfaceId", id);
        OpenapiInterfaceResponseParam openapiInterfaceResponseParam = new OpenapiInterfaceResponseParam();
        openapiInterfaceResponseParam.setIsDelete(DataConstant.IS_DELETE);
        openapiInterfaceResponseParamMapper.updateByExampleSelective(openapiInterfaceResponseParam, example);
    }

    private void deleteRequestParams(Long id) {
        Example example = new Example(OpenapiInterfaceRequestParam.class);
        example.createCriteria().andEqualTo("interfaceId", id);
        OpenapiInterfaceRequestParam openapiInterfaceRequestParam = new OpenapiInterfaceRequestParam();
        openapiInterfaceRequestParam.setIsDelete(DataConstant.IS_DELETE);
        openapiInterfaceRequestParamMapper.updateByExampleSelective(openapiInterfaceRequestParam, example);
    }

    private void saveRequestParams(Long interfaceId, List<OpenapiInterfaceRequestParamSaveVO> openapiInterfaceRequestParamSaveVOList) {
        if (CollectionUtils.isEmpty(openapiInterfaceRequestParamSaveVOList)) {
            return;
        }
        List<OpenapiInterfaceRequestParam> openapiInterfaceRequestParams = requestParamTreeToList(interfaceId,null,openapiInterfaceRequestParamSaveVOList);
        openapiInterfaceRequestParamMapper.insertList(openapiInterfaceRequestParams);
    }

    private List<OpenapiInterfaceRequestParam> requestParamTreeToList(Long interfaceId,String parentId, List<OpenapiInterfaceRequestParamSaveVO> openapiInterfaceRequestParamSaveVOList) {
        List<OpenapiInterfaceRequestParam> result=new ArrayList<>();
        openapiInterfaceRequestParamSaveVOList.forEach(i->{
            if(i==null){
                return;
            }
            i.setId(UUID.randomUUID().toString());
            i.setInterfaceId(interfaceId);
            i.setParentId(parentId);
            OpenapiInterfaceRequestParam openapiInterfaceRequestParam=JSONUtil.convert(i,OpenapiInterfaceRequestParam.class);
            openapiInterfaceRequestParam.setCreateTime(new Date());
            openapiInterfaceRequestParam.setIsDelete(DataConstant.NO_DELETE);
            openapiInterfaceRequestParam.setModifyTime(new Date());
            result.add(openapiInterfaceRequestParam);
            if (!CollectionUtils.isEmpty(i.getChildren())){
                result.addAll(requestParamTreeToList(interfaceId,i.getId(),i.getChildren()));
            }
        });
        return result;
    }

    private List<OpenapiInterfaceResponseParam> responseParamTreeToList(Long interfaceId,String parentId, List<OpenapiInterfaceResponseParamSaveVO> openapiInterfaceRequestParamSaveVOList) {
        List<OpenapiInterfaceResponseParam> result=new ArrayList<>();
        openapiInterfaceRequestParamSaveVOList.forEach(i->{
            if(i==null){
                return;
            }
            i.setId(UUID.randomUUID().toString());
            i.setInterfaceId(interfaceId);
            i.setParentId(parentId);
            OpenapiInterfaceResponseParam openapiInterfaceRequestParam=JSONUtil.convert(i,OpenapiInterfaceResponseParam.class);
            openapiInterfaceRequestParam.setCreateTime(new Date());
            openapiInterfaceRequestParam.setIsDelete(DataConstant.NO_DELETE);
            openapiInterfaceRequestParam.setModifyTime(new Date());
            result.add(openapiInterfaceRequestParam);
            if (!CollectionUtils.isEmpty(i.getChildren())){
                result.addAll(responseParamTreeToList(interfaceId,i.getId(),i.getChildren()));
            }
        });
        return result;
    }


    private void saveResponseParams(Long interfaceId, List<OpenapiInterfaceResponseParamSaveVO> openapiInterfaceResponseParamSaveVOList) {
        if (CollectionUtils.isEmpty(openapiInterfaceResponseParamSaveVOList)) {
            return;
        }
        List<OpenapiInterfaceResponseParam> openapiInterfaceResponseParams = responseParamTreeToList(interfaceId,null,openapiInterfaceResponseParamSaveVOList);
        openapiInterfaceResponseParamMapper.insertList(openapiInterfaceResponseParams);
    }

    @Override
    public Integer deleteOpenapiInterface(OpenapiInterfaceDeleteVO openapiInterfaceDeleteVO) {
        if (openapiInterfaceDeleteVO == null || openapiInterfaceDeleteVO.getId() == null) {
            return 0;
        }
        OpenapiInterface openapiInterface = new OpenapiInterface();
        openapiInterface.setModifyTime(new Date());
        openapiInterface.setIsDelete(openapiInterfaceDeleteVO.getIsDelete());
        Example example = new Example(OpenapiInterface.class);
        example.createCriteria().andEqualTo("id", openapiInterfaceDeleteVO.getId());
        return openapiInterfaceMapper.updateByExampleSelective(openapiInterface, example);
    }

    @Override
    public synchronized Integer updateInterfaceVersion(Long id) {
        if (id == null) {
            return null;
        }
        OpenapiInterface openapiInterface = openapiInterfaceMapper.selectByPrimaryKey(id);
        Integer versionNumber = openapiInterface.getVersionNumber();
        versionNumber++;
        openapiInterface.setVersionNumber(versionNumber);
        return openapiInterfaceMapper.updateByPrimaryKeySelective(openapiInterface);
    }

    @Override
    public OpenapiInterface getInterfaceByCode(String code) {
        OpenapiInterface example = new OpenapiInterface();
        example.setCode(code);
        example.setIsDelete(DataConstant.NO_DELETE);
        OpenapiInterface openapiInterface = openapiInterfaceMapper.selectOne(example);
        return openapiInterface;
    }

    @Override
    public JSONObject checkSignVaild(JSONObject param) {
        JSONObject result = new JSONObject();
        result.put("flag", 0);
        try {
            if (param == null || StringUtils.isEmpty(param.getString("appKey"))) {
                result.put("msg", "appKey必须存在！");
                return result;
            }
            String appKey = param.getString("appKey");
            OpenapiAppShowDetailVO openapiAppShowDetailVO = openapiAppFacade.getAppByAppKey(appKey);
            if (openapiAppShowDetailVO == null) {
                result.put("msg", "appKey不存在或错误！");
                return result;
            }
            result = SignUtil.checkSign(param, openapiAppShowDetailVO.getAppSecret());
            return result;
        } catch (Exception e) {
            log.error("接口参数校验异常", e);
            result.put("msg", "接口参数校验异常！");
            return result;
        }
    }

    @Override
    public String getAppKeyListByCommandCodeAndOrgNO(String commandCode, String orgNO) throws Exception{
        OpenapiUser examUser=new OpenapiUser();
        examUser.setOrgNo(orgNO);
        examUser.setIsDelete(DataConstant.NO_DELETE);
        OpenapiUser openapiUser=openapiUserMapper.selectOne(examUser);
        if (openapiUser==null){
            throw new Exception("机构不存在或已删除");
        }
        List<OpenapiAppShowDetailVO> openapiAppShowDetailVOs = openapiAppFacade.getAppsByUserId(openapiUser.getId());
        String resultString="";
        for (OpenapiAppShowDetailVO temp:openapiAppShowDetailVOs){
            if (temp!=null&&!CollectionUtils.isEmpty(temp.getOpenapiInterfaces())) {
                Set<String> orgNos = temp.getOpenapiInterfaces().stream().map(i->i.getCommandCode()).collect(Collectors.toSet());
                if (orgNos.contains(commandCode)){
                    resultString+=temp.getAppKey()+"|";
                }
            }
        }
        if (!StringUtils.isEmpty(resultString)){
            resultString=resultString.substring(0,resultString.length()-1);
        }
        return resultString;
    }
}
