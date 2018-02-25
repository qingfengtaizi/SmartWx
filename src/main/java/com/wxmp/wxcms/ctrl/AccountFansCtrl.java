package com.wxmp.wxcms.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxmp.core.page.Pagination;
import com.wxmp.wxcms.domain.AccountFans;
import com.wxmp.wxcms.domain.MsgText;
import com.wxmp.wxcms.service.AccountFansService;

/**
 * 
 */

@Controller
@RequestMapping("/accountfans")
public class AccountFansCtrl{

	@Autowired
	private AccountFansService entityService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id){
		entityService.getById(id);
		return new ModelAndView();
	}

	@RequestMapping(value = "/list")
	public  ModelAndView list(AccountFans searchEntity){
		ModelAndView mv = new ModelAndView("wxcms/paginationEntity");
		return mv;
	}

	@RequestMapping(value = "/paginationEntity_old")
	public  ModelAndView paginationEntityOld(AccountFans searchEntity, Pagination<AccountFans> pagination){
		ModelAndView mv = new ModelAndView("wxcms/fansPagination");
		pagination = entityService.paginationEntity(searchEntity,pagination);
		mv.addObject("pagination",pagination);
		mv.addObject("cur_nav","fans");
		return mv;
	}
	
	
	@RequestMapping(value = "/paginationEntity")
	public  ModelAndView paginationEntity(AccountFans searchEntity, 
			@RequestParam(required=false,defaultValue="1") Integer page,
            @RequestParam(required=false,defaultValue="10") Integer pageSize){
		ModelAndView mv = new ModelAndView("wxcms/fansPagination");
		
		PageHelper.startPage(page, pageSize);
		List<AccountFans> fansList = this.entityService.getAccountFansList(searchEntity);
		PageInfo pagination=new PageInfo(fansList);
		
		mv.addObject("pagination",pagination);
		mv.addObject("page", pagination);
		mv.addObject("cur_nav","fans");
		return mv;
	}
	
	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(AccountFans entity){

		return new ModelAndView();
	}

	@RequestMapping(value = "/merge")
	public ModelAndView doMerge(AccountFans entity){
		entityService.add(entity);
		return new ModelAndView();
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(AccountFans entity){
		entityService.delete(entity);
		return new ModelAndView();
	}

	/**
	 * 点击群发弹出粉丝选择列表
	 * @param searchEntity
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/fansSelect")
	public  ModelAndView fansSelect(AccountFans searchEntity, Pagination<AccountFans> pagination){
		ModelAndView mv = new ModelAndView("wxcms/fansSelect");
		pagination = entityService.paginationEntity(searchEntity,pagination);
		mv.addObject("pagination",pagination);
		mv.addObject("cur_nav","fans");
		return mv;
	}

}