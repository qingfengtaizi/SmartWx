package com.wxmp.wxcms.mapper;

import java.util.List;

import com.wxmp.core.page.Pagination;
import com.wxmp.wxcms.domain.AccountMenuGroup;


public interface AccountMenuGroupDao {

	public AccountMenuGroup getById(String id);

	public List<AccountMenuGroup> list(AccountMenuGroup searchEntity);

	public Integer getTotalItemsCount(AccountMenuGroup searchEntity);

	public List<AccountMenuGroup> paginationEntity(AccountMenuGroup searchEntity , Pagination<AccountMenuGroup> pagination);

	public void add(AccountMenuGroup entity);

	public void update(AccountMenuGroup entity);
	
	public void updateMenuGroupDisable();
	
	public void updateMenuGroupEnable(String gid);
	
	public void deleteAllMenu(AccountMenuGroup entity);
	
	public void delete(AccountMenuGroup entity);

	/**
	 * 删除菜单组
	 * @param entity
	 */
	public void deleteGroupById(long id);
	/**
	 * 删除菜单组下的菜单
	 * @param id
	 */
	public void deleteMenuByGId(long id);
}

