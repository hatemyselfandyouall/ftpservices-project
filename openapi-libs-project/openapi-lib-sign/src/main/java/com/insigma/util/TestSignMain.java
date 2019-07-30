package com.insigma.util;

public class TestSignMain {

	public static void main(String[] args) {
		 String testSecret="abed332121604f7e81cbc2cead8fc51f";
	        String testKey="cd4e3d5ff09e4a59ba94ebbb82bafc43";
	        String time="20190729 21:01:35";
	        String nonceStr = "W29FR0D03QIZPN8UU3Z0OY8VR39KKLZ1";
	        String paramStr="{\"ver\":\"V1.0\",\"orgNo\":\"330000101011\",\"orgName\":\"浙江省立同德医院\",\"id\":\"\",\"inPut\":[{\"AAC002\":\"330102197501016171\"}]}";
	        String param = paramStr+testKey+time+nonceStr;
	        String signature = SignUtil.createSign(param,testSecret);
	        System.out.println(signature);

	}

}
