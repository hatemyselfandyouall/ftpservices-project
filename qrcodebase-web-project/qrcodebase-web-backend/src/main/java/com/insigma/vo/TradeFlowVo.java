package com.insigma.vo;

import java.util.Date;

import star.vo.BaseVo;

public class TradeFlowVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String step;
	private String id;
	private String name;
	private Date time;
	private String result;
	private String desc;
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	

}
