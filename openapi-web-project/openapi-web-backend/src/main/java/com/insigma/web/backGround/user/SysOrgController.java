package com.insigma.web.backGround.user;

import com.alibaba.fastjson.JSONArray;
import com.insigma.facade.sysbase.SysOrgFacade;
import com.insigma.facade.sysbase.SysUserFacade;
import com.insigma.facade.sysbase.vo.SysOrgDTO;
import com.insigma.facade.sysbase.vo.SysUserDTO;
import com.insigma.facade.sysbase.vo.SysUserOrgDTO;
import com.insigma.webtool.component.LoginComponent;
import com.insigma.webtool.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.bizbase.exception.BizRuleException;
import star.bizbase.vo.result.Results;
import star.vo.result.ResultVo;

import java.util.*;

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

    /**
     * 查询树
     * @return
     * @throws BizRuleException
     */
    @ResponseBody
    @RequestMapping(value = { "/queryTree" })
    public ResultVo<JSONArray> queryTree() throws BizRuleException {
        //获取当前登录用户的所属区域
        //从缓存获取用户信息
        Long userId = loginComponent.getLoginUserId();
        SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);
        ResultVo<JSONArray> result = Results.newResultVo();
        List<SysOrgDTO> orgList = new ArrayList<>();
        if(user.getUserType().equals("1")){//管理员获取所有机构
            HashMap<String, Object> searchMap = new HashMap<>();
            searchMap.put("orgState", "1");
            orgList = sysOrgFacade.getListByWhere(searchMap);
        }else{
//			orgList = sysOrgFacade.queryOrgNodes(user.getAreaId().toString());
            List<SysUserOrgDTO> userOrgList = sysUserFacade.queryUserOrg(userId);
            if(userOrgList.size()>0){
                Set<String> s = new HashSet<>();
                for(SysUserOrgDTO uo :userOrgList){
                    SysOrgDTO org = sysOrgFacade.getByPrimaryKey(uo.getOrgId());
                    String idpath = org.getIdpath();
                    String[] splitAddress=idpath.split("/"); //如果以竖线为分隔符，则split的时候需要加上两个斜杠【\\】进行转义
                    for(int i=0;i<splitAddress.length;i++){
                        s.add(splitAddress[i]);
                    }
                }
                String orgIds = "'" + StringUtils.join(s, "','") + "'";
                orgList = sysOrgFacade.queryOrgListByOrgIds(orgIds);
            }
        }
        JSONArray jsonArray = null;
        if(orgList.size()>0){
            jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(orgList)), "id",
                    "parentId", "children");
        }

        result.setCode("0");
        result.setSuccess(true);
        result.setResult(jsonArray);
        return result;

    }
}
