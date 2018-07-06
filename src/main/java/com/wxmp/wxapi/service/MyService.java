/*
 * FileName：MyService.java 
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
package com.wxmp.wxapi.service;

import com.alibaba.fastjson.JSONObject;
import com.wxmp.core.exception.WxErrorException;
import com.wxmp.wxapi.process.MpAccount;
import com.wxmp.wxapi.vo.MsgRequest;
import com.wxmp.wxcms.domain.AccountFans;

/**
 * 我的微信服务接口，主要用于结合自己的业务和微信接口
 */
public interface MyService {
	
	//消息处理
	public String processMsg(MsgRequest msgRequest,MpAccount mpAccount) throws WxErrorException;

	//发布菜单
	public JSONObject publishMenu(MpAccount mpAccount) throws WxErrorException ;
	
	//删除菜单
	public JSONObject deleteMenu(MpAccount mpAccount) throws WxErrorException ;
	
	//获取用户列表
	public boolean syncAccountFansList(MpAccount mpAccount) throws WxErrorException ;
	
	//获取单个用户信息
	public AccountFans syncAccountFans(String openId, MpAccount mpAccount, boolean merge) throws WxErrorException ;
	
	//根据openid 获取粉丝，如果没有，同步粉丝
	public AccountFans getFansByOpenId(String openid,MpAccount mpAccount) throws WxErrorException ;

	//同步服务器的用户标签
	public boolean syncUserTagList(MpAccount mpAccount)throws WxErrorException ;
}
