/*
 * FileName：ExceptionHolder.java 
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
package com.wxmp.core.spring;

import javax.servlet.http.HttpServletRequest;

import com.wxmp.core.exception.BusinessException;
import com.wxmp.core.util.AjaxResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * spring mvc controller层抛出异常统一处理
 * @author hermit
 * @date 2017年6月20日
 */
@ControllerAdvice(annotations = Controller.class)
public class ExceptionHolder {
	//日志
	private static final Logger logger = Logger.getLogger(ExceptionHolder.class);
	
	/**
	 * 处理business异常
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public AjaxResult processException(HttpServletRequest request, BusinessException e){
	    logger.error("业务异常：", e);
	    String message = e.getMessage();
	    return AjaxResult.failure(message);
	}
	
	/**
	 * 处理异常
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public AjaxResult processException(HttpServletRequest request, Exception e){
	    logger.error("处理异常：", e);
	    String message = e.getMessage();
	    return AjaxResult.failure(message);
	}
}
