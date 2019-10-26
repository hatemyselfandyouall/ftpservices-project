package com.insigma.web.backGround.openapiearlywarning;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.dto.OpenapiEarlyWarningDto;
import com.insigma.facade.openapi.facade.OpenapiEarlyWarningFacade;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiEarlyWarning;
import com.insigma.web.BasicController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.vo.result.ResultVo;


@RestController
@RequestMapping("/openapiEarlyWarning")
@Api(tags ="预警人员管理")
@Slf4j
public class OpenapiEarlyWarningController extends BasicController {

    @Autowired
    private OpenapiEarlyWarningFacade openapiEarlyWarningFacade;

    @ApiOperation(value = "预警人员列表")
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiEarlyWarning> getOpenapiEarlyWarningList(@RequestParam(value = "name",required = false) String name,@RequestParam(value = "offset") Integer offset,@RequestParam(value = "limit") Integer limit){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiEarlyWarning> openapiEarlyWarningList=openapiEarlyWarningFacade.getOpenapiEarlyWarningList(name,offset,limit);
            if(openapiEarlyWarningList!=null){
                DataListResultDto<OpenapiEarlyWarning> dataListResultDto=new DataListResultDto<>(openapiEarlyWarningList.getList(),(int)openapiEarlyWarningList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取预警人员列表异常");
            log.error("获取预警人员列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "预警人员详情")
    @RequestMapping(value = "/detail",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiEarlyWarning> getOpenapiEarlyWarningDetail(@RequestParam(value = "id") Long id){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiEarlyWarning openapiEarlyWarning=openapiEarlyWarningFacade.getOpenapiEarlyWarningDetail(id);
        if(openapiEarlyWarning!=null){
            resultVo.setResult(openapiEarlyWarning);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
        } catch (Exception e){
        resultVo.setResultDes("获取预警人员详情异常");
        log.error("获取预警人员详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "预警人员保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> saveOpenapiEarlyWarning(@RequestBody OpenapiEarlyWarningDto openapiEarlyWarningDto){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiEarlyWarningFacade.saveOpenapiEarlyWarning(openapiEarlyWarningDto);
            if (1 == flag) {
                resultVo.setResultDes("预警人员保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("预警人员保存失败");
            }
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("预警人员保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "预警人员删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> deleteOpenapiEarlyWarning(Long id){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiEarlyWarningFacade.deleteOpenapiEarlyWarning(id);
            if (1 == flag) {
                resultVo.setResultDes("预警人员删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("预警人员删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("预警人员删除异常");
            log.error("预警人员删除异常",e);
        }
        return resultVo;
    }


}
