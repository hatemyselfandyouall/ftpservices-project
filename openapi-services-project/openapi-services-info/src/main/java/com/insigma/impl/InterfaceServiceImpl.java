package com.insigma.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiInterfaceHistoryFacade;
import com.insigma.facade.openapi.facade.OpenapiInterfaceTypeFacade;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiInterfaceDetailShowVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistorySaveVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceInnerUrl.OpenapiInterfaceInnerUrlSaveVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeTreeVO;
import com.insigma.facade.openapi.vo.interfaceStatistics.InterfaceStatisticsVO;
import com.insigma.facade.openapi.vo.openapiInterface.*;
import com.insigma.util.DateUtils;
import constant.DataConstant;
import com.insigma.enums.OpenapiCacheEnum;
import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.facade.OpenapiAppFacade;
import com.insigma.facade.openapi.po.*;
import com.insigma.facade.openapi.vo.OpenapiApp.OpenapiAppShowDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceRequestParam.OpenapiInterfaceRequestParamSaveVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceResponseParam.OpenapiInterfaceResponseParamSaveVO;
import com.insigma.mapper.*;
import com.insigma.util.JSONUtil;
import com.insigma.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.util.StringUtils;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;
import star.vo.result.ResultVo;
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
    @Autowired
    CachesKeyService cachesKeyService;
    @Autowired
    OpenapiAppInterfaceMapper openapiAppInterfaceMapper;
    @Autowired
    OpenapiInterfaceInnerUrlMapper openapiInterfaceInnerUrlMapper;
    @Autowired
    OpenapiInterfaceTypeMapper openapiInterfaceTypeMapper;
    @Autowired
    OpenapiInterfaceHistoryFacade openapiInterfaceHistoryFacade;
    @Autowired
    OpenapiInterfaceTypeFacade openapiInterfaceTypeFacade;
    @Autowired
    OpenapiInterfaceHistoryMapper openapiInterfaceHistoryMapper;

    @Override
    public PageInfo<OpenapiInterfaceDetailShowVO> getOpenapiInterfaceList(OpenapiInterfaceListVO openapiInterfaceListVO) {
        if (openapiInterfaceListVO == null || openapiInterfaceListVO.getPageNum() == null || openapiInterfaceListVO.getPageSize() == null) {
            return null;
        }

        Example example = new Example(OpenapiInterface.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete", DataConstant.NO_DELETE);
        if (openapiInterfaceListVO.getIsAvaliable() != null) {
            criteria.andEqualTo("isAvaliable", openapiInterfaceListVO.getIsAvaliable());
        }
        if (openapiInterfaceListVO.getTypeId() != null) {
            criteria.andEqualTo("typeId", openapiInterfaceListVO.getTypeId());
        }
        if (openapiInterfaceListVO.getIsPublic() != null) {
            criteria.andEqualTo("isPublic", openapiInterfaceListVO.getIsPublic());
        }
        if (openapiInterfaceListVO.getName() != null) {
            criteria.andCondition("(name like '%" + openapiInterfaceListVO.getName() + "%' or command_code like '%" + openapiInterfaceListVO.getName() + "%' or out_url like '%" + openapiInterfaceListVO.getName() + "%' )");
        }
        example.setOrderByClause("modify_time desc");
        PageHelper.startPage(openapiInterfaceListVO.getPageNum().intValue(), openapiInterfaceListVO.getPageSize().intValue());
        Page<OpenapiInterface> openapiInterfaceList = (Page<OpenapiInterface>) openapiInterfaceMapper.selectByExample(example);
        List<OpenapiInterfaceDetailShowVO> openapiInterfaceDetailShowVOS = openapiInterfaceList.stream().map(i -> {
            OpenapiInterfaceDetailShowVO openapiInterfaceDetailShowVO = JSONUtil.convert(i, OpenapiInterfaceDetailShowVO.class);
            openapiInterfaceDetailShowVO.setOpenapiInterfaceInnerUrls(getInnerUrlByInterfaceId(i.getId()));
            return openapiInterfaceDetailShowVO;
        }).collect(Collectors.toList());
        PageInfo<OpenapiInterfaceDetailShowVO> openapiInterfacePageInfo = new PageInfo<>(openapiInterfaceDetailShowVOS);
        List<OpenapiInterfaceType> openapiInterfaceTypes = openapiInterfaceTypeFacade
                .getOpenapiInterfaceTypeList(new OpenapiInterfaceTypeListVO().setType(DataConstant.INTERFACE_TYPE_BUSINESS).setPageNum(1l).setPageSize(9999L)).getList();
        Map<Long, OpenapiInterfaceType> openapiInterfaceTypeMap = openapiInterfaceTypes.stream().collect(Collectors.toMap(OpenapiInterfaceType::getId, i -> i));
        for (OpenapiInterfaceDetailShowVO openapiInterfaceDetailShowVO : openapiInterfacePageInfo.getList()) {
            if (openapiInterfaceTypeMap.get(openapiInterfaceDetailShowVO.getTypeId()) != null) {
                openapiInterfaceDetailShowVO.setTypeName(openapiInterfaceTypeMap.get(openapiInterfaceDetailShowVO.getTypeId()).getName());
            }
        }
        openapiInterfacePageInfo.setTotal(openapiInterfaceList.getTotal());
        return openapiInterfacePageInfo;
    }

    private List<OpenapiInterfaceInnerUrl> getInnerUrlByInterfaceId(Long id) {
        OpenapiInterfaceInnerUrl openapiInterfaceInnerUrl = new OpenapiInterfaceInnerUrl();
        openapiInterfaceInnerUrl.setInterfaceId(id);
        return openapiInterfaceInnerUrlMapper.select(openapiInterfaceInnerUrl);
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
        List<OpenapiInterfaceResponseParam> openapiInterfaceResponseParams = getResponseTreesById(openapiInterface.getId());
        List<OpenapiInterfaceRequestParam> openapiInterfaceRequestParams = getRequestTreesById(openapiInterface.getId());
        openapiInterfaceShowVO.setOpenapiInterfaceInnerUrls(getInnerUrlByInterfaceId(openapiInterface.getId()));
        openapiInterfaceShowVO.setOpenapiInterfaceRequestParams(openapiInterfaceRequestParams);
        openapiInterfaceShowVO.setOpenapiInterfaceResponseParams(openapiInterfaceResponseParams);
        List<OpenapiInterfaceType> openapiInterfaceTypes = openapiInterfaceTypeFacade
                .getOpenapiInterfaceTypeList(new OpenapiInterfaceTypeListVO().setPageNum(1l).setPageSize(9999L)).getList();
        Map<Long, OpenapiInterfaceType> openapiInterfaceTypeMap = openapiInterfaceTypes.stream().collect(Collectors.toMap(OpenapiInterfaceType::getId, i -> i));
        if (openapiInterfaceTypeMap.get(openapiInterfaceShowVO.getOpenapiInterface().getTypeId()) != null) {
            OpenapiInterfaceType openapiInterfaceTyp = openapiInterfaceTypeMap.get(openapiInterfaceShowVO.getOpenapiInterface().getTypeId());
            openapiInterfaceShowVO.getOpenapiInterface().setTypeName(openapiInterfaceTyp.getName());
            openapiInterfaceShowVO.getOpenapiInterface().setAppTypeNamw(openapiInterfaceTypeMap.get(openapiInterfaceTyp.getParentId()) != null ? openapiInterfaceTypeMap.get(openapiInterfaceTyp.getParentId()).getName() : "");
        }
        return openapiInterfaceShowVO;
    }

    @Override
    public List<OpenapiInterfaceShowVO> getAllInterfaces() {
        List<OpenapiInterface> openapiInterfaces = openapiInterfaceMapper.select(new OpenapiInterface().setIsDelete(DataConstant.NO_DELETE));
        List<OpenapiInterfaceShowVO> openapiInterfaceShowVOS = openapiInterfaces.stream().map(i -> {
            OpenapiInterfaceShowVO openapiInterfaceShowVO = new OpenapiInterfaceShowVO();
            openapiInterfaceShowVO.setOpenapiInterface(i);
            openapiInterfaceShowVO.setOpenapiInterfaceInnerUrls(openapiInterfaceInnerUrlMapper.select(new OpenapiInterfaceInnerUrl().setInterfaceId(i.getId())));
            return openapiInterfaceShowVO;
        }).collect(Collectors.toList());
        return openapiInterfaceShowVOS;
    }

    private List<OpenapiInterfaceResponseParam> getResponseTreesById(Long id) {
        List<OpenapiInterfaceResponseParam> openapiInterfaceResponseParams = openapiInterfaceResponseParamMapper.select(new OpenapiInterfaceResponseParam().setIsDelete(DataConstant.NO_DELETE).setInterfaceId(id));
        openapiInterfaceResponseParams = responseParamList2Tree(openapiInterfaceResponseParams);
        return openapiInterfaceResponseParams;
    }


    private List<OpenapiInterfaceRequestParam> getRequestTreesById(Long id) {
        List<OpenapiInterfaceRequestParam> openapiInterfaceResponseParams = openapiInterfaceRequestParamMapper.select(new OpenapiInterfaceRequestParam().setIsDelete(DataConstant.NO_DELETE).setInterfaceId(id));
        openapiInterfaceResponseParams = requestParamList2Tree(openapiInterfaceResponseParams);
        return openapiInterfaceResponseParams;
    }

    private List<OpenapiInterfaceRequestParam> requestParamList2Tree(List<OpenapiInterfaceRequestParam> openapiInterfaceResponseParams) {
        Map<String, List<OpenapiInterfaceRequestParam>> longListMap = openapiInterfaceResponseParams.stream().collect(Collectors.groupingBy(i -> i.getParentId()));
        List<OpenapiInterfaceRequestParam> roots = longListMap.get(DataConstant.ROOT_PARAM);
        roots = requestTreeIterate(roots, longListMap);
        return roots;
    }

    private List<OpenapiInterfaceRequestParam> requestTreeIterate(List<OpenapiInterfaceRequestParam> roots, Map<String, List<OpenapiInterfaceRequestParam>> longListMap) {
        if (CollectionUtils.isEmpty(roots)) {
            return new ArrayList<>();
        }
        roots.forEach(i -> {
            List<OpenapiInterfaceRequestParam> children = longListMap.get(i.getId());
            if (!CollectionUtils.isEmpty(children)) {
                requestTreeIterate(children, longListMap);
                i.setChildren(children);
            } else {
                i.setChildren(new ArrayList<>());
            }
        });
        return roots;
    }

    private List<OpenapiInterfaceResponseParam> responseParamList2Tree(List<OpenapiInterfaceResponseParam> openapiInterfaceResponseParams) {
        Map<String, List<OpenapiInterfaceResponseParam>> longListMap = openapiInterfaceResponseParams.stream().collect(Collectors.groupingBy(i -> i.getParentId()));
        List<OpenapiInterfaceResponseParam> roots = longListMap.get(DataConstant.ROOT_PARAM);
        roots = responseTreeIterate(roots, longListMap);
        return roots;
    }

    private List<OpenapiInterfaceResponseParam> responseTreeIterate(List<OpenapiInterfaceResponseParam> roots, Map<String, List<OpenapiInterfaceResponseParam>> longListMap) {
        if (CollectionUtils.isEmpty(roots)) {
            return new ArrayList<>();
        }
        roots.forEach(i -> {
            List<OpenapiInterfaceResponseParam> children = longListMap.get(i.getId());
            if (!CollectionUtils.isEmpty(children)) {
                responseTreeIterate(children, longListMap);
                i.setChildren(children);
            } else {
                i.setChildren(new ArrayList<>());
            }
        });
        return roots;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OpenapiInterfaceShowVO saveOpenapiInterface(OpenapiInterfaceSaveVO openapiInterfaceSaveVO) {
        if (openapiInterfaceSaveVO == null) {
            return null;
        }
        OpenapiInterface openapiInterface = JSONUtil.convert(openapiInterfaceSaveVO, OpenapiInterface.class);
        if (openapiInterface.getId() == null) {
            openapiInterface.setVersionNumber(1);
            openapiInterfaceMapper.insertSelective(openapiInterface);
            OpenapiInterfaceShowVO openapiInterfaceShowVO = getInterfaceDetail(openapiInterfaceSaveVO, openapiInterface);
            saveInitHisttory(openapiInterfaceShowVO);
            return openapiInterfaceShowVO;
        } else {
            updateInterface(openapiInterface);
            deleteRequestParams(openapiInterface.getId());
            deleteResponseParams(openapiInterface.getId());
            deleteInnerUrls(openapiInterface.getId());
            return getInterfaceDetail(openapiInterfaceSaveVO, openapiInterface);
        }
    }

    private void saveInitHisttory(OpenapiInterfaceShowVO openapiInterfaceShowVO) {
        OpenapiInterfaceHistory openapiInterfaceHistory = new OpenapiInterfaceHistory()
                .setHistoryJson(JSONObject.toJSONString(openapiInterfaceShowVO))
                .setInterfaceId(openapiInterfaceShowVO.getOpenapiInterface().getId())
                .setCreatorId(openapiInterfaceShowVO.getOpenapiInterface().getCreatorId())
                .setCreatorName(openapiInterfaceShowVO.getOpenapiInterface().getCreatorName())
                .setVersionNumber(openapiInterfaceShowVO.getOpenapiInterface().getVersionNumber());
        openapiInterfaceHistoryMapper.insertSelective(openapiInterfaceHistory);
    }

    private void deleteInnerUrls(Long id) {
        openapiInterfaceInnerUrlMapper.delete(new OpenapiInterfaceInnerUrl().setInterfaceId(id));
    }


    private OpenapiInterfaceShowVO getInterfaceDetail(OpenapiInterfaceSaveVO openapiInterfaceSaveVO, OpenapiInterface openapiInterface) {
        saveRequestParams(openapiInterface.getId(), openapiInterfaceSaveVO.getOpenapiInterfaceRequestParamSaveVOList());
        saveResponseParams(openapiInterface.getId(), openapiInterfaceSaveVO.getOpenapiInterfaceResponseParamSaveVOList());
        saveInnerUrls(openapiInterface.getId(), openapiInterfaceSaveVO.getOpenapiInterfaceInnerUrlSaveVOS());
        OpenapiInterfaceDetailVO openapiInterfaceDetailVO = new OpenapiInterfaceDetailVO();
        openapiInterfaceDetailVO.setId(openapiInterface.getId());
        OpenapiInterfaceShowVO openapiInterfaceShowVO = getOpenapiInterfaceDetail(openapiInterfaceDetailVO);
        if (openapiInterfaceSaveVO.getOpenapiInterfaceHistorySaveVO() != null) {
            openapiInterfaceSaveVO.getOpenapiInterfaceHistorySaveVO().setHistoryJson(JSONObject.parseObject(JSONObject.toJSONString(openapiInterfaceShowVO))).setVersionNumber(openapiInterfaceShowVO.getOpenapiInterface().getVersionNumber());
            saveUpdateHistory(openapiInterface.getId(), openapiInterfaceSaveVO.getOpenapiInterfaceHistorySaveVO(), openapiInterfaceSaveVO.getCreatorId(), openapiInterfaceSaveVO.getCreatorName());
        }
        return openapiInterfaceShowVO;
    }

    private void saveUpdateHistory(Long id, OpenapiInterfaceHistorySaveVO openapiInterfaceHistorySaveVO, Long userId, String userName) {
        if (openapiInterfaceHistorySaveVO != null) {
            openapiInterfaceHistorySaveVO.setInterfaceId(id);
            openapiInterfaceHistoryFacade.saveOpenapiInterfaceHistory(openapiInterfaceHistorySaveVO, userId, userName);
        }
    }

    private void saveInnerUrls(Long id, List<OpenapiInterfaceInnerUrlSaveVO> openapiInterfaceInnerUrlSaveVOS) {
        if (CollectionUtils.isEmpty(openapiInterfaceInnerUrlSaveVOS)) {
            return;
        }
        List<OpenapiInterfaceInnerUrl> openapiInterfaceInnerUrls = openapiInterfaceInnerUrlSaveVOS.stream().map(i -> {
            OpenapiInterfaceInnerUrl openapiInterfaceInnerUrl = JSONUtil.convert(i, OpenapiInterfaceInnerUrl.class);
            openapiInterfaceInnerUrl.setInterfaceId(id);
            return openapiInterfaceInnerUrl;
        }).collect(Collectors.toList());
        openapiInterfaceInnerUrlMapper.insertList(openapiInterfaceInnerUrls);
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

        List<OpenapiInterfaceRequestParam> openapiInterfaceRequestParams = requestParamTreeToList(interfaceId, DataConstant.ROOT_PARAM, openapiInterfaceRequestParamSaveVOList);
        openapiInterfaceRequestParamMapper.insertList(openapiInterfaceRequestParams);
    }


    private List<OpenapiInterfaceRequestParam> requestParamTreeToList(Long interfaceId, String parentId, List<OpenapiInterfaceRequestParamSaveVO> openapiInterfaceRequestParamSaveVOList) {
        List<OpenapiInterfaceRequestParam> result = new ArrayList<>();
        openapiInterfaceRequestParamSaveVOList.forEach(i -> {
            if (i == null) {
                return;
            }
            OpenapiInterfaceRequestParam openapiInterfaceRequestParam = JSONUtil.convert(i, OpenapiInterfaceRequestParam.class);
            openapiInterfaceRequestParam.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            openapiInterfaceRequestParam.setInterfaceId(interfaceId);
            openapiInterfaceRequestParam.setParentId(parentId);
            openapiInterfaceRequestParam.setCreateTime(new Date());
            openapiInterfaceRequestParam.setIsDelete(DataConstant.NO_DELETE);
            openapiInterfaceRequestParam.setModifyTime(new Date());
            result.add(openapiInterfaceRequestParam);
            if (!CollectionUtils.isEmpty(i.getChildren())) {
                result.addAll(requestParamTreeToList(interfaceId, openapiInterfaceRequestParam.getId(), i.getChildren()));
            }
        });
        return result;
    }

    private List<OpenapiInterfaceResponseParam> responseParamTreeToList(Long interfaceId, String parentId, List<OpenapiInterfaceResponseParamSaveVO> openapiInterfaceRequestParamSaveVOList) {
        List<OpenapiInterfaceResponseParam> result = new ArrayList<>();
        openapiInterfaceRequestParamSaveVOList.forEach(i -> {
            if (i == null) {
                return;
            }
            OpenapiInterfaceResponseParam openapiInterfaceRequestParam = JSONUtil.convert(i, OpenapiInterfaceResponseParam.class);
            openapiInterfaceRequestParam.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            openapiInterfaceRequestParam.setInterfaceId(interfaceId);
            openapiInterfaceRequestParam.setParentId(parentId);
            openapiInterfaceRequestParam.setCreateTime(new Date());
            openapiInterfaceRequestParam.setIsDelete(DataConstant.NO_DELETE);
            openapiInterfaceRequestParam.setModifyTime(new Date());
            result.add(openapiInterfaceRequestParam);
            if (!CollectionUtils.isEmpty(i.getChildren())) {
                result.addAll(responseParamTreeToList(interfaceId, openapiInterfaceRequestParam.getId(), i.getChildren()));
            }
        });
        return result;
    }


    private void saveResponseParams(Long interfaceId, List<OpenapiInterfaceResponseParamSaveVO> openapiInterfaceResponseParamSaveVOList) {
        if (CollectionUtils.isEmpty(openapiInterfaceResponseParamSaveVOList)) {
            return;
        }
        List<OpenapiInterfaceResponseParam> openapiInterfaceResponseParams = responseParamTreeToList(interfaceId, DataConstant.ROOT_PARAM, openapiInterfaceResponseParamSaveVOList);
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
        String cacheKey = code;
        return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_30M) {
            @Override
            protected Object doGetList(BaseCacheEnum type, String key) {
                OpenapiInterface example = new OpenapiInterface();
                example.setCode(code);
                example.setIsDelete(DataConstant.NO_DELETE);
                OpenapiInterface openapiInterface = openapiInterfaceMapper.selectOne(example);
                return openapiInterface;
            }
        }.getCache(OpenapiCacheEnum.INTERFACE_BY_CODE, cacheKey);
    }

    @Override
    public JSONObject checkSignVaild(JSONObject param) {
        log.info("开始验证参数合法性param" + param);
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
            log.info("开始调用验证参数合法性方法param" + param);
            result = SignUtil.checkSign(param, openapiAppShowDetailVO.getAppSecret());
            log.info("开始调用验证参数合法性方法返回" + result);
            return result;
        } catch (Exception e) {
            log.error("接口参数校验异常", e);
            result.put("msg", "接口参数校验异常！");
            return result;
        }
    }

    @Override
    public JSONObject checkSignVaild(String param) {
        JSONObject paramJSON = JSONObject.parseObject(param, Feature.OrderedField);
        return checkSignVaild(paramJSON);
    }

    @Override
    public JSONObject checkSignVaild(String paramString, String appKey, String time, String nonceStr, String signature, String encodeType) {
        log.info("开始验证参数合法性param" + paramString);
        JSONObject result = new JSONObject();
        result.put("flag", 0);
        try {
            if (StringUtils.isEmpty(appKey)) {
                result.put("msg", "appKey必须存在！");
                return result;
            }
            OpenapiAppShowDetailVO openapiAppShowDetailVO = openapiAppFacade.getAppByAppKey(appKey);
            if (openapiAppShowDetailVO == null) {
                result.put("msg", "appKey不存在或错误！");
                return result;
            }
            String appSecret = openapiAppShowDetailVO.getAppSecret();
            log.info("开始调用验证参数合法性方法param={},sign={}", paramString, signature);
            result = SignUtil.checkSign(paramString, appKey, time, nonceStr, signature, encodeType, appSecret);
            log.info("开始调用验证参数合法性方法返回" + result);
            return result;
        } catch (Exception e) {
            log.error("接口参数校验异常", e);
            result.put("msg", "接口参数校验异常！");
            return result;
        }
    }

    @Override
    public String getAppKeyListByCommandCodeAndOrgNO(String commandCode, String orgNO) throws Exception {
        log.info(":" + commandCode + ",orgNO=" + orgNO);
        if (com.github.pagehelper.StringUtil.isEmpty(orgNO) || com.github.pagehelper.StringUtil.isEmpty(commandCode)) {
            log.info("AppKeyListByCommandCodeAndOrgNO方法参数不全");
            return null;
        }
        Example example = new Example(OpenapiUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete", DataConstant.NO_DELETE);
        List<OpenapiUser> openapiUsers = openapiUserMapper.selectByExample(example);
        OpenapiUser openapiUser = null;
        for (OpenapiUser temp : openapiUsers) {
            if (temp.getOrgNo().equals(orgNO)) {
                openapiUser = temp;
                break;
            }
            if (temp.getOrgNo() != null && temp.getOrgNo().length() == 6 && temp.getOrgNo().substring(4, 6).equals("00") && orgNO.length() == 6) {
                if (temp.getOrgNo().substring(0, 4).equals(orgNO.substring(0, 4))) {
                    openapiUser = temp;
                }
            }
        }
        if (openapiUser == null) {
            log.error("未取得用户数据" + openapiUsers);
            throw new Exception("机构不存在或已删除");
        }
        List<OpenapiAppShowDetailVO> openapiAppShowDetailVOs = openapiAppFacade.getAppsByUserId(openapiUser.getId());
        log.info("获取用户数据" + openapiAppShowDetailVOs);
        String resultString = "";
        for (OpenapiAppShowDetailVO temp : openapiAppShowDetailVOs) {
            if (temp != null && !CollectionUtils.isEmpty(temp.getOpenapiInterfaces())) {
                Set<String> orgNos = temp.getOpenapiInterfaces().stream().filter(i -> i != null).map(i -> i.getCommandCode()).collect(Collectors.toSet());
                if (orgNos.contains(commandCode)) {
                    resultString += temp.getAppKey() + "|";
                }
            }
        }
        if (!StringUtils.isEmpty(resultString)) {
            resultString = resultString.substring(0, resultString.length() - 1);
        }
        log.info("结束调用AppKeyListByCommandCodeAndOrgNO方法,resultString=" + resultString);
        return resultString;
    }

    @Override
    public List<OpenapiInterfaceTypeTreeVO> getOpenapiInterfaceTree() {
        OpenapiInterfaceType examType = new OpenapiInterfaceType().setType(DataConstant.INTERFACE_TYPE_APPLICATION).setIsDelete(DataConstant.NO_DELETE);
        List<OpenapiInterfaceType> openapiInterfaceTypes = openapiInterfaceTypeMapper.select(examType);
        List<OpenapiInterfaceTypeTreeVO> openapiInterfaceTypeTreeVOS = openapiInterfaceTypes.stream().map(i -> {
            OpenapiInterfaceTypeTreeVO openapiInterfaceTypeTree = JSONUtil.convert(i, OpenapiInterfaceTypeTreeVO.class);
            OpenapiInterfaceType tempExam = new OpenapiInterfaceType().setParentId(i.getId()).setIsDelete(DataConstant.NO_DELETE);
            List<OpenapiInterfaceType> tempList = openapiInterfaceTypeMapper.select(tempExam);
            openapiInterfaceTypeTree.setChildrenTree(tempList.stream().map(j -> {
                OpenapiInterfaceTypeTreeVO tempTree = JSONUtil.convert(j, OpenapiInterfaceTypeTreeVO.class);
                OpenapiInterface examInterface = new OpenapiInterface().setTypeId(j.getId()).setIsDelete(DataConstant.NO_DELETE);
                List<OpenapiInterface> openapiInterfaces = openapiInterfaceMapper.select(examInterface);
                tempTree.setChildrenNode(openapiInterfaces);
                List<JSONObject> temp = openapiInterfaces.stream().map(k -> JSONObject.parseObject(JSONObject.toJSONString(k))).collect(Collectors.toList());
                tempTree.setTempChildren(temp);
                return tempTree;
            }).collect(Collectors.toList()));
            if (openapiInterfaceTypeTree.getChildrenTree() != null && !openapiInterfaceTypeTree.getChildrenTree().isEmpty()) {
                List<JSONObject> temp = openapiInterfaceTypeTree.getChildrenTree().stream().map(j -> JSONObject.parseObject(JSONObject.toJSONString(j))).collect(Collectors.toList());
                openapiInterfaceTypeTree.setTempChildren(temp);
            }
            return openapiInterfaceTypeTree;
        }).collect(Collectors.toList());
        return openapiInterfaceTypeTreeVOS;
    }

    @Override
    public Integer releaseOpenapiInterface(OpenapiInterfaceReleaseVO openapiInterfaceDeleteVO) {
        if (openapiInterfaceDeleteVO == null || openapiInterfaceDeleteVO.getIds() == null || openapiInterfaceDeleteVO.getIds().isEmpty()) {
            return 0;
        }
        Example example = new Example(OpenapiInterface.class);
        example.createCriteria().andIn("id", openapiInterfaceDeleteVO.getIds());
        OpenapiInterface openapiInterface = new OpenapiInterface().setIsPublic(openapiInterfaceDeleteVO.getIsPublic());
        return openapiInterfaceMapper.updateByExampleSelective(openapiInterface, example);
    }

    @Override
    public Integer setStatusOpenapiInterface(OpenapiInterfaceSetStatusVO openapiInterfaceSetStatusVO) {
        if (openapiInterfaceSetStatusVO == null || openapiInterfaceSetStatusVO.getId() == null) {
            return 0;
        }
        Example example = new Example(OpenapiInterface.class);
        example.createCriteria().andEqualTo("id", openapiInterfaceSetStatusVO.getId());
        OpenapiInterface openapiInterface = new OpenapiInterface().setIsAvaliable(openapiInterfaceSetStatusVO.getIsAvaliable());
        return openapiInterfaceMapper.updateByExampleSelective(openapiInterface, example);
    }

    @Override
    public ResultVo checkInterfaceSave(OpenapiInterfaceSaveVO openapiInterfaceSaveVO) {
        ResultVo resultVo = new ResultVo();
        if (openapiInterfaceSaveVO == null) {
            resultVo.setResultDes("参数必须传递");
            return resultVo;
        }
        String name = openapiInterfaceSaveVO.getName();
        String code = openapiInterfaceSaveVO.getCode();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(name)) {
            resultVo.setResultDes("参数必须传递");
            return resultVo;
        }
        Example example=new Example(OpenapiInterface.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("name",name);
        criteria.andEqualTo("isDelete",DataConstant.NO_DELETE);
        if (openapiInterfaceSaveVO.getId()!=null){
            criteria.andNotEqualTo("id",openapiInterfaceSaveVO.getId());
        }
        if (openapiInterfaceMapper.selectCountByExample(example) > 0) {
            resultVo.setResultDes("接口名称不允许重名");
            return resultVo;
        }
        example.clear();
        criteria=example.createCriteria();
        criteria.andEqualTo("code",code);
        criteria.andEqualTo("isDelete",DataConstant.NO_DELETE);
        if (openapiInterfaceSaveVO.getId()!=null){
            criteria.andNotEqualTo("id",openapiInterfaceSaveVO.getId());
        }
        if (openapiInterfaceMapper.selectCountByExample(example) > 0) {
            resultVo.setResultDes("不允许重复url");
            return resultVo;
        }
        resultVo.setSuccess(true);
        return resultVo;
    }

    @Override
    public List<OpenapiInterfaceStaaticsFieldsVO> interfacePublishingTrendByYear(InterfaceStatisticsVO interfaceStatisticsVO) {
        List<OpenapiInterfaceStaaticsFieldsVO> openapiInterfaceStaaticsVO = openapiInterfaceMapper.interfacePublishingTrendByYear(interfaceStatisticsVO);
        return openapiInterfaceStaaticsVO;
    }

    @Override
    public List<OpenapiInterfaceStaaticsFieldsVO> interfacePublishingTrendByWeek(InterfaceStatisticsVO interfaceStatisticsVO) {
        return openapiInterfaceMapper.interfacePublishingTrendByWeek(interfaceStatisticsVO);
    }

    @Override
    public List<OpenapiInterfaceStaaticsFieldsVO> interfacePublishingTrendByMonth(InterfaceStatisticsVO interfaceStatisticsVO) {
        return openapiInterfaceMapper.interfacePublishingTrendByMonth(interfaceStatisticsVO);
    }

    @Override
    public List<OpenapiInterfaceStaaticsFieldsVO> interfacePublishingTrendByDay(InterfaceStatisticsVO interfaceStatisticsVO) {
        return openapiInterfaceMapper.interfacePublishingTrendByDay(interfaceStatisticsVO);
    }

    @Override
    public OpenapiInterfaceStaaticsVO getTotalInterfaceTrendDetail(Integer staticType) {
        Integer totalCount = openapiInterfaceMapper.selectCount(new OpenapiInterface().setIsDelete(DataConstant.NO_DELETE));
        Example example = new Example(OpenapiInterface.class);
        Integer date;
        switch (staticType) {
            case 1:
                date = 1;
                break;
            case 2:
                date = 7;
                break;
            case 3:
                date = 30;
                break;
            case 4:
                date = 365;
                break;
            default:
                date = 365;
        }
        Date newDateStart = DateUtils.getDateBeforeDays(date);
        Date oldDateStrat = DateUtils.getDateBeforeDays(date * 2);
        example.createCriteria().andGreaterThan("createTime", newDateStart).andEqualTo("isDelete", DataConstant.NO_DELETE);
        Integer newInterfaceCount = openapiInterfaceMapper.selectCountByExample(example);
        example.clear();
        example.createCriteria().andBetween("createTime", newDateStart, oldDateStrat).andEqualTo("isDelete", DataConstant.NO_DELETE);
        Integer oldInterfaceCount = openapiInterfaceMapper.selectCountByExample(example);
        OpenapiInterfaceStaaticsVO openapiInterfaceStaaticsVO = new OpenapiInterfaceStaaticsVO();
        openapiInterfaceStaaticsVO.setTotaliInterfaceCount(totalCount);
        openapiInterfaceStaaticsVO.setNewInterfaceCount(newInterfaceCount);
        openapiInterfaceStaaticsVO.setCompareTotal(Double.valueOf(newInterfaceCount) / Double.valueOf(totalCount));
        openapiInterfaceStaaticsVO.setCmpareSomeTimesBeford(Double.valueOf(newInterfaceCount) / Double.valueOf(oldInterfaceCount));
        return openapiInterfaceStaaticsVO;
    }

}
