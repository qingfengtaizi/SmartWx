/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service;

import java.util.List;

import com.wxmp.wxcms.domain.AccountMenu;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface AccountMenuService {

	public AccountMenu getById(String id);

	public List<AccountMenu> listWxMenus(AccountMenu entityDao);

	public List<AccountMenu> listParentMenu(AccountMenu entityDao);
	
	public void add(AccountMenu entity);

	public void update(AccountMenu entity);

	public void delete(AccountMenu entity);



}