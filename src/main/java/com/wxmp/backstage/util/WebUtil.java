/**
 * 
 */
package com.wxmp.backstage.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;
import org.springframework.util.Assert;

import com.wxmp.backstage.common.Configuration;
import com.wxmp.backstage.util.ArrayUtil;
import com.wxmp.backstage.util.ConvertUtil;
import com.wxmp.backstage.util.ExcptUtil;
import com.wxmp.backstage.util.FileUtil;
import com.wxmp.backstage.util.StringUtil;
import com.wxmp.backstage.util.ValidateUtil;

/**
 * 功能：Web工具类
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class WebUtil {

	
	public static final String POST_METHOD = "POST";
	public static final String PREFIX_URL = "/";
	public static final String X_REAL_IP = "X-Real-IP";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
	/**
	 * 解析queryString
	 * @param request
	 * @param execludeParams
	 * @param charset
	 * @return
	 */
	public static String parseQueryString(ServletRequest request,String[] execludeParams,String charset){
		java.util.Set<String> paramAndValues = new java.util.HashSet<String>();
		Enumeration<?> e = request.getParameterNames();
		StringBuilder sb = new StringBuilder();
		for(int i=0;e.hasMoreElements();){
			String param = e.nextElement().toString();
			if(ArrayUtil.contain(execludeParams, param))continue;
			String[] vals = request.getParameterValues(param);
			if(vals!=null){
				for(int j=0,k=vals.length;k>0 && j<k && ValidateUtil.isNotNull(vals[j]);j++,i++){
					String paramAndValue = StringUtil.concat(urlEncoding(param,charset),"=",urlEncoding(vals[j],charset));
					if(paramAndValues.contains(paramAndValue))
						continue;
					if(i>0)sb.append("&");
					sb.append(paramAndValue);
				}
			}
		}
		return sb.toString();
	}
	/**
	 * 获得url请求地址加QueryString
	 * @param request
	 * @param execludeParams
	 * @param charset
	 * @return
	 */
	public static String urlRequest(HttpServletRequest request,String[] execludeParams,String charset){
		String requestUrl = request.getServletPath();
		String query = WebUtil.parseQueryString(request,execludeParams,Configuration.getEncoding());
		String url = null;
		if(ValidateUtil.isNotNull(query))
			url = new StringBuilder().append(requestUrl).append("?").append(query).toString();
		else
			url = requestUrl;
		return url;
	}

	/**
	 * url转码
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String urlEncoding(String value,String charset){
		try{
			return java.net.URLEncoder.encode(value,charset);
		}catch(Exception e){
			return StringUtil.EMPTY_STRING;
		}

	}
	/**
	 * url解码
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String urlDecoding(String value,String charset){
		try{
			return java.net.URLDecoder.decode(value,charset);
		}catch(Exception e){
			return StringUtil.EMPTY_STRING;
		}

	}

	/**
	 * 获得ip地址
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
        String remoteIp = request.getHeader(X_REAL_IP); //nginx反向代理
        if (ValidateUtil.isNotNull(remoteIp)) {
            return remoteIp;
        } else {
            remoteIp = request.getHeader(X_FORWARDED_FOR);//apache反射代理
            if (ValidateUtil.isNotNull(remoteIp)) {
                String[] ips = remoteIp.split(",");
                for (String ip : ips) {
                    if (!(ValidateUtil.isNull(ip) || ValidateUtil.isEqualsIgnoreCase("null", ip))) {
                        return ip;
                    }
                }
            }
            return request.getRemoteAddr();
        }
    }
	
	/**
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration<?> paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 组合Parameters生成Query String的Parameter部分, 并在paramter name上加上prefix.
	 * 
	 * @see #getParametersStartingWith
	 */
	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		if (params == null || params.size() == 0) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}

	/**
	 * 客户端对Http Basic验证的 Header进行编码.
	 */
	public static String encodeHttpBasic(String userName, String password) {
		String encode = userName + ":" + password;
		return "Basic " + ConvertUtil.convertEncodeBase64(encode.getBytes());
	}
	
	/**
	 * 验证站外提交
	 * @param request
	 * @return
	 */
	public static boolean isInvalidRequests(HttpServletRequest request){
		boolean invalidRequest = false;
		Enumeration<?> headerValues =  request.getHeaders("Referer");
		String referer = null;
		while (headerValues!=null && headerValues.hasMoreElements()){
			referer = (String) headerValues.nextElement();
			if(ValidateUtil.isNull(referer)){
				invalidRequest = true;
			}else{
				//判断请求页面和getRequestURI是否相同 
				String serverName = request.getServerName();
				if(ValidateUtil.isNotNull(serverName)){
					int index = 0;
					if(referer.indexOf("https://") == 0){
						index = 8;
					}else 
						if(referer.indexOf("http://") == 0){
							index = 7;
						}
					if(referer.length()-index<serverName.length()){
						//长度不够
						invalidRequest = true;
					}else{
						String referer_str=referer.substring(index,index+serverName.length()); 
						if(!serverName.equalsIgnoreCase(referer_str)){
							invalidRequest = true;
						}
					}
				}else{
					invalidRequest = true;
				}
			}
		}
		return invalidRequest;
	}
	
	/**
	 * 是否是第一次调用此过滤器
	 * if(true)添加对象""到request对象
	 * @param class1
	 * @return
	 */
	public static boolean isFirstFilter(Class<? extends Filter> clazz,HttpServletRequest request) {
		String filterSignature = StringUtil.concat("Filter:",clazz.getName());
		if(request != null && ValidateUtil.isEquals(request.getParameter(filterSignature), StringUtil.EMPTY_STRING)){
			return true;
		}
		request.setAttribute(filterSignature, StringUtil.EMPTY_STRING);
		return false;
	}
	
	/**
	 * 验证方法是否是POST提交
	 * @param request
	 * @return
	 */
	public static boolean isInvalidPostMethod(HttpServletRequest request){
		if(!ValidateUtil.isEqualsIgnoreCase(POST_METHOD, request.getMethod())){
			return true;
		}
		return false;
	}
	
	/**
	 * 获得对应应用的地址
	 * @param request
	 * @param url
	 * @return
	 */
	public static String getAppbaseUrl(HttpServletRequest request,String url){
		Assert.hasLength(url);
		Assert.isTrue(url.startsWith(PREFIX_URL));
		return StringUtil.concat(request.getContextPath(),url);
	}
	
	/**
	 * 输出流
	 * @param response
	 * @param responseMessage
	 * @throws IOException
	 */
	public static void writeResonse(HttpServletResponse response,
			String responseMessage) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			byte[] responseByte = responseMessage.getBytes(Configuration
					.getEncoding());
			response.setContentType("text/json");
			response.setContentLength(responseByte.length);// 添加响应包长度
			response.setCharacterEncoding(Configuration.getEncoding());
			out.write(responseByte); // 写入数据
			out.flush(); // 结束缓冲
		} catch (Exception e) {
			System.err.println(ExcptUtil.getStackTraceAsString(e));
		} finally {
			FileUtil.close(out);
		}
	}
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
