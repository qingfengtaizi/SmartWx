package com.wxmp.wxcms.mapper;

import java.util.List;

import com.wxmp.wxcms.domain.Account;


public interface AccountDao {

	public Account getById(String id);
	
	public Account getByAccount(String account);
	
	public Account getSingleAccount();

	public List<Account> listForPage(Account searchEntity);

	public void add(Account entity);

	public void update(Account entity);

	public void delete(Account entity);



}