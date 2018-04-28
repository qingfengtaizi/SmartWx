/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service;

import com.wxmp.wxcms.domain.MsgText;
import java.util.List;
/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface MsgTextService {

	public MsgText getById(String id);

	public List<MsgText> getMsgTextByPage(MsgText searchEntity);

	public void add(MsgText entity);

	public void update(MsgText entity);

	public void delete(MsgText entity);
	
	//根据用户发送的文本消息，随机获取一条文本消息
	public MsgText getRandomMsg(String inputcode);
	public MsgText getRandomMsg2();
	
	public MsgText getByBaseId(String baseid);
}
