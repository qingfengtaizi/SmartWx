/*
 * FileName：HTTPResultXml.java 
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
package com.wxmp.core.util.wx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
public class HTTPResultXml {

/*<xml>
  <return_code><![CDATA[SUCCESS]]></return_code>
  <return_msg><![CDATA[OK]]></return_msg>
</xml>*/

	String return_code="";
	String return_msg="";

	public String getReturn_code() {
		return return_code;
	}
	@XmlElement
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}


	public String getReturn_msg() {
		return return_msg;
	}
	@XmlElement
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	
	

	
}
