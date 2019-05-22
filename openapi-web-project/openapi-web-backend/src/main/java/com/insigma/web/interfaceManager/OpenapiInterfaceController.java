package com.insigma.web.interfaceManager;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.*;

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
@RequestMapping("interface")
@Api(tags ="接口管理")
@Slf4j
public class OpenapiInterfaceController extends BasicController {

    @Autowired
    InterfaceFacade interfaceFacade;

    @ApiOperation(value = "接口列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterface> getOpenapiInterfaceList(@RequestBody OpenapiInterfaceListVO openapiInterfaceListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiInterface> openapiInterfaceList=interfaceFacade.getOpenapiInterfaceList(openapiInterfaceListVO);
            if(openapiInterfaceList!=null){
                DataListResultDto<OpenapiInterface> dataListResultDto=new DataListResultDto<>(openapiInterfaceList.getList(),(int)openapiInterfaceList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取接口列表异常，原因为:"+e);
            log.error("获取接口列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "接口详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterface> getOpenapiInterfaceDetail(@RequestBody OpenapiInterfaceDetailVO openapiInterfaceDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiInterfaceShowVO openapiInterface=interfaceFacade.getOpenapiInterfaceDetail(openapiInterfaceDetailVO);
        if(openapiInterface!=null){
            resultVo.setResult(openapiInterface);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
        } catch (Exception e){
        resultVo.setResultDes("获取接口详情异常，原因为:"+e);
        log.error("获取接口详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "接口保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterface> saveOpenapiInterface(@RequestBody OpenapiInterfaceSaveVO openapiInterfaceSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = interfaceFacade.saveOpenapiInterface(openapiInterfaceSaveVO);
            if (1 == flag) {
                resultVo.setResultDes("接口保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("接口保存失败");
            }
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
            Integer flag = interfaceFacade.deleteOpenapiInterface(openapiInterfaceDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("接口删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("接口删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("接口删除异常，原因为:"+e);
            log.error("接口删除异常",e);
        }
        return resultVo;
    }


}
