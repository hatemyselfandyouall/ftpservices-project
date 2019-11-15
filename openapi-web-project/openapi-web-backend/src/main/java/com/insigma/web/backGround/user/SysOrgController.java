package com.insigma.web.backGround.user;

import com.alibaba.fastjson.JSONArray;
import com.insigma.facade.sysbase.SysUserFacade;
import com.insigma.facade.sysbase.vo.SysUserDTO;
import com.insigma.webtool.component.LoginComponent;
import com.insigma.webtool.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import star.bizbase.vo.result.Results;
import star.vo.result.ResultVo;

@RestController
@RequestMapping("SysOrg")
@Api(tags ="系统机构管理")
public class SysOrgController {
    @Autowired
    LoginComponent loginComponent;

    @Autowired
    SysUserFacade sysUserFacade;
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
}
