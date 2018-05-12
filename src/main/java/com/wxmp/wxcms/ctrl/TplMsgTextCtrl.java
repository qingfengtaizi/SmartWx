/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wxmp.core.common.BaseCtrl;
import com.wxmp.core.exception.BusinessException;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.wxcms.domain.TplMsgText;
import com.wxmp.wxcms.service.TplMsgTextService;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Controller
@RequestMapping("/tplmsgtext")
public class TplMsgTextCtrl extends BaseCtrl {

	@Autowired
	private TplMsgTextService entityService;
	

	@ResponseBody
	@RequestMapping(value = "/getById")
	public AjaxResult getById(String id){
		TplMsgText text = entityService.getById(id);
		return AjaxResult.success(text);
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public  AjaxResult list(TplMsgText searchEntity) throws BusinessException{
		List<TplMsgText> pageList = entityService.getTplMsgTextByPage(searchEntity);
		return getResult(searchEntity,pageList);
	}


	/**
	 * 修改/添加
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/updateText")
	@ResponseBody
	public AjaxResult updateText(TplMsgText entity){
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
	public AjaxResult deleteById(TplMsgText entity) {
		entityService.delete(entity);
		return AjaxResult.deleteSuccess();
	}

}

