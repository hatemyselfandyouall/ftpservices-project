package com.insigma.web.backGround.selfHelpMachine;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiOrgFacade;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.vo.OpenapiApp.ResetAppSecretVO;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.vo.result.ResultVo;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgListVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgSaveVO;


@RestController
@RequestMapping("openapiOrg")
@Api(tags ="开放平台机构管理")
@Slf4j
public class OpenapiOrgController extends BasicController {

    @Autowired
    OpenapiOrgFacade openapiOrgFacade;
    @Autowired
    LoginComponent loginComponent;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> getOpenapiOrgList(@RequestBody OpenapiOrgListVO openapiOrgListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiOrg> openapiOrgList=openapiOrgFacade.getOpenapiOrgList(openapiOrgListVO);
            if(openapiOrgList!=null){
                DataListResultDto<OpenapiOrg> dataListResultDto=new DataListResultDto<>(openapiOrgList.getList(),(int)openapiOrgList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取列表异常");
            log.error("获取列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> getOpenapiOrgDetail(@RequestBody OpenapiOrgDetailVO openapiOrgDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiOrg openapiOrg=openapiOrgFacade.getOpenapiOrgDetail(openapiOrgDetailVO);
        if(openapiOrg!=null){
            resultVo.setResult(openapiOrg);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
        } catch (Exception e){
        resultVo.setResultDes("获取详情异常");
        log.error("获取详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> saveOpenapiOrg(@RequestBody OpenapiOrgSaveVO openapiOrgSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            String userName=loginComponent.getLoginUserName();
            Integer flag = openapiOrgFacade.saveOpenapiOrg(openapiOrgSaveVO,userId,userName);
            if (1 == flag) {
                resultVo.setResultDes("保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("保存失败");
            }
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> deleteOpenapiOrg(@RequestBody OpenapiOrgDeleteVO openapiOrgDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiOrgFacade.deleteOpenapiOrg(openapiOrgDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("删除异常");
            log.error("删除异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "开放平台机构重设Appsecret")
    @RequestMapping(value = "/resetAppSecret",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> resetAppSecret(@RequestBody ResetAppSecretVO resetAppSecretVO){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiOrg openapiApp = openapiOrgFacade.resetAppSecret(resetAppSecretVO);
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
