/*
 * FileName：OAuth2RequestParamHelper.java 
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
package com.wxmp.wxapi.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 微信oauth interceptor 处理参数转换；
 * 对于interceptor的url如果有参数，业务中请用此类获取参数；
 */
public class OAuth2RequestParamHelper {
	
	//准备state参数
	public static String prepareState(HttpServletRequest request){
		Map<String, String[]> map = request.getParameterMap();
		StringBuilder sb = new StringBuilder("");
		for(String key : map.keySet()){
			if(map.get(key) != null && map.get(key).length > 0){
				if(map.get(key)[0] != null){
					sb.append(key+"="+map.get(key)[0]+"!");//用！间隔
				}
			}
		}
		String str = sb.toString();
		if(StringUtils.isBlank(str)){
			return "";
		}else{
			 return str.substring(0, str.length() - 1);
		}
	}
	
	//获取state参数map
	public static Map<String,String> getStateParam(HttpServletRequest request){
		String state = request.getParameter("state");
		String[] stateArr = state.split("!");//用！间隔
		Map<String,String> param = new HashMap<String,String>();
		if(stateArr != null && stateArr.length > 0){
			for(String s : stateArr){
				String key = s.split("=")[0];
				String value = s.split("=")[1];
				param.put(key, value);	
			}
		}
		return param;
	}
	
	/**
	 * 根据key获取；如果有state，从state中获取；否则从request中获取
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request,String name){
		String state = request.getParameter("state");
		if(state != null){
			String[] stateArr = state.split("!");//用！间隔
			if(stateArr != null && stateArr.length > 0){
				for(String s : stateArr){
					String key = s.split("=")[0];
					String value = s.split("=")[1];
					if(name.equals(key)){
						return value;
					}
				}
			}
			return null;
		}else{
			return request.getParameter(name);
		}
	}
	
}
