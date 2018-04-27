/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.core.exception;

/**
 * 业务异常
 * @author hermit
 * @date 2017年6月16日
 */
public class BusinessException extends Exception{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7625611546025433551L;
	/**
	 * 异常代码
	 */
	private String code;    
    
	/**
	 * 默认构造函数
	 */
    public BusinessException() {    
        super();    
    }    
    
    /**
     * 构造函数
     * @param message 异常信息
     */
    public BusinessException(String message) {    
        super(message);    
    }    
    
    /**
     * 构造函数
     * @param code 异常代码
     * @param message 异常信息
     */
    public BusinessException(String code, String message) {    
        super(message);    
        this.code = code;    
    }    
    
    /**
     * 构造函数
     * @param cause 来源异常
     */
    public BusinessException(Throwable cause) {    
        super(cause);    
    }    
    
    /**
     * 构造函数
     * @param message 异常信息
     * @param cause  来源异常
     */
    public BusinessException(String message, Throwable cause) {    
        super(message, cause);    
    }    
    
    /**
     * 构造函数
     * @param code 异常代码
     * @param message 异常信息
     * @param cause  来源异常
     */
    public BusinessException(String code, String message, Throwable cause) {    
        super(message, cause);    
        this.code = code;    
    }

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}    
    
     
}
