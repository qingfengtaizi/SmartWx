package com.wxmp.wxcms.mapper;

import java.util.List;
import com.wxmp.wxcms.domain.MsgBase;
import com.wxmp.wxcms.domain.MsgNews;
import com.wxmp.wxcms.domain.MsgText;

/**
 * @author : hermit
 */

public interface MsgBaseDao {

	public MsgBase getById(String id);

	public List<MsgBase> listForPage(MsgBase searchEntity);

	public List<MsgNews> listMsgNewsByBaseId(String[] ids);
	
	public MsgText getMsgTextByBaseId(String id);
	
	public MsgText getMsgTextBySubscribe();
	
	public MsgText getMsgTextByInputCode(String inputcode);
	
	public void add(MsgBase entity);

	public void update(MsgBase entity);
	
	public void updateInputcode(MsgBase entity);

	public void delete(MsgBase entity);

}