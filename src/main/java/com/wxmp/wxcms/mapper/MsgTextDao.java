/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.mapper;

import java.util.List;
import com.wxmp.wxcms.domain.MsgText;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface MsgTextDao {

	public MsgText getById(String id);

	public List<MsgText> getMsgTextByPage(MsgText searchEntity);

	public List<MsgText> getMsgTextList(MsgText searchEntity);

	public void add(MsgText entity);

	public void update(MsgText entity);

	public void delete(MsgText entity);

	public MsgText getRandomMsg(String inputCode);
	
	public MsgText getRandomMsg2();

	public MsgText getByBaseId(String baseid);
}