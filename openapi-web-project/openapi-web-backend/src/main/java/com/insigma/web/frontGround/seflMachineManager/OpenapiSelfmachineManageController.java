package com.insigma.web.frontGround.seflMachineManager;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.devops.OptSelfServiceMachinesSoftFacade;
import com.insigma.facade.devops.vo.OptSelfServiceMachinesSoftVo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiOrgFacade;
import com.insigma.facade.openapi.facade.OpenapiSelfmachineFacade;
import com.insigma.facade.openapi.po.OpenapiOrg;
import com.insigma.facade.openapi.po.OpenapiSelfmachine;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgListVO;
import com.insigma.facade.openapi.vo.OpenapiOrg.OpenapiOrgShowVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachine.*;
import com.insigma.facade.operatelog.facade.SysOperatelogRecordFacade;
import com.insigma.facade.sysbase.SysOrgFacade;
import com.insigma.facade.sysbase.vo.SysUserOrgDTO;
import com.insigma.util.AddLogUtil;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import com.insigma.webtool.util.Encrypt;
import constant.DataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.fw.web.util.ServletAttributes;
import star.vo.result.ResultVo;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("openapiSelfmachineManage")
@Api(tags ="自助机管理-用户")
@Slf4j
public class OpenapiSelfmachineManageController extends BasicController {

    @Autowired
    OpenapiSelfmachineFacade openapiSelfmachineFacade;

    @Autowired
    LoginComponent loginComponent;

    @Autowired
    SysOperatelogRecordFacade sysOperatelogRecordFacade;


    @Autowired
    SysOrgFacade sysOrgFacade;
    @Autowired
    OpenapiOrgFacade openapiOrgFacade;
    @Autowired
    OptSelfServiceMachinesSoftFacade optSelfServiceMachinesSoftFacade;

