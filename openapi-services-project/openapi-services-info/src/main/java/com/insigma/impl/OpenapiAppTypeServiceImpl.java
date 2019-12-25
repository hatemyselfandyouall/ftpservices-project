package com.insigma.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiAppTypeFacade;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiAppInterface;
import com.insigma.mapper.OpenapiAppMapper;
import com.insigma.mapper.OpenapiAppTypeMapper;
import com.insigma.util.JSONUtil;
import com.sun.org.apache.bcel.internal.generic.I2F;
import constant.DataConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import star.vo.result.ResultVo;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import com.insigma.facade.openapi.po.OpenapiAppType;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeDetailVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeListVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeSaveVO;

import javax.xml.crypto.Data;

@Slf4j
public class OpenapiAppTypeServiceImpl implements OpenapiAppTypeFacade {

    @Autowired
    OpenapiAppTypeMapper openapiAppTypeMapper;
    @Autowired
    OpenapiAppMapper openapiAppMapper;

    @Override
    public PageInfo<OpenapiAppType> getOpenapiAppTypeList(OpenapiAppTypeListVO openapiAppTypeListVO) {
        if (openapiAppTypeListVO==null||openapiAppTypeListVO.getPageNum()==null||openapiAppTypeListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiAppTypeListVO.getPageNum().intValue(),openapiAppTypeListVO.getPageSize().intValue());
        Example example=new Example(OpenapiAppType.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDelete",DataConstant.NO_DELETE);
        if (openapiAppTypeListVO.getKeyWord()!=null){
            criteria.andLike("name",'%'+openapiAppTypeListVO.getKeyWord()+'%');
        }
        Page<OpenapiAppType> openapiAppTypeList= (Page<OpenapiAppType>) openapiAppTypeMapper.selectByExample(example);
        PageInfo<OpenapiAppType> openapiAppTypePageInfo=new PageInfo<>(openapiAppTypeList);
        openapiAppTypePageInfo.setTotal(openapiAppTypeList.getTotal());
        return openapiAppTypePageInfo;
    }

    @Override
    public OpenapiAppType getOpenapiAppTypeDetail(OpenapiAppTypeDetailVO openapiAppTypeDetailVO) {
        if (openapiAppTypeDetailVO==null||openapiAppTypeDetailVO.getId()==null) {
            return null;
        };
        OpenapiAppType openapiAppType=openapiAppTypeMapper.selectByPrimaryKey(openapiAppTypeDetailVO.getId());
        return openapiAppType;
    }


    @Override
    public Integer saveOpenapiAppType(OpenapiAppTypeSaveVO openapiAppTypeSaveVO, Long userId, String userName) {
        if (openapiAppTypeSaveVO==null){
            return 0;
        }
        OpenapiAppType openapiAppType= JSONUtil.convert(openapiAppTypeSaveVO,OpenapiAppType.class);
        if (openapiAppType.getId()==null){
            openapiAppType.setCreatorId(userId);
            openapiAppType.setCreatorName(userName);
            return openapiAppTypeMapper.insertSelective(openapiAppType);
        }else {
            openapiAppType.setModifyTime(new Date());
            Example example=new Example(OpenapiAppType.class);
            example.createCriteria().andEqualTo("id",openapiAppType.getId());
            return openapiAppTypeMapper.updateByExampleSelective(openapiAppType,example);
        }
    }

    @Override
    public Integer deleteOpenapiAppType(OpenapiAppTypeDeleteVO openapiAppTypeDeleteVO) {
        if (openapiAppTypeDeleteVO==null||openapiAppTypeDeleteVO.getId()==null){
            return 0;
        }
        OpenapiAppType openapiAppType=new OpenapiAppType();
        openapiAppType.setModifyTime(new Date());
        openapiAppType.setIsDelete(openapiAppTypeDeleteVO.getIsDelete());
        Example example=new Example(OpenapiAppType.class);
        example.createCriteria().andEqualTo("id",openapiAppTypeDeleteVO.getId());
        return openapiAppTypeMapper.updateByExampleSelective(openapiAppType,example);
    }

    @Override
    public ResultVo checkAppInterfaceTypeSave(OpenapiAppTypeSaveVO openapiAppTypeSaveVO) {
        ResultVo resultVo=new ResultVo();
        if (openapiAppTypeSaveVO==null) {
            resultVo.setResultDes("参数必须传递");
            return resultVo;
        }
        Example example=new Example(OpenapiAppType.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("name",openapiAppTypeSaveVO.getName()).andEqualTo("isDelete",DataConstant.NO_DELETE);
        if (openapiAppTypeSaveVO.getId()!=null){
            criteria.andNotEqualTo("id",openapiAppTypeSaveVO.getId());
        }
        if (openapiAppTypeMapper.selectCountByExample(example)>0){
            resultVo.setResultDes("不允许应用类型同名");
            return resultVo;
        }
        resultVo.setSuccess(true);
        return resultVo;
    }

    @Override
    public ResultVo checkDeleteOpenapiAppType(OpenapiAppTypeDeleteVO openapiAppTypeDeleteVO) {
        ResultVo resultVo=new ResultVo();
        try {
            if (openapiAppTypeDeleteVO==null||openapiAppTypeDeleteVO.getId()==null){
                resultVo.setResultDes("参数不全");
            }
            Long id=openapiAppTypeDeleteVO.getId();
            if (openapiAppMapper.selectCount(new OpenapiApp().setIsDelete(DataConstant.NO_DELETE).setTypeId(id))>0){
                resultVo.setResultDes("有使用中的应用，不能删除该类型");
                return resultVo;
            }
            resultVo.setSuccess(true);
        }catch (Exception e){
            resultVo.setResultDes("删除校验异常");
            resultVo.setSuccess(false);
            log.error("删除校验异常",e);
        }
        return resultVo;
    }
}
