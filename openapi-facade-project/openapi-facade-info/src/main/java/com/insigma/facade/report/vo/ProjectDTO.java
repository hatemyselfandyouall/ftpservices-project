package com.insigma.facade.report.vo;

import star.vo.BaseVo;

public class ProjectDTO extends BaseVo {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String projectName;
	
	private String projectNo;
	
	private String projectType;
	
	private String projectModel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
