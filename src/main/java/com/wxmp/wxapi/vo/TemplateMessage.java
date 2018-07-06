/*
 * FileName：TemplateMessage.java 
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
package com.wxmp.wxapi.vo;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * 发送的模板消息对象
 */
@Data
public class TemplateMessage {

	private String openid;//粉丝id
	private String templateId;//模板id
	private String url;//链接
	private String color = "#173177";//颜色
	private Map<String,String> dataMap;//参数数据
	@Override
	public String toString(){
		JSONObject jsObj = new JSONObject();
		jsObj.put("touser", openid);
		jsObj.put("template_id", templateId);
		jsObj.put("url", url);
		
		JSONObject data = new JSONObject();
		if(dataMap != null){
			for(String key : dataMap.keySet()){
				JSONObject item = new JSONObject();
				item.put("value", dataMap.get(key));
				item.put("color", color);
				data.put(key,item);
			}
		}
		jsObj.put("data", data);
		return jsObj.toString();
	}
	
	
}
