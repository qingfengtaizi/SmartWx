/*
 * FileName：SessionUtil.java 
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

import com.wxmp.wxcms.domain.SysUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author hermit
 * Session 工具类
 */
@Component
public final class SessionUtil {

	protected static final Logger logger = Logger.getLogger(SessionUtil.class);

	public static HttpSession session;

	private static HashMap<String, HttpSession> sessions;
	private static HashMap<String, String> userSessionIds;
	public static final String SESSION_USER = "session_user";
	static {
		sessions = new HashMap<String, HttpSession>();
		userSessionIds = new HashMap<String, String>();
	}

	/**
	 * 设置session的值
	 * @param key
	 * @param value
	 */
	public static void setAttr(String key, Object value) {
		session.setAttribute(key, value);
	}

	/**
	 * 获取session的值
	 * @param key
	 */
	public static Object getAttr(String key) {
		return session.getAttribute(key);
	}

	/**
	 * 删除Session值
	 * @param key
	 */
	public static void removeAttr(String key) {
		session.removeAttribute(key);
	}

	/**
	 * 设置用户信息 到session
	 * @param user
	 */
	public synchronized static void setUser(SysUser user) {
		// 以有次用户，踢出此用户
		kickOutUserByUserAcc(user.getAccount());
		session.setAttribute(SESSION_USER, user);
		logger.debug("[session管理]用户有效，开始将session缓存起来，sessionid为：" + session.getId() + ",用户登陆账号：" + user.getAccount());
		userSessionIds.put(user.getAccount(), session.getId());
		addSession(session);
	}


	/**
	 * 从session中获取用户信息
	 * @return SysUser
	 */
	public static SysUser getUser() {
		return (SysUser) session.getAttribute(SESSION_USER);
	}

	/**
	 * 从session中获取用户信息
	 * @return SysUser
	 */
	public static String getUserId() {
		SysUser user = getUser();
		if (user != null) {
			return user.getId();
		}
		return null;
	}

	// 根据用户账户踢出用户
	public synchronized static void kickOutUserByUserAcc(String account) {
		if (userSessionIds.get(account) != null) {
			// 找到此用户
			HttpSession session = sessions.get(userSessionIds.get(account));
			if (session != null) {
				logger.debug("[session管理]踢出用户，sessionid为：" + session.getId() + ",用户登陆账号：" + account);
				removeSession(session);
				logger.debug("[session管理]踢出用户，将session变为无效，sessionid为：" + session.getId());
				session.invalidate();
				sessions.remove(session.getId());
				// 发送下线通知.
				System.out.println("用户在线，踢出用户：" + session.getId());
				logger.debug("[session管理]踢出用户，开始发送下线通知，sessionid为：" + session.getId() + ",用户登陆账号：" + account);
			}
			userSessionIds.remove(account);
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

	@Autowired
	public void setSession(HttpSession session) {
		this.session = session;
	}
}
