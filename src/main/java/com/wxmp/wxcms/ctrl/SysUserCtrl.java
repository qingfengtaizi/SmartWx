/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
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
