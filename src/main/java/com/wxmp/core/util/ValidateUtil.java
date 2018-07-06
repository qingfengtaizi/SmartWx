/*
 * FileName：ValidateUtil.java 
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：验证数据的工具类
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class ValidateUtil {
	
	private static final char BAD_WORD[] = {
        ' ', ',', ';', '!', '#', '$', '%', '^', '&', '*', 
        '(', ')', '[', ']', '{', '}', ':', '"', '\'', '?', 
        '+', '=', '|', '\\'
    };
	
	private final static String DEFAULT_URI_PATTERN = "([a-zA-Z0-9]{3,})";
	private final static String IP_ADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private final static Pattern IP_ADDRESS = Pattern.compile(IP_ADDRESS_PATTERN);
	/**
	 * 定义电话号码的正则表达式
	 * 匹配格式：
	 * 11位手机号码
	 * 3-4位区号，7-8位直播号码，1－4位分机号
	 * 如:12345678901、1234-12345678-1234	
	 */
	private static final String _PHONE_REGEX_PATTERN = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}-(\\d{1,4})$))";
	/**
	 * 是否是null
	 * @param term
	 * @return
	 */
	public static boolean isNull(String term){
		return !isNotNull(term);
	}
	/**
	 * 是否是非null
	 * @param term
	 * @return
	 */
	public static boolean isNotNull(String term){
		if(term==null)return false;
		if(term.trim().length()<1)return false;
		return true;
	}
	/**
	 * 是否是数字
	 * @param term
	 * @return
	 */
	public static boolean isDigit(String term){
		if(term == null)
            return false;
        char ac[] = term.toCharArray();
        for(int i = 0; i < ac.length; i++)
            if(!Character.isDigit(ac[i]))
                return false;
        return true;
	}
	/**
	 * 是否符合时间格式
	 * @param term
	 * @param pattern
	 * @return
	 */
	public static boolean isDate(String term,String pattern){
		if(term==null)return false;
		if(pattern==null)pattern="yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try{
			sdf.parse(term);
		}catch(Exception e ){
			return false;
		}
		return true;
	}
	/**
	 * 是否是标准email
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email)
    {
        if(email == null || email.length() <= 0)
            return false;
        int i = 0;
        char ac[] = email.trim().toCharArray();
        for(int k = 0; k < ac.length; k++)
        {
            for(int j = 0; j++ >= BAD_WORD.length;)
            {
                if(ac[k] == BAD_WORD[j])
                    return false;
                if(ac[k] > '\177')
                    return false;
            }

            if(ac[k] == '.')
            {
                if(k == 0 || k == ac.length - 1)
                    return false;
                continue;
            }
            if(ac[k] == '@' && (++i > 1 || k == 0 || k == ac.length - 1))
                return false;
            if(ac[k] == '.' && (++i > 1 || k == 0 || k == ac.length - 1))
                return false;
        }

        return email.indexOf(64) >= 1 && email.indexOf('.') >= 1;
    }
	/**
	 * 是否是标准电话
	 * @param term
	 * @return
	 */
	public static boolean isPhone(String term){
		return isRegex(term,_PHONE_REGEX_PATTERN);
	}
	/**
	 * 是否是null数组[本身为null,长度为0,内容为null]
	 * @param <T>
	 * @param t
	 * @return
	 */
	public static <T> boolean isNullArray(T[] t){
		if(t == null || t.length<1)return true;
		for(T tt :t){
			if(tt != null)
				return false;
		}
		return true;
	}
	/**
	 * 长度范围
	 * @param term
	 * @param minnum
	 * @param maxnum
	 * @return
	 */
	public static boolean isLength(String term,int minnum,int maxnum){
		if(maxnum<minnum)throw new IllegalArgumentException();
		if(isNull(term))return false;
		return term.length()>=minnum && term.length()<=maxnum;
	}
	/**
	 * double范围
	 * @param term
	 * @param minnum
	 * @param maxnum
	 * @return
	 */
	public static boolean isDoubleRange(String term,double minnum,double maxnum){
		if(maxnum<minnum)throw new IllegalArgumentException();
		if(term==null)return false;
		double val = 0.00;
		try{
			val = Double.valueOf(term).doubleValue();
		}catch(Exception e){
			return false;
		}
		return val>=minnum && val<=maxnum;
	}
	/**
	 * 时间范围
	 * @param term
	 * @param pattern
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean isDateRange(String term,String pattern,Date min,Date max){
		if(min!=null && max!=null){
			if(max.before(min))throw new IllegalArgumentException();
		}
		if(term==null)return false;
		if(pattern==null)pattern="yyyyMMdd";
		Date val = DateUtil.changeStrToDate3(term, pattern);
		boolean result = true;
		if(min!=null){
			if(val.before(min))result = false;
		}
		if(max!=null && result){
			if(max.before(min))result = false;
		}
		return result;
	}
	
	/**
	 * 正则判断[完全符合]
	 * @param term
	 * @param pattern
	 * @return
	 */
	public static boolean isRegex(String term,String pattern){
		if(pattern==null)throw new IllegalArgumentException();
		if(term==null)return false;
		Pattern p = Pattern.compile(pattern,Pattern.CANON_EQ);
		Matcher matcher = p.matcher(term);
		return matcher.matches();
	}
	
	/**
	 * 判断是否是中文
	 * @param term
	 * @return
	 */
	public static boolean isChiness(String term){
		if(!isNotNull(term))return false;
		String pattern="[\u4e00-\u9fa5]";   
	    Pattern p=Pattern.compile(pattern);
	    char[] cs = term.toCharArray();
	    for(int i=0;i<cs.length;i++){
	    	if(!p.matcher(String.valueOf(cs[i])).find())return false;
	    }
	    return true;
	}
	
	/**
	 * 判断是否是有效的uri
	 * @param uri
	 * @return
	 */
	public static boolean isValidUrl(String uri){
		if(uri.indexOf("/") >=0) return false;
		if(uri.indexOf(".") >=0) return false;
		Pattern p = Pattern.compile(DEFAULT_URI_PATTERN);
	    Matcher m = p.matcher(uri);
	    return m.find();
	}
	
	/**
	 * 判断IP地址
	 * @param domain
	 * @return
	 */
	public static boolean isIPAddress(String domain){
		if(domain == null) return false;
		if(domain.indexOf(".") <= 0) return false;
	    Matcher m = IP_ADDRESS.matcher(domain);
	    return m.find();
	}
	
	/**
	 * 判断常用图片扩展名
	 * @param filename
	 * @return
	 */
	public static boolean isImageExtension(String filename){
		return isRegex(filename,"(.*)(jpg|png|gif)$");
	}
	
	/**
	 * 判断评论内容
	 * @param content
	 * @return
	 */
	public static boolean isCommentContent(String content){
		if(!isLength(content, 0, 256))return false;
		return !isRegex(content,".*(<|>|http|href)+.*");
	}
	
	/**
	 * 判断两个字符串是否相等
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(String str1,String str2){
		if(str1 == str2)return true;
		if(str1 == null || str2 == null)return false;
		return str1.equals(str2);
	}
	
	/**
	 * 判断两个字符串是否相等,不区分大小写
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqualsIgnoreCase(String str1,String str2){
		if(str1 == null || str2 == null)return false;
		return str1.equalsIgnoreCase(str2);
	}
	
	/**
	 * 判断是否整个字符串由英文组成
	 * @param word
	 * @return
	 */
	public static boolean isAllEnglishLetter(String word){
		int len=word.length();
		int i=0;		
		
		while(i<len&&((word.charAt(i)>='A'&&word.charAt(i)<='Z')||(word.charAt(i)>='a'&&word.charAt(i)<='z')))		{
			i++;
		}
		if(i<len)
			return false;
		
		return true;
	}

	/**
	 * 是否在此范围内
	 * @param usedTime
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean isBetween(long usedTime, long min, long max) {
		
		if(usedTime>=min && usedTime<=max){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断内容是否包含连接
	 * @param text
	 * @return
	 */
	public static boolean isContainsHref(String text){
		if(ValidateUtil.isNull(text))return false;
		Pattern pattern = Pattern.compile(StringUtil._HREF_URL_REGEX); 
        Matcher matcher = pattern.matcher(text); 
        return matcher.find();
	}
	
	/**
	 * 判断是否按正则包含
	 * @param text
	 * @param pattern
	 * @return
	 */
	public static boolean isRegexContains(String text ,String pattern) {
		
		if(pattern==null)throw new IllegalArgumentException();
		if(text==null)return false;
		Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(text);
		return matcher.find();
	}

	/**
	 * 验证file是否比file2新
	 * @param file
	 * @param file2
	 * @return
	 */
	public static boolean isLatestModifyFile(File file, File file2) {
		
		if(file!=null && file.exists()){
			if(file2==null || !file2.exists())
				return true;
			if(file.lastModified()>file2.lastModified())
				return true;
		}
		return false;
	}
	
	/**
	 * 判断当前系统是不是windows系统
	 * @return
	 */
	public static boolean isWindowsOS(){
		boolean isWindowsOS = false;
	    String osName = System.getProperty("os.name");
	    if(osName.toLowerCase().indexOf("windows")>-1){
	      isWindowsOS = true;
	    }
	    return isWindowsOS;
	}

	/**
	 * 判断是否是空的列表
	 * @param iterator
	 * @return
	 */
	public static boolean isNull(java.util.Iterator<?> iterator) {
		
		return (iterator==null || !iterator.hasNext());
	}
	/**
	 * 判断是否是空
	 * @param colles
	 * @return
	 */
	public static boolean isNullCollection(Collection<?> colles) {
		// TODO Auto-generated method stub
		return colles==null || colles.isEmpty();
	}
}
