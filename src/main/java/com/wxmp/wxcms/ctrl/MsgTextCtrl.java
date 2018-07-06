/*
 * FileName：MsgTextCtrl.java 
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
package com.wxmp.wxcms.ctrl;

import java.util.List;

import com.wxmp.core.common.BaseCtrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wxmp.core.exception.BusinessException;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.wxcms.domain.MsgText;
import com.wxmp.wxcms.service.MsgBaseService;
import com.wxmp.wxcms.service.MsgTextService;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Controller
@RequestMapping("/msgtext")
public class MsgTextCtrl extends BaseCtrl {

    @Autowired
    private MsgTextService entityService;

    @Autowired
    private MsgBaseService baseService;

    @ResponseBody
    @RequestMapping(value = "/getById")
    public AjaxResult getById(String id) {
        MsgText text = entityService.getById(id);
        return AjaxResult.success(text);
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public AjaxResult list(MsgText searchEntity) throws BusinessException {
        List<MsgText> pageList = entityService.getMsgTextByPage(searchEntity);
        return getResult(searchEntity, pageList);
    }

    /**
     * 修改/添加
     * 
     * @param entity
     * @return
     */
    @RequestMapping(value = "/updateText")
    @ResponseBody
    public AjaxResult updateText(MsgText entity) {
        // 文本消息的关键词需要保证唯一性
        MsgText msgText = entityService.getRandomMsg(entity.getInputcode());
        if (msgText != null) {
            return AjaxResult.failure("关键词重复");
        }
        if (entity.getId() != null) {
            entityService.update(entity);
            // 更新成功
            return AjaxResult.updateSuccess();
        } else {
            // 添加成功
            entityService.add(entity);
            return AjaxResult.saveSuccess();
        }
    }

    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public AjaxResult deleteById(String baseId) {
        entityService.delete(baseId);
        return AjaxResult.deleteSuccess();
    }

}
