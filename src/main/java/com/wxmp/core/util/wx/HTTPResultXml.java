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
