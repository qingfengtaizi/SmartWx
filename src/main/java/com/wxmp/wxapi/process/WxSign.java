/*
 * FileName：WxSign.java 
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
package com.wxmp.wxapi.process;

import com.wxmp.core.common.Identities;
import com.wxmp.core.util.wx.SignUtil;

import java.util.SortedMap;
import java.util.TreeMap;


public class WxSign {
	private String appId;
	private String timestamp;
	private String nonceStr;
	private String signature;
	
	public WxSign(){
		
	}
	
	public WxSign(String appId,String jsTicket,String url){
		String timestamp = String.valueOf(System.currentTimeMillis()/1000);
		String nonceStr = Identities.getRandomString(8);
		SortedMap<String,String> map = new TreeMap<String, String>();
		map.put("jsapi_ticket", jsTicket);
		map.put("noncestr", nonceStr);
		map.put("timestamp", timestamp);
		map.put("url", url);
		this.appId = appId;
		this.nonceStr = nonceStr;
		this.timestamp = timestamp;
		this.signature = SignUtil.signature(map);
	}
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	
	
	
}
