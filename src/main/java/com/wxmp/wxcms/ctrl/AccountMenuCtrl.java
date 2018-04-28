/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.ctrl;

import com.wxmp.core.common.BaseCtrl;
import com.wxmp.core.common.Constants;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.core.util.wx.WxUtil;
import com.wxmp.wxapi.vo.Matchrule;
import com.wxmp.wxcms.domain.AccountMenu;
import com.wxmp.wxcms.service.AccountMenuService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

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
		JSONArray jsons = JSONArray.fromObject(menus);
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
					if (json.has("type")) {
						accountMenu.setMtype(json.getString("type"));
						//判断是否设置key
						if (Constants.MENU_NEED_KEY.contains(json.getString("type"))) {
							accountMenu.setEventType("fix");
                            accountMenu.setMsgType(json.getString("msgType"));
                            accountMenu.setMsgId(json.getString("msgId"));
						}
					}
					if (json.has("url")) {
						accountMenu.setUrl(json.getString("url"));
					}
					if (json.has("media_id")) {
						accountMenu.setMsgId(json.getString("media_id"));
					}
					accountMenu.setCreateTime(new Date());
					//保存
					entityService.add(accountMenu);
					//判断是否有subbutton
					if (json.has("sub_button")) {
						JSONArray buttons = json.getJSONArray("sub_button");
						if (CollectionUtils.isNotEmpty(buttons)) {
							long pid = accountMenu.getId();
							for (int j = 0; j < buttons.size(); j++) {
								json = buttons.getJSONObject(j);
								accountMenu = new AccountMenu();
								accountMenu.setParentId(pid);
								accountMenu.setName(json.getString("name"));
								accountMenu.setSort(j + 1);
								if (json.has("type")) {
									accountMenu.setMtype(json.getString("type"));
									//判断是否设置key
									if (Constants.MENU_NEED_KEY.contains(json.getString("type"))) {
										accountMenu.setEventType("fix");
                                        accountMenu.setMsgType(json.getString("msgType"));
                                        accountMenu.setMsgId(json.getString("msgId"));
									}
								}
								if (json.has("url")) {
									accountMenu.setUrl(json.getString("url"));
								}
								if (json.has("media_id")) {
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
