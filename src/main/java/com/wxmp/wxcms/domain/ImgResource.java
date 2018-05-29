/*
 * FileName：ImgResource.java 
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

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Data
public class ImgResource extends Page implements Serializable {
    private String  id;
	/**
	 * 图片原名称
	 */
	private String  trueName;
	
	/**
	 * 微信返回的mediaId
	 */
	private String mediaId;
	
	/**
	 * 图片尾缀名类型
	 */
	private String  type;
	
	/**
	 * 图片存储名称
	 */
	private String  name;
	
	/**
	 * 图片路径
	 */
	private String  url;
	
	/**
	 * 图片http访问路径
	 */
	private String  httpUrl;	
	
	/**
	 * 图片大小byte
	 */
	private Integer  size;
	
	/**
	 * 创建时间
	 */
	private Date  createTime;
	
	/**
	 * 修改时间
	 */
	private Date  updateTime;

	/**
	 * 图片状态字段：0.未引用 ，1.已被引用
	 */
	private Integer flag;

	@Transient
	private String start;
	@Transient
	private String end;

}
