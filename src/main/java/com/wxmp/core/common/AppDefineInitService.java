/*
 * FileName：AppDefineInitService.java 
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.wxmp.core.common;

import com.wxmp.core.spring.SpringBeanDefineService;
import com.wxmp.wxapi.process.WxMemoryCacheClient;
import com.wxmp.wxcms.domain.Account;
import com.wxmp.wxcms.mapper.AccountDao;

import javax.annotation.Resource;

/**
 * 系统启动时自动加载，把公众号信息加入到缓存中
 */
public class AppDefineInitService implements SpringBeanDefineService {

	@Resource
	private AccountDao accountDao;
	
	public void initApplicationCacheData() {
		Account account = accountDao.getSingleAccount();
		WxMemoryCacheClient.addMpAccount(account);
	}
	
}
