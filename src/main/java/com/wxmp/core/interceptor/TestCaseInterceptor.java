/*
 * FileName：TestCaseInterceptor.java 
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
