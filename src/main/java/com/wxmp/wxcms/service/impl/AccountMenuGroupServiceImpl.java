/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service.impl;

import com.wxmp.wxcms.domain.AccountMenu;
import com.wxmp.wxcms.domain.AccountMenuGroup;
import com.wxmp.wxcms.mapper.AccountMenuDao;
import com.wxmp.wxcms.mapper.AccountMenuGroupDao;
import com.wxmp.wxcms.service.AccountMenuGroupService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Service
public class AccountMenuGroupServiceImpl implements AccountMenuGroupService{

	@Resource
	private AccountMenuGroupDao entityDao;

	@Resource
	private AccountMenuDao menuDao;

	public AccountMenuGroup getById(String id){
		return entityDao.getById(id);
	}

	public List<AccountMenuGroup> list(AccountMenuGroup searchEntity){
		return entityDao.list(searchEntity);
	}

	public List<AccountMenuGroup> getGroupListByPage(AccountMenuGroup searchEntity){
		return entityDao.getGroupListByPage(searchEntity);
	}

	public void add(AccountMenuGroup entity){
		entityDao.add(entity);
	}

	public void update(AccountMenuGroup entity){
		entityDao.update(entity);
	}

	public void delete(AccountMenuGroup entity){
		entityDao.deleteAllMenu(entity);
		entityDao.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.wxmp.wxcms.service.AccountMenuGroupService#deleteById(long)
	 */
	@Override
	public void deleteById(long id) {
		AccountMenu searchEntity = new AccountMenu();
		searchEntity.setGid(id);
		List<AccountMenu> menuList = menuDao.listForPage(searchEntity);
		if(menuList != null && menuList.size() > 0){
				//删除菜单组
				entityDao.deleteGroupById(id);
				//删除菜单
				entityDao.deleteMenuByGId(id);
		}else{
			entityDao.deleteGroupById(id);
		}
	}
}

