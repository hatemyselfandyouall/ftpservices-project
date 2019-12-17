package com.insigma.web.backGround.selfHelpMachine;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineRequestFacade;
import com.insigma.facade.operatelog.facade.SysOperatelogRecordFacade;
import com.insigma.util.AddLogUtil;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.fw.web.util.ServletAttributes;
import star.vo.result.ResultVo;
import com.insigma.facade.openapi.po.OpenapiSelfmachineRequest;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;


@RestController
@RequestMapping("openapiSelfmachineRequest")
@Api(tags ="自助机请求管理")
@Slf4j
public class OpenapiSelfmachineRequestController extends BasicController {

    @Autowired
    OpenapiSelfmachineRequestFacade openapiSelfmachineRequestFacade;
    @Autowired
    LoginComponent loginComponent;
    @Autowired
    SysOperatelogRecordFacade sysOperatelogRecordFacade;

    @ApiOperation(value = "自助机请求列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineRequest> getOpenapiSelfmachineRequestList(@RequestBody OpenapiSelfmachineRequestListVO openapiSelfmachineRequestListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiSelfmachineRequest> openapiSelfmachineRequestList=openapiSelfmachineRequestFacade.getOpenapiSelfmachineRequestList(openapiSelfmachineRequestListVO);
            if(openapiSelfmachineRequestList!=null){
                DataListResultDto<OpenapiSelfmachineRequest> dataListResultDto=new DataListResultDto<>(openapiSelfmachineRequestList.getList(),(int)openapiSelfmachineRequestList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"自助机请求列表","自助机请求列表", DataConstant.SYSTEM_NAME,"自助机请求管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取自助机请求列表异常");
            log.error("获取自助机请求列表异常",e);
        }
        return resultVo;
    }

//    @ApiOperation(value = "自助机请求详情")
//    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiSelfmachineRequest> getOpenapiSelfmachineRequestDetail(@RequestBody OpenapiSelfmachineRequestDetailVO openapiSelfmachineRequestDetailVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//        OpenapiSelfmachineRequest openapiSelfmachineRequest=openapiSelfmachineRequestFacade.getOpenapiSelfmachineRequestDetail(openapiSelfmachineRequestDetailVO);
//        if(openapiSelfmachineRequest!=null){
//            resultVo.setResult(openapiSelfmachineRequest);
//            resultVo.setSuccess(true);
//        }else {
//            resultVo.setResultDes("获取详情失败");
//        }
//        } catch (Exception e){
//        resultVo.setResultDes("获取自助机请求详情异常");
//        log.error("获取自助机请求详情异常",e);
//    }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "自助机请求保存")
//    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiSelfmachineRequest> saveOpenapiSelfmachineRequest(@RequestBody OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Integer flag = openapiSelfmachineRequestFacade.saveOpenapiSelfmachineRequest(openapiSelfmachineRequestSaveVO);
//            if (1 == flag) {
//                resultVo.setResultDes("自助机请求保存成功");
//                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("自助机请求保存失败");
//            }
//        }catch (Exception e){
//                resultVo.setResultDes("接口保存异常");
//                log.error("自助机请求保存异常",e);
//            }
//        return resultVo;
//    }

    @ApiOperation(value = "自助机设置白名单或黑名单")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineRequest> deleteOpenapiSelfmachineRequest(@RequestBody OpenapiSelfmachineRequestDeleteVO openapiSelfmachineRequestDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiSelfmachineRequestFacade.deleteOpenapiSelfmachineRequest(openapiSelfmachineRequestDeleteVO);
            if ( flag!=null&&flag>0) {
                resultVo.setResultDes("自助机设置白名单或黑名单成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("自助机设置白名单或黑名单失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"自助机设置白名单或黑名单","自助机设置白名单或黑名单", DataConstant.SYSTEM_NAME,"自助机请求管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("自助机设置白名单或黑名单异常");
            log.error("自助机请求删除异常",e);
        }
        return resultVo;
    }


}
