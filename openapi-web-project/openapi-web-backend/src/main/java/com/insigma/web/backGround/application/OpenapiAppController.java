package com.insigma.web.backGround.application;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiAppFacade;
import com.insigma.facade.openapi.facade.OpenapiAppInterfaceFacade;
import com.insigma.facade.openapi.po.OpenapiAppInterface;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.OpenapiApp.*;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceListVO;
import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiAppInterfaceSaveVO;
import com.insigma.facade.sysbase.SysOrgFacade;
import com.insigma.facade.sysbase.SysUserFacade;
import com.insigma.facade.sysbase.vo.SysOrgDTO;
import com.insigma.facade.sysbase.vo.SysUserOrgDTO;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.vo.result.ResultVo;
import com.insigma.facade.openapi.po.OpenapiApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("openapiApp")
@Api(tags ="开放平台应用管理")
@Slf4j
public class OpenapiAppController extends BasicController {

    @Autowired
    OpenapiAppFacade openapiAppFacade;
    @Autowired
    SysOrgFacade sysOrgFacade;
    @Autowired
    OpenapiAppInterfaceFacade openapiAppInterfaceFacade;
    @Autowired
    LoginComponent loginComponent;
    @Autowired
    SysUserFacade sysUserFacade;

    @ApiOperation(value = "开放平台应用列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiAppListShowVO> getOpenapiAppList(@RequestBody OpenapiAppListVO OpenapiAppListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiAppListShowVO> OpenapiAppList=openapiAppFacade.getOpenapiAppList(OpenapiAppListVO);
            if(OpenapiAppList!=null){
                DataListResultDto<OpenapiAppListShowVO> dataListResultDto=new DataListResultDto<>(OpenapiAppList.getList(),(int)OpenapiAppList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取开放平台应用列表异常");
            log.error("获取开放平台应用列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "开放平台应用-接口列表")
    @RequestMapping(value = "/interfaceList",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiAppInterfaceShowVO> getOpenapiAppInterfaceList(@RequestBody OpenapiAppInterfaceListVO openapiAppInterfaceListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiAppInterfaceShowVO> OpenapiAppList=openapiAppFacade.getOpenapiAppInterfaceList(openapiAppInterfaceListVO);
            if(OpenapiAppList!=null){
                DataListResultDto<OpenapiAppInterfaceShowVO> dataListResultDto=new DataListResultDto<OpenapiAppInterfaceShowVO>(OpenapiAppList.getList(),(int)OpenapiAppList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取开放平台应用列表异常");
            log.error("获取开放平台应用列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "开放平台应用-获取未使用的接口列表")
    @RequestMapping(value = "/getUnUsedInterfaceList",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiAppInterfaceShowVO> getUnUsedInterfaceList(@RequestBody UnUsedInterfaceListVO unUsedInterfaceListVO){
        ResultVo resultVo=new ResultVo();
        try {
            List<OpenapiInterface> OpenapiAppList=openapiAppFacade.getUnUsedInterfaceList(unUsedInterfaceListVO);
            if(OpenapiAppList!=null){
                resultVo.setResult(OpenapiAppList);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取开放平台应用列表异常");
            log.error("获取开放平台应用列表异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "开放平台应用详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> getOpenapiAppDetail(@RequestBody OpenapiAppDetailVO OpenapiAppDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiApp OpenapiApp=openapiAppFacade.getOpenapiAppDetail(OpenapiAppDetailVO);
        if(OpenapiApp!=null){
            resultVo.setResult(OpenapiApp);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
        } catch (Exception e){
        resultVo.setResultDes("获取开放平台应用详情异常");
        log.error("获取开放平台应用详情异常",e);
    }
        return resultVo;
    }


    @ApiOperation(value = "开放平台应用保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> saveOpenapiApp(@RequestBody OpenapiAppSaveVO OpenapiAppSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            String userName=loginComponent.getLoginUserName();
            List<SysUserOrgDTO> orgList = sysUserFacade.queryUserOrg(userId);
            String orgName= CollectionUtils.isEmpty(orgList)?"":orgList.get(0).getOrgName();
            OpenapiAppSaveVO.setUserId(userId);
            OpenapiAppSaveVO.setUserName(userName);
            OpenapiAppSaveVO.setOrgName(orgName);
            ResultVo checkResult=openapiAppFacade.checkSave(OpenapiAppSaveVO);
            if (!checkResult.isSuccess()){
                return checkResult;
            }
            Integer flag = openapiAppFacade.saveOpenapiApp(OpenapiAppSaveVO,userId,userName,orgName);
            if (1 == flag) {
                resultVo.setResultDes("开放平台应用保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("开放平台应用保存失败");
            }
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("开放平台应用保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "开放平台应用-接口保存")
    @RequestMapping(value = "/saveAppInterface",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> saveAppInterface(@RequestBody OpenapiAppInterfaceSaveVO openapiAppInterfaceSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            ResultVo checkResult=openapiAppFacade.checkAppInterfaceSave(openapiAppInterfaceSaveVO);
            if (!checkResult.isSuccess()){
                return checkResult;
            }
            Integer flag = openapiAppFacade.saveAppInterface(openapiAppInterfaceSaveVO);
            if (1 == flag) {
                resultVo.setResultDes("开放平台应用-接口保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("开放平台应用-接口保存保存失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("接口保存异常");
            log.error("开放平台应用保存异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "开放平台应用删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> deleteOpenapiApp(@RequestBody OpenapiAppDeleteVO OpenapiAppDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiAppFacade.deleteOpenapiApp(OpenapiAppDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("开放平台应用删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("开放平台应用删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("开放平台应用删除异常");
            log.error("开放平台应用删除异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "开放平台应用-接口删除")
    @RequestMapping(value = "/deleteInterface",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> deleteInterface(@RequestBody OpenapiAppInterfaceDeleteVO openapiAppInterfaceDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiAppInterfaceFacade.deleteOpenapiAppInterface(openapiAppInterfaceDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("开放平台应用-接口删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("开放平台应用-接口删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("开放平台应用-接口删除异常");
            log.error("开放平台应用-接口删除异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "获取机构列表")
    @RequestMapping(value = "/getOrgList",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SysOrgDTO> getOrgList(){
        ResultVo resultVo=new ResultVo();
        try {
            HashMap test=new HashMap();
            test.put("type", 1);
            List<SysOrgDTO> sysOrgDTOS = sysOrgFacade.getListByWhere(test);
            resultVo.setResult(sysOrgDTOS);
            resultVo.setSuccess(true);
        }catch (Exception e){
            resultVo.setResultDes("获取机构列表异常，原因为:"+e);
            log.error("获取机构列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "开放平台应用黑名单状态改变")
    @RequestMapping(value = "/changeAppBlackStatus",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> changeAppBlackStatus(@RequestBody ChangeAppBlackStatusVO changeAppBlackStatusVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiAppFacade.changeAppBlackStatus(changeAppBlackStatusVO);
            if (1 == flag) {
                resultVo.setResultDes("开放平台应用黑名单状态改变成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("开放平台应用黑名单状态改变失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("开放平台应用黑名单状态改变异常");
            log.error("开放平台应用黑名单状态改变异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "开放平台应用重设Appsecret")
    @RequestMapping(value = "/resetAppSecret",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> resetAppSecret(@RequestBody ResetAppSecretVO resetAppSecretVO){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiApp openapiApp = openapiAppFacade.resetAppSecret(resetAppSecretVO);
            if (openapiApp != null) {
                resultVo.setResultDes("开放平台应用重设Appsecret成功");
                resultVo.setResult(openapiApp);
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("开放平台应用重设Appsecret失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("开放平台应用重设Appsecret异常");
            log.error("开放平台应用重设Appsecret异常",e);
        }
        return resultVo;
    }

}
