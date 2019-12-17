package com.insigma.web.backGround.application;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiAppTypeFacade;
import com.insigma.facade.operatelog.facade.SysOperatelogRecordFacade;
import com.insigma.util.AddLogUtil;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.fw.web.util.ServletAttributes;
import star.vo.result.ResultVo;
import com.insigma.facade.openapi.po.OpenapiAppType;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeDetailVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeListVO;
import com.insigma.facade.openapi.vo.OpenapiAppType.OpenapiAppTypeSaveVO;


@RestController
@RequestMapping("openapiAppType")
@Api(tags ="开放平台应用类型管理")
@Slf4j
public class OpenapiAppTypeController extends BasicController {

    @Autowired
    OpenapiAppTypeFacade openapiAppTypeFacade;
    @Autowired
    LoginComponent loginComponent;
    @Autowired
    SysOperatelogRecordFacade sysOperatelogRecordFacade;

    @ApiOperation(value = "开放平台应用类型列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiAppType> getOpenapiAppTypeList(@RequestBody OpenapiAppTypeListVO openapiAppTypeListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiAppType> openapiAppTypeList=openapiAppTypeFacade.getOpenapiAppTypeList(openapiAppTypeListVO);
            if(openapiAppTypeList!=null){
                DataListResultDto<OpenapiAppType> dataListResultDto=new DataListResultDto<>(openapiAppTypeList.getList(),(int)openapiAppTypeList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"开放平台应用类型列表","开放平台应用类型列表", DataConstant.SYSTEM_NAME,"应用管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取开放平台应用类型列表异常");
            log.error("获取开放平台应用类型列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "开放平台应用类型详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiAppType> getOpenapiAppTypeDetail(@RequestBody OpenapiAppTypeDetailVO openapiAppTypeDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiAppType openapiAppType=openapiAppTypeFacade.getOpenapiAppTypeDetail(openapiAppTypeDetailVO);
        if(openapiAppType!=null){
            resultVo.setResult(openapiAppType);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"开放平台应用类型详情","开放平台应用类型详情id:"+openapiAppType.getId(), DataConstant.SYSTEM_NAME,"应用管理",sysOperatelogRecordFacade);
        } catch (Exception e){
        resultVo.setResultDes("获取开放平台应用类型详情异常");
        log.error("获取开放平台应用类型详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "开放平台应用类型保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiAppType> saveOpenapiAppType(@RequestBody OpenapiAppTypeSaveVO openapiAppTypeSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            String userName=loginComponent.getLoginUserName();
            Long userId=loginComponent.getLoginUserId();
            ResultVo checkResult=openapiAppTypeFacade.checkAppInterfaceTypeSave(openapiAppTypeSaveVO);
            if (!checkResult.isSuccess()){
                return checkResult;
            }
            Integer flag = openapiAppTypeFacade.saveOpenapiAppType(openapiAppTypeSaveVO,userId,userName);
            if (1 == flag) {
                resultVo.setResultDes("开放平台应用类型保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("开放平台应用类型保存失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"开放平台应用类型保存","开放平台应用类型保存:"+flag, DataConstant.SYSTEM_NAME,"应用管理",sysOperatelogRecordFacade);
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("开放平台应用类型保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "开放平台应用类型删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiAppType> deleteOpenapiAppType(@RequestBody OpenapiAppTypeDeleteVO openapiAppTypeDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            ResultVo checkResult=openapiAppTypeFacade.checkDeleteOpenapiAppType(openapiAppTypeDeleteVO);
            if (!checkResult.isSuccess()){
                return checkResult;
            }
            Integer flag = openapiAppTypeFacade.deleteOpenapiAppType(openapiAppTypeDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("开放平台应用类型删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("开放平台应用类型删除失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"开放平台应用类型删除","开放平台应用类型删除"+openapiAppTypeDeleteVO.getId(), DataConstant.SYSTEM_NAME,"应用管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("开放平台应用类型删除异常");
            log.error("开放平台应用类型删除异常",e);
        }
        return resultVo;
    }


}
