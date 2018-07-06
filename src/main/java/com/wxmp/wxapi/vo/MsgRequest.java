/*
 * FileName：MsgRequest.java 
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


import lombok.Data;

/**
 * 用户发送给公众号的消息；
 * 
 */
@Data
public class MsgRequest{
	
	private String MsgType;//消息类型
	private String MsgId;
	private String FromUserName;//openid
	private String ToUserName;
	private String CreateTime;
	//文本消息
	private String Content;
	//图片消息
	private String PicUrl;
	//地理位置消息
	private String Location_X;
	private String Location_Y;
	private String Scale;
	private String Label;
	//事件消息
	private String Event;
	private String EventKey;
}
