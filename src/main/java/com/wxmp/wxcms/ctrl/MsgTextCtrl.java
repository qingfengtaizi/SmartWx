package com.wxmp.wxcms.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxmp.wxcms.domain.MsgBase;
import com.wxmp.wxcms.domain.MsgText;
import com.wxmp.wxcms.service.MsgBaseService;
import com.wxmp.wxcms.service.MsgTextService;

/**
 * 
 */

@Controller
@RequestMapping("/msgtext")
public class MsgTextCtrl{

	@Autowired
	private MsgTextService entityService;
	
	@Autowired
	private MsgBaseService baseService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id){
		entityService.getById(id);
		return new ModelAndView();
	}

	@RequestMapping(value = "/list")
	public  ModelAndView list(@ModelAttribute MsgText searchEntity,
			@RequestParam(required=false,defaultValue="1") Integer page,
            @RequestParam(required=false,defaultValue="3") Integer pageSize){
		ModelAndView modelAndView = new ModelAndView("wxcms/msgtextList");
		
		PageHelper.startPage(page, pageSize);
		List<MsgText> pageList = entityService.listForPage(searchEntity);
		PageInfo p=new PageInfo(pageList);
		
		modelAndView.addObject("pageList", pageList);
		
		modelAndView.addObject("page", p);
		modelAndView.addObject("cur_nav", "text");
		return modelAndView;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(MsgText entity){
		ModelAndView mv = new ModelAndView("wxcms/msgtextMerge");
		mv.addObject("cur_nav", "text");
		if(entity.getId() != null){
			MsgText text = entityService.getById(entity.getId().toString());
			mv.addObject("entity",text);
			mv.addObject("baseEntity", baseService.getById(text.getBaseId().toString()));
		}else{
			mv.addObject("entity",new MsgText());
			mv.addObject("baseEntity", new MsgBase());
		}
		return mv;
	}
	
	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(MsgText entity){
		if(entity.getId() != null){
			entityService.update(entity);
		}else{
			entityService.add(entity);
		}
		return new ModelAndView("redirect:/msgtext/list");
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(MsgText entity){
		entityService.delete(entity);
		return new ModelAndView("redirect:/msgtext/list");
	}


	@RequestMapping(value = "/updateText")
	@ResponseBody
	public String updateText(MsgText entity){
		String code = "";
		if(entity.getId() != null){
			entityService.update(entity);
			//更新成功
			code = "2";
		}else{
			//添加成功
			entityService.add(entity);
			code = "1";
		}
		return code;
	}
	
	@RequestMapping(value = "/deleteById")
	@ResponseBody
	public String deleteById(MsgText entity){
		String code = "";
		
		try {
			entityService.delete(entity);
			code = "1";
		} catch (Exception e) {
			code = "-1";
			e.printStackTrace();
		}
		return code;
	}

}

