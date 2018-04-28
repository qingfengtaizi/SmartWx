/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.ctrl;

import java.util.List;

import com.wxmp.core.common.BaseCtrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wxmp.core.exception.BusinessException;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.wxcms.domain.MsgBase;
import com.wxmp.wxcms.domain.MsgText;
import com.wxmp.wxcms.service.MsgBaseService;
import com.wxmp.wxcms.service.MsgTextService;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Controller
@RequestMapping("/msgtext")
public class MsgTextCtrl extends BaseCtrl {

	@Autowired
	private MsgTextService entityService;
	
	@Autowired
	private MsgBaseService baseService;

	@ResponseBody
	@RequestMapping(value = "/getById")
	public AjaxResult getById(String id){
		MsgText text = entityService.getById(id);
		return AjaxResult.success(text);
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public  AjaxResult list(MsgText searchEntity) throws BusinessException{
		List<MsgText> pageList = entityService.getMsgTextByPage(searchEntity);
		return getResult(searchEntity,pageList);
	}


	/**
	 * 修改/添加
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/updateText")
	@ResponseBody
	public AjaxResult updateText(MsgText entity){
		if (entity.getId() != null) {
			entityService.update(entity);
			//更新成功
			return AjaxResult.updateSuccess();
		} else {
			//添加成功
			entityService.add(entity);
			return AjaxResult.saveSuccess();
		}
	}

	@RequestMapping(value = "/deleteById")
	@ResponseBody
	public AjaxResult deleteById(MsgText entity) {
		entityService.delete(entity);
		return AjaxResult.deleteSuccess();
	}

}

