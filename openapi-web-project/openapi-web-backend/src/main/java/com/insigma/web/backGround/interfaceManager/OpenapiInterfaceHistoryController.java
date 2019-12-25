package com.insigma.web.backGround.interfaceManager;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiInterfaceHistoryFacade;
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
import com.insigma.facade.openapi.po.OpenapiInterfaceHistory;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistorySaveVO;

@RestController
@RequestMapping("openapiInterfaceHistory")
@Api(tags ="接口历史管理")
@Slf4j
public class OpenapiInterfaceHistoryController extends BasicController {

    @Autowired
    OpenapiInterfaceHistoryFacade openapiInterfaceHistoryFacade;
    @Autowired
    LoginComponent loginComponent;
    @Autowired
    SysOperatelogRecordFacade sysOperatelogRecordFacade;


    @ApiOperation(value = "接口历史列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceHistory> getOpenapiInterfaceHistoryList(@RequestBody OpenapiInterfaceHistoryListVO openapiInterfaceHistoryListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiInterfaceHistory> openapiInterfaceHistoryList=openapiInterfaceHistoryFacade.getOpenapiInterfaceHistoryList(openapiInterfaceHistoryListVO);
            if(openapiInterfaceHistoryList!=null){
                DataListResultDto<OpenapiInterfaceHistory> dataListResultDto=new DataListResultDto<>(openapiInterfaceHistoryList.getList(),(int)openapiInterfaceHistoryList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"审核信息列表查询","审核信息列表查询", DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取接口历史列表异常");
            log.error("获取接口历史列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "接口历史详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceHistory> getOpenapiInterfaceHistoryDetail(@RequestBody OpenapiInterfaceHistoryDetailVO openapiInterfaceHistoryDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiInterfaceHistory openapiInterfaceHistory=openapiInterfaceHistoryFacade.getOpenapiInterfaceHistoryDetail(openapiInterfaceHistoryDetailVO);
        if(openapiInterfaceHistory!=null){
            resultVo.setResult(openapiInterfaceHistory);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口历史详情","接口历史详情"+openapiInterfaceHistoryDetailVO.getId(), DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        } catch (Exception e){
        resultVo.setResultDes("获取接口历史详情异常");
        log.error("获取接口历史详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "接口历史保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceHistory> saveOpenapiInterfaceHistory(@RequestBody OpenapiInterfaceHistorySaveVO openapiInterfaceHistorySaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            String userName=loginComponent.getLoginUserName();
            Integer flag = openapiInterfaceHistoryFacade.saveOpenapiInterfaceHistory(openapiInterfaceHistorySaveVO,userId,userName);
            if (1 == flag) {
                resultVo.setResultDes("接口历史保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("接口历史保存失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口历史保存","接口历史保存"+flag, DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("接口历史保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "接口历史删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceHistory> deleteOpenapiInterfaceHistory(@RequestBody OpenapiInterfaceHistoryDeleteVO openapiInterfaceHistoryDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {

            Integer flag = openapiInterfaceHistoryFacade.deleteOpenapiInterfaceHistory(openapiInterfaceHistoryDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("接口历史删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("接口历史删除失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口历史删除","接口历史删除"+openapiInterfaceHistoryDeleteVO.getId(), DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("接口历史删除异常");
            log.error("接口历史删除异常",e);
        }
        return resultVo;
    }


}
