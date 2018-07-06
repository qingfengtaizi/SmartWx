/*
 * FileName：MyTrustManager.java 
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
package com.wxmp.core.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;
/** 
 * 证书信任管理器（用于https请求） 
 *  
 * @author 
 * @date 
 */  
public class MyTrustManager implements X509TrustManager{
	 public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
	    }  
	  
	    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
	    }  
	  
	    public X509Certificate[] getAcceptedIssuers() {  
	        return null;  
	    }  
}
