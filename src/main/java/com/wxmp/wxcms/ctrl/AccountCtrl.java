package com.wxmp.wxcms.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wxmp.wxcms.domain.Account;
import com.wxmp.wxcms.service.AccountService;

/**
 * 
 */

@Controller
@RequestMapping("/account")
public class AccountCtrl{

	@Autowired
	private AccountService entityService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id){
		entityService.getById(id);
		return new ModelAndView();
	}

	@RequestMapping(value = "/listForPage")
	public  ModelAndView listForPage(@ModelAttribute Account searchEntity){
		ModelAndView mv = new ModelAndView();
		List<Account> list = entityService.listForPage(searchEntity);
		mv.addObject("list",list);
		return mv;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(Account entity){

		return new ModelAndView();
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(Account entity){
		entityService.add(entity);
		return new ModelAndView();
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(Account entity){
		entityService.delete(entity);
		return new ModelAndView();
	}



}