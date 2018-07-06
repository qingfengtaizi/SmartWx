/*
 * FileName：CookieUtil.java 
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
package com.wxmp.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	public static final String WEIXIN_OPENID = "_weixin_openid_";
	
	public static Cookie addCookie(HttpServletResponse response,String cookieName,String value,Integer maxAge,String domain) {  
        return addCookie(response, cookieName, value, maxAge, domain, "/");
    }
	
	public static Cookie addCookie(HttpServletResponse response,String cookieName,String value,Integer maxAge,String domain,String path) {  
        Cookie cookie = new Cookie(cookieName,value); 
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setPath(path);
        response.addCookie(cookie);
        return cookie;  
    }
  
    // 获取 cookie  
    public static String getCookie(HttpServletRequest request, String cookieName) {  
        Cookie[] cookies = request.getCookies();  
        if (cookies != null) {  
            for (Cookie cookie : cookies) {  
                if (cookieName.equalsIgnoreCase(cookie.getName())) {  
                    return cookie.getValue();  
                }  
            }  
        }  
        return null;
    }
  
    // 删除 cookie  
    public static void delCookie(HttpServletResponse response, String cookieName) {  
    	Cookie cookie = new Cookie(cookieName,null); 
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
