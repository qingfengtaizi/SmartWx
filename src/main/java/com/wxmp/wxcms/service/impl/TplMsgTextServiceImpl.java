/*
 * FileName：TplMsgTextServiceImpl.java 
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

import com.wxmp.core.util.StringUtil;
import com.wxmp.wxcms.domain.MsgText;
import org.springframework.stereotype.Service;

import com.wxmp.wxapi.process.MsgType;
import com.wxmp.wxcms.domain.MsgBase;
import com.wxmp.wxcms.domain.TplMsgText;
import com.wxmp.wxcms.mapper.MsgBaseDao;
import com.wxmp.wxcms.mapper.TplMsgTextDao;
import com.wxmp.wxcms.service.TplMsgTextService;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Service
public class TplMsgTextServiceImpl implements TplMsgTextService{

	@Resource
	private TplMsgTextDao entityDao;

	@Resource
	private MsgBaseDao baseDao;

	public TplMsgText getById(String id){
		return entityDao.getById(id);
	}

	public List<TplMsgText> getTplMsgTextByPage(TplMsgText searchEntity){
		return entityDao.getTplMsgTextByPage(searchEntity);
	}

	public void add(TplMsgText entity){
		MsgBase base = new MsgBase();
		base.setInputcode(entity.getInputcode());
		base.setCreateTime(new Date());
		base.setMsgtype(MsgType.Text.toString());
		baseDao.add(base);
		
		entity.setBaseId(base.getId());
		entityDao.add(entity);
	}

	public void update(TplMsgText entity){
		MsgBase base = baseDao.getById(entity.getBaseId().toString());
		base.setInputcode(entity.getInputcode());
		baseDao.updateInputcode(base);
		entityDao.update(entity);
	}

    public void delete(String baseIds) {
        String[] ids = StringUtil.split(baseIds, ",");
        for (String id : ids) {
            MsgBase base = new MsgBase();
            TplMsgText tplMsgText = new TplMsgText();
            base.setId(Long.valueOf(id));
            tplMsgText.setBaseId(Long.valueOf(id));
            entityDao.delete(tplMsgText);
            baseDao.delete(base);
        }
    }

	@Override
	public TplMsgText getByBaseId(String baseid) {
		return entityDao.getByBaseId(baseid);
	}
}
