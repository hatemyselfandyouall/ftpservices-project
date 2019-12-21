package com.insigma.web.backGround.interfaceStatistics;

import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.facade.openapi.vo.interfaceStatistics.InterfaceStatisticsVO;
import com.insigma.facade.openapi.vo.openapiInterface.InterfaceStatisticsByAreaVO;
import com.insigma.facade.openapi.vo.openapiInterface.OpenapiInterfaceStaaticsFieldsVO;
import com.insigma.facade.openapi.vo.openapiInterface.OpenapiInterfaceStaaticsVO;
import com.insigma.facade.operatelog.facade.SysOperatelogRecordFacade;
import com.insigma.table.TableInfo;
import com.insigma.util.AddLogUtil;
import com.insigma.util.BIUtil;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import star.fw.web.util.ServletAttributes;
import star.vo.result.ResultVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

@RestController
@RequestMapping("/interfaceStatistics")
@Api(tags ="服务监控分析模块")
@Slf4j
public class InterfaceStatisticsController extends BasicController {

    @Autowired
    OpenapiDictionaryFacade openapiDictionaryFacade;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    InterfaceFacade interfaceFacade;
    @Autowired
    LoginComponent loginComponent;
    @Autowired
    SysOperatelogRecordFacade sysOperatelogRecordFacade;

