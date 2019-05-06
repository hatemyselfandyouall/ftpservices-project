package com.insigma.vo;
/**
 * 
 * 
 * 
 * Title:学校Vo
 * 
 * Description:
 * 
 * Copyright: Copyright star.com(c) 2015
 * 
 * @author wuhh
 * @created 2015年6月26日 下午1:55:33
 * @version $Id$
 */
public class SchoolVo extends CityVo {

	private static final long serialVersionUID = 2890757691191182053L;

	//学校ID
	private Long schoolId;
	
	//学校名称
	private String schoolName;

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	
}
