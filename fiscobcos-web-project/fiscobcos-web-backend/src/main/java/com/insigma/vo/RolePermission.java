package com.insigma.vo;

import star.vo.BaseVo;

public class RolePermission extends BaseVo{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String permission;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	
}
