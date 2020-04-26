//package com.insigma.web.sysuser;
//
//import com.alibaba.fastjson.JSONArray;
//import com.insigma.facade.sysbase.SysFunctionFacade;
//import com.insigma.facade.sysbase.SysUserFacade;
//import com.insigma.facade.sysbase.vo.SysFunctionDTO;
//import com.insigma.facade.sysbase.vo.SysUserDTO;
//import JSONUtil;
//import com.insigma.vo.SysFunctionVO;
//import com.insigma.web.BasicController;
//import com.insigma.webtool.component.LoginComponent;
//import com.insigma.webtool.util.TreeUtil;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import star.bizbase.vo.result.Results;
//import star.vo.result.ResultVo;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * 菜单管理
// *
// * @author Administrator
// *
// */
//@Controller
//@RequestMapping("sys/function")
//public class SysFunctionController extends BasicController {
//
//	@Autowired
//	private SysFunctionFacade sysFunctionFacade;
//	@Autowired
//	private LoginComponent loginComonent;
//	@Autowired
//	private SysUserFacade sysUserFacade;
//
//
//	/**
//	 * 首页菜单树
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = { "/queryFunctionList" })
//	public ResultVo<JSONArray> queryFunctionList(String funType) {
//		ResultVo<JSONArray> result = Results.newResultVo();
//		//从缓存获取用户信息
//		Long userId = loginComonent.getLoginUserId();
////		SysUserDTO sysUser = sysUserFacade.getCacheByPrimaryKey(userId);
//		List<SysFunctionVO> functionList = new ArrayList<>();
//		if (null != funType) {
//			functionList = sysFunctionFacade.findByFunTypeList(funType).stream().map(i-> JSONUtil.convert(i,SysFunctionVO.class)).collect(Collectors.toList());
//		}
//		SysUserDTO sysUser = sysUserFacade.getByPrimaryKey(userId);
//		List<SysFunctionDTO> sysFunctionDTOS=sysFunctionFacade.queryFunctionListByRoleId(sysUser);
//		Set<Long> ids  = sysFunctionDTOS.stream().map(SysFunctionDTO::getId).collect(Collectors.toSet());
//		functionList.forEach(i->{
//			if ("1".equals(sysUser.getUserType())) {//超级管理员
//				i.setHasPrivilege(true);
//			}else {
//				if (ids.contains(i.getId())) {
//					i.setHasPrivilege(true);
//				} else {
//					i.setHasPrivilege(false);
//				}
//			}
//		});
//		JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id",
//				"parentId", "children");
//		result.setCode("0");
//		result.setSuccess(true);
//		result.setResult(jsonArray);
//		return result;
//
//	}
//
//
//	/**
//	 * 首页菜单树
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = { "/BackGroundMenu" })
//	public ResultVo<JSONArray> queryFunctionList() {
//		ResultVo<JSONArray> result = Results.newResultVo();
//		//从缓存获取用户信息
//		List<SysFunctionVO> functionList = new ArrayList<SysFunctionVO>();
//		Long userId = loginComonent.getLoginUserId();
//		SysUserDTO sysUser = sysUserFacade.getByPrimaryKey(userId);
//		if(null != sysUser) {
//			for (int i = 6; i < 10; i++) {
////				String absulateUrl = DataConstant.ABSOLUTE_URL_MAP.get(i);
//				functionList.addAll(sysFunctionFacade.findByFunTypeList(i + "").stream().map(j -> {
//					SysFunctionVO sysFunctionVO = new SysFunctionVO();
//					BeanUtils.copyProperties(j, sysFunctionVO);
////					sysFunctionVO.setAbsoluteUrl(absulateUrl);
//					return sysFunctionVO;
//				}).collect(Collectors.toList()));
//			}
//			if ("1".equals(sysUser.getUserType())) {//超级管理员
//				functionList.forEach(i->i.setHasPrivilege(true));
//			} else {
//				Set<Long> ids  = sysFunctionFacade.queryFunctionListByRoleId(sysUser).stream().map(SysFunctionDTO::getId).collect(Collectors.toSet());
//				functionList.forEach(i->{
//					if ("1".equals(sysUser.getUserType())) {//超级管理员
//						i.setHasPrivilege(true);
//					}else {
//						if (ids.contains(i.getId())) {
//							i.setHasPrivilege(true);
//						} else {
//							i.setHasPrivilege(false);
//						}
//					}
//				});
//			}
//		}
//		JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id",
//				"parentId", "children");
//		result.setCode("0");
//		result.setSuccess(true);
//		result.setResult(jsonArray);
//		return result;
//
//	}
//}
