//package com.insigma.web.backGround.user;
//
//import com.github.pagehelper.PageInfo;
//import com.insigma.facade.openapi.dto.DataListResultDto;
//import com.insigma.facade.openapi.facade.OpenapiOrgFacade;
//import com.insigma.facade.openapi.po.OpenapiOrg;
//import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDeleteVO;
//import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDetailVO;
//import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgListVO;
//import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgSaveVO;
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
//@RequestMapping("openapiOrg")
//@Api(tags ="机构管理")
//@Slf4j
//public class OpenapiOrgController extends BasicController {
//
//    @Autowired
//    OpenapiOrgFacade openapiOrgFacade;
//
//    @ApiOperation(value = "机构列表")
//    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiOrg> getOpenapiOrgList(@RequestBody OpenapiOrgListVO OpenapiOrgListVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            PageInfo<OpenapiOrg> OpenapiOrgList=openapiOrgFacade.getOpenapiOrgList(OpenapiOrgListVO);
//            if(OpenapiOrgList!=null){
//                DataListResultDto<OpenapiOrg> dataListResultDto=new DataListResultDto<>(OpenapiOrgList.getList(),(int)OpenapiOrgList.getTotal());
//                resultVo.setResult(dataListResultDto);
//                resultVo.setSuccess(true);
//            }else {
//                resultVo.setResultDes("分页数据缺失");
//            }
//        }catch (Exception e){
//            resultVo.setResultDes("获取机构列表异常，原因为:"+e);
//            log.error("获取机构列表异常",e);
//        }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "机构详情")
//    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiOrg> getOpenapiOrgDetail(@RequestBody OpenapiOrgDetailVO OpenapiOrgDetailVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//        OpenapiOrg OpenapiOrg=openapiOrgFacade.getOpenapiOrgDetail(OpenapiOrgDetailVO);
//        if(OpenapiOrg!=null){
//            resultVo.setResult(OpenapiOrg);
//            resultVo.setSuccess(true);
//        }else {
//            resultVo.setResultDes("获取详情失败");
//        }
//        } catch (Exception e){
//        resultVo.setResultDes("获取机构详情异常，原因为:"+e);
//        log.error("获取机构详情异常",e);
//    }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "机构保存")
//    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiOrg> saveOpenapiOrg(@RequestBody OpenapiOrgSaveVO OpenapiOrgSaveVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Integer flag = openapiOrgFacade.saveOpenapiOrg(OpenapiOrgSaveVO);
//            if (1 == flag) {
//                resultVo.setResultDes("机构保存成功");
//                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("机构保存失败");
//            }
//        }catch (Exception e){
//                resultVo.setResultDes("接口保存异常，原因为:"+e);
//                log.error("机构保存异常",e);
//            }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "机构删除")
//    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiOrg> deleteOpenapiOrg(@RequestBody OpenapiOrgDeleteVO OpenapiOrgDeleteVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Integer flag = openapiOrgFacade.deleteOpenapiOrg(OpenapiOrgDeleteVO);
//            if (1 == flag) {
//                resultVo.setResultDes("机构删除成功");
//                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("机构删除失败");
//            }
//        }catch (Exception e){
//            resultVo.setResultDes("机构删除异常，原因为:"+e);
//            log.error("机构删除异常",e);
//        }
//        return resultVo;
//    }
//
//
//}
