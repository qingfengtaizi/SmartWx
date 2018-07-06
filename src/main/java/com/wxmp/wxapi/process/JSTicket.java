/*
 * FileName：JSTicket.java 
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

import java.io.Serializable;
import com.wxmp.core.util.CalendarUtil;

/**
 * 接口凭证 
 */
public class JSTicket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ticket;// 接口访问凭证
	private int expiresIn;// 凭证有效期，单位：秒
	private long createTime;//创建时间，单位：秒 ，用于判断是否过期
	
	private Integer errcode;//错误编码
	private String errmsg;//错误消息
	
	public JSTicket(){
		this.createTime = CalendarUtil.getTimeInSeconds();
	}
	
	public JSTicket(String ticket,int expiresIn){
		this.ticket = ticket;
		this.expiresIn = expiresIn;
		this.createTime = CalendarUtil.getTimeInSeconds();
	}
	
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
		this.errmsg = ErrCode.errMsg(errcode);
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	/**
	 * 是否超时
	 * @return true-超时；false-没有超时
	 */
	public boolean isExpires(){
		long now = CalendarUtil.getTimeInSeconds();
		return now - this.createTime - 10 >= this.expiresIn; //预留 10s 
	}
	
	/**
	 * 是否超时
	 * @return true-超时；false-没有超时
	 */
	public boolean isExpires(Long expireTime){
		long now = CalendarUtil.getTimeInSeconds();
		return now - this.createTime - 10 >= expireTime; //预留 10s 
	}
	
	
}
