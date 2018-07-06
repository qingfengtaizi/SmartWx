/*
 * FileName：SysUserCtrl.java 
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
package com.wxmp.wxcms.ctrl;

import com.wxmp.core.common.BaseCtrl;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.core.util.MD5Util;
import com.wxmp.core.util.SessionUtil;
import com.wxmp.wxcms.domain.SysUser;
import com.wxmp.wxcms.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Controller
@RequestMapping("user")
public class SysUserCtrl extends BaseCtrl {

	@Autowired
	private SysUserService sysUserService;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AjaxResult login(SysUser user) {
		user.setPwd(MD5Util.getMD5Code(user.getPwd()));
		SysUser sysUser = this.sysUserService.getSysUser(user);
		if (sysUser == null) {
			return AjaxResult.failure("用户名或者密码错误");
		}
		SessionUtil.setUser(sysUser);
		return AjaxResult.success(sysUser.getTrueName());
	}

	@ResponseBody
	@RequestMapping(value = "/updatepwd", method = RequestMethod.POST)
	public AjaxResult updatepwd(SysUser user) {
		if (!SessionUtil.getUser().getPwd().equals(MD5Util.getMD5Code(user.getPwd()))) {
			return AjaxResult.failure("用户名或密码错误");
		}
		user.setNewpwd(MD5Util.getMD5Code(user.getNewpwd()));
		this.sysUserService.updateLoginPwd(user);
		//注销用户
		request.getSession().invalidate();
		return AjaxResult.success();
	}

	/**
	 * ： 用户退出
	 * @return
	 */
	@ResponseBody
	@RequestMapping("logout")
	public AjaxResult logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return AjaxResult.success();
	}
}
