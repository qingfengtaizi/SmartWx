/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service;

import java.util.List;

import com.wxmp.wxapi.process.MpAccount;
import com.wxmp.wxcms.domain.UserTag;

/**
 *
 * @author fuzi Kong
 * @version 2.0
 * @date 2018-05-30 10:54:58
 */
public interface UserTagService {

	public UserTag getById(Integer id);

	public List<UserTag> listForPage(UserTag searchEntity);

	public void add(UserTag entity);

	public void update(UserTag entity);

	public void delete(UserTag entity);
	//同步服务器的用户标签
	public boolean syncUserTagList(MpAccount mpAccount);
	
	public Integer deleteBatchIds(String [] ids);
	//获取数据库中用户标签的最大值，判断是否同步
	public Integer getMaxId();
}