    @ApiOperation(value = "调用总量")
    @ResponseBody
    @RequestMapping(value = "/interfaceUserTotalCount",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo interfaceUserTotalCount(@RequestBody InterfaceStatisticsVO interfaceStatisticsVO){
        ResultVo resultVo=new ResultVo();
        try {
            String code;
            switch(interfaceStatisticsVO.getStaticType()){
                case 1:
                    code=DataConstant.TOTAL_USE_COUNT_DAY;
                    break;
                case 2:
                    code=DataConstant.TOTAL_USE_COUNT_WEEK;
                    break;
                case 3:
                    code=DataConstant.TOTAL_USE_COUNT_MONTH;
                    break;
                case 4:
                    code=DataConstant.TOTAL_USE_COUNT_YEAR;
                    break;
                    default:
                        code=DataConstant.TOTAL_USE_COUNT_YEAR;
            }
            Map<String,Object> whereMap=new HashMap<>();
            if (interfaceStatisticsVO.getInterfaceId()!=null){
                whereMap.put("interface_id","and interface_id = "+interfaceStatisticsVO.getInterfaceId());
            }
            ResultVo<TableInfo> BIResult=BIUtil.getRequestResult(1l,1000l,whereMap,null,code,openapiDictionaryFacade,restTemplate);
            if (BIResult.isSuccess()){
                resultVo=BIResult;
            }else {
                resultVo.setResultDes(BIResult.getResultDes());
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"调用总量","调用总量", DataConstant.SYSTEM_NAME,"服务监控分析",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取调用总量异常"+e);
            log.error("获取调用总量异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "调用总量-机构排名")
    @ResponseBody
    @RequestMapping(value = "/interfaceUserTotalCountByOrgName",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo interfaceUserTotalCountByOrgName(@RequestBody InterfaceStatisticsVO interfaceStatisticsVO){
        ResultVo resultVo=new ResultVo();
        try {
            String code;
            switch(interfaceStatisticsVO.getStaticType()){
                case 1:
                    code=DataConstant.TOTAL_USE_COUNT_DAY_ORG;
                    break;
                case 2:
                    code=DataConstant.TOTAL_USE_COUNT_WEEK_ORG;
                    break;
                case 3:
                    code=DataConstant.TOTAL_USE_COUNT_MONTH_ORG;
                    break;
                case 4:
                    code=DataConstant.TOTAL_USE_COUNT_YEAR_ORG;
                    break;
                default:
                    code=DataConstant.TOTAL_USE_COUNT_YEAR_ORG;
            }
            Map<String,Object> whereMap=new HashMap<>();
            if (interfaceStatisticsVO.getInterfaceId()!=null){
                whereMap.put("interface_id","and interface_id = "+interfaceStatisticsVO.getInterfaceId());
            }
            ResultVo<TableInfo> BIResult=BIUtil.getRequestResult(1l,1000l,whereMap,null,code,openapiDictionaryFacade,restTemplate);
            if (BIResult.isSuccess()){
                resultVo=BIResult;
            }else {
                resultVo.setResultDes(BIResult.getResultDes());
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"调用总量-机构排名","调用总量-机构排名", DataConstant.SYSTEM_NAME,"服务监控分析",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取调用总量-机构排名异常"+e);
            log.error("获取调用总量-机构排名异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "接口调用实时情况")
    @ResponseBody
    @RequestMapping(value = "/interfaceUseRealTime",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo interfaceUseRealTime(@RequestBody InterfaceStatisticsVO interfaceStatisticsVO){
        ResultVo resultVo=new ResultVo();
        try {
            String code=DataConstant.INTERFACE_USE_REAL_TIME;
            Map<String,Object> whereMap=new HashMap<>();
            if (interfaceStatisticsVO.getInterfaceId()!=null){
                whereMap.put("interface_id","and interface_id = "+interfaceStatisticsVO.getInterfaceId());
            }
            ResultVo<TableInfo> BIResult=BIUtil.getRequestResult(1l,1000l,whereMap,null,code,openapiDictionaryFacade,restTemplate);
            if (BIResult.isSuccess()){
                resultVo=BIResult;
            }else {
                resultVo.setResultDes(BIResult.getResultDes());
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口调用实时情况","接口调用实时情况", DataConstant.SYSTEM_NAME,"服务监控分析",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("接口调用实时情况异常"+e);
            log.error("接口调用实时情况异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "接口发布趋势")
    @ResponseBody
    @RequestMapping(value = "/interfacePublishingTrend",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiInterfaceStaaticsVO> interfacePublishingTrend(@RequestBody InterfaceStatisticsVO interfaceStatisticsVO){
        ResultVo resultVo=new ResultVo();
        try {
            List<OpenapiInterfaceStaaticsFieldsVO> openapiInterfaceStaaticsVOS;
            switch(interfaceStatisticsVO.getStaticType()){
                case 1:
                    openapiInterfaceStaaticsVOS=interfaceFacade.interfacePublishingTrendByDay(interfaceStatisticsVO);
                    break;
                case 2:
                    openapiInterfaceStaaticsVOS=interfaceFacade.interfacePublishingTrendByWeek(interfaceStatisticsVO);
                    break;
                case 3:
                    openapiInterfaceStaaticsVOS=interfaceFacade.interfacePublishingTrendByMonth(interfaceStatisticsVO);
                    break;
                case 4:
                    openapiInterfaceStaaticsVOS=interfaceFacade.interfacePublishingTrendByYear(interfaceStatisticsVO);
                    break;
                default:
                    openapiInterfaceStaaticsVOS=interfaceFacade.interfacePublishingTrendByYear(interfaceStatisticsVO);
            }
            OpenapiInterfaceStaaticsVO openapiInterfaceStaaticsVO=interfaceFacade.getTotalInterfaceTrendDetail(interfaceStatisticsVO.getStaticType());
            openapiInterfaceStaaticsVO.setNewInterfaceCount(openapiInterfaceStaaticsVOS.stream().map(OpenapiInterfaceStaaticsFieldsVO::getTotalCount).reduce(0,(a,b)->a+b));
            openapiInterfaceStaaticsVO.setOpenapiInterfaceStaaticsFieldsVOS(openapiInterfaceStaaticsVOS);
            resultVo.setResult(openapiInterfaceStaaticsVO);
            resultVo.setSuccess(true);
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口发布趋势","接口发布趋势", DataConstant.SYSTEM_NAME,"服务监控分析",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("接口发布趋势异常"+e);
            log.error("接口发布趋势异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "接口调用概况-地区")
    @ResponseBody
    @RequestMapping(value = "/interfaceUserTotalCountByArea",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo interfaceUserTotalCountByArea(@RequestBody InterfaceStatisticsByAreaVO interfaceStatisticsByAreaVO){
        ResultVo resultVo=new ResultVo();
        try {
            String code;
            switch(interfaceStatisticsByAreaVO.getStaticType()){
                case 1:
                    code=DataConstant.TOTAL_USE_COUNT_DAY_AREA;
                    break;
                case 2:
                    code=DataConstant.TOTAL_USE_COUNT_WEEK_AREA;
                    break;
                case 3:
                    code=DataConstant.TOTAL_USE_COUNT_MONTH_AREA;
                    break;
                case 4:
                    code=DataConstant.TOTAL_USE_COUNT_YEAR_AREA;
                    break;
                default:
                    code=DataConstant.TOTAL_USE_COUNT_YEAR_AREA;
            }
            Map<String,Object> whereMap=new HashMap<>();
            if (interfaceStatisticsByAreaVO.getInterfaceId()!=null){
                whereMap.put("interface_id","and interface_id = "+interfaceStatisticsByAreaVO.getInterfaceId());
            }
            String countWord;
            switch(interfaceStatisticsByAreaVO.getType()){
                case 1:
                    countWord="";
                    break;
                case 2:
                    countWord="and is_forward_success!=1";
                    break;
                case 3:
                    countWord="and is_overtime=1";
                    break;
                default:
                    countWord="";
            }
            whereMap.put("countWord",countWord);
            ResultVo<TableInfo> BIResult=BIUtil.getRequestResult(1l,1000l,whereMap,null,code,openapiDictionaryFacade,restTemplate);
            if (BIResult.isSuccess()){
                resultVo=BIResult;
            }else {
                resultVo.setResultDes(BIResult.getResultDes());
            }
//            resultVo.setResult(openapiInterfaceStaaticsVO);
            resultVo.setSuccess(true);
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"接口调用概况-地区","接口调用概况-地区", DataConstant.SYSTEM_NAME,"服务监控分析",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("接口调用概况-地区异常"+e);
            log.error("接口调用概况-地区异常",e);
        }
        return resultVo;
    }
}