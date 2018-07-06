/*
 * FileName：AccountMenuCtrl.java 
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

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxmp.core.common.BaseCtrl;
import com.wxmp.core.common.Constants;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.core.util.wx.WxUtil;
import com.wxmp.wxapi.vo.Matchrule;
import com.wxmp.wxcms.domain.AccountMenu;
import com.wxmp.wxcms.service.AccountMenuService;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Controller
@RequestMapping("/accountmenu")
public class AccountMenuCtrl extends BaseCtrl {
	@Autowired
	private AccountMenuService entityService;

	@RequestMapping(value = "/list")
	@ResponseBody
	public AjaxResult list(AccountMenu accountMenu) {
		List<AccountMenu> menus = entityService.listWxMenus(accountMenu);
		Matchrule matchrule = new Matchrule();
		return AjaxResult.success(WxUtil.prepareMenus(menus, matchrule));
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public AjaxResult save(String menus) {
		JSONArray jsons = JSONArray.parseArray(menus);
		//每次先行删除公众号所有菜单
		entityService.delete(new AccountMenu());
		if (CollectionUtils.isNotEmpty(jsons)) {
			for (int i = 0; i < jsons.size(); i++) {
				JSONObject json = jsons.getJSONObject(i);
				if (null != json) {
					AccountMenu accountMenu = new AccountMenu();
//					String pid = CommonUtil.getUID();
//					accountMenu.setId(pid);
					accountMenu.setName(json.getString("name"));
					accountMenu.setSort(i + 1);
					accountMenu.setParentId((long) 0);
					if (json.containsKey("type")) {
						accountMenu.setMtype(json.getString("type"));
						//判断是否设置key
						if (Constants.MENU_NEED_KEY.contains(json.getString("type"))) {
							accountMenu.setEventType("fix");
                            accountMenu.setMsgType(json.getString("msgType"));
                            accountMenu.setMsgId(json.getString("msgId"));
						}
					}
					if (json.containsKey("url")) {
						accountMenu.setUrl(json.getString("url"));
					}
					if (json.containsKey("media_id")) {
						accountMenu.setMsgId(json.getString("media_id"));
					}
					accountMenu.setCreateTime(new Date());
					//保存
					entityService.add(accountMenu);
					//判断是否有subbutton
					if (json.containsKey("sub_button")) {
						JSONArray buttons = json.getJSONArray("sub_button");
						if (CollectionUtils.isNotEmpty(buttons)) {
							long pid = accountMenu.getId();
							for (int j = 0; j < buttons.size(); j++) {
								json = buttons.getJSONObject(j);
								accountMenu = new AccountMenu();
								accountMenu.setParentId(pid);
								accountMenu.setName(json.getString("name"));
								accountMenu.setSort(j + 1);
								if (json.containsKey("type")) {
									accountMenu.setMtype(json.getString("type"));
									//判断是否设置key
									if (Constants.MENU_NEED_KEY.contains(json.getString("type"))) {
										accountMenu.setEventType("fix");
                                        accountMenu.setMsgType(json.getString("msgType"));
                                        accountMenu.setMsgId(json.getString("msgId"));
									}
								}
								if (json.containsKey("url")) {
									accountMenu.setUrl(json.getString("url"));
								}
								if (json.containsKey("media_id")) {
									accountMenu.setMsgId(json.getString("media_id"));
								}
								accountMenu.setCreateTime(new Date());
								entityService.add(accountMenu);
							}
						}
					}
				}
			}
		}
		return AjaxResult.success();
	}
}
