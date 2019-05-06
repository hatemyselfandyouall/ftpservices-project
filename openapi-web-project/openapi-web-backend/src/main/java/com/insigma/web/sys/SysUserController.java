package com.insigma.web.sys;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insigma.facade.report.ProjectFacade;
import com.insigma.facade.report.vo.ProjectDTO;
import com.insigma.webtool.component.LoginComponent;

import star.bizbase.vo.result.Results;
import star.vo.result.ResultVo;

@Controller
@RequestMapping("/test")
public class SysUserController {

//	@Autowired
//	private ManagerFacade sysUserFacade;
//
//	@Resource
//	private PurviewFacade purviewFacade;
	
	@Autowired
	private LoginComponent loginComponent;
	@Autowired
	private ProjectFacade projectFacade;
	
	
	
	@RequestMapping("/getProject")
	@ResponseBody
	public ResultVo<ProjectDTO> getProject(HttpServletRequest request,  HttpServletResponse response) {
		ResultVo<ProjectDTO> result=Results.newResultVo();	
		ProjectDTO dto = projectFacade.getByPrimaryKey(1L);
		result.setResult(dto);
		result.setSuccess(true);
		return result;
	}
	@RequestMapping("/getProjectList")
	@ResponseBody
	public ResultVo<List<ProjectDTO>> getProjectList(String projectName, String projectType) {
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("projectName", projectName);
		param.put("projectType", projectType);
		ResultVo<List<ProjectDTO>> result=Results.newResultVo();	
		List<ProjectDTO> dtoList = projectFacade.getProjectList(param);
		result.setResult(dtoList);
		result.setSuccess(true);
		return result;
	}

	
	@RequestMapping("/toTest" )
	public String index(ModelMap modelMap) {
		return "test/test";
	}
	@RequestMapping("/ajax" )
	public String ajax(ModelMap modelMap) {
		return "test/ajax";
	}
	
//	@RequestMapping("/doAdd")
//	@ResponseBody
//	public ModelMap doAdd(HttpServletRequest reqeust, ManagerVo sysUser, String roleIds) {
//		ModelMap model = new ModelMap();
//		ResultVo<Integer> ret = new ResultVo<Integer>();
//		sysUser = XSSUtil.cleanXSS(sysUser);
//		if (sysUser == null) {
//			ret.setResultDes("sysUserVo is null");
//			ret.setSuccess(false);
//			model.addAttribute("ret", ret);
//			return model;
//		}
//		
//		if (StringUtil.isEmpty(sysUser.getPasswd())) {
//			ret.setResultDes("密码不能空");
//			ret.setSuccess(false);
//			model.addAttribute("ret", ret);
//			return model;
//		}
//		if (StringUtil.isEmpty(sysUser.getPasswd())) {
//			ret.setResultDes("密码不能空");
//			ret.setSuccess(false);
//			model.addAttribute("ret", ret);
//			return model;
//		}
//		String password = Encrypt.desEncrypt2(sysUser.getPasswd());
//		if (password.length() < 6) {
//			ret.setResultDes("密码长度必须大于6");
//			ret.setSuccess(false);
//			model.addAttribute("ret", ret);
//			return model;
//		}
//		if (StringUtil.isEmpty(roleIds)) {
//			ret.setResultDes("角色不能为空");
//			ret.setSuccess(false);
//			model.addAttribute("ret", ret);
//			return model;
//		}
//		if (sysUserFacade.getManagerByUserName(sysUser.getName()) != null) {
//			ret.setResultDes("该用户已存在");
//			ret.setSuccess(false);
//			model.addAttribute("ret", ret);
//			return model;
//		}
//		String str[]=roleIds.split(",");
//		List<Integer> list=new ArrayList<Integer>();
//		for(int i=0;i<str.length;i++){
//			list.add(Integer.parseInt(str[i]));
//		}
//		
//		sysUser.setPasswd(password);
//		ret = sysUserFacade.insertManager(sysUser, list);
//		model.addAttribute("ret", ret);
//		return model;
//	}
//
//	@RequestMapping("/add")
//	public String add(Model model, HttpServletRequest reqeust) {
//		
//		List<SysRoleVo> sysRoles =purviewFacade.getRoleListByPlatform(PlatformEnum.CPSBK.value());
//		
//		model.addAttribute("sysRoles", sysRoles);
//		return "/platform/user/add";
//	}
//
//	@RequestMapping("/edit")
//	public String edit(Model model, Integer id) {
//
//		ManagerVo sysUser = sysUserFacade.getManagerAndRoleNameById(id);
//		List<SysRoleVo> sysRoles =purviewFacade.getRoleListByPlatform(PlatformEnum.CPSBK.value());
////		sysUser.setPlatform(SystemConstants.SYSTEM_PLATFORM);//设置本用户登录的系统。。
//		this.convert2CheckRoleVo(sysRoles,sysUser);
//		
//		model.addAttribute("sysUserVo", sysUser);
//		model.addAttribute("sysRoles", sysRoles);
//		return "/platform/user/add";
//	}
//
//
//
//	/**
//	 * 账户信息，展示当前登录用户信息
//	 * 
//	 * @param model
//	 * @param reqeust
//	 * @return
//	 */
//	@RequestMapping("/info")
//	public String info(Model model, HttpServletRequest reqeust) {
//		Integer sysUserId = loginComponent.getLoginUserId();
//		ManagerVo sysUser = sysUserFacade.getManagerAndRoleNameById(sysUserId);
//
//		model.addAttribute("sysUserVo", sysUser);
//		return "/platform/user/info";
//	}
//
//	@RequestMapping("/delete")
//	@ResponseBody
//	public Model delete(Model model, HttpServletRequest reqeust, Integer sysUserId) {
//		ResultVo<Integer> ret = new ResultVo<Integer>();
//		ret = sysUserFacade.deleteManagerById(sysUserId);
//		model.addAttribute("ret", ret);
//		return model;
//	}
//
//	@RequestMapping("/list.html")
//	public String accounts(Model model, HttpServletRequest reqeust,String name,String realname,String rolename) {
//        SearchDataVo vo = SearchUtil.getVo();
//        SearchQuery query = new SearchQuery();
//        query.setStart(vo.getStart());
//        query.setSize(vo.getSize());
//        if (StringUtil.isNotEmpty(name)) {
//			query.putQuery("name", name);
//			vo.putQueryParam("name", name);
//		}
//        if (StringUtil.isNotEmpty(realname)) {
//			query.putQuery("realname", realname);
//			vo.putQueryParam("realname", realname);
//		}
//        if (StringUtil.isNotEmpty(rolename)) {
//			query.putQuery("rolename", rolename);
//			vo.putQueryParam("rolename", rolename);
//		}
//        SearchResult<SysUserInfoVo> result = sysUserFacade.getSysuserInfo(query);
//        vo.putSearchResult(result);
//        SearchUtil.putToModel(model, vo);
//		return "/platform/user/list";
//	}
//
//	@RequestMapping("/update")
//	@ResponseBody
//	public ModelMap update(HttpServletRequest reqeust, ManagerVo sysUser, String roleIds) {
//		sysUser = XSSUtil.cleanXSS(sysUser);
//		ModelMap model = new ModelMap();
//		
//		ResultVo<Integer> ret = new ResultVo<Integer>();
//		if (StringUtil.isEmpty(roleIds)) {
//			ret.setResultDes("角色不能为空");
//			ret.setSuccess(false);
//			model.addAttribute("ret", ret);
//			return model;
//		}
//		String str[]=roleIds.split(",");
//		List<Integer> list=new ArrayList<Integer>();
//		for(int i=0;i<str.length;i++){
//			list.add(Integer.parseInt(str[i]));
//		}
//		ret = sysUserFacade.updateManagerByInfoRoleId(sysUser, list);
//		
//		model.addAttribute("ret", ret);
//		return model;
//	}
//
//	/**
//	 * 修改密码
//	 */
//	@RequestMapping("/updatePasswd")
//	@ResponseBody
//	public Model updatePasswd(Model model, Integer sysUserId, String oldPasswd, String newPasswd) {
//		ResultVo<Integer> ret = new ResultVo<Integer>();
//		String oldPassword = Encrypt.desEncrypt2(oldPasswd);
//		String newPassword = Encrypt.desEncrypt2(newPasswd);
//		ret  = sysUserFacade.updatePassword(sysUserId, oldPassword, newPassword);
//		model.addAttribute("ret", ret);
//		return model;
//	}
//
//	@RequestMapping("/initPasswd")
//	@ResponseBody
//	public Model updatePasswd(Model model, Integer sysUserId) {
//		ResultVo<Integer> ret = new ResultVo<Integer>();
//		ret = sysUserFacade.updateInitPassword(sysUserId);
//		model.addAttribute("ret", ret);
//		return model;
//	}
//	
//	private void convert2CheckRoleVo(List<SysRoleVo> roleVos, ManagerVo sysUser) {
//		// TODO Auto-generated method stub
//		List<SysRoleVo> checkRoles=purviewFacade.getRoleBy(sysUser);
//		for(SysRoleVo roleVo:roleVos){
//			if(isContainRoleVo(checkRoles,roleVo))
//				roleVo.setCheck(YesOrNo.YES.getVal());
//			else
//				roleVo.setCheck(YesOrNo.NO.getVal());
//			
//		}
//		
//	}
//	private boolean isContainRoleVo(List<SysRoleVo> roleVos,SysRoleVo roleVo){
//		for(SysRoleVo r:roleVos){
//			if(r.getId()==roleVo.getId())
//				return true;
//		}
//		return false;
//	}
}
