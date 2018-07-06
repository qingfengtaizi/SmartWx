/*
 * FileName：MsgTextServiceImpl.java 
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

import com.wxmp.core.util.StringUtil;
import com.wxmp.wxapi.process.MsgType;
import com.wxmp.wxcms.domain.MsgBase;
import com.wxmp.wxcms.domain.MsgText;
import com.wxmp.wxcms.mapper.MsgBaseDao;
import com.wxmp.wxcms.mapper.MsgTextDao;
import com.wxmp.wxcms.service.MsgTextService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Service
public class MsgTextServiceImpl implements MsgTextService{

	@Resource
	private MsgTextDao entityDao;

	@Resource
	private MsgBaseDao baseDao;

	public MsgText getById(String id){
		return entityDao.getById(id);
	}

	public List<MsgText> getMsgTextByPage(MsgText searchEntity){
		return entityDao.getMsgTextByPage(searchEntity);
	}

	public void add(MsgText entity){
		MsgBase base = new MsgBase();
		base.setInputcode(entity.getInputcode());
		base.setCreateTime(new Date());
		base.setMsgtype(MsgType.Text.toString());
		baseDao.add(base);
		entity.setBaseId(base.getId());
		entityDao.add(entity);
	}

	public void update(MsgText entity){
		MsgBase base = baseDao.getById(entity.getBaseId().toString());
		base.setInputcode(entity.getInputcode());
		baseDao.updateInputcode(base);
		entityDao.update(entity);
	}

    public void delete(String baseIds) {
        String[] ids = StringUtil.split(baseIds, ",");
        for (String id : ids) {
            MsgBase base = new MsgBase();
            MsgText msgText = new MsgText();
            base.setId(Long.valueOf(id));
            msgText.setBaseId(Long.valueOf(id));
            entityDao.delete(msgText);
            baseDao.delete(base);
        }
    }

	//根据用户发送的文本消息，随机获取一条文本消息
	public MsgText getRandomMsg(String inputCode){
		return entityDao.getRandomMsg(inputCode);
	}
	public MsgText getRandomMsg2(){
		return entityDao.getRandomMsg2();
	}

	/* (non-Javadoc)
	 * @see com.wxmp.wxcms.service.MsgTextService#getByBaseId(java.lang.String)
	 */
	@Override
	public MsgText getByBaseId(String baseid) {
		// TODO Auto-generated method stub
		return entityDao.getByBaseId(baseid);
	}
}
