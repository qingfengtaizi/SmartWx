/*
 * FileName：CalendarUtil.java 
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日期工具类
 * @author Brain
 */
public class CalendarUtil {
	
	public static final String YEAR = "year";
	public static final String MONTH = "month";
	public static final String MONTH_FIRST_DAY_TIME = "month_first_day_time";
	public static final String MONTH_LAST_DAY_TIME = "month_last_day_time";
	
	//获取当前年的前5年数据
	public static List<String> getPre5Years(){
		List<String> years = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		for(int i = 0; i < 5; i++){
			years.add((year - i) + "");
		}
		return years;
	}
	
	//获取12个月的数据
	public static List<String> get12MonthStr(){
		List<String> months = new ArrayList<String>();
		for(int i = 1 ;i < 13 ;i++){
			months.add(i+"");
		}
		return months;
	}
	
	//获取当前明天的数据
	public static String getNextDay(String format){
		Calendar calendar = Calendar.getInstance();
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));//设置本月最大日期
		int maxDate = tmpCalendar.get(Calendar.DATE);
		
		int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int date = calendar.get(Calendar.DATE);
		
        if(date + 1 > maxDate ){
        	if(month + 1 > 11){
        		calendar.add(Calendar.YEAR, 1);
        		calendar.set(Calendar.MONTH, 0);
        		calendar.set(Calendar.DATE, 1);
        	}else{
        		calendar.set(year, month, date+1);
        	}
        }else{
        	calendar.set(year, month, date+1);
        }
        return DateUtilOld.getDateText(calendar.getTime(), format);
	}
	
	//获取n天后的日期
	public static Date getNextNDay(Date startTime,Integer n){
		Calendar start = Calendar.getInstance();
        start.setTime(startTime);
        start.add(Calendar.DATE, n);
        return start.getTime();
	}
	
	//获取n天后的日期 00:00:00
	public static Date getNextNDayBegin(Date startTime,Integer n){
		Calendar start = Calendar.getInstance();
        start.setTime(startTime);
        start.add(Calendar.DATE, n);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        return start.getTime();
	}
	
	//获取n天后的日期 23:59:59
	public static Date getNextNDayEnd(Date startTime,Integer n){
		Calendar start = Calendar.getInstance();
        start.setTime(startTime);
        start.add(Calendar.DATE, n);
        start.set(Calendar.HOUR_OF_DAY, 23);
        start.set(Calendar.MINUTE, 59);
        start.set(Calendar.SECOND, 59);
        return start.getTime();
	}
	
	//获取n天前的日期 
	public static Date getPreNDay(Date startTime,Integer n){
		Calendar start = Calendar.getInstance();
        start.setTime(startTime);
        start.add(Calendar.DATE, -n);
        return start.getTime();
	}
	
	//获取n天前的日期 00:00:00
	public static Date getPreNDayBegin(Date startTime,Integer n){
		Calendar start = Calendar.getInstance();
        start.setTime(startTime);
        start.add(Calendar.DATE, -n);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        return start.getTime();
	}
	
	//获取n天前的日期 23:59:59
	public static Date getPreNDayEnd(Date startTime,Integer n){
		Calendar start = Calendar.getInstance();
        start.setTime(startTime);
        start.add(Calendar.DATE, -n);
        start.set(Calendar.HOUR_OF_DAY, 23);
        start.set(Calendar.MINUTE, 59);
        start.set(Calendar.SECOND, 59);
        return start.getTime();
	}
	
	//获取前x天的数据, x < 10;
	public static String getPreXDay(int x ,String format){
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int date = calendar.get(Calendar.DATE);
		
        if(date - x < 0 ){
        	if(month - 1 < 0){
        		calendar.add(Calendar.YEAR, -1);
        		calendar.set(Calendar.MONTH, 11);
        		calendar.set(Calendar.DATE, 31  - (x-date));
        	}else{
        		calendar.add(Calendar.MONTH, -1);
        		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));//设置本月最大日期
        		int maxDate = calendar.get(Calendar.DATE);
        		calendar.set(year, month - 1, maxDate - (x-date));
        	}
        }else{
        	calendar.set(year, month, date - x);
        }
        return DateUtilOld.getDateText(calendar.getTime(), format);
	}
	
	//获取当前年
	public static String getYear(){
		Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year+"";
	}
	
	//获取当前月
	public static String getMonth(){
		Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        if(month > 12){
        	return "12";
        }
        return month+"";
	}
	
	//获取 年-月的 第一天 和 最后一天 时间
	public static Map<String,String> getMonthFirstEndDayTime(String year,String month){
		Map<String,String> rst = new HashMap<String,String>();
		Calendar calendar = Calendar.getInstance();
		int m = Integer.parseInt(month);
		m = m - 1;
		if(m < 0){
			m = 0;
		}
		calendar.set(Integer.parseInt(year), m, 1);
		String firstDayTime = DateUtilOld.COMMON.getDateText(calendar.getTime()) +" 00:00:00";
		
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));//设置本月最大日期
		String lastDayTime = DateUtilOld.COMMON.getDateText(calendar.getTime()) +" 23:59:59";
		
		rst.put(CalendarUtil.MONTH_FIRST_DAY_TIME, firstDayTime);
		rst.put(CalendarUtil.MONTH_LAST_DAY_TIME, lastDayTime);
		
		return rst;
	}
	
	//获取前一个月
	public static Map<String,String> getPreMonth(){
		Map<String,String> rst = new HashMap<String,String>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		month = month + 1;
		
		rst.put("year", year+"");
        if(month > 12){
        	rst.put("month", "12");
        }else{
        	rst.put("month", month+"");
        }
        return rst;
	}
	
	//获取前一个月
	public static Map<String,String> getPreMonth(Integer year,Integer month){
		Map<String,String> rst = new HashMap<String,String>();
		if(month == 1){
			rst.put("year", (year-1)+"");
			rst.put("month", "12");
		}else{
			rst.put("year", year+"");
			rst.put("month", (month-1)+"");
		}
        return rst;
	}
	
	//获取开始月份和结束月份中间的所有月份，包括开始月份和结束月份
	public static List<String> getBetweenMonths(String startMonth ,String endMonth){
		String[] startArr = startMonth.split("-");
		String[] endArr = endMonth.split("-");
		
		Integer startY = Integer.parseInt(startArr[0]);
		Integer startM = Integer.parseInt(startArr[1]);
		
		Integer endY = Integer.parseInt(endArr[0]);
		Integer endM = Integer.parseInt(endArr[1]);
		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(startY, startM - 1, 1, 0, 0);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(endY, endM - 1, 1, 0, 0);
		
		List<String> months = new ArrayList<String>();
		while(startCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()){
			months.add(DateUtilOld.getDateText(startCalendar.getTime(),"yyyy-MM"));
			startCalendar.add(Calendar.MONTH, 1);
		}
		return months;
	}
	
	//获取日期之间的天List formt = "yyyy-MM-dd"
	public static List<String> getBetweenDateStr(String startTime,String endTime){
		List<String> dateList = new ArrayList<String>();
		try {
			Long startM = DateUtilOld.COMMON.getTextDate(startTime).getTime();
			Long endM = DateUtilOld.COMMON.getTextDate(endTime).getTime();
			long result = (endM-startM) / (24 * 60 * 60*1000);
			String[] startTimeStr = startTime.split("-");
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.set(Integer.parseInt(startTimeStr[0]), Integer.parseInt(startTimeStr[1]) - 1, Integer.parseInt(startTimeStr[2]));
			startCalendar.add(Calendar.DATE,-1);
			for(int i = 0; i <= result ; i++){
				startCalendar.add(Calendar.DATE,1);
				dateList.add(DateUtilOld.COMMON.getDateText(startCalendar.getTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateList;
	}
	
	public static List<String> getBetweenDateStr(String startTime,String endTime,String format){
		List<String> dateList = new ArrayList<String>();
		try {
			Long startM = DateUtilOld.COMMON.getTextDate(startTime).getTime();
			Long endM = DateUtilOld.COMMON.getTextDate(endTime).getTime();
			long result = (endM-startM) / (24 * 60 * 60*1000);
			String[] startTimeStr = startTime.split("-");
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.set(Integer.parseInt(startTimeStr[0]), Integer.parseInt(startTimeStr[1]) - 1, Integer.parseInt(startTimeStr[2]));
			startCalendar.add(Calendar.DATE,-1);
			for(int i = 0; i <= result ; i++){
				startCalendar.add(Calendar.DATE,1);
				dateList.add(DateUtilOld.getDateText(startCalendar.getTime(),format));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateList;
	}
	
	//获取日期的前n个月的月份（闭区间）
	public static List<String> getPreNMonths(String curMonth ,Integer n){
		String[] curArr = curMonth.split("-");
		Integer curY = Integer.parseInt(curArr[0]);
		Integer curM = Integer.parseInt(curArr[1]);
		
		Calendar curCalendar = Calendar.getInstance();
		curCalendar.set(curY, curM - 1, 1, 0, 0);
		
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.set(curY, curM - 1 - n, 1, 0, 0);
		
		List<String> months = new ArrayList<String>();
		while(tmpCalendar.getTimeInMillis() <= curCalendar.getTimeInMillis()){
			months.add(DateUtilOld.getDateText(tmpCalendar.getTime(),"yyyy-MM"));
			tmpCalendar.add(Calendar.MONTH, 1);
		}
		return months;
	}
	
	//获取当前天
	public static String getToday(String format){
		Calendar calendar = Calendar.getInstance();
        return DateUtilOld.getDateText(calendar.getTime(), format);
	}
	
	//获取当前月的第一天
	public static String getFirstDay(String format){
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, month, 1);
        return DateUtilOld.getDateText(calendar.getTime(), format);
	}
	
	//获取当前月的最后一天
	public static String getLastDay(String format){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));//设置本月最大日期
        return DateUtilOld.getDateText(calendar.getTime(), format);
	}
	
	//获取当前月的第一天
	public static Date getFirstDate(){
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, month, 1);
        return calendar.getTime();
	}
	
	//获取当前月的最后一天
	public static Date getLastDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));//设置本月最大日期
        return calendar.getTime();
	}
	
	
	//日期的天是否一样；
	public static boolean isDayEqual(Date date1,Date date2){
		if(date1==null || date2 == null){
			return false;
		}
		String date1Str = DateUtilOld.COMPAT.getDateText(date1);
		String date2Str = DateUtilOld.COMPAT.getDateText(date2);
		return date1Str.equals(date2Str);
	}
	
	/**
	 * 时间间距是否为xx；
	 * 如果在 space 之内返回true；否则返回false
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param space 毫秒
	 * @return
	 */
	public static boolean isTimeSpace(Date startTime,Date endTime,long space){
		if (startTime == null || endTime == null) {
			return false;
		}
		return endTime.getTime() - startTime.getTime() <= space;
	}
	
	/**
	 * 时间间距是否在 startSpace 和 endSpace 之间
	 * @param startTime
	 * @param endTime
	 * @param startSpace
	 * @param endSpace
	 * @return
	 */
	public static boolean isTimeBetween(Date startTime,Date endTime,long startSpace,long endSpace){
		return endTime.getTime() - startTime.getTime() <= endSpace && 
				endTime.getTime() - startTime.getTime() >= startSpace;
	}
	
	public static boolean isTimeBetween(Date time,Date startTime,Date endTime){
		return time.getTime() >= startTime.getTime() && time.getTime() <= endTime.getTime();
	}
	
	//获取日期之间年的距离
	public static Integer getYearSpace(Date startTime,Date endTime){
		Calendar start = Calendar.getInstance();
        start.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        return end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
	}
	
	//获取日期之间天的距离
	public static Integer getDaySpace(Date startTime,Date endTime){
		return (int)(endTime.getTime() - startTime.getTime())/(24*60*60*1000);
	}
	
	//获取当前日期 毫秒
	public static long getTimeInMillis(){
		Calendar now = Calendar.getInstance();
		return now.getTimeInMillis();
	}
	
	//获取当前日期 秒
	public static long getTimeInSeconds(){
		return getTimeInMillis()/1000L;
	}
	/**
	 * 
	*
	* @Title: getMonthDay 
	* @Description: TODO 获取前/后一月的日期 
	* @param @param date
	* @param @param operation
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getMonthDay(String date,String operation){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int oper=30;
		if(operation.equals("before")||operation=="before"){
			oper=-30;
		}
		try {
			c.setTime(sdf.parse(date));
	        c.add(Calendar.DATE, oper);
	        Date d = c.getTime();
			String day = sdf.format(d);
			return day;
		} catch (ParseException e) {
			e.getStackTrace();
		}
		return "";
	}
	/**
	 * 
	*
	* @Title: getWeekDay 
	* @Description: TODO 获取前/后一周的日期 
	* @param @param date
	* @param @param operation before或after
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getWeekDay(String date,String operation){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int oper=7;
		if(operation.equals("before")||operation=="before"){
			oper=-7;
		}
		try {
			c.setTime(sdf.parse(date));
	        c.add(Calendar.DATE, oper);
	        Date d = c.getTime();
			String day = sdf.format(d);
			return day;
		} catch (ParseException e) {
			e.getStackTrace();
		}
		return "";
	}
	/**
	 * 
	*
	* @Title: getDay 
	* @Description: TODO 获取前/后一天的日期 
	* @param @param date
	* @param @param operation
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getDay(String date,String operation){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int oper=1;
		if(operation.equals("before")||operation=="before"){
			oper=-1;
		}
		try {
			c.setTime(sdf.parse(date));
	        c.add(Calendar.DATE, oper);
	        Date d = c.getTime();
			String day = sdf.format(d);
			return day;
		} catch (ParseException e) {
			e.getStackTrace();
		}
		return "";
	}
	/**
	 * 获取某一天的前一天或后一天
	 * @param date
	 * @param oper
	 * @return
	 */
	public static String getDay(String date,int oper){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			c.setTime(sdf.parse(date));
	        c.add(Calendar.DATE, oper);
	        Date d = c.getTime();
			String day = sdf.format(d);
			return day;
		} catch (ParseException e) {
			e.getStackTrace();
		}
		return "";
	}
	/**
	 * 获取两个时间点的间隔天数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int dayDiff(String beginDate,String endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long from;
		long to;
		try {
			
			from = sdf.parse(beginDate).getTime();
			to = sdf.parse(endDate).getTime();  
			int days = (int) ((to - from)/(1000 * 60 * 60 * 24));
			return days;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static void main(String[] args){
		/*String startTime = "2009-12-14";
		String endTime = "2010-1-14";
		List<String> list = getBetweenDateStr(startTime,endTime);
		for(String s : list){
			System.out.println(s);
		}*/
		/*String nextday = getNextDay("yyyy-MM-dd");
        String xday = getPreXDay(6,"yyyy-MM-dd");
		System.out.println(nextday);
		System.out.println(xday);
		System.out.println(getFirstDay("yyyy-MM-dd"));
		System.out.println(getLastDay("yyyy-MM-dd"));*/
		
		try {
			Date startTime = DateUtilOld.COMMON.getTextDate("2015-06-03 00:00:00");
			Date endTime = DateUtilOld.COMMON_FULL.getTextDate("2015-06-03 23:59:59");
			System.out.println(isTimeBetween(new Date(),startTime,endTime));
			
			List<String> month = getPreNMonths("2015-06",11);
			for(String s : month){
				System.out.println(s);
			}
			
			String str = DateUtilOld.COMMON_FULL.getDateText(getPreNDayEnd(new Date(),1));
			System.out.println(str);
			
			List<String> days = getBetweenDateStr("2015-03-09","2015-06-15","MM/dd");
			for(String s : days){
				System.out.println(s);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
