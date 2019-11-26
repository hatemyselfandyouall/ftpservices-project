package com.insigma.web.frontGround.seflMachineManager;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineAddressFacade;
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
import com.insigma.facade.openapi.po.OpenapiSelfmachineAddress;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressSaveVO;


@RestController
@RequestMapping("openapiSelfmachineAddress")
@Api(tags ="自助机地址管理-用户")
@Slf4j
public class OpenapiSelfmachineAddressController extends BasicController {

    @Autowired
    OpenapiSelfmachineAddressFacade openapiSelfmachineAddressFacade;
    @Autowired
    LoginComponent loginComponent;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineAddress> getOpenapiSelfmachineAddressList(@RequestBody OpenapiSelfmachineAddressListVO openapiSelfmachineAddressListVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            PageInfo<OpenapiSelfmachineAddress> openapiSelfmachineAddressList=openapiSelfmachineAddressFacade.getOpenapiSelfmachineAddressList(openapiSelfmachineAddressListVO,userId);
            if(openapiSelfmachineAddressList!=null){
                DataListResultDto<OpenapiSelfmachineAddress> dataListResultDto=new DataListResultDto<>(openapiSelfmachineAddressList.getList(),(int)openapiSelfmachineAddressList.getTotal());
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
    public ResultVo<OpenapiSelfmachineAddress> getOpenapiSelfmachineAddressDetail(@RequestBody OpenapiSelfmachineAddressDetailVO openapiSelfmachineAddressDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiSelfmachineAddress openapiSelfmachineAddress=openapiSelfmachineAddressFacade.getOpenapiSelfmachineAddressDetail(openapiSelfmachineAddressDetailVO);
        if(openapiSelfmachineAddress!=null){
            resultVo.setResult(openapiSelfmachineAddress);
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
    public ResultVo<OpenapiSelfmachineAddress> saveOpenapiSelfmachineAddress(@RequestBody OpenapiSelfmachineAddressSaveVO openapiSelfmachineAddressSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            String userName=loginComponent.getLoginUserName();
            Integer flag = openapiSelfmachineAddressFacade.saveOpenapiSelfmachineAddress(openapiSelfmachineAddressSaveVO,userId,userName);
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
    public ResultVo<OpenapiSelfmachineAddress> deleteOpenapiSelfmachineAddress(@RequestBody OpenapiSelfmachineAddressDeleteVO openapiSelfmachineAddressDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            Integer flag = openapiSelfmachineAddressFacade.deleteOpenapiSelfmachineAddress(openapiSelfmachineAddressDeleteVO,userId);
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


}
