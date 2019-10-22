package com.insigma.vo;

import com.insigma.facade.sysbase.vo.SysOrgDTO;
import star.vo.BaseVo;

import java.util.List;

public class OrgVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	
	private SysOrgDTO sysOrgDTO;
	
	private List<String> insList;

	public SysOrgDTO getSysOrgDTO() {
		return sysOrgDTO;
	}

	public void setSysOrgDTO(SysOrgDTO sysOrgDTO) {
		this.sysOrgDTO = sysOrgDTO;
	}

	public List<String> getInsList() {
		return insList;
	}

	public void setInsList(List<String> insList) {
		this.insList = insList;
	}
	
	

}
