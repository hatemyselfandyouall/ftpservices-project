package com.insigma.web.backGround.openapiServeMonitor;

import com.insigma.facade.openapi.facade.OpenapiDictionaryFacade;
import com.insigma.facade.openapi.vo.InterfaceDetailVO;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/serverMonitor")
@Api(tags = "服务监控接口")
@Slf4j
public class OpenapiServerMonitorController extends BasicController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OpenapiDictionaryFacade openapiDictionaryFacade;


    @ApiOperation(value = "服务监控统计接口查询")
    @ResponseBody
    @RequestMapping(value = "/listCount", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo getStatisticCount(@RequestBody InterfaceDetailVO interfaceDetailVO) {
        ResultVo resultVo = new ResultVo();
        ResultVo temp1=BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.SERVER_MONITOR1, openapiDictionaryFacade, restTemplate);
        if (!temp1.isSuccess()){
            ResultVo temp2=BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.SERVER_MONITOR2, openapiDictionaryFacade, restTemplate);
            if(!temp2.isSuccess()){
                resultVo.setSuccess(true);
                resultVo.setResultDes("调用成功！");
                resultVo.setResult(Arrays.asList(temp1.getResult(),temp2.getResult()));
            }
        }
        return resultVo;
    }
    @ApiOperation(value = "服务监控列表查询")
    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo getStatisticList(@RequestBody InterfaceDetailVO interfaceDetailVO) {
        ResultVo resultVo = new ResultVo();
        //where r.interface_name='rrr'
        if(!interfaceDetailVO.getWhereWord().isEmpty()){
            Map<String,Object> map = new HashMap();
            map.put("interface_name","where r.interface_name= " + interfaceDetailVO.getWhereWord().get("interface_name"));
            interfaceDetailVO.setWhereWord(map);
        }
        resultVo =  BIUtil.getRequestResult(interfaceDetailVO.getPageNum(), interfaceDetailVO.getPageSize(), interfaceDetailVO.getWhereWord(), interfaceDetailVO.getOrderByword(), DataConstant.SERVER_MONITOR_LIST, openapiDictionaryFacade, restTemplate);
        resultVo.setResult(resultVo.getResult());
        resultVo.setResultDes("调用成功!");
        resultVo.setSuccess(true);
        return resultVo;
    }

}
