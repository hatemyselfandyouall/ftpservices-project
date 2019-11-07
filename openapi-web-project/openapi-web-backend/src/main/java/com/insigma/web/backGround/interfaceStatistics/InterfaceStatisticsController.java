package com.insigma.web.backGround.interfaceStatistics;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiBlackWhiteFacade;
import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.facade.openapi.po.OpenapiBlackWhite;
import com.insigma.facade.openapi.po.OpenapiDictionary;
import com.insigma.facade.openapi.vo.interfaceStatistics.InterfaceStatisticsVO;
import com.insigma.table.TableInfo;
import com.insigma.util.BIUtil;
import com.insigma.web.BasicController;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import star.vo.result.ResultVo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/interfaceStatistics")
@Api(tags ="服务监控分析模块")
@Slf4j
public class InterfaceStatisticsController extends BasicController {

    @Autowired
    OpenapiDictionaryFacade openapiDictionaryFacade;
    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(value = "调用总量")
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo getOpenapiBlackWhiteList(@RequestBody InterfaceStatisticsVO interfaceStatisticsVO){
        ResultVo resultVo=new ResultVo();
        try {
            String code= DataConstant.TOTAL_USE_COUNT;
            Map<String,Object> whereMap=new HashMap<>();
            if (interfaceStatisticsVO.getInterfaceId()!=null){
                whereMap.put("#{interface_id}","where interface_id = "+interfaceStatisticsVO.getInterfaceId());
            }
            ResultVo<TableInfo> BIResult=BIUtil.getRequestResult(1l,1000l,whereMap,null,code,openapiDictionaryFacade,restTemplate);
            if (BIResult.isSuccess()){
                resultVo=BIResult;
            }else {
                resultVo.setResultDes(BIResult.getResultDes());
            }
        }catch (Exception e){
            resultVo.setResultDes("获取调用总量异常"+e);
            log.error("获取调用总量异常",e);
        }
        return resultVo;
    }
}