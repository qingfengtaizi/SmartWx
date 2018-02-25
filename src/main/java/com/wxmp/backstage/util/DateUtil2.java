package com.wxmp.backstage.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DateUtil2 {
	
	/**
	 * 天的秒数
	 */
	public static final int DAY_SECONDS = 60 * 60 * 24;
	
	/**
	 * 年的秒数
	 */
	public static final int YEAR_SECONDS = 60 * 60 * 24 * 365;
	
	/**
     * 格式：年月日小时分钟秒
     */
	public static final String FULL_TIME = "yyyyMMddHHmmss";
	
	/**
     * 格式：年－月－日 小时：分钟：秒
     */
    public static final String FULL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 格式：年－月－日 小时：分钟
     */
    public static final String FULL_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    
    /**
     * 格式：年－月－日
     */
    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 格式：小时：分钟：秒
     */
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";
    
    /**
     * 年的加减
     */
    public static final int SUB_YEAR = Calendar.YEAR;

    /**
     * 月加减
     */
    public static final int SUB_MONTH = Calendar.MONTH;

    /**
     * 天的加减
     */
    public static final int SUB_DAY = Calendar.DATE;

    /**
     * 小时的加减
     */
    public static final int SUB_HOUR = Calendar.HOUR;

    /**
     * 分钟的加减
     */
    public static final int SUB_MINUTE = Calendar.MINUTE;

    /**
     * 秒的加减
     */
    public static final int SUB_SECOND = Calendar.SECOND;

    /**
     * 星期名称
     */
    public static final String WEEK_NAME[] = { "星期日", "星期一", "星期二", "星期三", "星期四",
            "星期五", "星期六" };

    public DateUtil2() {
    }

	/**
	 * 格式化日期 字符串型转换成日期型
	 * 
	 * @param strDate 	字符串型日期
	 * @param fm			格式化类型
	 * @return 日期型日期
	 */
	public static Date stringToDate(String strDate, String fm) {
		Date date = null;
		DateFormat df = new SimpleDateFormat(fm);
		try {
			date = df.parse(strDate);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期 日期型转换为字符串型
	 * 
	 * @param date 	日期型日期
	 * @param fm 	格式化类型
	 * @return 字符串型日期
	 */
	public static String dateToString(Date date, String fm) {
		String result = "";
		try {
			SimpleDateFormat dateFmt = new SimpleDateFormat(fm);
			result = dateFmt.format(date);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 获取当前时间的指定格式
     * 
     * @param format
     * @return
     */
    public static String getCurrDate(String format) {
        return dateToString(new Date(), format);
    }
    
    /**
     * 比较两个日期的年差
     * 
     * @param startDay
     * @param endDay
     * @return 年数
     */
    public static int subYear(String startDate, String endDate) {
        Date beforeDay = stringToDate(startDate, FULL_DATE_FORMAT);
        Date afterDay = stringToDate(endDate, FULL_DATE_FORMAT);
        return getYear(afterDay) - getYear(beforeDay);
    }
    
    /**
     * 比较两个日期的月差
     * @param startDate
     * @param endDate
     * @return 月数
     */
    public static int subMonth(String startDate, String endDate) {
    	int result = 0;    
    	Calendar c1 = Calendar.getInstance();    
        Calendar c2 = Calendar.getInstance();
        c1.setTime(DateUtil2.stringToDate(startDate, FULL_DATE_FORMAT));    
        c2.setTime(DateUtil2.stringToDate(endDate, FULL_DATE_FORMAT)); 
        while (!c1.after(c2)) {  
        	result++;
        	c1.add(Calendar.MONTH, 1);
        }
        result = result-1;
		return result;
	}
    
    /**
	 * 比较两个日期的日差
	 * 
	 * @param startDate 	yyyy-MM-dd 开始日期
	 * @param endDate 	yyyy-MM-dd 结束日期
	 * @return 日期天数
	 */
	public static long subDay(String startDate, String endDate) {
		Date dt1 = stringToDate(startDate, FULL_DATE_FORMAT);
		Date dt2 = stringToDate(endDate, FULL_DATE_FORMAT);
		long l = dt2.getTime() - dt1.getTime();
		return l / 60 / 60 / 1000 / 24;
	}
	
	/**
     * 计算日期时间差
     * 
     * @param startTime
     * @param endTime
     * @return 日期秒数
     */
    public static long subTime(String startTime, String endTime) {
        long first = stringToDate(startTime, FULL_TIME_FORMAT).getTime();
        long second = stringToDate(endTime, FULL_TIME_FORMAT).getTime();
        return (second - first) / 1000;
    }

	/**
	 * 字符串日期转换成中文格式日期
	 * 
	 * @param date	字符串日期 yyyy-MM-dd
	 * @return 	yyyy年MM月dd日
	 * @throws Exception
	 */
	public static String dateToCnDate(String date) {
		String result = "";
		String[] cnDate = new String[] { "〇", "一", "二", "三", "四", "五", "六",
				"七", "八", "九" };
		String ten = "十";
		String[] dateStr = date.split("-");
		for (int i = 0; i < dateStr.length; i++) {
			for (int j = 0; j < dateStr[i].length(); j++) {
				String charStr = dateStr[i];
				String str = String.valueOf(charStr.charAt(j));
				if (charStr.length() == 2) {
					if (charStr.equals("10")) {
						result += ten;
						break;
					} else {
						if (j == 0) {
							if (charStr.charAt(j) == '1')
								result += ten;
							else if (charStr.charAt(j) == '0')
								result += "";
							else
								result += cnDate[Integer.parseInt(str)] + ten;
						}
						if (j == 1) {
							if (charStr.charAt(j) == '0')
								result += "";
							else
								result += cnDate[Integer.parseInt(str)];
						}
					}
				} else {
					result += cnDate[Integer.parseInt(str)];
				}
			}
			if (i == 0) {
				result += "年";
				continue;
			}
			if (i == 1) {
				result += "月";
				continue;
			}
			if (i == 2) {
				result += "日";
				continue;
			}
		}
		return result;
	}
	
	/**
     * 获得某月的天数
     * 
     * @param year
     * @param month
     * @return int
     */
    public static int getDaysOfMonth(String year, String month) {
        int days = 0;
        if (month.equals("1") || month.equals("3") || month.equals("5")
                || month.equals("7") || month.equals("8") || month.equals("10")
                || month.equals("12")) {
            days = 31;
        } else if (month.equals("4") || month.equals("6") || month.equals("9")
                || month.equals("11")) {
            days = 30;
        } else {
            if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
                    || Integer.parseInt(year) % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }

        return days;
    }

    /**
     * 获取某年某月的天数
     * 
     * @param year
     * @param month
     * @return int
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前日期
     * 
     * @return int
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得当前月份
     * 
     * @return int
     */
    public static int getToMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得当前年份
     * 
     * @return int
     */
    public static int getToYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的天
     * 
     * @param date
     * @return int
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的年
     * 
     * @param date
     * @return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的月份，1-12
     * 
     * @param date
     * @return int
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取每月的第一周
     * @param year
     * @param month
     * @return int
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取每月的最后一周
     * @param year
     * @param month
     * @return int
     */
    public static int getLastWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, getDaysOfMonth(year, month));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据生日获取星座
     * 
     * @param birth yyyy-MM-dd
     * @return String
     */
    public static String getAstro(String birth) {
        if (!isDate(birth)) {
            birth = "2000" + birth;
        }
        if (!isDate(birth)) {
            return "";
        }
        int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1,
                birth.lastIndexOf("-")));
        int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
        String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
        int[] arr = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
        int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
        return s.substring(start, start + 2) + "座";
    }

    /**
     * 判断日期是否有效,包括闰年的情况
     * 
     * @param date yyyy-MM-dd
     * @return boolean
     */
    public static boolean isDate(String date) {
        StringBuffer reg = new StringBuffer(
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }

    /**
     * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
     * 
     * @param date 日期 为null时表示当天
     * @param month 相加(相减)的月数
     */
    public static Date nextMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
     * 
     * @param date 日期 为null时表示当天
     * @param month 相加(相减)的月数
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    /**
     * 取得距离今天 day 日的日期
     * @param day
     * @param format
     * @return String
     */
    public static String nextDay(int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, day);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 取得指定日期过 day 周后的日期 (当 day 为负数表示指定月之前)
     * 
     * @param date 日期 为null时表示当天
     */
    public static Date nextWeek(Date date, int week) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.WEEK_OF_MONTH, week);
        return cal.getTime();
    }
    
    /**
     * 根据日期获得下周一的日期
     * @param week
     * @return
     */
	public static Date getNextWeekFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, (9-cal.get(Calendar.DAY_OF_WEEK))%7);
        return cal.getTime();
    }

    /**
     * 获取当前的日期 yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return dateToString(new Date(), FULL_DATE_FORMAT);
    }
    /**
     * 获取当前的时间 yyyy-MM-dd HH:mm:ss 
     */
    public static String getCurrentTime() {
    	return dateToString(new Date(), FULL_TIME_FORMAT);
    }
    
    /**
     * 获得当前开始时间 yyyy-MM-dd 00:00:00
     * @param date 当前时间
     * @return Date
     */
    public static Date getDateStartTime(String date) {
    	Date d = stringToDate(date, FULL_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MINUTE, 00);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获得当前结束时间 yyyy-MM-dd 23:59:59
     * @param date 当前时间
     * @return Date
     */
    public static Date getDateEndTime(String date) {
    	Date d = stringToDate(date, FULL_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前小时开始时间
     * @param date
     * @return Date
     */
    public static Date getHourStartTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MINUTE, 00);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前小时结束时间
     * @param date
     * @return Date
     */
    public static Date getHourEndTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取昨天的日期
     * 
     * @return
     */
    public static String getYesterday() {
        return yesterday(FULL_DATE_FORMAT);
    }

    /**
     * 根据时间类型获取昨天的日期
     * @param format
     * @return
     */
    public static String yesterday(String format) {
        return dateToString(nextDay(new Date(), -1), format);
    }

    /**
     * 获取明天的日期
     */
    public static String getNextDay() {
        return dateToString(nextDay(new Date(), 1), FULL_DATE_FORMAT);
    }

    /**
     * 取得当前时间距离1900/1/1的天数
     * 
     * @return
     */
    public static int getDayNum() {
        int daynum = 0;
        GregorianCalendar gd = new GregorianCalendar();
        Date dt = gd.getTime();
        GregorianCalendar gd1 = new GregorianCalendar(1900, 1, 1);
        Date dt1 = gd1.getTime();
        daynum = (int) ((dt.getTime() - dt1.getTime()) / (24 * 60 * 60 * 1000));
        return daynum;
    }

    /**
     * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
     * 
     * @param day
     * @return
     */
    public static Date getDateByNum(int day) {
        GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
        Date date = gd.getTime();
        date = nextDay(date, day);
        return date;
    }

    /** 针对yyyy-MM-dd HH:mm:ss格式,显示yyyymmdd */
    public static String getYmdDateCN(String datestr) {
        if (datestr == null)
            return "";
        if (datestr.length() < 10)
            return "";
        StringBuffer buf = new StringBuffer();
        buf.append(datestr.substring(0, 4)).append(datestr.substring(5, 7))
                .append(datestr.substring(8, 10));
        return buf.toString();
    }

    /**
     * 获取本月第一天
     * 
     * @param format
     * @return
     */
    public static String getFirstDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 获取本月最后一天
     * 
     * @param format
     * @return
     */
    public static String getLastDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return dateToString(cal.getTime(), format);
    }
    
    /**
     * 获取日期的星期
     * @param date
     * @return String
     */
    public static String getWeekByDate(Date date) {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0)
        	week = 0;
        return WEEK_NAME[week];
    }
    
    /**
     * 获取timestamp的星期
     * @param time
     * @return String
     */
	public static String getWeekByTimestamp(Timestamp time) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(time.getTime());
    	
    	int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0)
        	week = 0;
        return WEEK_NAME[week];
    }
    
    /**
     * 获得日期列表
     * @param resultList	定义好的集合
     * @param startDate	开始日期
     * @param endDate		结束日期
     * @return
     */
    public static List<String> getDialectDate(String startDate, String endDate) {
    	Calendar c1 = Calendar.getInstance();    
        Calendar c2 = Calendar.getInstance();
        c1.setTime(DateUtil2.stringToDate(startDate, FULL_DATE_FORMAT));    
        c2.setTime(DateUtil2.stringToDate(endDate, FULL_DATE_FORMAT)); 
        List<String> resultList = new ArrayList<String>();
        while (!c1.after(c2)) {  
        	resultList.add(dateToString(c1.getTime(), FULL_DATE_FORMAT));
        	c1.add(Calendar.DATE, 1);
        }
		return resultList;
    }
    /**
     * 获得两个日期间的周 
     * @param startDate	开始日期
     * @param endDate		结束日期
     * @return 周开始时间和结束时间的list<map>
     */
    public static List<Map<String,String>> getDialectWeek(String startDate, String endDate){
    	Calendar c1 = Calendar.getInstance();    
        Calendar c2 = Calendar.getInstance();
        c1.setTime(stringToDate(startDate, FULL_DATE_FORMAT));    
        c2.setTime(stringToDate(endDate, FULL_DATE_FORMAT)); 
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		while (c1.compareTo(c2)<0){
			Map<String,String> map = new HashMap<String,String>();
			String date = dateToString(c1.getTime(), "yyyy-MM-dd 23:59:59:999");
			map.put("start", dateToString(getDateStartTime(dateToString(stringToDate(date, "yyyy-MM-dd HH:mm:ss:SSS"),"yyyy-MM-dd")),FULL_TIME_FORMAT));
			c1.roll(Calendar.DAY_OF_YEAR, -c1.get(Calendar.DAY_OF_WEEK) + 8);
			if(c1.compareTo(c2)>0){
				map.put("end", dateToString(getDateEndTime(dateToString(stringToDate(dateToString(c2.getTime(), "yyyy-MM-dd 23:59:59:999"), "yyyy-MM-dd HH:mm:ss:SSS"),"yyyy-MM-dd")),FULL_TIME_FORMAT));
				break;
			}
			date = dateToString(c1.getTime(), "yyyy-MM-dd 23:59:59:999");
			map.put("end", dateToString(getDateEndTime(dateToString(stringToDate(date, "yyyy-MM-dd HH:mm:ss:SSS"),"yyyy-MM-dd")),FULL_TIME_FORMAT));
			c1.setTime(addDateByDay(c1.getTime(),1));
			resultList.add(map);
		}
		Calendar lastEndTime =  Calendar.getInstance();
		lastEndTime.setTime(addDateByDay(stringToDate(resultList.get(resultList.size()-1).get("end"),FULL_DATE_FORMAT),1));
		if(lastEndTime.compareTo(c2)<0){
			Map<String,String> map = new HashMap<String,String>();
			map.put("start", dateToString(getDateStartTime(dateToString(stringToDate(dateToString(lastEndTime.getTime(), "yyyy-MM-dd 23:59:59:999"), "yyyy-MM-dd HH:mm:ss:SSS"),"yyyy-MM-dd")),FULL_TIME_FORMAT));
			map.put("end", dateToString(getDateEndTime(dateToString(stringToDate(dateToString(c2.getTime(), "yyyy-MM-dd 23:59:59:999"), "yyyy-MM-dd HH:mm:ss:SSS"),"yyyy-MM-dd")),FULL_TIME_FORMAT));
			resultList.add(map);
		}else if(lastEndTime.compareTo(c2)==0){
			Map<String,String> map = new HashMap<String,String>();
			map.put("start", dateToString(getDateStartTime(dateToString(stringToDate(dateToString(lastEndTime.getTime(), "yyyy-MM-dd 23:59:59:999"), "yyyy-MM-dd HH:mm:ss:SSS"),"yyyy-MM-dd")),FULL_TIME_FORMAT));
			map.put("end", dateToString(getDateEndTime(dateToString(stringToDate(dateToString(lastEndTime.getTime(), "yyyy-MM-dd 23:59:59:999"), "yyyy-MM-dd HH:mm:ss:SSS"),"yyyy-MM-dd")),FULL_TIME_FORMAT));
			resultList.add(map);
		}else{
			resultList.get(resultList.size()-1).put("end",dateToString(getDateEndTime(dateToString(stringToDate(dateToString(c2.getTime(), "yyyy-MM-dd 23:59:59:999"), "yyyy-MM-dd HH:mm:ss:SSS"),"yyyy-MM-dd")),FULL_TIME_FORMAT));
		}
    	return resultList;
    }
    /**
	 * 给日期累加指定天数并返回累加后的日期
	 * @param time 日期参数
	 * @param add_day 累加天数
	 * @return 累加后的日期
	 */
	public static Date addDateByDay(Date time, int add_day) {

		if (time == null) {
			return new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.DAY_OF_YEAR, add_day);
		return cal.getTime();
	}
	/**
	 * 返回某个时间的格式化串
	 * @author cui 
	 * @date Jun 26, 2009
	 * @param timeStamp 时间参数
	 * @param strFormat 格式化串
	 * @return 格式化串(如果参数为空返回&nbsp;)
	 */
	public static String getFormatDate(Timestamp timeStamp, String strFormat) {
		String strRet = null;
		if (timeStamp == null)
			return strRet = "&nbsp;";
		try {
			SimpleDateFormat simple = new SimpleDateFormat(strFormat);
			if (simple != null) {
				strRet = simple.format(timeStamp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRet;
	}
    public static void main(String[] args) {
    	String startDate = "2010-10-01";
    	String endDate = "2011-11-11";
    	int l = DateUtil2.subMonth(startDate, endDate);
    	System.out.println(l);
    	List<Map<String,String>> list = getDialectWeek("2011-06-01","2011-08-31");
		for(int i=0;i<list.size();i++){
			Map<String,String> map = list.get(i);
			System.out.println(map.get("start")+"-----"+map.get("end"));
			//System.out.println(list.get(i));
		}
    }
}
