package com.insigma.web.backGround.openapiblackwhite;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.dto.OpenapiBlackWhiteDto;
import com.insigma.facade.openapi.facade.OpenapiBlackWhiteFacade;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiBlackWhite;
import com.insigma.web.BasicController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.vo.result.ResultVo;

import java.util.Date;


@RestController
@RequestMapping("/openapiBlackWhite")
@Api(tags ="黑白名单管理")
@Slf4j
public class OpenapiBlackWhiteController extends BasicController {

    @Autowired
    private OpenapiBlackWhiteFacade openapiBlackWhiteFacade;

    @ApiOperation(value = "黑白名单列表")
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})

    public ResultVo<OpenapiBlackWhite> getOpenapiBlackWhiteList(@RequestParam(value = "name",required = false) String name,
                                                                @RequestParam(value = "addType",required = false) Integer addType,
                                                                @RequestParam(value = "startDate",required = false) Date startDate,
                                                                @RequestParam(value = "endDate",required = false) Date endDate,
                                                                @RequestParam(value = "offset") Integer offset,
                                                                @RequestParam(value = "limit") Integer limit){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiBlackWhite> openapiBlackWhiteList=openapiBlackWhiteFacade.getOpenapiBlackWhiteList(name,addType,startDate,endDate,offset,limit);
            if(openapiBlackWhiteList!=null){
                DataListResultDto<OpenapiBlackWhite> dataListResultDto=new DataListResultDto<>(openapiBlackWhiteList.getList(),(int)openapiBlackWhiteList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取黑白名单列表异常");
            log.error("获取黑白名单列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "黑白名单详情")
    @RequestMapping(value = "/detail",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiBlackWhite> getOpenapiBlackWhiteDetail(@RequestParam(value = "id") Long id){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiBlackWhite openapiBlackWhite=openapiBlackWhiteFacade.getOpenapiBlackWhiteDetail(id);
        if(openapiBlackWhite!=null){
            resultVo.setResult(openapiBlackWhite);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
        } catch (Exception e){
        resultVo.setResultDes("获取黑白名单详情异常");
        log.error("获取黑白名单详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "黑白名单保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> saveOpenapiBlackWhite(@RequestBody OpenapiBlackWhiteDto openapiBlackWhiteDto){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiBlackWhiteFacade.saveOpenapiBlackWhite(openapiBlackWhiteDto);
            if (1 == flag) {
                resultVo.setResultDes("黑白名单保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("黑白名单保存失败");
            }
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("黑白名单保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "黑白名单删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiBlackWhite> deleteBlackWhite(Long id){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiBlackWhiteFacade.deleteOpenapiBlackWhite(id);
            if (1 == flag) {
                resultVo.setResultDes("黑白名单删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("黑白名单删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("黑白名单删除异常");
            log.error("黑白名单删除异常",e);
        }
        return resultVo;
    }


}
