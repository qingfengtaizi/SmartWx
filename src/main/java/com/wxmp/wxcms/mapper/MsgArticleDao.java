/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.mapper;

import java.util.List;

import com.wxmp.wxcms.domain.MsgArticle;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface MsgArticleDao {

	List<MsgArticle> getByNewsId(int id);
	
	MsgArticle getById(int id);
	
	void add(MsgArticle article);
	
	void insertByBatch(List<MsgArticle> articles);
	
	void update(MsgArticle article);
	
	void delete(int id);
	
	void deleteByBatch(int id);
}
