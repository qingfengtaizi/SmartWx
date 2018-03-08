package com.wxmp.backstage.sys.ctrl;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wxmp.backstage.sys.service.ISysUserService;
import com.wxmp.core.annotation.Auth;


/**
 * 
 * @title : 
 * @description : 不返回json格式数据 | 只用于页面跳转分发 
 * @projectname : wxmp
 * @classname : ViewAction
 * @version 1.0
 * @author : hermit
 * @createtime : 2017年4月2日 下午12:00:39
 */
@Controller
@RequestMapping("view")
public class ViewCtrl {
	private static Logger log = LogManager.getLogger(ViewCtrl.class);
	
	@Autowired
	private ISysUserService sysUserService;
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@Auth(verifyLogin=false)
    @RequestMapping(value = "/login")
	public String login(Integer page, String city, Model model) {
		return "login";
	}

	/**
	 * 跳转到主页面
	 * @return
	 */
    @RequestMapping(value = "/main")
	public String main(HttpSession session) {
    	
    	log.debug("跳入主页面.................");
		return "main";
	}

}
