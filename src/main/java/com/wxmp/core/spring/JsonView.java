package com.wxmp.core.spring;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

public class JsonView {
	
	//错误代码 0-成功
	private Integer errcode = 0;
	
	// 消息
	private String message;
	
	// 数据
	private Object data;
	
	
	public JsonView(Integer errcode, String message) {
		this.errcode = errcode;
		this.message = message;
	}

	public JsonView(Integer errcode) {
		this.errcode = errcode;
	}

	public JsonView() {
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String toString(){
		return JSONObject.fromObject(this).toString();
	}
	
	public String toIso8859String() throws UnsupportedEncodingException{
		return new String(this.toString().getBytes(), "ISO-8859-1");
	}
	
}
