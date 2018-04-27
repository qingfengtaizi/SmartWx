package com.wxmp.core.util.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import net.sf.json.JSONObject;

public class QiniuResult {

	private int code;//状态码 200表示成功
	private String msg;//信息
	private double duration;//耗时
	private String address;//服务器ip地址
	private String hash;
	private String key;
	private String contentType;
	private String url;//图片调用地址
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public static QiniuResult forBoject(Response res) throws QiniuException{
		QiniuResult result=new QiniuResult();
		result.setAddress(res.address);
		result.setCode(res.statusCode);
		result.setContentType(res.contentType());
		result.setDuration(res.duration);
		JSONObject json=JSONObject.fromObject(res.bodyString());
		
		result.setHash(json.getString("hash"));
		result.setKey(json.getString("key"));
		
		result.setMsg(res.error);
		//
		result.setUrl("http://oyeve9iiu.bkt.clouddn.com/"+json.getString("key"));
		return result;
	}
	
}
