package com.wxmp.wxcms.service;

import com.wxmp.core.page.Pagination;
import com.wxmp.wxcms.domain.AccountMenuGroup;

import java.util.List;

/**
 * @author : hermit
 */
public interface AccountMenuGroupService {

	public AccountMenuGroup getById(String id);

	public List<AccountMenuGroup> list(AccountMenuGroup searchEntity);

	public Pagination<AccountMenuGroup> paginationEntity(AccountMenuGroup searchEntity ,Pagination<AccountMenuGroup> pagination);

	public void add(AccountMenuGroup entity);

	public void update(AccountMenuGroup entity);

	public void delete(AccountMenuGroup entity);

	/**
	 * 根据id删除
	 * @param id
	 */
	public void deleteById(long id);
    
}

