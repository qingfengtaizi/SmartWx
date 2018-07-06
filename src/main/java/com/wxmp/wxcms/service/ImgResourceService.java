/*
 * FileName：ImgResourceService.java 
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
package com.wxmp.wxcms.service;

import java.util.List;

import com.wxmp.wxcms.domain.ImgResource;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface ImgResourceService {
	
	public List<ImgResource> getImgListByPage(ImgResource entity);
	/**
	 * 获取图片信息
	 * @param id
	 * @return
	 */
	public ImgResource getImg(String id);
	
	/**
	 * 创建资源
	 * @param img
	 * @return
	 */
	public String addImg(ImgResource img);
	
	/**
	 * 删除中间表记录
	 * @param otherId
	 * @return
	 */
	public boolean removeOtherToImg(String otherId);
	
	/**
	 * 删除图片记录
	 * @param id
	 * @return
	 */
	public boolean updateImgFlag(String id, Integer flag);
	
	public boolean delImg(String id);
}
