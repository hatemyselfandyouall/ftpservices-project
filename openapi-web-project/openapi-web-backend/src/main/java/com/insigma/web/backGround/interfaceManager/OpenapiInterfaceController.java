package com.insigma.web.backGround.interfaceManager;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.po.OpenapiInterface;

import com.insigma.facade.openapi.vo.OpenapiAppInterface.OpenapiInterfaceDetailShowVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeTreeVO;
import com.insigma.facade.openapi.vo.openapiInterface.*;
import com.insigma.facade.operatelog.facade.SysOperatelogRecordFacade;
import com.insigma.util.AddLogUtil;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import com.insigma.webtool.component.loadBalance.UpstreamCacheManager;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import star.fw.web.util.ServletAttributes;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("interface")
@Api(tags ="接口管理")
@Slf4j
public class OpenapiInterfaceController extends BasicController {

    @Autowired
    InterfaceFacade interfaceFacade;
    @Autowired
    LoginComponent loginComponent;
    @Autowired
    SysOperatelogRecordFacade sysOperatelogRecordFacade;


    @ApiOperation(value = "接口列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceDetailShowVO> getOpenapiInterfaceList(@RequestBody OpenapiInterfaceListVO openapiInterfaceListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiInterfaceDetailShowVO> openapiInterfaceList=interfaceFacade.getOpenapiInterfaceList(openapiInterfaceListVO);
            if(openapiInterfaceList!=null){
                DataListResultDto<OpenapiInterfaceDetailShowVO> dataListResultDto=new DataListResultDto<>(openapiInterfaceList.getList(),(int)openapiInterfaceList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口列表","接口列表", DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取接口列表异常，原因为:"+e);
            log.error("获取接口列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "接口列表树")
    @RequestMapping(value = "/tree",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceTypeTreeVO> getOpenapiInterfaceTree(){
        ResultVo resultVo=new ResultVo();
        try {
            List<OpenapiInterfaceTypeTreeVO> openapiInterfaceList=interfaceFacade.getOpenapiInterfaceTree();
            if(openapiInterfaceList!=null){
                resultVo.setResult(openapiInterfaceList);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("接口列表树获取失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口列表树","接口列表树", DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取接口列表树异常，原因为:"+e);
            log.error("获取接口列表树异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "接口详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceShowVO> getOpenapiInterfaceDetail(@RequestBody OpenapiInterfaceDetailVO openapiInterfaceDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiInterfaceShowVO openapiInterface=interfaceFacade.getOpenapiInterfaceDetail(openapiInterfaceDetailVO);
        if(openapiInterface!=null){
            resultVo.setResult(openapiInterface);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口详情","接口详情id："+openapiInterface.getOpenapiInterface().getId(), DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        } catch (Exception e){
        resultVo.setResultDes("获取接口详情异常，原因为:"+e);
        log.error("获取接口详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "接口保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceSaveVO> saveOpenapiInterface(@RequestBody OpenapiInterfaceSaveVO openapiInterfaceSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            //------------------------操作日志处理-----------------------------------------------------------------------
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            ResultVo checkResult=interfaceFacade.checkInterfaceSave(openapiInterfaceSaveVO);
            if (!checkResult.isSuccess()){
                return checkResult;
            }
            Long userId=loginComponent.getLoginUserId();
            String userName=loginComponent.getLoginUserName();
            openapiInterfaceSaveVO.setCreatorId(userId);
            openapiInterfaceSaveVO.setCreatorName(userName);
            OpenapiInterfaceShowVO openapiInterfaceShowVO = interfaceFacade.saveOpenapiInterface(openapiInterfaceSaveVO);
            UpstreamCacheManager.removeByKey(openapiInterfaceShowVO.getOpenapiInterface().getId());
            UpstreamCacheManager.submit(openapiInterfaceShowVO);
            if (openapiInterfaceShowVO!=null) {
                resultVo.setResult(openapiInterfaceShowVO);
                resultVo.setResultDes("接口保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("接口保存失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口保存","接口保存："+openapiInterfaceShowVO.getOpenapiInterface().getId(), DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常，原因为:"+e);
                log.error("接口保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "接口删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterface> deleteOpenapiInterface(@RequestBody OpenapiInterfaceDeleteVO openapiInterfaceDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            //------------------------操作日志处理-----------------------------------------------------------------------
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            Integer flag = interfaceFacade.deleteOpenapiInterface(openapiInterfaceDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("接口删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("使用的应用类型1医保2医院失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口删除","接口删除："+openapiInterfaceDeleteVO.getId(), DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("接口删除异常，原因为:"+e);
            log.error("接口删除异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "接口发布状态修改")
    @RequestMapping(value = "/release",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterface> releaseOpenapiInterface(@RequestBody OpenapiInterfaceReleaseVO openapiInterfaceDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            //------------------------操作日志处理-----------------------------------------------------------------------
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            Integer flag = interfaceFacade.releaseOpenapiInterface(openapiInterfaceDeleteVO);
            if (flag>0) {
                resultVo.setResultDes("接口发布状态修改成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("接口发布状态修改失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口发布状态修改","接口发布状态修改："+openapiInterfaceDeleteVO.getIds(), DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("接口发布异常，原因为:"+e);
            log.error("接口发布异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "接口生效状态修改")
    @RequestMapping(value = "/setStatus",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterface> setStatusOpenapiInterface(@RequestBody OpenapiInterfaceSetStatusVO openapiInterfaceSetStatusVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = interfaceFacade.setStatusOpenapiInterface(openapiInterfaceSetStatusVO);
            if (1 == flag) {
                resultVo.setResultDes("接口生效状态修改成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("接口生效状态修改失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口生效状态修改","接口生效状态修改："+openapiInterfaceSetStatusVO.getId(), DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("接口生效状态修改异常，原因为:"+e);
            log.error("接口删除异常",e);
        }
        return resultVo;
    }



}
