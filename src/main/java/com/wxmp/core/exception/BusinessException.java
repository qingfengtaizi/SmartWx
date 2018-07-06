/*
 * FileName：BusinessException.java 
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
