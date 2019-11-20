package com.insigma.web.backGround.selfHelpMachine;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineFacade;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.*;
import com.insigma.web.BasicController;
import com.insigma.webtool.util.Encrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.vo.result.ResultVo;
import com.insigma.facade.openapi.po.OpenapiSelfmachine;


@RestController
@RequestMapping("openapiSelfmachine")
@Api(tags ="自助机管理")
@Slf4j
public class OpenapiSelfmachineController extends BasicController {

    @Autowired
    OpenapiSelfmachineFacade openapiSelfmachineFacade;

    @ApiOperation(value = "自助机列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(@RequestBody OpenapiSelfmachineListVO openapiSelfmachineListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiSelfmachineShowVO> openapiSelfmachineList=openapiSelfmachineFacade.getOpenapiSelfmachineList(openapiSelfmachineListVO);
            if(openapiSelfmachineList!=null){
                DataListResultDto<OpenapiSelfmachineShowVO> dataListResultDto=new DataListResultDto<>(openapiSelfmachineList.getList(),(int)openapiSelfmachineList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取自助机列表异常");
            log.error("获取自助机列表异常",e);
        }
        return resultVo;
    }

//    @ApiOperation(value = "自助机详情")
//    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiSelfmachine> getOpenapiSelfmachineDetail(@RequestBody OpenapiSelfmachineDetailVO openapiSelfmachineDetailVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//        OpenapiSelfmachine openapiSelfmachine=openapiSelfmachineFacade.getOpenapiSelfmachineDetail(openapiSelfmachineDetailVO);
//        if(openapiSelfmachine!=null){
//            resultVo.setResult(openapiSelfmachine);
//            resultVo.setSuccess(true);
//        }else {
//            resultVo.setResultDes("获取详情失败");
//        }
//        } catch (Exception e){
//        resultVo.setResultDes("获取自助机详情异常");
//        log.error("获取自助机详情异常",e);
//    }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "自助机保存")
//    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiSelfmachine> saveOpenapiSelfmachine(@RequestBody OpenapiSelfmachineSaveVO openapiSelfmachineSaveVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Integer flag = openapiSelfmachineFacade.saveOpenapiSelfmachine(openapiSelfmachineSaveVO);
//            if (1 == flag) {
//                resultVo.setResultDes("自助机保存成功");
//                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("自助机保存失败");
//            }
//        }catch (Exception e){
//                resultVo.setResultDes("接口保存异常");
//                log.error("自助机保存异常",e);
//            }
//        return resultVo;
//    }

    @ApiOperation(value = "自助机设置状态")
    @RequestMapping(value = "/setStatu",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachine> setStatu(@RequestBody OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiSelfmachineFacade.setStatu(openapiSelfmachineDeleteVO);
            if (flag!=null&&flag>0) {
                resultVo.setResultDes("自助机设置状态成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("自助机设置状态失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("自助机设置状态异常");
            log.error("自助机设置状态异常",e);
        }
        return resultVo;
    }

    public static void main(String[] args) {
        String test=Encrypt.desEncrypt("knQ0f9xh35XvtZZZ2eaVhBVghHR33bPJiYEvg1EkALpWWNFFHg9hzRrKfTRVPO+3IoInksWzqqv5ZlavdxTfyi+YJdbqu89+lJcE5phbabv8ScooKKS+MsB3c0gtF+7o");
        System.out.println(test);
    }

}