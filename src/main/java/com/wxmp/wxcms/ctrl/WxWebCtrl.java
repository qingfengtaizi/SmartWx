package com.wxmp.wxcms.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wxmp.core.page.Pagination;
import com.wxmp.core.util.HttpRequestDeviceUtils;
import com.wxmp.wxapi.process.WxMemoryCacheClient;
import com.wxmp.wxcms.domain.MsgNews;
import com.wxmp.wxcms.domain.MsgNewsVO;
import com.wxmp.wxcms.service.MsgNewsService;

/**
 * 手机微信页面
 */
@Controller
@RequestMapping("/wxweb")
public class WxWebCtrl {
	
	private static Logger log = LogManager.getLogger(WxWebCtrl.class);
	
	@Autowired
	private MsgNewsService msgNewsService;
	

	
	@RequestMapping(value = "/msg/newsread")
	public ModelAndView newsread(HttpServletRequest request,String id){
		ModelAndView mv = new ModelAndView("wxweb/mobilenewsread");
		MsgNews news = msgNewsService.getById(id);
		mv.addObject("news",news);
		
		if(!HttpRequestDeviceUtils.isMobileDevice(request)){
			mv.setViewName("wxweb/pcnewsread");
		}
		return mv;
	}
	
	@RequestMapping(value = "/msg/newsList")
	public ModelAndView pageWebNewsList(HttpServletRequest request,MsgNews searchEntity,Pagination<MsgNews> page){
		ModelAndView mv = new ModelAndView("wxweb/mobilenewslist");
		List<MsgNewsVO> pageList = msgNewsService.pageWebNewsList(searchEntity,page);
		mv.addObject("pageList", pageList);
		return mv;
	}
	
	@RequestMapping(value = "/jssdk")
	public ModelAndView jssdk(HttpServletRequest request,String api){
		ModelAndView mv = new ModelAndView("wxweb/jssdk");
		if(!StringUtils.isBlank(api)){
			mv.addObject("api", api);
		}
		return mv;
	}
	
	@RequestMapping(value = "/sendmsg.html")
	public ModelAndView sendmsg(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("wxweb/sendmsg");
		//拦截器已经处理了缓存,这里直接取
		String openid = WxMemoryCacheClient.getOpenid(request.getSession().getId());
		mv.addObject("openid", openid);
		return mv;
	}
	
	@RequestMapping(value = "/weui")
	public ModelAndView weui(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("wxweb/weui");
		//拦截器已经处理了缓存,这里直接取
		String openid = WxMemoryCacheClient.getOpenid(request.getSession().getId());
		mv.addObject("openid", openid);
		return mv;
	}
	
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,String id){
		ModelAndView mv = new ModelAndView("wxweb/index");
		return mv;
	}
	
}
