package com.insigma.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import constant.DataConstant;
import com.insigma.facade.openapi.facade.OpenapiUserFacade;
import com.insigma.facade.openapi.po.OpenapiFtpAccount;
import com.insigma.facade.openapi.po.OpenapiUser;
import com.insigma.facade.openapi.vo.OpenapiFtpAccount.OpenapiFtpAccountSaveVO;
import com.insigma.facade.openapi.vo.OpenapiUser.*;
import com.insigma.mapper.OpenapiFtpAccountMapper;
import com.insigma.mapper.OpenapiUserMapper;
import com.insigma.util.JSONUtil;
import com.insigma.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public class OpenapiUserServiceImpl implements OpenapiUserFacade {

    @Autowired
    OpenapiUserMapper openapiUserMapper;

    @Autowired
    OpenapiFtpAccountMapper openapiFtpAccountMapper;

    @Override
    public PageInfo<OpenapiUser> getOpenapiUserList(OpenapiUserListVO openapiUserListVO) {
        if (openapiUserListVO==null||openapiUserListVO.getPageNum()==null||openapiUserListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiUserListVO.getPageNum().intValue(),openapiUserListVO.getPageSize().intValue());
        List<OpenapiUser> OpenapiUserList=openapiUserMapper.getOpenapiUserList(openapiUserListVO);
        PageInfo<OpenapiUser> OpenapiUserPageInfo=new PageInfo<>(OpenapiUserList);
        return OpenapiUserPageInfo;
    }

    @Override
    public OpenapiUserDetailShowVO getOpenapiUserDetail(OpenapiUserDetailVO openapiUserDetailVO) {
        if (openapiUserDetailVO==null||openapiUserDetailVO.getId()==null) {
            return null;
        };
        OpenapiUser OpenapiUser=openapiUserMapper.selectByPrimaryKey(openapiUserDetailVO.getId());
        if (OpenapiUser==null){
            return null;
        }
        OpenapiUserDetailShowVO openapiUserDetailShowVO=JSONUtil.convert(OpenapiUser,OpenapiUserDetailShowVO.class);
        OpenapiFtpAccount example=new OpenapiFtpAccount();
        example.setIsDelete(DataConstant.NO_DELETE);
        example.setUserId(OpenapiUser.getId());
        List<OpenapiFtpAccount> openapiFtpAccounts=openapiFtpAccountMapper.select(example);
        if (openapiFtpAccounts!=null&&!openapiFtpAccounts.isEmpty()){
            openapiUserDetailShowVO.setOpenapiFtpAccount(openapiFtpAccounts.get(0));
        }
        return openapiUserDetailShowVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SaveUserBackVO saveOpenapiUser(OpenapiUserSaveVO openapiUserSaveVO) {

        SaveUserBackVO checkSaveVO=checkSaveUserVO(openapiUserSaveVO);
        if (1!=checkSaveVO.getFlag()){
            return checkSaveVO;
        }
        OpenapiUser openapiUser= JSONUtil.convert(openapiUserSaveVO,OpenapiUser.class);
        if (openapiUser.getId()==null){
            openapiUser.setOpenId(UUID.randomUUID().toString().replaceAll("-",""));
            openapiUser.setPasswd(PasswordUtils.encodePassWord(openapiUser.getPasswd(),openapiUser.getOpenId()));
            openapiUserMapper.insertSelective(openapiUser);
            if (openapiUserSaveVO.getOpenapiFtpAccountSaveVO()!=null){
                saveFtpAccount(openapiUserSaveVO.getOpenapiFtpAccountSaveVO(),openapiUser.getId());
            }
            checkSaveVO.setMsg("保存成功");
            return checkSaveVO;
        }else {
            Example example=new Example(OpenapiUser.class);
            example.createCriteria().andEqualTo("id",openapiUser.getId());
            OpenapiUser oldUser=openapiUserMapper.selectOneByExample(example);
            if (oldUser==null){
                checkSaveVO.setMsg("无此用户!");
                return checkSaveVO;
            }
            if ("1".equals(openapiUserSaveVO.getResetPasswdFlag())){
                openapiUser.setPasswd(oldUser.getLogonName()+"123456");
            }
            openapiUser.setPasswd(PasswordUtils.encodePassWord(openapiUser.getPasswd(),openapiUser.getOpenId()));
            openapiUser.setModifyTime(new Date());
            openapiUserMapper.updateByExampleSelective(openapiUser,example);
            if (openapiUserSaveVO.getOpenapiFtpAccountSaveVO()!=null){
                saveFtpAccount(openapiUserSaveVO.getOpenapiFtpAccountSaveVO(),openapiUser.getId());
            }
            checkSaveVO.setMsg("保存成功");
            return checkSaveVO;
        }
    }

    private void saveFtpAccount(OpenapiFtpAccountSaveVO openapiFtpAccountSaveVO,Long userId) {
        //删除账号
        Example example=new Example(OpenapiFtpAccount.class);
        example.createCriteria().andEqualTo("userId",userId);
        OpenapiFtpAccount deleteOb=new OpenapiFtpAccount();
        deleteOb.setIsDelete(DataConstant.IS_DELETE);
        openapiFtpAccountMapper.updateByExampleSelective(deleteOb,example);
        //重新插入
        OpenapiFtpAccount openapiFtpAccount=JSONUtil.convert(openapiFtpAccountSaveVO,OpenapiFtpAccount.class);
        openapiFtpAccount.setUserId(userId);
        openapiFtpAccountMapper.insertSelective(openapiFtpAccount);
    }

    private SaveUserBackVO checkSaveUserVO(OpenapiUserSaveVO openapiUserSaveVO) {
        SaveUserBackVO saveUserBackVO=new SaveUserBackVO();
        saveUserBackVO.setFlag(0);
        if (openapiUserSaveVO==null){
            saveUserBackVO.setMsg("入参为空！");
            return saveUserBackVO;
        }
        if (StringUtils.isEmpty(openapiUserSaveVO.getLogonName())){
            saveUserBackVO.setMsg("登录用户名为空！");
            return saveUserBackVO;
        }else {
            OpenapiUser exampleUser=new OpenapiUser();
            exampleUser.setLogonName(openapiUserSaveVO.getLogonName());
            if (0!=openapiUserMapper.selectCount(exampleUser)){
                saveUserBackVO.setMsg("用户名被占用！");
                return saveUserBackVO;
            }
        }
        if (openapiUserSaveVO.getPasswd().length()<6){
            saveUserBackVO.setMsg("密码少于6位！");
            return saveUserBackVO;
        }
        saveUserBackVO.setFlag(1);
        return saveUserBackVO;
    }

    @Override
    public Integer deleteOpenapiUser(OpenapiUserDeleteVO OpenapiUserDeleteVO) {
        if (OpenapiUserDeleteVO==null||OpenapiUserDeleteVO.getId()==null){
            return 0;
        }
        OpenapiUser OpenapiUser=new OpenapiUser();
        OpenapiUser.setModifyTime(new Date());
        Example example=new Example(OpenapiUser.class);
        example.createCriteria().andEqualTo("id",OpenapiUserDeleteVO.getId());
        return openapiUserMapper.updateByExampleSelective(OpenapiUser,example);
    }

    @Override
    public OpenapiUser getUserById(Long userId) {
        return openapiUserMapper.selectByPrimaryKey(userId);
    }
}
