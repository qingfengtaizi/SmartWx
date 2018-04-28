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
