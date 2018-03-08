package com.wxmp.wxcms.ctrl;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wxmp.core.page.Pagination;
import com.wxmp.wxcms.domain.AccountMenu;
import com.wxmp.wxcms.domain.AccountMenuGroup;
import com.wxmp.wxcms.service.AccountMenuGroupService;
import com.wxmp.wxcms.service.AccountMenuService;

/**
 * @author : hermit
 */

@Controller
@RequestMapping("/wxcms/accountMenuGroup")
public class AccountMenuGroupCtrl extends BaseCtrl{

	@Autowired
	private AccountMenuGroupService entityService;
	
	@Autowired
	private AccountMenuService menuService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id){
		ModelAndView mv = new ModelAndView("wxcms/accountMenuGroup");
		mv.addObject("entity",entityService.getById(id));
		return mv;
	}

	@RequestMapping(value = "/list")
	public  ModelAndView list(AccountMenuGroup searchEntity){
		ModelAndView mv = new ModelAndView("wxcms/accountMenuGroupList");
		List<AccountMenuGroup> list = entityService.list(searchEntity);
		mv.addObject("list",list);
		return mv;
	}

	@RequestMapping(value = "/paginationEntity")
	public  ModelAndView paginationEntity(AccountMenuGroup searchEntity , Pagination<AccountMenuGroup> pagination){
		ModelAndView mv = new ModelAndView("wxcms/accountMenuGroupPagination");
		pagination = entityService.paginationEntity(searchEntity,pagination);
		mv.addObject("pagination",pagination);
		mv.addObject("searchEntity",searchEntity);
		mv.addObject("cur_nav", "menu");
		return mv;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(AccountMenuGroup entity){
		ModelAndView modelAndView = new ModelAndView("wxcms/menuList");
		if(entity.getId() != null){
			entity = entityService.getById(entity.getId().toString());
		}
		modelAndView.addObject("groupEntity",entity);
		List<AccountMenu> list = menuService.listForPage(new AccountMenu());
		modelAndView.addObject("pageList", list);
		modelAndView.addObject("cur_nav", "menu");
		return modelAndView;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(AccountMenuGroup entity){
		if(entity.getId() == null){
			entity.setEnable(0);
			entityService.add(entity);
		}else{
			entityService.update(entity);
		}
		return new ModelAndView("redirect:/accountmenu/list.html?gid=" + entity.getId());
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(AccountMenuGroup entity){
		entityService.delete(entity);
		return new ModelAndView("redirect:/wxcms/accountMenuGroup/paginationEntity");
	}


    /**
     * 保存菜单组名称
     * @param entity
     * @return
     */
	@RequestMapping(value = "/doSaveMenu")
	@ResponseBody
	public String doSaveMenu(AccountMenuGroup entity){
		String code = "";
		if(entity.getId() == null){
			entity.setEnable(0);
			entityService.add(entity);
			code = "1";
		}else{
			entityService.update(entity);
			code = "2";
		}
		return code;
	}
	
	
	@RequestMapping(value = "/deleteMenu")
	@ResponseBody
	public String deleteMenu(String id){
		String code = "0";
	    try {
	    	entityService.deleteById(Long.valueOf(id));
	    	code = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
}

