/**
 * 
 */
package com.wxmp.backstage.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 功能：异常的工具类
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class ExcptUtil {
	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Throwable e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	public static boolean isCausedBy(Throwable ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
}
