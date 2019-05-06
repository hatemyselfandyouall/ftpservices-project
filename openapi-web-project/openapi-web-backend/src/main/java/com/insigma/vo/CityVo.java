package com.insigma.vo;
/**
 * 
 * 
 * 
 * Title:城市Vo
 * 
 * Description:
 * 
 * Copyright: Copyright star.com(c) 2015
 * 
 * @author wuhh
 * @created 2015年6月26日 上午11:52:19
 * @version $Id$
 */
public class CityVo extends ProvinceVo {

	private static final long serialVersionUID = -950768691117508783L;

	//城市ID
	private String cityId;
	
	//城市名称
	private String cityName;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
