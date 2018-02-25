package com.wxmp.backstage.sys.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @title :
 * @description :
 * @projectname : sw-platformadmin-webapp
 * @classname : SysContent
 * @version 1.0
 * @author : xiongliang
 * @createtime : 2017年3月9日 下午8:13:09
 */
public class SysContent {
	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) requestLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestLocal.set(request);
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) responseLocal.get();
	}

	public static void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}

	public static HttpSession getSession() {
		return (HttpSession) ((HttpServletRequest) requestLocal.get()).getSession();
	}

}
