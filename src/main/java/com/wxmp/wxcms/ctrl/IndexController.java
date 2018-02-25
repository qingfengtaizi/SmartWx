package com.wxmp.wxcms.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping()
public class IndexController {
	@RequestMapping(value = "/index")
	public ModelAndView index(){
		return new ModelAndView("index");
//		return new ModelAndView("redirect:/wxcms/urltoken.html");
	}
}
