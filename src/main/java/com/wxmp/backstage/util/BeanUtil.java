/**
 * 
 */
package com.wxmp.backstage.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 功能：
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class BeanUtil {

	/**
	 * 得到属性的描述
	 * @param clazz
	 * @param propertyName 属性名
	 * @return
	 */
	public static PropertyDescriptor getPropertyDescriptor(Object obj,
			String propertyName) {
		try {
			BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : pds) {
				if("propertyName".equals(propertyDescriptor.getName())){
					return propertyDescriptor;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 给当前对象set属性值
	 * @param obj 类实例
	 * @param propertyName 属性名
	 * @param value 属性值
	 */
	public static void setProperty(Object obj, String propertyName, Object value) {
		PropertyDescriptor pd = getPropertyDescriptor(obj, propertyName);// 获取 clazz 类型中的 propertyName  的属性描述器
		setProperty(obj, pd, value);
	}
	
	/**
	 * 给当前对象set属性值
	 * @param obj 类实例
	 * @param pd 属性的描述
	 * @param value 属性值
	 */
	public static void setProperty(Object obj, PropertyDescriptor pd, Object value) {
		try {
			Method setMethod = pd.getWriteMethod();// 从属性描述器中获取 set 方法
			setMethod.invoke(obj, new Object[] { value });// 调用 set 方法将传入的value值保存属性中去
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 调用属性的get方法
	 * @param obj 类实例
	 * @param propertyName 属性名
	 * @return 类实例的属性值
	 */
	public static Object getProperty(Object obj, String propertyName) {
		PropertyDescriptor pd = getPropertyDescriptor(obj, propertyName);// 获取 clazz 类型中的  propertyName 的属性描述器
		return getProperty(obj, pd);
	}
	
	/**
	 * 调用属性的get方法
	 * @param obj 类实例
	 * @param pd
	 * @return 类实例的属性值
	 */
	public static Object getProperty(Object obj,PropertyDescriptor pd) {
		Method getMethod = pd.getReadMethod();// 从属性描述器中获取 get 方法
		Object value = null;
		try {
			value = getMethod.invoke(obj);// 调用方法获取方法的返回值
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;// 返回值
	}
	
}
