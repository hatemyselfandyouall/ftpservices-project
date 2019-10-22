package com.insigma.vo;

import com.alibaba.fastjson.JSONArray;

public class SysOrgVo {

	private String idpath;
	
	private JSONArray orgList;

	public String getIdpath() {
		return idpath;
	}

	public void setIdpath(String idpath) {
		this.idpath = idpath;
	}

	public JSONArray getOrgList() {
		return orgList;
	}

	public void setOrgList(JSONArray orgList) {
		this.orgList = orgList;
	}
	
	
}
