package com.insigma.constant;

public enum BusinessCode {

	ARUGERROR("2001","参数不对!"),
	user_not_exist("2003","用户不存在"),
	;
	
	private String code;
	private String msg;
	
	private BusinessCode(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
}
