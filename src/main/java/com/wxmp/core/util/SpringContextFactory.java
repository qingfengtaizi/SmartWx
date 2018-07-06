/*
 * FileName：SpringContextFactory.java 
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
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
