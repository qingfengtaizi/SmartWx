/*
 * FileName：DateUtilOld.java 
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 日期工具类
 * 
 */

public class DateUtilOld {
	
	private final SimpleDateFormat format;

    public DateUtilOld(SimpleDateFormat format) {
        this.format = format;
    }
    
    public SimpleDateFormat getFormat() {
        return format;
    }
    
    
    //yyyyMMddHHmmss
	public static final DateUtilOld COMPAT_HHmmss = new DateUtilOld(new SimpleDateFormat("yyyyMMddHHmmss"));
    
    //紧凑型日期格式，也就是纯数字类型yyyyMMdd
	public static final DateUtilOld COMPAT = new DateUtilOld(new SimpleDateFormat("yyyyMMdd"));
	
	//常用日期格式，yyyy-MM-dd
	public static final DateUtilOld COMMON = new DateUtilOld(new SimpleDateFormat("yyyy-MM-dd"));
	public static final DateUtilOld COMMON_FULL = new DateUtilOld(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	
	//使用斜线分隔的，西方多采用，yyyy/MM/dd
	public static final DateUtilOld SLASH = new DateUtilOld(new SimpleDateFormat("yyyy/MM/dd"));
	
	//中文日期格式常用，yyyy年MM月dd日
	public static final DateUtilOld CHINESE = new DateUtilOld(new SimpleDateFormat("yyyy年MM月dd日"));
	public static final DateUtilOld CHINESE_FULL = new DateUtilOld(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"));
	
	/**
	 * 日期获取字符串
	 */
	public String getDateText(Date date){
		return getFormat().format(date);
	}
	
	/**
	 * 字符串获取日期
	 * @throws ParseException 
	 */
	public Date getTextDate(String text) throws ParseException{
		return getFormat().parse(text);
	}
	/**
	 * 微信中long转日期
	 * @param ld
	 * @return
	 */
	public Date getLongDate(long ld){
		
		return new Date(ld*1000);
	}
	
	/**
	 * 日期获取字符串
	 */
	public static String getDateText(Date date ,String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 字符串获取日期
	 * @throws ParseException 
	 */
	public static Date getTextDate(String dateText ,String format) throws ParseException{
		return new SimpleDateFormat(format).parse(dateText);
	}
	
	/**
	 * 根据日期，返回其星期数，周一为1，周日为7
	 * @param date
	 * @return
	 */
	public static int getWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int w = calendar.get(Calendar.DAY_OF_WEEK);
		int ret;
		if (w == Calendar.SUNDAY)
			ret = 7;
		else
			ret = w - 1;
		return ret;
	}
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
}
