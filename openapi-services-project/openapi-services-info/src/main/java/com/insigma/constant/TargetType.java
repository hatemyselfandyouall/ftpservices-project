package com.insigma.constant;

public enum TargetType {
	ANDROID("android"),
	IOS("ios"),
	ALL("all");
	
	String val;
	
	public final static String[] values = new String[]{ALL.getVal(),IOS.getVal(),IOS.getVal()};
	
	public String getVal() {
		return val;
	}


	public void setVal(String val) {
		this.val = val;
	}


	private TargetType(String val){
		this.val = val;
	}
}
