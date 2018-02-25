package com.wxmp.backstage.sys.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wxmp.backstage.common.DataModel;
import com.wxmp.backstage.sys.ISysUserService;
import com.wxmp.backstage.sys.domain.SysUser;
import com.wxmp.backstage.util.HashUtil;
import com.wxmp.backstage.util.ValidateUtil;
import com.wxmp.core.util.SessionUtilsWeb;

import org.apache.log4j.LogManager;  
import org.apache.log4j.Logger;

/**
 * 
 * @title : 
 * @description : 
 * @projectname : wxmp
 * @classname : LoginAction
 * @version 1.0
 * @author : hermit
 * @createtime : 2017年4月2日 下午12:00:39
 */
@Controller
@RequestMapping("login")
public class LoginAction {
	
	private static Logger log = LogManager.getLogger(LoginAction.class);


	
	@Autowired
	private ISysUserService sysUserService;

	
	/**
	 * 登录验证
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
	public DataModel validate(String account, String pwd, HttpSession session) {
		DataModel data = new DataModel();
	   if(ValidateUtil.isNull(account)) {
			data.setMsg("请输入帐号！");	
			data.setCode(1);
			return data;
		}
		if(ValidateUtil.isNull(pwd)) {
			data.setMsg("请输入密码！");
			data.setCode(2);
			return data;
		}

		
		//test
		session.setAttribute("sysUser", "adminPO");
		
			
		data.setCode(0);

		return data;
	}
	
	
	/**
	 * 登录验证
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/checkLogin2", method = RequestMethod.POST)
	public String checkLogin2(SysUser paramUser,HttpSession session) {
		String code = "0";
		SysUser sysUser = null;
		sysUser = this.sysUserService.getSysUser(paramUser);
		if(sysUser == null){
			//用户名或者密码错误
			code = "-1";
		}
		return code;
	}
	
	@ResponseBody
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
	public Map<String, Object> checkLogin(HttpServletRequest request,SysUser paramUser){
		Map<String, Object> resMap = new HashMap<String, Object>();
		String code = "0";
		SysUser sysUser = null;
		sysUser = this.sysUserService.getSysUser(paramUser);
		if(sysUser == null){
			//用户名或者密码错误
			code = "-1";
		}else{
			resMap.put("userId", sysUser.getId());
			SessionUtilsWeb.setUser(request, sysUser);
		}
		resMap.put("code", code);
		return resMap;
	}


	/**
	 * 修改登录密码
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/updateLoginPwd", method = RequestMethod.POST)
	public String updateLoginPwd(SysUser paramUser,HttpSession session) {
		String code = "0";
		int m = 0;
		m = this.sysUserService.updateLoginPwd(paramUser);
		if(m > 0){
			//用户名或者密码错误
			code = "1";
		}
		return code;
	}

	/**
	 * ： 用户退出
	 * 
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
}
