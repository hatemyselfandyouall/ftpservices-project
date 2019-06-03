//package com.insigma.web.backGround.user;
//
//import com.github.pagehelper.PageInfo;
//import com.insigma.facade.openapi.dto.DataListResultDto;
//import com.insigma.facade.openapi.facade.OpenapiOrgInterfaceFacade;
//import com.insigma.facade.openapi.po.OpenapiOrgInterface;
//import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceDeleteVO;
//import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceDetailVO;
//import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceListVO;
//import com.insigma.facade.openapi.vo.OpenapiOrgInterface.OpenapiOrgInterfaceSaveVO;
//import com.insigma.web.BasicController;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import star.vo.result.ResultVo;
//
//
//
//@RestController
//@RequestMapping("openapiOrgInterface")
//@Api(tags ="机构-接口管理")
//@Slf4j
//public class OpenapiOrgInterfaceController extends BasicController {
//
//    @Autowired
//    OpenapiOrgInterfaceFacade openapiOrgInterfaceFacade;
//
//    @ApiOperation(value = "机构-接口列表")
//    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiOrgInterface> getOpenapiOrgInterfaceList(@RequestBody OpenapiOrgInterfaceListVO OpenapiOrgInterfaceListVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            PageInfo<OpenapiOrgInterface> OpenapiOrgInterfaceList=openapiOrgInterfaceFacade.getOpenapiOrgInterfaceList(OpenapiOrgInterfaceListVO);
//            if(OpenapiOrgInterfaceList!=null){
//                DataListResultDto<OpenapiOrgInterface> dataListResultDto=new DataListResultDto<>(OpenapiOrgInterfaceList.getList(),(int)OpenapiOrgInterfaceList.getTotal());
//                resultVo.setResult(dataListResultDto);
//                resultVo.setSuccess(true);
//            }else {
//                resultVo.setResultDes("分页数据缺失");
//            }
//        }catch (Exception e){
//            resultVo.setResultDes("获取机构-接口列表异常，原因为:"+e);
//            log.error("获取机构-接口列表异常",e);
//        }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "机构-接口详情")
//    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiOrgInterface> getOpenapiOrgInterfaceDetail(@RequestBody OpenapiOrgInterfaceDetailVO OpenapiOrgInterfaceDetailVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//        OpenapiOrgInterface OpenapiOrgInterface=openapiOrgInterfaceFacade.getOpenapiOrgInterfaceDetail(OpenapiOrgInterfaceDetailVO);
//        if(OpenapiOrgInterface!=null){
//            resultVo.setResult(OpenapiOrgInterface);
//            resultVo.setSuccess(true);
//        }else {
//            resultVo.setResultDes("获取详情失败");
//        }
//        } catch (Exception e){
//        resultVo.setResultDes("获取机构-接口详情异常，原因为:"+e);
//        log.error("获取机构-接口详情异常",e);
//    }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "机构-接口保存")
//    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiOrgInterface> saveOpenapiOrgInterface(@RequestBody OpenapiOrgInterfaceSaveVO OpenapiOrgInterfaceSaveVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Integer flag = openapiOrgInterfaceFacade.saveOpenapiOrgInterface(OpenapiOrgInterfaceSaveVO);
//            if (1 == flag) {
//                resultVo.setResultDes("机构-接口保存成功");
//                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("机构-接口保存失败");
//            }
//        }catch (Exception e){
//                resultVo.setResultDes("接口保存异常，原因为:"+e);
//                log.error("机构-接口保存异常",e);
//            }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "机构-接口删除")
//    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiOrgInterface> deleteOpenapiOrgInterface(@RequestBody OpenapiOrgInterfaceDeleteVO OpenapiOrgInterfaceDeleteVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Integer flag = openapiOrgInterfaceFacade.deleteOpenapiOrgInterface(OpenapiOrgInterfaceDeleteVO);
//            if (1 == flag) {
//                resultVo.setResultDes("机构-接口删除成功");
//                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("机构-接口删除失败");
//            }
//        }catch (Exception e){
//            resultVo.setResultDes("机构-接口删除异常，原因为:"+e);
//            log.error("机构-接口删除异常",e);
//        }
//        return resultVo;
//    }
//
//
//}
