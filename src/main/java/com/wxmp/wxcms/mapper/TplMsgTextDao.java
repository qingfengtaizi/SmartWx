/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.mapper;

import java.util.List;
import com.wxmp.wxcms.domain.MsgText;
import com.wxmp.wxcms.domain.TplMsgText;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface TplMsgTextDao {

	public TplMsgText getById(String id);

	public List<TplMsgText> getTplMsgTextByPage(TplMsgText searchEntity);

	public List<TplMsgText> getTplMsgTextList(TplMsgText searchEntity);

	public void add(TplMsgText entity);

	public void update(TplMsgText entity);

	public void delete(TplMsgText entity);

	public TplMsgText getByBaseId(String baseid);
}