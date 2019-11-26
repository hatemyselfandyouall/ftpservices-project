package com.insigma.web.backGround.selfHelpMachine;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.dto.DataListResultDto;
import com.insigma.facade.openapi.facade.OpenapiOrgShortnameFacade;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.vo.result.ResultVo;
import com.insigma.facade.openapi.po.OpenapiOrgShortname;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameDetailVO;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameListVO;
import com.insigma.facade.openapi.vo.OpenapiOrgShortname.OpenapiOrgShortnameSaveVO;


@RestController
@RequestMapping("openapiOrgShortname")
@Api(tags ="开放平台机构简称管理")
@Slf4j
public class OpenapiOrgShortnameController extends BasicController {

    @Autowired
    OpenapiOrgShortnameFacade openapiOrgShortnameFacade;
    @Autowired
    LoginComponent loginComponent;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> getOpenapiOrgShortnameList(@RequestBody OpenapiOrgShortnameListVO openapiOrgShortnameListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiOrgShortname> openapiOrgShortnameList=openapiOrgShortnameFacade.getOpenapiOrgShortnameList(openapiOrgShortnameListVO);
            if(openapiOrgShortnameList!=null){
                DataListResultDto<OpenapiOrgShortname> dataListResultDto=new DataListResultDto<>(openapiOrgShortnameList.getList(),(int)openapiOrgShortnameList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取列表异常");
            log.error("获取列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> getOpenapiOrgShortnameDetail(@RequestBody OpenapiOrgShortnameDetailVO openapiOrgShortnameDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiOrgShortname openapiOrgShortname=openapiOrgShortnameFacade.getOpenapiOrgShortnameDetail(openapiOrgShortnameDetailVO);
        if(openapiOrgShortname!=null){
            resultVo.setResult(openapiOrgShortname);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
        } catch (Exception e){
        resultVo.setResultDes("获取详情异常");
        log.error("获取详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> saveOpenapiOrgShortname(@RequestBody OpenapiOrgShortnameSaveVO openapiOrgShortnameSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            String userName=loginComponent.getLoginUserName();
            openapiOrgShortnameSaveVO.setModifierName(userName);
            resultVo  = openapiOrgShortnameFacade.saveOpenapiOrgShortname(openapiOrgShortnameSaveVO);
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("保存异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "确认删除是否可行")
    @RequestMapping(value = "/checkDelete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> checkDelete(@RequestBody OpenapiOrgShortnameDeleteVO openapiOrgShortnameDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiOrgShortnameFacade.checkDelete(openapiOrgShortnameDeleteVO);
            if (1 == flag) {
                resultVo.setResult(flag);
                resultVo.setResultDes("可以删除");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResult(flag);
                resultVo.setResultDes("不可删除");
                resultVo.setSuccess(true);
            }
        }catch (Exception e){
            resultVo.setResultDes("确认删除是否可行异常");
            log.error("确认删除是否可行异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> deleteOpenapiOrgShortname(@RequestBody OpenapiOrgShortnameDeleteVO openapiOrgShortnameDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiOrgShortnameFacade.deleteOpenapiOrgShortname(openapiOrgShortnameDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("删除异常");
            log.error("删除异常",e);
        }
        return resultVo;
    }


}
