/*
 * FileName：MsgNewsDao.java 
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
package com.wxmp.wxcms.mapper;

import com.wxmp.wxcms.domain.MsgNews;

import java.util.List;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface MsgNewsDao {

	public MsgNews getById(String id);

	public List<MsgNews> listForPage(MsgNews searchEntity);
	
	public List<MsgNews> getByMediaId(String mediaId);
	
	public List<MsgNews> getWebNewsListByPage(MsgNews searchEntity);

	public void add(MsgNews entity);

	public void update(MsgNews entity);
	
	public void updateUrl(MsgNews entity);

	public void delete(MsgNews entity);

	public List<MsgNews> getRandomMsg(Integer num);

	public List<MsgNews> getRandomMsgByContent(String inputcode ,Integer num);
	
	public List<MsgNews> getMsgNewsByIds(String[] array);

	public MsgNews getByBaseId(String baseid);
	
	/**
	 * 同步到微信后更新表
	 * @param entity
	 */
	public void updateMediaId(MsgNews entity);
	
	/**
	 * 保存图文消息
	 * @param entity
	 */
	public Integer addNews(MsgNews entity);
	
	/**
	 * 查询图文列表
	 * @return
	 */
	public List<MsgNews> getMsgNewsList();
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteByMediaId(String mediaId);
	
	/**
	 * 更新图文媒体素材
	 * @param entity
	 */
	public void updateNews(MsgNews entity);
}
