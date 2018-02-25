package com.wxmp.backstage.sys.web.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wxmp.backstage.util.ValidateUtil;

/**
 * 
 * @title :  登陆拦截器
 * @description : 
 * @projectname : wxmp
 * @classname : SysInterceptor
 * @version 1.0
 * @author : sw_jee_dev
 * @createtime : 2017年4月2日 上午10:41:12
 */
public class SysInterceptor extends HandlerInterceptorAdapter {
    
	
	@SuppressWarnings("unchecked")
	@Override  
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {  
/*		HttpSession session = req.getSession();
		SysUserPO user = (SysUserPO) session.getAttribute("sysUser");
		String url = req.getRequestURI();
		String path = req.getContextPath();
		if(null != user) {
			url = url.replace(" ", "").replace( path + (ValidateUtil.isEquals(path.substring(path.lastIndexOf("/"), path.length()), "/") ? "" : "/"), "");
			List<SysMenuPO> menuCs = (List<SysMenuPO>)req.getSession().getAttribute("menuC");
			session.setAttribute("menuP", (List<SysMenuPO>)req.getSession().getAttribute("menuP"));
			session.setAttribute("menuC", menuCs);
			for(SysMenuPO menu : menuCs) {
				if(url.contains(menu.getMenuUrl())) {
					session.setAttribute("menuSelected", menu.getpNodeId());
					session.setAttribute("menuCSelected", menu.getMenuUrl());
					break;
				}
			}
			session.setAttribute("sysUser", user);
			return true;
		}
//		session.removeAttribute("menusP_status");
//		session.removeAttribute("menusC_status");
		String requestType = req.getHeader("X-Requested-With");
		if("XMLHttpRequest".equals(requestType)) {
			res.setHeader("sessionstatus", "timeout");
			return false;
		}else {
			res.sendRedirect(req.getContextPath()+"/login");
	    	return false;
		}*/
		
		
		return true;
    }  
	
	
    @Override  
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView arg3) throws Exception {  
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3) throws Exception {  
    }  
}
