package com.insigma.web.user;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiUserFacade;
import com.insigma.facade.openapi.po.OpenapiUser;
import com.insigma.facade.openapi.vo.OpenapiUser.*;
import com.insigma.web.BasicController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.vo.result.ResultVo;



@RestController
@RequestMapping("openapiUser")
@Api(tags ="用户管理")
@Slf4j
public class OpenapiUserController extends BasicController {

    @Autowired
    OpenapiUserFacade openapiUserFacade;

    @ApiOperation(value = "用户列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiUser> getOpenapiUserList(@RequestBody OpenapiUserListVO OpenapiUserListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiUser> OpenapiUserList=openapiUserFacade.getOpenapiUserList(OpenapiUserListVO);
            if(OpenapiUserList!=null){
                DataListResultDto<OpenapiUser> dataListResultDto=new DataListResultDto<>(OpenapiUserList.getList(),(int)OpenapiUserList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取用户列表异常，原因为:"+e);
            log.error("获取用户列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "用户详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiUserDetailShowVO> getOpenapiUserDetail(@RequestBody OpenapiUserDetailVO OpenapiUserDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiUserDetailShowVO OpenapiUser=openapiUserFacade.getOpenapiUserDetail(OpenapiUserDetailVO);
        if(OpenapiUser!=null){
            resultVo.setResult(OpenapiUser);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
        } catch (Exception e){
        resultVo.setResultDes("获取用户详情异常，原因为:"+e);
        log.error("获取用户详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "用户保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiUser> saveOpenapiUser(@RequestBody OpenapiUserSaveVO OpenapiUserSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            SaveUserBackVO saveUserBackVO = openapiUserFacade.saveOpenapiUser(OpenapiUserSaveVO);
            if (saveUserBackVO!=null&&1 == saveUserBackVO.getFlag()) {
                resultVo.setResultDes("用户保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("用户保存失败,原因为："+saveUserBackVO.getMsg());
            }
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常，原因为:"+e);
                log.error("用户保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "用户删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiUser> deleteOpenapiUser(@RequestBody OpenapiUserDeleteVO OpenapiUserDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiUserFacade.deleteOpenapiUser(OpenapiUserDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("用户删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("用户删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("用户删除异常，原因为:"+e);
            log.error("用户删除异常",e);
        }
        return resultVo;
    }


}
