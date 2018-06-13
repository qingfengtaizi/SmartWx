/*
 * FileName：XSSUtils.java
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

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;
/**
 * 
* @ClassName: XSSUtils 
* @Description: TODO XSS攻击解决方案汇总 
* @version 1.0 2017年11月21日 下午5:42:42
 */
public class XSSUtils {

	/**
     * 替换HTML脚本
     *	XSS攻击解决方案 1
     * @param value
     * @return
     */
    public static String cleanXSS(String value) {
        //You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }
    /**
     * XSS攻击解决方案 2
    *
    * @Title: xssEncode 
    * @Description: TODO 将所有的编程全角字符 
    * @param @param s
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String xssEncode(String s) {
        if (s == null || s.equals("")) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '>':
                sb.append('＞');// 全角大于号
                break;
            case '<':
                sb.append('＜');// 全角小于号
                break;
            case '\'':
                sb.append('\\');
                sb.append('\'');
                sb.append('\\');
                sb.append('\'');
                break;
            case '\"':
                sb.append('\\');
                sb.append('\"');// 全角双引号
                break;
            case '&':
                sb.append('＆');// 全角
                break;
            case '\\':
                sb.append('＼');// 全角斜线
                break;
            case '#':
                sb.append('＃');// 全角井号
                break;
            case ':':
                sb.append('：');// 全角冒号
                break;
            case '%':
                sb.append("\\\\%");
                break;
            default:
                sb.append(c);
                break;
            }
        }
        return sb.toString();
    }
    /**
     * XSS攻击解决方案 3
    *
    * @Title: escapeForLang 
    * @Description: TODO 使用commons-lang.jar中的 StringEscapeUtils进行替换
    * @param @param str
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String escapeForLang(String str){
    	
    	str=StringEscapeUtils.escapeHtml(str);
    	str=StringEscapeUtils.escapeJavaScript(str);
    	str=StringEscapeUtils.escapeSql(str);
    	return str;
    }
    /**
     * XSS攻击解决方案 4
    *
    * @Title: escapeForSpring 
    * @Description: TODO 使用spring中的 HtmlUtils替换html标签
    * @param @param str
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String escapeForSpring(String str){
    	
    	str=HtmlUtils.htmlEscape(str);// ①转换为HTML转义字符表示   
//    	str=HtmlUtils.htmlEscapeDecimal(str);// ②转换为数据转义表示 
//    	str=HtmlUtils.htmlEscapeHex(str);//③转换为十六进制数据转义表示
    	return str;
    }
}
