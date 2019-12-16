package com.insigma.web.backGround.audit;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiAuditFacade;
import com.insigma.facade.openapi.po.OpenapiAudit;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditDetailVO;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditListVO;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditSaveVO;
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

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("openapiAudit")
@Api(tags ="审核管理")
@Slf4j
public class OpenapiAuditController extends BasicController {

    @Autowired
    OpenapiAuditFacade openapiAuditFacade;
    @Autowired
    private LoginComponent loginComponent;
    @Autowired
    SysOperatelogRecordFacade sysOperatelogRecordFacade;
    @ApiOperation(value = "审核信息列表查询")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiAudit> getOpenapiAuditList(@RequestBody OpenapiAuditListVO openapiAuditListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiAudit> openapiAuditList=openapiAuditFacade.getOpenapiAuditList(openapiAuditListVO);
            if(openapiAuditList!=null){
                DataListResultDto<OpenapiAudit> dataListResultDto=new DataListResultDto<>(openapiAuditList.getList(),(int)openapiAuditList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
                resultVo.setResultDes("查询成功！");
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"审核信息列表查询","审核信息列表查询", DataConstant.SYSTEM_NAME,"审核管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取列表异常");
            log.error("获取列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "根据id查询审核详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiAudit> getOpenapiAuditDetail(@RequestBody OpenapiAuditDetailVO openapiAuditDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiAudit openapiAudit=openapiAuditFacade.getOpenapiAuditDetail(openapiAuditDetailVO);
        if(openapiAudit!=null){
            resultVo.setResult(openapiAudit);
            resultVo.setSuccess(true);
            resultVo.setResultDes("查询成功！");
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"根据id查询审核详情","根据id:"+openapiAudit.getId()+"查询审核详情", DataConstant.SYSTEM_NAME,"审核管理",sysOperatelogRecordFacade);
           }else {
            resultVo.setResultDes("获取详情失败");
         }
        } catch (Exception e){
        resultVo.setResultDes("获取详情异常");
        log.error("获取详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "审核信息保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiAudit> saveOpenapiAudit(@RequestBody OpenapiAuditSaveVO openapiAuditSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiAuditFacade.saveOpenapiAudit(openapiAuditSaveVO);
            if (flag > 0) {
                if (openapiAuditSaveVO.getIds()==null){
                    AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                            ,"审核信息保存","新增审核信息id:"+flag, DataConstant.SYSTEM_NAME,"审核管理",sysOperatelogRecordFacade);
                }else{
                    AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                            ,"审核信息修改","修改审核信息id:"+flag, DataConstant.SYSTEM_NAME,"审核管理",sysOperatelogRecordFacade);
                }
                resultVo.setResultDes("操作成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setSuccess(false);
                resultVo.setResultDes("操作失败");
            }


        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("保存异常",e);
            }
        return resultVo;
    }

//    @ApiOperation(value = "删除")
//    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiAudit> deleteOpenapiAudit(@RequestBody OpenapiAuditDeleteVO openapiAuditDeleteVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Integer flag = openapiAuditFacade.deleteOpenapiAudit(openapiAuditDeleteVO);
//            if (1 == flag) {
//                resultVo.setResultDes("删除成功");
//                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("删除失败");
//            }
//        }catch (Exception e){
//            resultVo.setResultDes("删除异常");
//            log.error("删除异常",e);
//        }
//        return resultVo;
//    }


}
