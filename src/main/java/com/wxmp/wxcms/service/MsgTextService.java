package com.wxmp.wxcms.service;

import com.wxmp.wxcms.domain.MsgText;
import java.util.List;
/**
 * @author : hermit
 */
public interface MsgTextService {

	public MsgText getById(String id);

	public List<MsgText> listForPage(MsgText searchEntity);

	public void add(MsgText entity);

	public void update(MsgText entity);

	public void delete(MsgText entity);
	
	//根据用户发送的文本消息，随机获取一条文本消息
	public MsgText getRandomMsg(String inputcode);
	public MsgText getRandomMsg2();
	
	public MsgText getByBaseId(String baseid);
}
