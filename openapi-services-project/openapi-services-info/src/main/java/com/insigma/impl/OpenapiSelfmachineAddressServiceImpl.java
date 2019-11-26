package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineAddressFacade;
import com.insigma.mapper.OpenapiSelfmachineAddressMapper;
import com.insigma.util.JSONUtil;
import constant.DataConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import com.insigma.facade.openapi.po.OpenapiSelfmachineAddress;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressSaveVO;

public class OpenapiSelfmachineAddressServiceImpl implements OpenapiSelfmachineAddressFacade {

    @Autowired
    OpenapiSelfmachineAddressMapper openapiSelfmachineAddressMapper;

    @Override
    public PageInfo<OpenapiSelfmachineAddress> getOpenapiSelfmachineAddressList(OpenapiSelfmachineAddressListVO openapiSelfmachineAddressListVO, Long userId) {
        if (openapiSelfmachineAddressListVO==null||openapiSelfmachineAddressListVO.getPageNum()==null||openapiSelfmachineAddressListVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(OpenapiSelfmachineAddress.class);
        Example.Criteria criteria=example.createCriteria();
        if (userId!=null){
            criteria.andEqualTo("userId",userId);
        }
        if (!StringUtils.isEmpty(openapiSelfmachineAddressListVO.getAddress())){
            criteria.andLike("address",openapiSelfmachineAddressListVO.getAddress());
        }
        criteria.andEqualTo("isDelete", DataConstant.NO_DELETE);
        PageHelper.startPage(openapiSelfmachineAddressListVO.getPageNum().intValue(),openapiSelfmachineAddressListVO.getPageSize().intValue());
        List<OpenapiSelfmachineAddress> openapiSelfmachineAddressList=openapiSelfmachineAddressMapper.selectByExample(example);
        PageInfo<OpenapiSelfmachineAddress> openapiSelfmachineAddressPageInfo=new PageInfo<>(openapiSelfmachineAddressList);
        return openapiSelfmachineAddressPageInfo;
    }

    @Override
    public OpenapiSelfmachineAddress getOpenapiSelfmachineAddressDetail(OpenapiSelfmachineAddressDetailVO openapiSelfmachineAddressDetailVO) {
        if (openapiSelfmachineAddressDetailVO==null||openapiSelfmachineAddressDetailVO.getId()==null) {
            return null;
        };
        OpenapiSelfmachineAddress openapiSelfmachineAddress=openapiSelfmachineAddressMapper.selectByPrimaryKey(openapiSelfmachineAddressDetailVO.getId());
        return openapiSelfmachineAddress;
    }

    @Override
    public Integer saveOpenapiSelfmachineAddress(OpenapiSelfmachineAddressSaveVO openapiSelfmachineAddressSaveVO, Long userId, String userName) {
        if (openapiSelfmachineAddressSaveVO==null){
            return 0;
        }
        OpenapiSelfmachineAddress openapiSelfmachineAddress= JSONUtil.convert(openapiSelfmachineAddressSaveVO,OpenapiSelfmachineAddress.class);
        if (openapiSelfmachineAddress.getId()==null){
            openapiSelfmachineAddress.setUserId(userId).setCreatorName(userName);
            return openapiSelfmachineAddressMapper.insertSelective(openapiSelfmachineAddress);
        }else {
            Example example=new Example(OpenapiSelfmachineAddress.class);
            example.createCriteria().andEqualTo("id",openapiSelfmachineAddress.getId());
            return openapiSelfmachineAddressMapper.updateByExampleSelective(openapiSelfmachineAddress,example);
        }
    }

    @Override
    public Integer deleteOpenapiSelfmachineAddress(OpenapiSelfmachineAddressDeleteVO openapiSelfmachineAddressDeleteVO, Long userId) {
        if (openapiSelfmachineAddressDeleteVO==null||openapiSelfmachineAddressDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachineAddress openapiSelfmachineAddress=new OpenapiSelfmachineAddress();
        openapiSelfmachineAddress.setIsDelete(openapiSelfmachineAddressDeleteVO.getIsDelete());
        Example example=new Example(OpenapiSelfmachineAddress.class);
        example.createCriteria().andEqualTo("id",openapiSelfmachineAddressDeleteVO.getId()).andEqualTo("userId",userId);
        return openapiSelfmachineAddressMapper.updateByExampleSelective(openapiSelfmachineAddress,example);
    }
}
