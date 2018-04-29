/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service;

import com.wxmp.wxcms.domain.TplMsgText;

import java.util.List;
/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface TplMsgTextService {

	public TplMsgText getById(String id);
	
	public List<TplMsgText> getTplMsgTextByPage(TplMsgText searchEntity);

	public void add(TplMsgText entity);

	public void update(TplMsgText entity);

	public void delete(TplMsgText entity);
	
	//根据用户发送的文本消息，随机获取一条文本消息
	public TplMsgText getByBaseId(String baseid);
}
