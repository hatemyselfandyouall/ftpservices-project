package com.insigma.web.backGround.user;

import com.alibaba.fastjson.JSONArray;
import com.insigma.facade.sysbase.SysOrgFacade;
import com.insigma.facade.sysbase.SysUserFacade;
import com.insigma.facade.sysbase.vo.SysOrgDTO;
import com.insigma.facade.sysbase.vo.SysUserDTO;
import com.insigma.webtool.component.LoginComponent;
import com.insigma.webtool.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.bizbase.vo.result.Results;
import star.vo.result.ResultVo;

import java.util.List;

@RestController
@RequestMapping("SysOrg")
@Api(tags ="系统机构管理")
public class SysOrgController {
    @Autowired
    LoginComponent loginComponent;

    @Autowired
    SysUserFacade sysUserFacade;

    @Autowired
    SysOrgFacade sysOrgFacade;
    /**
     * 获取区域列表
     * @return
     */
    @ResponseBody
    @ApiOperation(value ="获取地区列表")
    @RequestMapping(value = { "/getAreaTreeNodes" },method = RequestMethod.POST)
    public ResultVo<JSONArray> getAreaTreeNodes() {
        ResultVo<JSONArray> result = Results.newResultVo();
        //从缓存获取用户信息
//        Long userId = loginComponent.getLoginUserId();
        SysUserDTO sysUser = sysUserFacade.getCacheByPrimaryKey(1L);
        JSONArray orgNodes = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysUserFacade.queryAreaNodes(sysUser))), "id", "parentId", "children");
        result.setCode("0");
        result.setSuccess(true);
        result.setResult(orgNodes);
        return result;
    }


    @ResponseBody
    @ApiOperation(value ="根据统筹区编码获取机构")
    @RequestMapping(value = { "/getOrgsByAreaId" },method = RequestMethod.GET)
    public ResultVo<List<SysOrgDTO>> getAOrgsByAreaId(@RequestParam("areaId") String areaId) {
        ResultVo<List<SysOrgDTO>> result = Results.newResultVo();
        //从缓存获取用户信息
//        Long userId = loginComponent.getLoginUserId();
        List<SysOrgDTO> orgList= sysOrgFacade.queryOrgNodes(areaId);
        result.setCode("0");
        result.setSuccess(true);
        result.setResult(orgList);
        return result;
    }
}
