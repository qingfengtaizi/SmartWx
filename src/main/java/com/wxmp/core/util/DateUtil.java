/*
 * FileName：DateUtil.java 
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
import java.util.List;

/**
 * 时间工具类
 * 
 * @author hermit
 * @date 2017 -06-10 14:04:41
 */
public class DateUtil {
	
	private static final long ONE_DAY_MILL_SECS=1000*3600*24;
    
	/**
	 * 时间格式满足yyyy-MM-dd
	 * 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 * @throws ParseException 
	 */
	public static int dayDiff(String start,String end) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long from = sdf.parse(start).getTime();  
		long to = sdf.parse(end).getTime();
		//获取天数差
		int days = (int) ((to - from)/ONE_DAY_MILL_SECS); 
		return days;
	}
    // 将字符串日期 转换成 yyyy-MM-dd HH:mm:ss
    public static Date changeStrToDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将字符串日期 转换成 yyyy-MM-dd
    public static Date changeStrToDate2(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 将字符串日期 转换成 yyyy-MM-dd
    public static Date changeStrToDate3(String dateStr,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String getDateToStr(Date date, SimpleDateFormat sdf) {
        String dateStr = null;
        try {
            dateStr = sdf.format(date);
            return dateStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // 将date类型转换为字符串yyyy-MM-dd HH:mm:ss
    public static String changeDateTOStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateToStr(date, sdf);
    }
    
    // 将date类型转换为字符串yyyy-MM-dd
    public static String changeDateTOStr3(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return getDateToStr(date, sdf);
    }
    
    // 将date类型转换为字符串yyyy年MM月dd日
    public static String changeDateTOStr4(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return getDateToStr(date, sdf);
    }
    // 将date类型转换为字符串yyyy年MM月dd日
    public static String changeDateTOStr5(Date date,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return getDateToStr(date, sdf);
    }
    
    // 将date类型转换为字符串yyyyMMddHHmmss
    public static String changeDateToYmdhms(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return getDateToStr(date, sdf);
    }
    
    // 将date类型转换为字符串yyyyMMdd--20100302
    public static String changeDateTOStr2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return getDateToStr(date, sdf);
    }
    
    // 得到前一天的日期
    public static String getFormerDate() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // 得到前一天
        
        return changeDateTOStr2(calendar.getTime());
    }
    
    // 得到下一天的日期
    public static String getAfterOneDayDate() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // 得到前一天
        
        return changeDateTOStr2(calendar.getTime());
    }

    public static String getAfterOneDayDate2() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // 得到前一天

        return changeDateTOStr3(calendar.getTime());
    }
    
    // 得到 mountday前的日期，mountday为天数
    public static String getFormerMonth(int mountday) {
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.add(Calendar.DATE, -mountday);
        
        return changeDateTOStr(calendar.getTime());
        
    }
    
    /**
     * 获得当前天(yyyy-MM-dd)
     * 
     * @Description
     * @author hermit
     * @date 2015年3月6日 上午8:44:01
     * @return
     */
    public static String getNowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return getDateToStr(new Date(), sdf);
    }
    
    /**
     * 获得当时间(yyyy-MM-dd HH:mm:ss)
     * 
     * @Description
     * @author hermit
     * @date 2015年3月6日 上午8:44:45
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateToStr(new Date(), sdf);
    }
    
    /**
     * 获取当前时间戳
     * 
     * @return
     * @author hermit
     * @date 2015年10月
     */
    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return getDateToStr(new Date(), sdf);
    }
    
    /**
     * 
     * @Description 获取yyyyMM年月
     * @author hermit
     * @date 2015年3月19日 下午2:23:38
     * @param date
     * @return
     */
    public static String getDateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return getDateToStr(date, sdf);
    }
    
    /**
     * 
     * @Description 获取任意时间
     * @author hermit
     * @date 2015年4月14日 下午12:28:26
     * @param year 0为 当年，1为下一年
     * @param month 0为 当月，1为下一月
     * @param day 0为 当日，1为下一天
     * @param hour 0为 00点，1为1点
     * @param minute 0为 00分，1为1分
     * @param second 0为 00秒，1为1秒
     * @return
     */
    public static Date getSysDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DAY_OF_MONTH, day);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        cal.add(Calendar.MINUTE, minute);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }
    
    /**
     * 
     * @Description 加num个月
     * @author hermit
     * @date 2015年4月14日 上午11:23:23
     * @param date 当前时间
     * @param num 延长的月数
     * @return
     */
    public static Date delayMonth(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, num);
        date = cal.getTime();
        return date;
    }
    
    /**
     * 当前时间延期num个月数
     * 
     * @author hermit
     * @date 2015年4月14日 下午5:24:06
     * @param num
     * @return
     */
    public static Date delayMonth(int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, num);
        return cal.getTime();
    }
    
    /**
     * 计算两个日期之间相隔的天数，不到一天不算
     * 如果开始日期大于截止日期，返回负数
     * @param s 开始日期
     * @param e 截止日期
     * @return
     */
    public static int diffDays(Date s,Date e){
    	long difMill=e.getTime()-s.getTime();
    	long days= difMill/ONE_DAY_MILL_SECS;
    	return (int)days;
    }


    /**
     * 计算两个日期之间相隔的天数，不到一天不算
     * 如果开始日期大于截止日期，返回负数
     * @param s 开始日期
     * @param e 截止日期
     * @return
     */
    public static String diffYears(Date s,Date e){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String s1=sdf.format(s);
        String e1=sdf.format(e);
        String[] arg1 = s1.split("-");
        String[] arg2 = e1.split("-");
        int year1 = Integer.valueOf(arg1[0]);
        int year2 = Integer.valueOf(arg2[0]);
        int month1 = Integer.valueOf(arg1[1]);
        int month2 = Integer.valueOf(arg2[1]);
        int day1 = Integer.valueOf(arg1[2]);
        int day2 = Integer.valueOf(arg2[2]);
        int md = 0 ;
        if(year1!=year2){
            md = day2>day1?0:-1;
        }
        int diffMonth = (year2*12+month2)-(year1*12+month1)+md;
        int yearNum = diffMonth/12;
        int monthNum = diffMonth % 12;
        String year =Integer.toString(yearNum);
        String month = Integer.toString(monthNum);
        String workdays=year+"年"+month+"月";
        return workdays;
    }
    /**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}


    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDate2(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        return today;
    }

    public static Date timestampToDate(String str_num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (str_num.length() == 13) {
            String date = format.format(new Date(Long.parseLong(str_num)));
            return changeStrToDate(date);
        } else {
            String date = format.format(new Date(Integer.parseInt(str_num) * 1000L));
            return changeStrToDate(date);
        }
    }

    public static void main(String[] args) throws Exception{

    	String start="2018-01-04";
    	String end="2018-01-03";
    	System.out.println(dayDiff(start, end));
//        System.out.println(timestamp2Date("1480405849"));
    }

}
