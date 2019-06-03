package com.insigma.web.backGround.application;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiAppFacade;
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
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.vo.OpenapiApp.OpenapiAppDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiApp.OpenapiAppDetailVO;
import com.insigma.facade.openapi.vo.OpenapiApp.OpenapiAppListVO;
import com.insigma.facade.openapi.vo.OpenapiApp.OpenapiAppSaveVO;


@RestController
@RequestMapping("openapiApp")
@Api(tags ="开放平台应用管理")
@Slf4j
public class OpenapiAppController extends BasicController {

    @Autowired
    OpenapiAppFacade openapiAppFacade;

    @ApiOperation(value = "开放平台应用列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> getOpenapiAppList(@RequestBody OpenapiAppListVO OpenapiAppListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiApp> OpenapiAppList=openapiAppFacade.getOpenapiAppList(OpenapiAppListVO);
            if(OpenapiAppList!=null){
                DataListResultDto<OpenapiApp> dataListResultDto=new DataListResultDto<>(OpenapiAppList.getList(),(int)OpenapiAppList.getTotal());
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

    @ApiOperation(value = "开放平台应用详情")
    @RequestMapping(value = "/detail",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
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
            Integer flag = openapiAppFacade.saveOpenapiApp(OpenapiAppSaveVO);
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


}
