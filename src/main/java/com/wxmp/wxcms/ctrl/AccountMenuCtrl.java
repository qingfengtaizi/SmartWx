package com.wxmp.wxcms.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxmp.wxcms.domain.AccountMenu;
import com.wxmp.wxcms.domain.AccountMenuGroup;
import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.service.AccountMenuGroupService;
import com.wxmp.wxcms.service.AccountMenuService;

/**
 * 
 */

@Controller
@RequestMapping("/accountmenu")
public class AccountMenuCtrl{

	@Autowired
	private AccountMenuService entityService;
	
	@Autowired
	private AccountMenuGroupService groupService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id){
		entityService.getById(id);
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/listold")
	public  ModelAndView listold(@ModelAttribute AccountMenu searchEntity){
		ModelAndView modelAndView = new ModelAndView("wxcms/menuList");
		List<AccountMenu> list = entityService.listForPage(searchEntity);
		AccountMenuGroup groupEntity = groupService.getById(searchEntity.getGid().toString());
		modelAndView.addObject("groupEntity", groupEntity);
		modelAndView.addObject("pageList", list);
		
		
		modelAndView.addObject("cur_nav", "menu");
		return modelAndView;
	}

	@RequestMapping(value = "/list")
	public  ModelAndView list(@ModelAttribute AccountMenu searchEntity,
			@RequestParam(required=false,defaultValue="1") Integer page,
            @RequestParam(required=false,defaultValue="3") Integer pageSize){
		
		ModelAndView modelAndView = new ModelAndView("wxcms/menuList");
		PageHelper.startPage(page, pageSize);
		List<AccountMenu> menuList = entityService.listForPage(searchEntity);
		PageInfo menuPage=new PageInfo(menuList);
		AccountMenuGroup groupEntity = groupService.getById(searchEntity.getGid().toString());
		modelAndView.addObject("groupEntity", groupEntity);
		modelAndView.addObject("gid", searchEntity.getGid());
		
		//分页对象
		modelAndView.addObject("page",menuPage);
		modelAndView.addObject("cur_nav", "menu");
		return modelAndView;
	}
	
	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(AccountMenu entity,Long gid){
		ModelAndView modelAndView = new ModelAndView("wxcms/menuMerge");
		if(gid != null){
			List<AccountMenu> list = entityService.listParentMenu();
			modelAndView.addObject("parentMenu", list);
			modelAndView.addObject("cur_nav", "menu");
			modelAndView.addObject("gid", gid);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(AccountMenu entity,String gid){
		if(gid != null){
			entity.setGid(Long.parseLong(gid));
			entityService.add(entity);
		}
		return new ModelAndView("redirect:/accountmenu/list?gid="+gid);
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(AccountMenu entity,String gid){
		ModelAndView modelAndView = new ModelAndView("redirect:/accountmenu/list?gid="+gid);
		entityService.delete(entity);
		return modelAndView;
	}

}
