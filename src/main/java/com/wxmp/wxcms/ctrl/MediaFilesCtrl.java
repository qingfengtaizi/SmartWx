/*
 * FileName：MediaFilesCtrl.java 
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wxmp.core.common.BaseCtrl;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.service.MediaFileService;
/**
 * 语音和视频控制器
 * @author nigualding
 *
 */
@Controller
@RequestMapping("mediaFile")
public class MediaFilesCtrl extends BaseCtrl {

	@Autowired
	private MediaFileService mediaFileService;
	
	
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(MediaFiles searchEntity) {
        List<MediaFiles> pageList = mediaFileService.getMediaListByPage(searchEntity);
        return getResult(searchEntity, pageList);
    }
    
}
