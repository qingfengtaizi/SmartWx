package com.wxmp.backstage.exception;

/**
 * 业务处理中非预期的异常
 * 出现此异常说明系统不能正常处理，不需捕获处理
 * 主要用户java封装中有异常抛出但实际中确定没有异常的替代
 * @author xiaosf
 * @date 2017年6月16日
 */
public class UnexpectedException extends RuntimeException{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8889196147214525583L;
	
	/**
	 * 异常代码
	 */
	private String code;    
    
	/**
	 * 默认构造函数
	 */
    public UnexpectedException() {    
        super();    
    }    
    
    /**
     * 构造函数
     * @param message 异常信息
     */
    public UnexpectedException(String message) {    
        super(message);    
    }    
    
    /**
     * 构造函数
     * @param code 异常代码
     * @param message 异常信息
     */
    public UnexpectedException(String code, String message) {    
        super(message);    
        this.code = code;    
    }    
    
    /**
     * 构造函数
     * @param cause 来源异常
     */
    public UnexpectedException(Throwable cause) {    
        super(cause);    
    }    
    
    /**
     * 构造函数
     * @param message 异常信息
     * @param cause  来源异常
     */
    public UnexpectedException(String message, Throwable cause) {    
        super(message, cause);    
    }    
    
    /**
     * 构造函数
     * @param code 异常代码
     * @param message 异常信息
     * @param cause  来源异常
     */
    public UnexpectedException(String code, String message, Throwable cause) {    
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
