package com.wxmp.core.util;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.wxmp.backstage.sys.domain.SysUser;

/**
 * 
 * Cookie 工具类
 *
 */
public final class SessionUtilsWeb {

	protected static final Logger logger = Logger.getLogger(SessionUtils.class);

	public static final String SESSION_USER = "session_user_web";

	private static final String SESSION_VALIDATECODE = "session_validatecode";// 验证码

	private static final String SESSION_ACCESS_URLS_WEB = "session_access_urls_web"; // 系统能够访问的URL

	private static final String SESSION_MENUBTN_MAP = "session_menubtn_map"; // 系统菜单按钮

	/**
	 * 设置session的值
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void setAttr(HttpServletRequest request, String key, Object value) {
		getSession(request).setAttribute(key, value);
	}

	/**
	 * 获取session的值
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	public static Object getAttr(HttpServletRequest request, String key) {
		return getSession(request).getAttribute(key);
	}

	/**
	 * 删除Session值
	 * 
	 * @param request
	 * @param key
	 */
	public static void removeAttr(HttpServletRequest request, String key) {
		getSession(request).removeAttribute(key);
	}

	/**
	 * 设置用户信息 到session
	 * 
	 * @param request
	 * @param user
	 */
	public synchronized static void setUser(HttpServletRequest request, SysUser user) {
		HttpSession session = getSession(request);
		// 以有次用户，踢出此用户
		kickOutUserByUserZH(user.getAccount());
		session.setAttribute(SESSION_USER, user);
		logger.debug("[session管理]用户有效，开始将session缓存起来，sessionid为：" + session.getId() + ",用户登陆账号：" + user.getAccount());
		userSessionIds.put(user.getAccount(), session.getId());
		addSession(session);
	}

	/**
	 * 设置用户信息 到session
	 * 
	 * @param request
	 * @param wytuser
	 */
	public static void setwytUser(HttpServletRequest request, SysUser wytuser) {
		getSession(request).setAttribute(SESSION_USER, wytuser);
	}

	/**
	 * 从session中获取用户信息
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static SysUser getUser(HttpServletRequest request) {
		return (SysUser) getSession(request).getAttribute(SESSION_USER);
	}

	/**
	 * 从session中获取用户信息
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static String getUserId(HttpServletRequest request) {
		SysUser user = getUser(request);
		if (user != null) {
			return user.getId();
		}
		return null;
	}

	/**
	 * 从session中获取用户信息
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void removeUser(HttpServletRequest request) {
		removeAttr(request, SESSION_USER);
	}

	/**
	 * 设置验证码 到session
	 * 
	 * @param request
	 * @param user
	 */
	public static void setValidateCode(HttpServletRequest request, String validateCode) {
		getSession(request).setAttribute(SESSION_VALIDATECODE, validateCode);
	}

	/**
	 * 从session中获取验证码
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static String getValidateCode(HttpServletRequest request) {
		return (String) getSession(request).getAttribute(SESSION_VALIDATECODE);
	}

	/**
	 * 从session中获删除验证码
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void removeValidateCode(HttpServletRequest request) {
		removeAttr(request, SESSION_VALIDATECODE);
	}

	/**
	 * 设置可以访问的路径
	 * 
	 * @param request
	 * @return
	 */
	public static void setAccessWebUrl(HttpServletRequest request, List<String> accessUrls) { // 判断登录用户是否超级管理员
		setAttr(request, SESSION_ACCESS_URLS_WEB, accessUrls);
	}

	/**
	 * 判断URL是否可访问
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAccessWebUrl(HttpServletRequest request, String url) {
		List<String> accessUrls = (List<String>) getAttr(request, SESSION_ACCESS_URLS_WEB);
		if (accessUrls == null || accessUrls.isEmpty()) {
			return false;
		}
		if (accessUrls.contains(url)) {
			return true;
		} else {
			return false;
		}
	}

	private static HashMap<String, HttpSession> sessions;
	private static HashMap<String, String> userSessionIds;

	static {
		sessions = new HashMap<String, HttpSession>();
		userSessionIds = new HashMap<String, String>();
	}

	/**
	 * 获取当前用户session对象，内部使用，如果没有，则新建session
	 * 
	 * @param request
	 * @return
	 */
	public synchronized static HttpSession getSession(HttpServletRequest request) {
		// HttpSession session = request.getSession();
		// if (request.getSession() == null) {
		//
		// session = request.getSession(true);
		//
		// addSession(session);
		// }
		return request.getSession(true);
	}

	// 根据用户账户踢出用户
	public synchronized static void kickOutUserByUserZH(String dlzh) {
		if (userSessionIds.get(dlzh) != null) {
			// 找到此用户
			HttpSession session = sessions.get(userSessionIds.get(dlzh));
			if (session != null) {
				logger.debug("[session管理]踢出用户，sessionid为：" + session.getId() + ",用户登陆账号：" + dlzh);
				removeSession(session);
				logger.debug("[session管理]踢出用户，将session变为无效，sessionid为：" + session.getId());
				session.invalidate();
				sessions.remove(session.getId());
				// 发送下线通知.
				System.out.println("用户在线，踢出用户：" + session.getId());
				logger.debug("[session管理]踢出用户，开始发送下线通知，sessionid为：" + session.getId() + ",用户登陆账号：" + dlzh);
			}
			userSessionIds.remove(dlzh);
		}
	}


	/**
	 * 更加用户登陆账号，重新设置可访问路径
	 * 
	 * @param dlzh
	 *            手机号
	 * @param request
	 * @param accessUrls
	 */
	public static void setAccessWebUrlByDlzh(String dlzh, HttpServletRequest request, List<String> accessUrls) { // 判断登录用户是否超级管理员
		String sessionId = userSessionIds.get(dlzh);
		if (sessionId != null) {
			HttpSession session = sessions.get(sessionId);
			session.setAttribute(SESSION_ACCESS_URLS_WEB, accessUrls);
		}
	}


	/**
	 * 将session加入到缓存中.
	 * 
	 * @param session
	 */
	public synchronized static void addSession(HttpSession session) {
		logger.debug("[session管理]设置用户信息，将此session保存至内存，sessionid为：" + session.getId());
		sessions.put(session.getId(), session);
	}

	private synchronized static void removeSession(HttpSession session) {
		logger.debug("[session管理]将session从内存中移除，sessionid为：" + session.getId());
		sessions.remove(session.getId());
	}
}