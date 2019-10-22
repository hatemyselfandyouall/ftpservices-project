package com.insigma.web.sysuser;

import com.alibaba.fastjson.JSONArray;
import com.insigma.facade.sysbase.SysFunctionFacade;
import com.insigma.facade.sysbase.SysUserFacade;
import com.insigma.facade.sysbase.vo.SysFunctionDTO;
import com.insigma.facade.sysbase.vo.SysUserDTO;
import com.insigma.web.BasicController;
import com.insigma.webtool.component.LoginComponent;
import com.insigma.webtool.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import star.bizbase.vo.result.Results;
import star.vo.result.ResultVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("sys/function")
public class SysFunctionController extends BasicController {

	@Autowired
	private SysFunctionFacade sysFunctionFacade;
	@Autowired
	private LoginComponent loginComonent;
	@Autowired
	private SysUserFacade sysUserFacade;


	/**
	 * 首页菜单树
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryFunctionList" })
	public ResultVo<JSONArray> queryFunctionList(String funType) {
		ResultVo<JSONArray> result = Results.newResultVo();
		//从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		SysUserDTO sysUser = sysUserFacade.getCacheByPrimaryKey(userId);
		List<SysFunctionDTO> functionList = new ArrayList<SysFunctionDTO>();
		if(null != funType){
			functionList = sysFunctionFacade.findByFunTypeAndUserList(funType,sysUser);
		}
		JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id",
				"parentId", "children");
		result.setCode("0");
		result.setSuccess(true);
		result.setResult(jsonArray);
		return result;

	}
	

}
