/*
 * FileName：AccountMenu.java 
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
package com.wxmp.wxcms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wxmp.core.page.Page;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Data
public class AccountMenu extends Page implements Serializable {

	private Long id;
	private String mtype;//消息类型： click - 事件消息；view - 链接消息 

	/**
	 * 事件消息类型；即mtype = click; 系统定义了2中模式  key / fix
	 * key 即是 inputcode ；
	 * fix 即是 固定消息id，在创建菜单时，用 _fix_开头，方便解析；
	 * 同样的开发者可以自行定义其他事件菜单
	 */
	private String eventType;
	private String name;
	private String inputCode;
	private String url;
	private Integer sort;
	private Long parentId;
	private String msgType;
	private String msgId;

	private String parentName;
	private Long gid;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
	private Date createTime;//创建时间

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
