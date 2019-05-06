package com.insigma.vo;

import star.vo.BaseVo;
/**
 * 
 * 
 * 
 * Title:省
 * 
 * Description:
 * 
 * Copyright: Copyright star.com(c) 2015
 * 
 * @author wuhh
 * @created 2015年6月26日 上午11:38:27
 * @version $Id$
 */
public class ProvinceVo extends BaseVo {
	
	private static final long serialVersionUID = -6658128538240390388L;

	//省ID
	private String provinceId;
	
	//省名称
	private String provinceName;
	
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}
