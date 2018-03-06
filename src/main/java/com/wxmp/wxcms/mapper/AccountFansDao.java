package com.wxmp.wxcms.mapper;

import java.util.List;
import com.wxmp.core.page.Pagination;
import com.wxmp.wxcms.domain.AccountFans;

/**
 * @author : hermit
 */
public interface AccountFansDao {

	public AccountFans getById(String id);

	public AccountFans getByOpenId(String openId);
	
	public List<AccountFans> list(AccountFans searchEntity);

	public Integer getTotalItemsCount(AccountFans searchEntity);
	
	public List<AccountFans> paginationEntity(AccountFans searchEntity ,Pagination<AccountFans> pagination);

	public AccountFans getLastOpenId();
	
	public void add(AccountFans entity);
	
	public void addList(List<AccountFans> list);

	public void update(AccountFans entity);

	public void delete(AccountFans entity);

	public void deleteByOpenId(String openId);

	/**
	 * 分页查询粉丝列表
	 * @param searchEntity
	 * @return
	 */
	public List<AccountFans> getAccountFansList(AccountFans searchEntity);
}