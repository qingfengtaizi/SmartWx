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

