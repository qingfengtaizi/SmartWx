package com.wxmp.core.interceptor;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
/**
 * xss攻击防御过滤器 -csrf攻击防御过滤
* @ClassName: XSSFilter 
* @version 1.0 2018年5月30日 下午3:55:42
 */
public class XSSFilter implements Filter {

	/**   
	* 需要排除的页面   
	*/    
	private String excludedPages;  
	
	private String[] excludedPageArray;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
		//获取访问路径
		String reqUri = req.getRequestURI();
		boolean isExcludedPage = false;     
		for (String page : excludedPageArray) {//判断是否在过滤url之外    equals(page) 
			if(reqUri.indexOf(page)!=-1){     
			isExcludedPage = true;     
			break;     
			}     
		} 
		//额外页不进行xss过滤
		if(isExcludedPage){
			chain.doFilter(request, response);
			return;
		}
		// pass the request along the filter chain
		XSSRequestWrapper xssRequest=new XSSRequestWrapper((HttpServletRequest) request);
		//设置cookie为HTTPOnly，防止js获取cookie
//		((HttpServletResponse)response).setHeader("Set-Cookie","cookiename=value; Path=/;Domain=domainvalue;Max-Age=seconds;HTTPOnly");
		/**
		  * csrf防御
		  */
		/*HttpSession session = xssRequest.getSession();
		// 从 session 中得到 csrftoken 属性
		String sToken = (String)session.getAttribute("csrf_token"); 
		
		if(sToken == null){ 
		   // 产生新的 token 放入 session 中
			String csrf_token = UUID.randomUUID().toString();
			session.setAttribute("csrf_token",csrf_token); 
		} else{ 
		 
		   // 从 HTTP 头中取得 csrftoken 
		   String xhrToken = xssRequest.getHeader("csrf_token"); 
		 
		   // 从请求参数中取得 csrftoken 
		   String pToken = xssRequest.getParameter("csrf_token"); 
		   
		   //登录页、主页不验证csrf
		   if(!reqUri.contains("index.do")
				   &&!reqUri.contains("login.do")
			   			&&!reqUri.contains("loginCheck.do")){
			   
			   if(sToken != null && xhrToken != null && sToken.equals(xhrToken)){ 
				   
			   }else if(sToken != null && pToken != null && sToken.equals(pToken)){ 
				   
			   }else{ 
				   
				   resp.sendRedirect(xssRequest.getContextPath()+"/jsp/500.jsp");
				   return ;
			   } 
		   }
		}*/
		/**
		  * csrf防御-结束
		  */
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub

		 //获取不拦截地址
		 excludedPages = config.getInitParameter("excludedPages");     
		 if (StringUtils.isNotEmpty(excludedPages)) {
			 excludedPageArray = excludedPages.split(",");     
		 } 
		 return;
	}

}
