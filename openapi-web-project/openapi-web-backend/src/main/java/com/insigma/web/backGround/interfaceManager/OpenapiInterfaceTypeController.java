package com.insigma.web.backGround.interfaceManager;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiInterfaceTypeFacade;
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
import com.insigma.facade.openapi.po.OpenapiInterfaceType;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceType.OpenapiInterfaceTypeSaveVO;


@RestController
@RequestMapping("openapiInterfaceType")
@Api(tags ="接口分类管理")
@Slf4j
public class OpenapiInterfaceTypeController extends BasicController {

    @Autowired
    OpenapiInterfaceTypeFacade openapiInterfaceTypeFacade;
    @Autowired
    LoginComponent loginComponent;
    @Autowired
    SysOperatelogRecordFacade sysOperatelogRecordFacade;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceType> getOpenapiInterfaceTypeList(@RequestBody OpenapiInterfaceTypeListVO openapiInterfaceTypeListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiInterfaceType> openapiInterfaceTypeList=openapiInterfaceTypeFacade.getOpenapiInterfaceTypeList(openapiInterfaceTypeListVO);
            if(openapiInterfaceTypeList!=null){
                DataListResultDto<OpenapiInterfaceType> dataListResultDto=new DataListResultDto<>(openapiInterfaceTypeList.getList(),(int)openapiInterfaceTypeList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口分类管理","接口分类管理", DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取列表异常");
            log.error("获取列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceType> getOpenapiInterfaceTypeDetail(@RequestBody OpenapiInterfaceTypeDetailVO openapiInterfaceTypeDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiInterfaceType openapiInterfaceType=openapiInterfaceTypeFacade.getOpenapiInterfaceTypeDetail(openapiInterfaceTypeDetailVO);
        if(openapiInterfaceType!=null){
            resultVo.setResult(openapiInterfaceType);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口分类详情","接口分类详情"+openapiInterfaceType.getId(), DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        } catch (Exception e){
        resultVo.setResultDes("获取详情异常");
        log.error("获取详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceType> saveOpenapiInterfaceType(@RequestBody OpenapiInterfaceTypeSaveVO openapiInterfaceTypeSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            ResultVo checkResult=openapiInterfaceTypeFacade.checkSave(openapiInterfaceTypeSaveVO);
            if (!checkResult.isSuccess()){
                return checkResult;
            }
            Integer flag = openapiInterfaceTypeFacade.saveOpenapiInterfaceType(openapiInterfaceTypeSaveVO);
            if (1 == flag) {
                resultVo.setResultDes("保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("保存失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口分类保存","接口分类保存"+flag, DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceType> deleteOpenapiInterfaceType(@RequestBody OpenapiInterfaceTypeDeleteVO openapiInterfaceTypeDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            if (openapiInterfaceTypeFacade.hasChildInterface(openapiInterfaceTypeDeleteVO)){
                resultVo.setResultDes("当前分类下有有效接口或已发布，不可删除");
                return resultVo;
            }
            Integer flag = openapiInterfaceTypeFacade.deleteOpenapiInterfaceType(openapiInterfaceTypeDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("删除失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口分类删除","接口分类删除"+openapiInterfaceTypeDeleteVO.getId(), DataConstant.SYSTEM_NAME,"接口管理",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("删除异常");
            log.error("删除异常",e);
        }
        return resultVo;
    }


}
