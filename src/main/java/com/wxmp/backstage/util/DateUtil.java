/**
 * 
 */
package com.wxmp.backstage.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.wxmp.backstage.common.I18n;

/**
 * 功能：时间处理工具类
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class DateUtil {
	
	public static final long SECOND = 1000L;
	public static final long MINUTE = 60000L;
	public static final long HOUR = 3600000L;
	public static final long DAY = 86400000L;
	public static final long WEEK = 604800000L;
	public static final String YEAR="year";
	public static final String MONTH="month";
	public static final String DATE="date";
	public static final String HOURS="hours";
	public static final String MINUTES="minutes";
	public static final String SECONDS="seconds";
	
	/**
	 * 获得昨天的时间
	 * @return
	 */
	public static Date getYestoday(){
		return truncate(getDateAgo(1),Calendar.DATE);
	}
	
	/**
	 * 获得前days天的这个时候
	 * @param days
	 * @return
	 */
	public static Date getDateAgo(int days){
		return getDateAgo(new Date(),days);
	}
	
	/**
	 * 获得某时间之前days天的时间
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getDateAgo(Date date,int days){
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.DATE,-days);
		return c.getTime();
	}
	/**
	 * 获得days天后的时间
	 * @param days
	 * @return
	 */
	public static Date getDateAfter(int days){
		return getDateAgo(new Date(),-days);
	}
	/**
	 * 获得某个时间之后days天的时间
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getDateAfter(Date date,int days){
		return getDateAgo(date,-days);
	}
	
	/**
	 * 获得本周第一天
	 * @return
	 */
	public static Date getFirstDayOfThisWeek() {
		
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_WEEK,2);
		return truncate(c.getTime(),Calendar.DATE);
	}
	
	/**
	 * 获得本周最后一天
	 * @return
	 */
	public static Date getLastDayOfThisWeek() {
		
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_WEEK,7);
		c.add(Calendar.DATE, 1);
		return truncate(c.getTime(),Calendar.DATE);
	}
	
	/**
	 * 获得本月第一天
	 * @return
	 */
	public static Date getFirstDayOfThisMonth() {
		
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_MONTH,1);
		return truncate(c.getTime(),Calendar.DATE);
	}
	
	/**
	 * 获得本月最后一天
	 * @return
	 */
	public static Date getLastDayOfThisMonth() {
		
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_MONTH,1);
		c.add(Calendar.DATE,-1);
		c.add(Calendar.MONTH, 1);
		return truncate(c.getTime(),Calendar.DATE);
	}
	/**
	 * 获得上周的最后一天
	 * @return
	 */
	public static Date getLastDayOfLastWeek(){
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_WEEK,1);
		return truncate(c.getTime(),Calendar.DATE);
	}
	/**
	 * 获得上周的第一天
	 * @return
	 */
	public static Date getFirstDayOfLastWeek(){
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_WEEK,1);
		c.add(Calendar.DATE,-6);
		return truncate(c.getTime(),Calendar.DATE);
	}
	/**
	 * 获得上个月的最后一天
	 * @return
	 */
	public static Date getLastDayOfLastMonth(){
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_MONTH,1);
		c.add(Calendar.DATE,-1);
		return truncate(c.getTime(),Calendar.DATE);
	}
	/**
	 * 获得上个月第一天
	 * @return
	 */
	public static Date getFirstDayOfLastMonth(){
		Calendar c = new GregorianCalendar();
		c.add(Calendar.MONTH,-1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return truncate(c.getTime(),Calendar.DATE);
	}
	/**
	 * 查看两个日期是否是同一天
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameDate(Date d1,Date d2){
		return org.apache.commons.lang.time.DateUtils.isSameDay(d1,d2);
	}
	/**
	 * 查看d1是否在d2之后
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean afterDate(Date d1,Date d2){
		d1 = org.apache.commons.lang.time.DateUtils.truncate(d1,Calendar.DATE);
		d2 = org.apache.commons.lang.time.DateUtils.truncate(d2,Calendar.DATE);
		return d1.after(d2);
	}
	/**
	 * 查看d1是否在d2之前
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean beforeDate(Date d1,Date d2){
		d1 = org.apache.commons.lang.time.DateUtils.truncate(d1,Calendar.DATE);
		d2 = org.apache.commons.lang.time.DateUtils.truncate(d2,Calendar.DATE);
		return d1.before(d2);
	}
	/**
	 * 在两个日期之间
	 * @param d
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean isBetweenDate(Date d,Date from,Date to){
		return (DateUtil.afterDate(d, from) 
				&& DateUtil.beforeDate(d, to)) || (isSameDate(d,from) || isSameDate(d,to));
	}
	/**
	 * 获得某个日期的零值
	 * @param date
	 * @return
	 */
	public static Date getDate(Date date){
		date = (date==null?new Date():date);
		return truncate(date,Calendar.DATE);
	}
	/**
	 * 截断日期
	 * @param d1
	 * @param i java.util.Calendar.DATE
	 * @return
	 */
	public static Date truncate(Date d1,int i){
		return org.apache.commons.lang.time.DateUtils.truncate(d1,i);
	}
	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date,String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern,Locale.CHINESE);
		return df.format(date);
	}
	/**
	 * 解析时间字符串
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Date parse(String dateString,String format){
		return parse(dateString,format,Locale.CHINESE,TimeZone.getDefault());
	}
	
	/**
	 * 时间字符串转成毫秒
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Long dateStrToMillis(String dateString,String format){
		java.util.Date date=DateUtil.parse(dateString, format);
		return date.getTime();
	}
	
	/**
	 * 毫秒转成时间字符串
	 * @param millis
	 * @param format
	 * @return
	 */
	public static String millisToDateStr(Long millis,String format){
		java.util.Date date=DateUtil.getDateByCurrentTimeMillis(millis);
		return DateUtil.format(date, format);
	}
	
	
	/**
	 * 解析时间字符串
	 * @param dateString
	 * @param format
	 * @param locale
	 * @param timeZone
	 * @return
	 */
	public static Date parse(String dateString,String format,Locale locale,TimeZone timeZone){
		SimpleDateFormat formatter = (SimpleDateFormat)DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG, locale);
        formatter.applyPattern(format);
        formatter.setTimeZone(timeZone);
		Date date = null;
		try{
			date = formatter.parse(dateString);
		}catch(Exception e){
			date = null;
		}
		return date;
	}
	/**
	 * 获得当前时间的格式
	 * @param pattern
	 * @return 
	 */
	public static String getCurrentTime(String pattern){
		SimpleDateFormat ilIlI11I1 = new SimpleDateFormat(pattern);
		return ilIlI11I1.format(new Date());
	}
	/**
	 * 通过时间戳得到当前时间
	 * @param currentTimeMillis
	 * @return
	 */
	public static  Date getDateByCurrentTimeMillis(long currentTimeMillis){
		Timestamp now = new Timestamp(currentTimeMillis);
		return now;  
	}
	/**
	 * 获得两个时间的间隔天数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getIntervalDays(Date begin, Date end) {
		
		if(begin == null || end == null)return 0;
		long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
		return (int)between/(24*3600);
	}
	
	/**
	 * 获得两个时间的间隔小时
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getIntervalHours(Date begin,Date end){
		if(begin == null || end == null)return 0;
		long between=(end.getTime()-begin.getTime())/1000;
		return (int)between/3600;
	}
	
	/**
	 * 获得两个时间的间隔分钟
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getIntervalMinites(Date begin,Date end){
		if(begin == null || end == null)return 0;
		long between=(end.getTime()-begin.getTime())/1000;
		return (int)between/60;
	}

	/**
	 * 获得两个时间的间隔秒
	 * @param date
	 * @param date2
	 * @return
	 */
	public static int getIntervalSecond(Date begin,Date end) {
		
		if(begin == null || end == null)return 0;
		long between=(end.getTime()-begin.getTime())/1000;
		return (int)between;
	}

	/**
	 * 获得有好的时间显示
	 * @param second2
	 * @return
	 */
	public static String getFormatSecondText(int second2) {
		
		int hours = second2/(60*60);
		int minites = (second2%(60*60))/60;
		int seconds = (second2%60);
		StringBuilder sb = new StringBuilder();
		if(hours>0)sb.append(hours).append(I18n.getMessage("core.hours"));
		if(minites>0)sb.append(minites).append(I18n.getMessage("core.minites"));
		if(seconds>0)sb.append(seconds).append(I18n.getMessage("core.seconds"));
		return sb.toString();
	}

	/**
	 * 获得当前距离1970年的秒数
	 * @return
	 */
	public static int getCurrentTimeSeconds() {
		return (int)(System.currentTimeMillis() / 1000L);
	}
	
	/**
	 * 获得SQL时间
	 * @param date
	 * @return
	 */
	public static java.sql.Date getSqlDate(Date date){
		if(date==null)return null;
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 获得SQL时间戳
	 * @param date
	 * @return
	 */
	public static Timestamp getSqlTimestamp(Date date) {
		
		if(date==null)return null;
		return new java.sql.Timestamp(date.getTime());
	}
	
 }