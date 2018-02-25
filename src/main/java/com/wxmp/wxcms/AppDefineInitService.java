package com.wxmp.wxcms;

import org.springframework.beans.factory.annotation.Autowired;

import com.wxmp.core.spring.SpringBeanDefineService;
import com.wxmp.wxapi.process.WxMemoryCacheClient;
import com.wxmp.wxcms.domain.Account;
import com.wxmp.wxcms.mapper.AccountDao;

/**
 * 系统启动时自动加载，把公众号信息加入到缓存中
 */
public class AppDefineInitService implements SpringBeanDefineService {

	@Autowired
	private AccountDao accountDao;
	
	public void initApplicationCacheData() {
		Account account = accountDao.getSingleAccount();
		WxMemoryCacheClient.addMpAccount(account);
	}
	
}
