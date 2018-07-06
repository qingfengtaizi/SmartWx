/*
 * FileName：SysUser.java 
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

import com.wxmp.core.page.Page;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Data
public class SysUser extends Page implements Serializable {
    
	//主键id
	private String id;
	//用户名
	private String account;
	//密码
	private String pwd;
	//性别 0男 1女
	private String sex;
	//手机号
	private String phone;
	//姓名
	private String trueName;
	//创建时间
	private String createTime;
	//更新时间
	private String updateTime;
	//状态
	private String flag;
	
	//新登录密码
	private String newpwd;
}
