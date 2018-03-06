package com.wxmp.wxcms.service.impl;

import com.wxmp.wxcms.domain.Account;
import com.wxmp.wxcms.mapper.AccountDao;
import com.wxmp.wxcms.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : hermit
 */

@Service
public class AccountServiceImpl implements AccountService{

	@Resource
	private AccountDao entityDao;

	public Account getById(String id){
		return entityDao.getById(id);
	}
	
	public Account getByAccount(String account){
		return entityDao.getByAccount(account);
	}

	public List<Account> listForPage(Account searchEntity){
		return entityDao.listForPage(searchEntity);
	}

	public void add(Account entity){
		entityDao.add(entity);
	}

	public void update(Account entity){
		entityDao.update(entity);
	}

	public void delete(Account entity){
		entityDao.delete(entity);
	}

}