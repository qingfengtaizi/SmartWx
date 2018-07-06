/*
 * FileName：MsgNewsService.java 
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

import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.domain.MsgNews;

import java.util.List;


/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface MsgNewsService {

	public MsgNews getById(String id);

	public List<MsgNews> listForPage(MsgNews searchEntity);
	
	public List<MsgNews> getWebNewsListByPage(MsgNews searchEntity);

	public void add(MsgNews entity);

	public void update(MsgNews entity);

	public void delete(MsgNews entity);

	//根据用户发送的文本消息，随机获取 num 条文本消息
	public List<MsgNews> getRandomMsg(String inputcode,Integer num);

	public MsgNews getByBaseId(String baseid);
	
	public int updateMediaId(MsgNews entity);
	
	public int addSingleNews(MsgNews news,MediaFiles entity);
	
	public int addMediaFiles(MediaFiles entity);
	
	public int addMoreNews(MsgNews news);
	
	//多图文添加
	public Boolean addMoreNews(List<MsgNews> news);
	/**
	 * 查询多图文主表
	 * @return
	 */
    public List<MediaFiles> getMediaFileList();
    
    
	public List<MsgNews> getMsgNewsList();
	
	
	public void deleteNews(String mediaId);
	
	/**
	 * 更新单图文消息
	 * @param news
	 */
	public void updateSingleNews(MsgNews news);
	
	
	public List<MsgNews> getByMediaId(String mediaId);
}
