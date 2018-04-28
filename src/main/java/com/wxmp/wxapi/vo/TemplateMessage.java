package com.wxmp.wxapi.vo;

import java.util.Map;

import lombok.Data;
import net.sf.json.JSONObject;

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
