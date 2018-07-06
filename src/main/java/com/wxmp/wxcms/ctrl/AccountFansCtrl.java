/*
 * FileName：AccountFansCtrl.java 
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

import com.wxmp.core.common.BaseCtrl;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.wxcms.domain.AccountFans;
import com.wxmp.wxcms.service.AccountFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Controller
@RequestMapping("/accountfans")
public class AccountFansCtrl extends BaseCtrl {

	@Autowired
	private AccountFansService entityService;

	@RequestMapping(value = "/info")
	@ResponseBody
	public AjaxResult getById(String id){
		AccountFans fans = entityService.getById(id);
		return AjaxResult.success(fans);
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public AjaxResult list(AccountFans searchEntity){
		List<AccountFans> fansList = this.entityService.getFansListByPage(searchEntity);
		return getResult(searchEntity,fansList);
	}
	
}
