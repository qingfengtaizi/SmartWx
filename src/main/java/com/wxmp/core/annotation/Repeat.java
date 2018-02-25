package com.wxmp.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD) 
@Documented 
@Inherited
public @interface  Repeat {
	/**
	 * 重发时间
	 * @return
	 */
	 public boolean repeatTime() default true;
	 
	 
	
}
