package com.wxmp.core.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class SpringContextFactory implements ApplicationContextAware{
	
	 //获取classpath下的applicationContext.xml
	 private static ApplicationContext ctx ;
	 
	 public static ApplicationContext getSpringContext(){
		 return ctx;
	 }
	 //get bean
	 public static Object getBean(String beanKey){
		 return ctx.getBean(beanKey);
	 }
	 
	 // get web spring context
	 public static ApplicationContext getWebSpringContext(HttpServletRequest request){
		 return getWebSpringContext(request.getSession());
	 }
	 
	 public static ApplicationContext getWebSpringContext(HttpSession session){
		 return getWebSpringContext(session.getServletContext());
	 }
	 
	 public static ApplicationContext getWebSpringContext(ServletContext servletContext){
		 return WebApplicationContextUtils.getWebApplicationContext(servletContext);
	 }
	 
	// get web spring context bean
	 public static Object getWebBean(HttpServletRequest request,String beanKey){
		 return getWebSpringContext(request).getBean(beanKey);
	 }
	 
	 public static Object getWebBean(HttpSession session,String beanKey){
		 return getWebSpringContext(session).getBean(beanKey);
	 }
	 
	 public static Object getWebBean(ServletContext servletContext,String beanKey){
		 return getWebSpringContext(servletContext).getBean(beanKey);
	 }
	 
	@Override
	public void setApplicationContext(ApplicationContext arg0)throws BeansException {
		ctx = arg0;
	}
	 
}
