//package com.insigma.web.backGround.interfaceManager;
//
//import com.github.pagehelper.PageInfo;
//import com.insigma.facade.openapi.dto.DataListResultDto;
//import com.insigma.facade.openapi.facade.OpenapiInterfaceGroupFacade;
//import com.insigma.facade.openapi.po.OpenapiInterfaceGroup;
//import com.insigma.facade.openapi.vo.OpenapiInterfaceGroup.*;
//import com.insigma.web.BasicController;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import star.util.ExceptionUtil;
//import star.vo.result.ResultVo;
//
//
//@RestController
//@RequestMapping("openapiInterfaceGroup")
//@Api(tags ="接口组管理")
//@Slf4j
//public class OpenapiInterfaceGroupController extends BasicController {
//
//    @Autowired
//    OpenapiInterfaceGroupFacade openapiInterfaceGroupFacade;
//
//    @ApiOperation(value = "接口组列表")
//    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiInterfaceGroupDetailShowVO> getOpenapiInterfaceGroupList(@RequestBody OpenapiInterfaceGroupListVO OpenapiInterfaceGroupListVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            PageInfo<OpenapiInterfaceGroupDetailShowVO> OpenapiInterfaceGroupList=openapiInterfaceGroupFacade.getOpenapiInterfaceGroupList(OpenapiInterfaceGroupListVO);
//            if(OpenapiInterfaceGroupList!=null){
//                DataListResultDto<OpenapiInterfaceGroupDetailShowVO> dataListResultDto=new DataListResultDto<>(OpenapiInterfaceGroupList.getList(),(int)OpenapiInterfaceGroupList.getTotal());
//                resultVo.setResult(dataListResultDto);
//                resultVo.setSuccess(true);
//            }else {
//                resultVo.setResultDes("分页数据缺失");
//            }
//        }catch (Exception e){
//            resultVo.setResultDes("获取接口组列表异常");
//            log.error("获取接口组列表异常，esg={}", ExceptionUtil.getMessage(e));
//        }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "接口组详情")
//    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiInterfaceGroup> getOpenapiInterfaceGroupDetail(@RequestBody OpenapiInterfaceGroupDetailVO OpenapiInterfaceGroupDetailVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//        OpenapiInterfaceGroup OpenapiInterfaceGroup=openapiInterfaceGroupFacade.getOpenapiInterfaceGroupDetail(OpenapiInterfaceGroupDetailVO);
//        if(OpenapiInterfaceGroup!=null){
//            resultVo.setResult(OpenapiInterfaceGroup);
//            resultVo.setSuccess(true);
//        }else {
//            resultVo.setResultDes("获取详情失败");
//        }
//        } catch (Exception e){
//        resultVo.setResultDes("获取接口组详情异常，原因为:"+e);
//        log.error("获取接口组详情异常",e);
//    }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "接口组保存")
//    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiInterfaceGroup> saveOpenapiInterfaceGroup(@RequestBody OpenapiInterfaceGroupSaveVO OpenapiInterfaceGroupSaveVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Integer flag = openapiInterfaceGroupFacade.saveOpenapiInterfaceGroup(OpenapiInterfaceGroupSaveVO);
//            if (1 == flag) {
//                resultVo.setResultDes("接口组保存成功");
//                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("接口组保存失败");
//            }
//        }catch (Exception e){
//                resultVo.setResultDes("接口保存异常，原因为:"+e);
//                log.error("接口组保存异常",e);
//            }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "接口组删除")
//    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiInterfaceGroup> deleteOpenapiInterfaceGroup(@RequestBody OpenapiInterfaceGroupDeleteVO OpenapiInterfaceGroupDeleteVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Integer flag = openapiInterfaceGroupFacade.deleteOpenapiInterfaceGroup(OpenapiInterfaceGroupDeleteVO);
//            if (1 == flag) {
//                resultVo.setResultDes("接口组删除成功");
//                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("接口组删除失败");
//            }
//        }catch (Exception e){
//            resultVo.setResultDes("接口组删除异常，原因为:"+e);
//            log.error("接口组删除异常",e);
//        }
//        return resultVo;
//    }
//
//
//}
