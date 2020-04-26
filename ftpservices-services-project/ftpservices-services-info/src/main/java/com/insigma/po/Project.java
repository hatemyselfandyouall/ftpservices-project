package com.insigma.po;

import star.vo.BaseVo;

public class Project extends BaseVo {
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String projectName;
	
	private String projectNo;
	
	private String projectType;
	
	private String projectModel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectModel() {
		return projectModel;
	}

	public void setProjectModel(String projectModel) {
		this.projectModel = projectModel;
	}
	
	
}
