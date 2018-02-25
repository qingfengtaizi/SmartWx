package com.wxmp.wxcms.service;

import java.util.List;

import com.wxmp.core.page.Pagination;
import com.wxmp.wxcms.domain.AccountFans;


public interface AccountFansService {

	public AccountFans getById(String id);
	
	public AccountFans getByOpenId(String openId);

	public List<AccountFans> list(AccountFans searchEntity);

	public Pagination<AccountFans> paginationEntity(AccountFans searchEntity,Pagination<AccountFans> pagination);

	public AccountFans getLastOpenId();
	
	public void sync(AccountFans searchEntity);
	
	public void add(AccountFans entity);

	public void update(AccountFans entity);

	public void delete(AccountFans entity);

	public void deleteByOpenId(String openId);

    public List<AccountFans> getAccountFansList(AccountFans accountFans);
}