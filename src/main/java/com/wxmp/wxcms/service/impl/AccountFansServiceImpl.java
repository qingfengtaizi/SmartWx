/*
 * FileName：AccountFansServiceImpl.java 
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
package com.wxmp.wxcms.service.impl;

import com.wxmp.wxcms.domain.AccountFans;
import com.wxmp.wxcms.mapper.AccountFansDao;
import com.wxmp.wxcms.service.AccountFansService;
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
public class AccountFansServiceImpl implements AccountFansService{

	@Resource
	private AccountFansDao entityDao;

	public AccountFans getById(String id){
		return entityDao.getById(id);
	}

	public AccountFans getByOpenId(String openId){
		return entityDao.getByOpenId(openId);
	}
	
	public List<AccountFans> list(AccountFans searchEntity){
		return entityDao.list(searchEntity);
	}

	public List<AccountFans> getFansListByPage(AccountFans searchEntity){
		return entityDao.getFansListByPage(searchEntity);
	}
	
	public AccountFans getLastOpenId(){
		return entityDao.getLastOpenId();
	}
	
	public void sync(AccountFans searchEntity){
		AccountFans lastFans = entityDao.getLastOpenId();
		String lastOpenId = "";
		if(lastFans != null){
			lastOpenId = lastFans.getOpenId();
		}
	}
	public void add(AccountFans entity){
		entityDao.add(entity);
	}

	public void update(AccountFans entity){
		entityDao.update(entity);
	}

	public void delete(AccountFans entity){
		entityDao.delete(entity);
	}

	public void deleteByOpenId(String openId){
		entityDao.deleteByOpenId(openId);
	}
	@Override
	public List<AccountFans> getFansByOpenIdListByPage(List<AccountFans> openIds) {
		return entityDao.getFansByOpenIdListByPage(openIds);
	}
}
