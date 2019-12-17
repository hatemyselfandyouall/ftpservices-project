package com.insigma.web.backGround.openapiblackwhite;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.dto.OpenapiBlackWhiteDto;
import com.insigma.facade.openapi.dto.OpenapiBlackWhiteDtoList;
import com.insigma.facade.openapi.facade.OpenapiBlackWhiteFacade;
import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiBlackWhite;
import com.insigma.facade.operatelog.facade.SysOperatelogRecordFacade;
import com.insigma.util.AddLogUtil;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.fw.web.util.ServletAttributes;
import star.vo.result.ResultVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/openapiBlackWhite")
@Api(tags ="黑白名单管理")
@Slf4j
public class OpenapiBlackWhiteController extends BasicController {

    @Autowired
    private OpenapiBlackWhiteFacade openapiBlackWhiteFacade;

    @Autowired
    private LoginComponent loginComponent;

    @Autowired
    SysOperatelogRecordFacade sysOperatelogRecordFacade;

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
                List<OpenapiBlackWhite> openapiBlackWhites = openapiBlackWhiteList.getList();
                List<OpenapiBlackWhiteDtoList> OpenapiBlackWhiteDtoLists = new ArrayList<>();
                for(OpenapiBlackWhite openapiBlackWhite:openapiBlackWhites){
                    OpenapiBlackWhiteDtoList openapiBlackWhiteDtoList = new OpenapiBlackWhiteDtoList();
                    BeanUtils.copyProperties(openapiBlackWhite,openapiBlackWhiteDtoList);
                    String userName=loginComponent.getLoginUserName();
                    openapiBlackWhiteDtoList.setCreateByName(userName);
                    openapiBlackWhiteDtoList.setModifyByName(userName);
                    OpenapiBlackWhiteDtoLists.add(openapiBlackWhiteDtoList);
                }
                DataListResultDto<OpenapiBlackWhiteDtoList> dataListResultDto=new DataListResultDto<>(OpenapiBlackWhiteDtoLists,(int)openapiBlackWhiteList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"黑白名单列表","黑白名单列表", DataConstant.SYSTEM_NAME,"黑白名单",sysOperatelogRecordFacade);
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
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"黑白名单详情","黑白名单详情"+id, DataConstant.SYSTEM_NAME,"黑白名单",sysOperatelogRecordFacade);
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
        Long userId=loginComponent.getLoginUserId();
        openapiBlackWhiteDto.setCreateBy(userId);
        openapiBlackWhiteDto.setModifyBy(userId);
        try {
            String ipAddressStr = openapiBlackWhiteFacade.saveOpenapiBlackWhite(openapiBlackWhiteDto);
            if(ipAddressStr.length()<=0) {
                resultVo.setCode("00");
                resultVo.setResultDes("黑白名单保存成功");
                resultVo.setSuccess(true);
            }else{
                resultVo.setCode("400");
                resultVo.setResult("fail");
                resultVo.setResultDes(ipAddressStr+"已存在");
                resultVo.setSuccess(false);
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"黑白名单保存","黑白名单保存", DataConstant.SYSTEM_NAME,"黑白名单",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setSuccess(false);
            resultVo.setCode("400");
            resultVo.setResult("fail");
            resultVo.setResultDes("接口保存异常");
            log.error("黑白名单保存异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "验证黑白名单是否存在")
    @RequestMapping(value = "/verification",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> verificationOpenapiBlackWhite(@RequestBody OpenapiBlackWhiteDto openapiBlackWhiteDto){
        ResultVo resultVo=new ResultVo();
        try {
            List<String> ipList = openapiBlackWhiteFacade.verificationOpenapiBlackWhite(openapiBlackWhiteDto);
            resultVo.setCode("00");
            resultVo.setResult(ipList);
            resultVo.setResultDes("验证黑白名单是否存在成功");
            resultVo.setSuccess(true);
        }catch (Exception e){
            resultVo.setCode("400");
            resultVo.setSuccess(false);
            resultVo.setResult("fail");
            resultVo.setResultDes("验证黑白名单是否存在异常");
            log.error("验证黑白名单是否存在异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "黑白名单修改")
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiApp> updateOpenapiBlackWhite(@RequestBody OpenapiBlackWhite openapiBlackWhite){
        ResultVo resultVo=new ResultVo();
        try {
            openapiBlackWhiteFacade.updateOpenapiBlackWhite(openapiBlackWhite);
            resultVo.setResultDes("黑白名单修改成功");
            resultVo.setSuccess(true);
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"黑白名单修改","黑白名单修改"+openapiBlackWhite.getId(), DataConstant.SYSTEM_NAME,"黑白名单",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("黑白名单修改异常");
            log.error("黑白名单修改异常",e);
        }
        return resultVo;
    }
    @ApiOperation(value = "黑白名单删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiBlackWhite> deleteBlackWhite(@RequestBody OpenapiBlackWhiteDto openapiBlackWhiteDto){
        ResultVo resultVo=new ResultVo();
        if(openapiBlackWhiteDto==null||openapiBlackWhiteDto.getId()==null){
            resultVo.setResultDes("参数不能为空");
            ;
        }
        try {
            Integer flag = openapiBlackWhiteFacade.deleteOpenapiBlackWhite(openapiBlackWhiteDto.getId());
            if (1 == flag) {
                resultVo.setResultDes("黑白名单删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("黑白名单删除失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"黑白名单删除","黑白名单删除"+openapiBlackWhiteDto.getId(), DataConstant.SYSTEM_NAME,"黑白名单",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("黑白名单删除异常");
            log.error("黑白名单删除异常",e);
        }
        return resultVo;
    }


}
