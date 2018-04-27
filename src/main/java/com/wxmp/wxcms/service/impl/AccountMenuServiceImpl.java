/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wxmp.wxcms.domain.AccountMenu;
import com.wxmp.wxcms.mapper.AccountMenuDao;
import com.wxmp.wxcms.service.AccountMenuService;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Service
public class AccountMenuServiceImpl implements AccountMenuService{

	@Resource
	private AccountMenuDao entityDao;

	public AccountMenu getById(String id) {
		return entityDao.getById(id);
	}

	public List<AccountMenu> listWxMenus(AccountMenu entity) {
		return entityDao.listWxMenus(entity);
	}

	public List<AccountMenu> listParentMenu(AccountMenu entity) {
		return entityDao.listParentMenu(entity);
	}

	public void add(AccountMenu entity) {
		entityDao.add(entity);
	}

	public void update(AccountMenu entity) {
		entityDao.update(entity);
	}

	public void delete(AccountMenu entity) {
		entityDao.delete(entity);
	}



}