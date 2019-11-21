package com.insigma.web.backGround.openapimonitoringdataconfig;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.dto.OpenapiEarlyWarningDto;
import com.insigma.facade.openapi.dto.OpenapiMonitoringDataConfigDto;
import com.insigma.facade.openapi.facade.OpenapiEarlyWarningFacade;
import com.insigma.facade.openapi.facade.OpenapiMonitoringDataConfigFacade;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiEarlyWarning;
import com.insigma.facade.openapi.po.OpenapiMonitoringDataConfig;
import com.insigma.web.BasicController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.vo.result.ResultVo;


@RestController
@RequestMapping("/openapiMonitoringDataConfig")
@Api(tags ="监控数据配置")
@Slf4j
public class OpenapiMonitoringDataConfigController extends BasicController {

    @Autowired
    private OpenapiMonitoringDataConfigFacade openapiMonitoringDataConfigFacade;

    @ApiOperation(value = "监控数据配置列表")
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiMonitoringDataConfigDto> getOpenapiMonitoringDataConfig(@RequestParam(value = "name",required = false) String name,@RequestParam(value = "offset") Integer offset,@RequestParam(value = "limit") Integer limit){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiMonitoringDataConfigDto> openapiMonitoringDataConfigDtoList=openapiMonitoringDataConfigFacade.getOpenapiMonitoringDataConfig(name,offset,limit);
            if(openapiMonitoringDataConfigDtoList!=null){
                DataListResultDto<OpenapiMonitoringDataConfigDto> dataListResultDto=new DataListResultDto<>(openapiMonitoringDataConfigDtoList.getList(),(int)openapiMonitoringDataConfigDtoList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取监控数据配置列表异常");
            log.error("获取监控数据配置列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "监控数据配置详情")
    @RequestMapping(value = "/detail",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiEarlyWarning> getOpenapiMonitoringDataConfigDetail(@RequestParam(value = "id") Long id){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiMonitoringDataConfigDto openapiMonitoringDataConfigDto=openapiMonitoringDataConfigFacade.getOpenapiMonitoringDataConfigDetail(id);
        if(openapiMonitoringDataConfigDto!=null){
            resultVo.setResult(openapiMonitoringDataConfigDto);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取监控数据配置详情失败");
        }
        } catch (Exception e){
        resultVo.setResultDes("获取监控数据配置详情异常");
        log.error("获取监控数据配置详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "根据接口id查询监控数据配置详情")
    @RequestMapping(value = "/detailInfo",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiEarlyWarning> getOpenapiMonitoringDataConfigInfo(@RequestParam(value = "interfaceId") Long interfaceId){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiMonitoringDataConfigDto openapiMonitoringDataConfigDto=openapiMonitoringDataConfigFacade.getOpenapiMonitoringDataConfigInfo(interfaceId);
            if(openapiMonitoringDataConfigDto!=null){
                resultVo.setResult(openapiMonitoringDataConfigDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("获取监控数据配置详情失败");
            }
        } catch (Exception e){
            resultVo.setResultDes("获取监控数据配置详情异常");
            log.error("获取监控数据配置详情异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "监控数据配置保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> saveOpenapiMonitoringDataConfig(@RequestBody OpenapiMonitoringDataConfigDto openapiMonitoringDataConfigDto){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiMonitoringDataConfigFacade.saveOpenapiMonitoringDataConfig(openapiMonitoringDataConfigDto);
            if (1 == flag) {
                resultVo.setResultDes("监控数据配置保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("监控数据配置保存失败");
            }
        }catch (Exception e){
                resultVo.setResultDes("监控数据配置保存异常");
                log.error("监控数据配置保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "监控数据配置删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> deleteOpenapiMonitoringDataConfig(@RequestBody OpenapiMonitoringDataConfigDto openapiMonitoringDataConfigDto){
        ResultVo resultVo=new ResultVo();
        if(openapiMonitoringDataConfigDto==null&&openapiMonitoringDataConfigDto.getId()==null){
            return null;
        }
        try {
            Integer flag = openapiMonitoringDataConfigFacade.deleteOpenapiMonitoringDataConfig(openapiMonitoringDataConfigDto.getId());
            if (1 == flag) {
                resultVo.setResultDes("监控数据配置删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("监控数据配置删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("监控数据配置删除异常");
            log.error("监控数据配置删除异常",e);
        }
        return resultVo;
    }


}
