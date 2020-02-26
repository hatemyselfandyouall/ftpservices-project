package com.insigma.constant;

public enum SysUserEnum {

	DEL_UNDELETED("n"), DEL_DELETED("y"),INIT_PASSWD("1qaz2wsx");
	private String value;

	SysUserEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
