/*
 * FileName：AuthInterceptor.java 
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
package com.wxmp.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wxmp.wxcms.domain.SysUser;
import com.wxmp.core.util.SessionUtil;


/**
 * 用户拦截器
 * @author  hermit
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
		// HandlerMethod method = (HandlerMethod)handler;
		// Auth auth = method.getMethod().getAnnotation(Auth.class);
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		if (null != allowUrls && allowUrls.length >= 1)
			for (String url : allowUrls) {
				if (requestUrl.contains(".svg") || requestUrl.contains(".ttf") || requestUrl.contains(".woff") || requestUrl.contains(".eot") || requestUrl.contains(".css") || requestUrl.contains(".js") || requestUrl.contains(".png") || requestUrl.contains(".jpg") || requestUrl.contains("/message") || requestUrl.equals(url)) {
					return true;
				}
			}
		if (SessionUtil.getUser() != null) {
			SessionUtil.session.setMaxInactiveInterval(60 * 60 * 30);
		}
		if(null==SessionUtil.getUser()){
			if (isAjaxRequest(request)) {
				// 赋值错误状态码
				response.setStatus(300);
				// 前端弹出登录框
				response.setHeader("sessionState", "notLogin");
			} else {
				// 跳转到登录页
				response.sendRedirect("/views/login.html");
			}
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		boolean isAjax = request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
		return isAjax;
	}

//	public static boolean isReferer(HttpServletRequest request) {
//		return Util.isEmpty(request.getHeader("Referer")) && request.getMethod().equals("GET");
//	}
}
