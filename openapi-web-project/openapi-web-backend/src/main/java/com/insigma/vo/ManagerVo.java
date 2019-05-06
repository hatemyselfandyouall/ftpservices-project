package com.insigma.vo;

import star.vo.BaseVo;
/**
 * 
 *@Author:xhy
 *@since：2019年3月18日 下午7:42:45
 *@return:
 */
public class ManagerVo extends BaseVo {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String passwd;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
}
