package com.wxmp.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wxmp.core.util.HttpUtil;

/**
 * 测试
 */
public class TestCaseInterceptor extends HandlerInterceptorAdapter {
	
	private String flag;
	private String[] excludes;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		for(String s : excludes){
			if(uri.contains(s)){
				return true;
			}
		}
		Object tmpFlag = request.getSession().getAttribute("flag");
		if(tmpFlag != null && tmpFlag.toString().equals(flag)){
			return true;
		}else{
			if(request.getParameter("flag") != null){
				tmpFlag = request.getParameter("flag");
				if(tmpFlag != null && tmpFlag.toString().equals(flag)){
					request.getSession().setAttribute("flag", tmpFlag);
					return true;
				}
			}
		}
		HttpUtil.redirectHttpUrl(request, response, "http://www.jeeweixin.com");
		return false;
	}
	
	public String[] getExcludes() {
		return excludes;
	}

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}

