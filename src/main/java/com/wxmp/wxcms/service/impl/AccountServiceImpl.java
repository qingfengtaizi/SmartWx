/*
 * FileName：AccountServiceImpl.java 
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

import com.wxmp.wxcms.domain.Account;
import com.wxmp.wxcms.mapper.AccountDao;
import com.wxmp.wxcms.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    
    @Resource
    private AccountDao entityDao;
    
    public Account getById(Long id) {
        return entityDao.getById(id);
    }
    
    public Account getByAccount(String account) {
        return entityDao.getByAccount(account);
    }
    
    public List<Account> listForPage(Account searchEntity) {
        return entityDao.listForPage(searchEntity);
    }
    
    public void add(Account entity) {
        entityDao.add(entity);
    }
    
    public void update(Account entity) {
        entityDao.update(entity);
    }
    
    public void delete(Account entity) {
        entityDao.delete(entity);
    }
    
    public Account getSingleAccount() {
        return entityDao.getSingleAccount();
    }
    
}
