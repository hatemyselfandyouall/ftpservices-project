package com.insigma.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class HttpHelper {
	
   public static String sendPost(String linkUrl, String xmlData) {
		String result = "";
		URL url=null;
		HttpURLConnection httpUrl=null;
		BufferedReader reader=null;
		try {
			url = new URL(linkUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			// 设置请求方式，默认为POST
			httpUrl.setRequestMethod("POST");//POST||GET
			// 设置请求超时
			httpUrl.setConnectTimeout(8000);
			// Post 请求不能使用缓存
			httpUrl.setUseCaches(false);
			//设置通用的请求属性
	  		httpUrl.setRequestProperty("Pragma","no-cache");
	  		httpUrl.setRequestProperty("Cache-Control","no-cache");
	  		httpUrl.setRequestProperty("Content-Type","application/json");
	  		httpUrl.setRequestProperty("appKey","123");
	  		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	  		String time = sdf.format(new Date());
	  		httpUrl.setRequestProperty("time",time);
	
	  		String nonceStr = UUID.randomUUID().toString().replace("-", "");
	  		httpUrl.setRequestProperty("nonceStr",nonceStr);

	  		SignUtil2 sign = new SignUtil2();
	  		String signature = sign.getSign(nonceStr, time, xmlData);
	  		httpUrl.setRequestProperty("signature",signature);
	        //提交的数据
           if(xmlData!=null&&!"".equals(xmlData)){
           	   httpUrl.setDoOutput(true);
           	   httpUrl.getOutputStream().write(xmlData.getBytes("utf-8"));
               httpUrl.getOutputStream().flush();
        
           }else{
           	httpUrl.connect();
           }
 

           int code = httpUrl.getResponseCode();   
           if(code==200){
				reader = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "utf-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					result += line;
				}
           }
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (reader != null) {
					reader.close();
				}
			
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
   
}
