/*
 * FileName：SpringContextHolder.java 
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
package com.wxmp.core.spring;

import com.wxmp.core.exception.UnexpectedException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * 
 * 
 */
@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * 如果有多个Bean符合Class, 取出第一个.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> requiredType) {
		checkApplicationContext();
		Map<String,T> beans= applicationContext.getBeansOfType(requiredType);
		if(!beans.isEmpty()){
			return beans.values().iterator().next();
		}
		throw new UnexpectedException("spring中没有类型【"+requiredType.getName()+"】的实例");
	}
	
	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * 如果有多个Bean符合Class, 取出第一个.如果没有对应的类返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBeanByClassName(String clazz) {
		checkApplicationContext();
		try {
			Map<String,?> beans= applicationContext.getBeansOfType(Class.forName(clazz));
			if(!beans.isEmpty()){
				return (T)beans.values().iterator().next();
			}
			throw new UnexpectedException("spring中没有类型【"+clazz+"】的实例");
		} catch (Exception e) {
			throw new UnexpectedException(e.getMessage(),e);
		} 
	}
	
	/**
	 * 清除applicationContext静态变量.
	 */
	public static void cleanApplicationContext(){
		applicationContext = null;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
}