    @ApiOperation(value = "自助机列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(@RequestBody OpenapiSelfmachineListVO openapiSelfmachineListVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            PageInfo<OpenapiSelfmachineShowVO> openapiSelfmachineList=openapiSelfmachineFacade.getOpenapiSelfmachineList(openapiSelfmachineListVO,userId);
            openapiSelfmachineList.getList().forEach(i->i.setCertificateCode(openapiOrgFacade.getOpenapiOrgDetail(new OpenapiOrgDetailVO().setId(i.getOrgId())).getCertificateCode()));
            if(openapiSelfmachineList!=null){
                DataListResultDto<OpenapiSelfmachineShowVO> dataListResultDto=new DataListResultDto<>(openapiSelfmachineList.getList(),(int)openapiSelfmachineList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"自助机管理-自助机列表","自助机管理-自助机列表", DataConstant.SYSTEM_NAME,"自助机管理-用户",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取自助机列表异常");
            log.error("获取自助机列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "证书列表")
    @RequestMapping(value = "/orgList",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShowVO> orgList(){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            OpenapiOrgListVO openapiOrgListVO= new OpenapiOrgListVO();
            openapiOrgListVO.setPageNum(1l);
            openapiOrgListVO.setPageSize(99999l);
            PageInfo<OpenapiOrgShowVO> openapiSelfmachineList=openapiOrgFacade.getOpenapiOrgList(openapiOrgListVO,userId);
            resultVo.setResult(openapiSelfmachineList);
            resultVo.setSuccess(true);
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"自助机管理-证书列表","自助机管理-证书列表", DataConstant.SYSTEM_NAME,"自助机管理-用户",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("获取自助机列表异常");
            log.error("获取自助机列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "自助机版本号列表")
    @RequestMapping(value = "/SelfMachineVersionlist",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineShowVO> SelfMachineVersionlist(){
        ResultVo resultVo=new ResultVo();
        try {
            HashMap<String, Object> searchMap = new HashMap<>();
            searchMap.put("isValid", 1);
            List<OptSelfServiceMachinesSoftVo> optSelfServiceMachinesSoftVos=optSelfServiceMachinesSoftFacade.getListByWhere(searchMap);
                resultVo.setResult(optSelfServiceMachinesSoftVos);
                resultVo.setSuccess(true);
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"自助机管理-自助机版本号列表","自助机管理-自助机版本号列表", DataConstant.SYSTEM_NAME,"自助机管理-用户",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("自助机版本号列表异常");
            log.error("自助机版本号列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "自助机详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachine> getOpenapiSelfmachineDetail(@RequestBody OpenapiSelfmachineDetailVO openapiSelfmachineDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiSelfmachine openapiSelfmachine=openapiSelfmachineFacade.getOpenapiSelfmachineDetail(openapiSelfmachineDetailVO);
        if(openapiSelfmachine!=null){
            resultVo.setResult(openapiSelfmachine);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"自助机管理-自助机详情","自助机管理-自助机详情"+openapiSelfmachineDetailVO.getId(), DataConstant.SYSTEM_NAME,"自助机管理-用户",sysOperatelogRecordFacade);
        } catch (Exception e){
        resultVo.setResultDes("获取自助机详情异常");
        log.error("获取自助机详情异常",e);
    }
        return resultVo;
    }
//
    @ApiOperation(value = "自助机保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachine> saveOpenapiSelfmachine(@RequestBody OpenapiSelfmachineSaveVO openapiSelfmachineSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiSelfmachine openapiSelfmachine = openapiSelfmachineFacade.saveOpenapiSelfmachine(openapiSelfmachineSaveVO);
            if (openapiSelfmachine!=null) {
                resultVo.setResult(openapiSelfmachine);
                resultVo.setResultDes("自助机保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("自助机保存失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"自助机管理-自助机保存","自助机管理-自助机保存", DataConstant.SYSTEM_NAME,"自助机管理-用户",sysOperatelogRecordFacade);
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("自助机保存异常",e);
            }
        return resultVo;
    }


    @ApiOperation(value = "设置激活状态")
    @RequestMapping(value = "/setActiveStatus",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachine> setActiveStatus(@RequestBody OpenapiSelfmachineSetActiveStatusVO openapiSelfmachineSetActiveStatusVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiSelfmachineFacade.setActiveStatus(openapiSelfmachineSetActiveStatusVO);
            if (flag!=null&&flag>0) {
                resultVo.setResultDes("自助机设置激活状态成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("自助机设置激活状态失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"自助机管理-设置激活状态","自助机管理-设置激活状态"+openapiSelfmachineSetActiveStatusVO.getIds(), DataConstant.SYSTEM_NAME,"自助机管理-用户",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("自助机设置激活状态异常");
            log.error("自助机设置激活状态异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "自助机设置机构")
    @RequestMapping(value = "/setOrg",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachine> setOrg(@RequestBody OpenapiSelfmachineSetOrgVO openapiSelfmachineSetOrgVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiSelfmachineFacade.setOrg(openapiSelfmachineSetOrgVO);
            if (flag!=null&&flag>0) {
                resultVo.setResultDes("自助机设置机构成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("自助机设置机构失败");
            }
            AddLogUtil.addLog(ServletAttributes.getRequest(),loginComponent.getLoginUserName(),loginComponent.getLoginUserId()
                    ,"自助机管理-自助机设置机构","自助机管理-自助机设置机构"+openapiSelfmachineSetOrgVO.getIds(), DataConstant.SYSTEM_NAME,"自助机管理-用户",sysOperatelogRecordFacade);
        }catch (Exception e){
            resultVo.setResultDes("自助机设置机构异常");
            log.error("自助机设置机构异常",e);
        }
        return resultVo;
    }

    public static void main(String[] args) {
        String test=Encrypt.desEncrypt("knQ0f9xh35XvtZZZ2eaVhBVghHR33bPJiYEvg1EkALpWWNFFHg9hzRrKfTRVPO+3IoInksWzqqv5ZlavdxTfyi+YJdbqu89+lJcE5phbabv8ScooKKS+MsB3c0gtF+7o");
        System.out.println(test);
    }

}
