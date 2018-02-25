package com.wxmp.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wxmp.backstage.sys.domain.SysUser;
import com.wxmp.core.util.SessionUtilsWeb;


/**
 * 权限拦截器
 * @author  www.bufeng.org
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	private final static Logger log= Logger.getLogger(AuthInterceptor.class);
	
	public String[] allowUrls;// 也可以注解

	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String userAgent = request.getHeader("user-agent");
		// HandlerMethod method = (HandlerMethod)handler;
		// Auth auth = method.getMethod().getAnnotation(Auth.class);
		log.debug("进入拦截器。。。。。。。。。。。。。。。。。。。。。");
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		System.out.println(requestUrl);
		if (null != allowUrls && allowUrls.length >= 1)
			for (String url : allowUrls) {
				if (requestUrl.equals(url)) {
					return true;
				}
			}
		if (SessionUtilsWeb.getUser(request) != null) {
			SessionUtilsWeb.getSession(request).setMaxInactiveInterval(60 * 60 * 30);
		}
		//验证登陆超时问题 auth = null，默认验证
		String baseUri = request.getContextPath();
		String path = request.getServletPath();
		SysUser user =SessionUtilsWeb.getUser(request);
		
	
		if(user  == null){
			log.debug("用户为空即将跳走。。。。。。。。。。。。。。。。。。。。。");
			response.setStatus(response.SC_GATEWAY_TIMEOUT);
			response.sendRedirect(baseUri+"/");
			return false;
		}

		return super.preHandle(request, response, handler);
	}

	
}
