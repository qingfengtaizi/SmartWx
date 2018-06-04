/*
 * FileName：AccountCtrl.java 
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
package com.wxmp.wxcms.ctrl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wxmp.core.common.BaseCtrl;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.core.util.wx.WxUtil;
import com.wxmp.wxapi.process.WxMemoryCacheClient;
import com.wxmp.wxcms.domain.Account;
import com.wxmp.wxcms.service.AccountService;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Controller
@RequestMapping("/account")
public class AccountCtrl extends BaseCtrl {

	@Autowired
	private AccountService entityService;

	@RequestMapping(value = "/getById")
	@ResponseBody
	public AjaxResult getById(long id){
		entityService.getById(id);
		return AjaxResult.success();
	}

	@RequestMapping(value = "/listForPage")
	@ResponseBody
	public AjaxResult listForPage(Account searchEntity) {
		Account account;
		List<Account> list = entityService.listForPage(searchEntity);
		if (CollectionUtils.isEmpty(list)) {
			return AjaxResult.success();
		}
		//account存入缓存中
		if (null != searchEntity && null != searchEntity.getId()) {
			account = entityService.getById(searchEntity.getId());
			WxMemoryCacheClient.setAccount(account.getAccount());
		} else {
			account = entityService.getByAccount(WxMemoryCacheClient.getAccount());
		}
		return AjaxResult.success(WxUtil.getAccount(list, account.getName()));
	}
}
