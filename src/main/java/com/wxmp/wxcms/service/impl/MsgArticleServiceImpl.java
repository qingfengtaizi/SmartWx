/*
 * FileName：MsgArticleServiceImpl.java 
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
package com.wxmp.wxcms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wxmp.wxcms.domain.MsgArticle;
import com.wxmp.wxcms.domain.MsgNews;
import com.wxmp.wxcms.mapper.MsgArticleDao;
import com.wxmp.wxcms.mapper.MsgNewsDao;
import com.wxmp.wxcms.service.MsgArticleService;
/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Service
public class MsgArticleServiceImpl implements MsgArticleService {

	@Resource
	private MsgArticleDao articleDao;
	
	@Resource
	private MsgNewsDao entityDao;
	
	@Override
	public List<MsgArticle> getByNewsId(int id) {
		// TODO Auto-generated method stub
		return articleDao.getByNewsId(id);
	}

	@Override
	public MsgArticle getById(int id) {
		// TODO Auto-generated method stub
		return articleDao.getById(id);
	}

	@Override
	public void add(MsgArticle article) {
		// TODO Auto-generated method stub
		articleDao.add(article);
	}

	@Override
	public void insertByBatch(List<MsgArticle> articles) {
		// TODO Auto-generated method stub
		articleDao.insertByBatch(articles);
	}

	@Override
	public void update(MsgArticle article) {
		// TODO Auto-generated method stub
//		this.getById(article.getArId());
		if(article.getNewsIndex()==0){
			MsgNews news = entityDao.getById(String.valueOf(article.getNewsId()));
			news.setTitle(article.getTitle());
			news.setAuthor(article.getAuthor());
			news.setBrief(article.getDigest());
			news.setDescription(article.getContent());
			news.setPicpath(article.getPicUrl());
			news.setThumbMediaId(article.getThumbMediaId());
			news.setFromurl(article.getContentSourceUrl());
			news.setShowpic(article.getShowCoverPic());
			entityDao.update(news);
		}
		articleDao.update(article);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		articleDao.delete(id);
	}

	@Override
	public void deleteByBatch(int id) {
		// TODO Auto-generated method stub
		articleDao.deleteByBatch(id);
	}

}
