package com.insigma.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring获取ApplicationContext对象工具类
 * @author xhy
 * @since:2019年3月27日上午3:14:35
 */
final public class MyApplicationContextUtil{
	private static ApplicationContext ac=null; 
	private MyApplicationContextUtil(){  } 
	    
	  static{ 
	      ac=new ClassPathXmlApplicationContext("spring-modules.xml"); 
	  } 
	    
	  public static ApplicationContext getApplicationContext(){ 
		    //获得返回的容器对象 
		    return ac; 
	  } 
}
