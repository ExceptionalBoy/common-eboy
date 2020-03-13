package com.eboy.common.util;
 
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @ClassName: DateUtil
 * @Description: TODO(日期工具类)
 * @author ExceptionalBoy
 * @date 2018年8月29日
 *
 */
public class DateUtil {

	private static final Logger logger = Logger.getLogger(DateUtil.class);
	
	/**
	 * 前一周的第一天
	 */
	public static final Integer FIRST_DAY_OF_PRE_WEEK = 0;
	
	/**
	 * 前一周的最后一天
	 */
	public static final Integer LAST_DAY_OF_PRE_WEEK = 1;
	
	/**
	 * 本周的第一天
	 */
	public static final Integer FIRST_DAY_OF_CUR_WEEK = 2;
	
	/**
	 * 本周的最后一天
	 */
	public static final Integer LAST_DAY_OF_CUR_WEEK = 3;
	
	/**
	 * 前一月的第一天
	 */
	public static final Integer FIRST_DAY_OF_PRE_MONTH = 4;
	
	/**
	 * 前一月的最后一天
	 */
	public static final Integer LAST_DAY_OF_PRE_MONTH = 5;
	
	/**
	 * 本月的第一天
	 */
	public static final Integer FIRST_DAY_OF_CUR_MONTH = 6;
	
	/**
	 * 本月的最后一天
	 */
	public static final Integer LAST_DAY_OF_CUR_MONTH = 7;
	
	/**
	 * 前一年的第一天
	 */
	public static final Integer FIRST_DAY_OF_PRE_YEAR = 8;
	
	/**
	 * 前一年的最后一天
	 */
	public static final Integer LAST_DAY_OF_PRE_YEAR = 9;
	
	/**
	 * 本年的第一天
	 */
	public static final Integer FIRST_DAY_OF_CUR_YEAR = 10;
	
	/**
	 * 本年的最后一天
	 */
	public static final Integer LAST_DAY_OF_CUR_YEAR = 11;
	
	/**
	 * 第一天
	 */
	private static String firstDay;
	
	/**
	 * 最后一天
	 */
	private static String lastDay;
	
	/**
	 * 日期格式化对象
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateTimeFormatter shortDateFormatter = DateTimeFormat.forPattern("yyyyMMdd");
	public static final DateTimeFormatter shortDateFormatter1 = DateTimeFormat.forPattern("yyMMdd");
	public static final DateTimeFormatter shortDateFormatter2 = DateTimeFormat.forPattern("yyyy/MM/dd");
	public static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static final DateTimeFormatter dateYearMonthFormatter = DateTimeFormat.forPattern("yyyy-MM");
	public static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
	public static final DateTimeFormatter longDateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
	public static final DateTimeFormatter dateTimeFormatter2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm:ss");
	public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final long ONE_DAY = 24L * 60 * 60 * 1000;
	public static final long ONE_HOUR = 60L * 60 * 1000;
	public static final long ONE_MINUTE = 60L * 1000;
	
 
	/**
	 * 
	 * @Title: getDateByConditions 
	 * @Description: 根据参数日期及要获取的日期条件，获取相应的日期,格式为"yyyy-MM-dd" TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @param contition 
	 * @param @return 
	 *  String   
	 * @throws
	 * @date 2017年@return11月14日 上午10:59:44
	 */
	public static String getDateByConditions(Date date,int contition){
		String day = null;
		switch (contition) {
		case 0:
			day = firstDayOfPreWeek(date);
			break;
		case 1:
			day = lastDayOfPreWeek(date);
			break;
		case 2:
			day = firstDayOfCurWeek(date);
			break;
		case 3:
			day = lastDayOfCurWeek(date);
			break;
		case 4:
			day = firstDayOfPreMonth(date);
			break;
		case 5:
			day = lastDayOfPreMonth(date);
			break;
		case 6:
			day = firstDayOfCurMonth(date);
			break;
		case 7:
			day = lastDayOfCurMonth(date);
			break;
		case 8:
			day = firstDayOfPreYear(date);
			break;
		case 9:
			day = lastDayOfPreYear(date);
			break;
		case 10:
			day = firstDayOfCurYear(date);
			break;
		case 11:
			day = lastDayOfCurYear(date);
			break;
		default:
			break;
		}
		return day;
	}
 
	/**
	 * 
	 * @Title: firstDayOfPreWeek 
	 * @Description: 获取参数日期前一周的第一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:54:41
	 */
	private static String firstDayOfPreWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,-7);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		firstDay = sdf.format(cal.getTime());
		return firstDay;
	}
 
	/**
	 * 
	 * @Title: lastDayOfPreWeek 
	 * @Description: 获取参数日期前一周的最后一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:55:04
	 */
	private static String lastDayOfPreWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,-7);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, 6);
		lastDay = sdf.format(cal.getTime());
		return lastDay;
	}
 
	/**
	 * 
	 * @Title: firstDayOfCurWeek 
	 * @Description: 获取参数日期所在周的第一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:55:27
	 */
	private static String firstDayOfCurWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		firstDay = sdf.format(cal.getTime());
		return firstDay;
	}
 
	/**
	 * 
	 * @Title: lastDayOfCurWeek 
	 * @Description: 获取参数日期所在周的最后一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:55:50
	 */
	private static String lastDayOfCurWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, 6);
		lastDay = sdf.format(cal.getTime());
		return lastDay;
	}
 
	/**
	 * 
	 * @Title: firstDayOfPreMonth 
	 * @Description: 获取参数日期前一月的第一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:56:15
	 */
	private static String firstDayOfPreMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		Date theDate = calendar.getTime();
		// 上个月第一天
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		firstDay = sdf.format(gcLast.getTime());
		return firstDay;
	}
 
	/**
	 * 
	 * @Title: lastDayOfPreMonth 
	 * @Description: 获取参数日期前一月的最后一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:56:36
	 */
	private static String lastDayOfPreMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		lastDay = sdf.format(calendar.getTime());
		return lastDay;
	}
 
	/**
	 * 
	 * @Title: firstDayOfCurMonth 
	 * @Description: 获取参数日期所在月的第一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:57:02
	 */
	private static String firstDayOfCurMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date theDate = calendar.getTime();
		// 上个月第一天
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		firstDay = sdf.format(gcLast.getTime());
		return firstDay;
	}
 
	/**
	 * 
	 * @Title: lastDayOfCurMonth 
	 * @Description: 获取参数日期所在月的最后一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:57:29
	 */
	private static String lastDayOfCurMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		lastDay = sdf.format(calendar.getTime());
		return lastDay;
	}
 
	/**
	 * 
	 * @Title: firstDayOfPreYear 
	 * @Description: 获取参数日期前一年的第一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:58:02
	 */
	private static String firstDayOfPreYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		calendar.clear();
		calendar.set(Calendar.YEAR, year-1);
		firstDay = sdf.format(calendar.getTime());
		return firstDay;
	}
 
	/**
	 * 
	 * @Title: lastDayOfPreYear 
	 * @Description: 获取参数日期前一年的最后一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:58:25
	 */
	private static String lastDayOfPreYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.add(Calendar.DATE, -1);
		lastDay = sdf.format(calendar.getTime());
		return lastDay;
	}
 
	/**
	 * 
	 * @Title: firstDayOfCurYear 
	 * @Description: 获取参数日期所在年的第一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:58:52
	 */
	private static String firstDayOfCurYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		firstDay = sdf.format(calendar.getTime());
		return firstDay;
	}
 
	/**
	 * 
	 * @Title: lastDayOfCurYear 
	 * @Description: 获取参数日期所在年的最后一天TODO
	 * @author ExceptionalBoy 
	 * @param @param date
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年11月14日 上午10:59:16
	 */
	private static String lastDayOfCurYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		calendar.clear();
		calendar.set(Calendar.YEAR, year+1);
		calendar.add(Calendar.DATE, -1);
		lastDay = sdf.format(calendar.getTime());
		return lastDay;
	}


		public static DateTime parseDate(String dateStr, DateTimeFormatter formatter) {
			return parseDate(dateStr, formatter, true);

		}

		public static DateTime parseDate(String dateStr, DateTimeFormatter formatter, boolean isLog) {
			DateTime dateTime = null;
			try {
				dateTime = formatter.parseDateTime(dateStr);
			} catch (Exception e) {
				if (isLog) logger.error("Error in parse date: " + dateStr + "! ", e);
			}
			return dateTime;
		}

		public static String formateShortDate(long msec) {
			DateTime dateTime = new DateTime(msec);
			return formatDate(dateTime, dateFormatter);
		}

		/**
		 * 解析格式如"14:00"的时间
		 *
		 * @param timeStr
		 * @return
		 */
		public static DateTime parseTime(String timeStr) {
			return parseDate(timeStr, timeFormatter);
		}

		/**
		 * 解析格式如"2014-06-24"的日期
		 *
		 * @param dateStr
		 * @return
		 */
		public static DateTime parseDate(String dateStr) {
			return parseDate(dateStr, dateFormatter);
		}

		/**
		 * 解析格式如"2014-06"的日期
		 *
		 * @param dateStr
		 * @return
		 */
		public static DateTime parseYearMonthDate(String dateStr) {
			return parseDate(dateStr, dateYearMonthFormatter);
		}


		/**
		 * 解析格式如"20140624"的日期
		 *
		 * @param dateStr
		 * @return
		 */
		public static DateTime parseShortDate(String dateStr) {
			return parseDate(dateStr, shortDateFormatter);
		}

		/**
		 * 解析格式如"2014/06/24"的日期
		 *
		 * @param dateStr
		 * @return
		 */
		public static DateTime parseShortDate2(String dateStr) {
			return parseDate(dateStr, shortDateFormatter2);
		}

		/**
		 * 解析格式如"2014-06-24 08:30"的时间
		 *
		 * @param dateTimeStr
		 * @return
		 */
		public static DateTime parseDateTime(String dateTimeStr) {
			return parseDate(dateTimeStr, dateTimeFormatter);
		}

		/**
		 * 解析格式如"201411301630"的时间
		 *
		 * @param dateTimeStr
		 * @return
		 */
		public static DateTime parseLongDateTime(String dateTimeStr) {
			return parseDate(dateTimeStr, longDateTimeFormatter);
		}

		/**
		 * 解析格式如"2015-01-27 13:34:12"的时间
		 *
		 * @param dateTimeStr
		 * @return
		 */
		public static DateTime parseDateTime2(String dateTimeStr) {
			return parseDate(dateTimeStr, dateTimeFormatter2);
		}

		public static boolean isValidDateString(String dateStr) {
			return parseDate(dateStr, dateFormatter, false) != null;
		}

		/**
		 * HHmm -> HH:mm
		 */
		public static String transformTimeInt(String timeIntStr) {
			if (StringUtil.isEmpty(timeIntStr)) return "";
			if (timeIntStr.length() != 4 || timeIntStr.indexOf(":") != -1) return timeIntStr;
			return (new StringBuffer()).append(timeIntStr.substring(0, 2)).append(":").append(timeIntStr.substring(2)).toString();
		}

		/**
		 * yyyyMMdd -> yyyy-MM-dd
		 */
		public static String transformDateInt(String dateIntStr) {
			if (StringUtil.isEmpty(dateIntStr)) return "";
			if (dateIntStr.length() != 8) return dateIntStr;
			return dateIntStr.substring(0, 4) + "-" + dateIntStr.substring(4, 6) + "-" + dateIntStr.substring(6);
		}


		/**
		 * 时间差，返回差值分钟数
		 *
		 * @param d1
		 * @param d2
		 * @return
		 */
		public static int getTimeInterval(DateTime d1, DateTime d2) {
			return Minutes.minutesBetween(d1, d2).getMinutes();
		}

		/**
		 * 将string型date转long
		 *
		 * @param dateStr
		 * @return
		 */
		public static long parseString2TimeLong(String dateStr, DateTimeFormatter formatter) {
			long datelong = 0;
			try {
				DateTime dateTime = parseDate(dateStr, formatter);
				if (null != dateTime) {
					datelong = dateTime.getMillis();
				}
			} catch (Exception e) {
				logger.error("Error in parse date long: " + datelong + "! ", e);
			}
			return datelong;
		}


		/**
		 * 将long型date转string
		 *
		 * @param datelong
		 * @param formatter
		 * @return
		 */
		public static String parseLong2String(long datelong, DateTimeFormatter formatter) {
			DateTime dateTime = null;
			try {
				dateTime = new DateTime(datelong);
				if (null == formatter)
					formatter = dateFormatter;
				return dateTime.toString(formatter);

			} catch (Exception e) {
				logger.error("Error in parse date long: " + datelong + "! ", e);
			}
			return null;
		}


		/**
		 * 计算两个日期的之间相差天数、时、分、秒
		 */
		public static Map<String, Long> dateSubtract(String startTime, String endTime) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			Date d1 = null;
			Date d2 = null;
			Map<String, Long> map = new HashMap<>();
			try {
				d2 = sdf.parse(startTime);
				d2 = sdf.parse(endTime);
				long diff = d2.getTime() - d1.getTime();
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				map.put("days", diffDays);
				map.put("hours", diffHours);
				map.put("minutes", diffMinutes);
				map.put("seconds", diffSeconds);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return map;
		}

		/**
		 * 转换成格式【yyyy年MM月dd日HH:mm】
		 *
		 * @param date
		 * @return 2014年12月22日14:48
		 * @date 2014-12-22 下午2:48:23
		 */
		public static String getCNDate(Date date) {
			DateTime dt = new DateTime(date);
			String cnDate = dt.toString("yyyy年MM月dd日HH:mm");
			return cnDate;
		}

		/**
		 * 用指定格式，格式化java.util.Date
		 *
		 * @param date      java.util.Date
		 * @param formatter
		 * @return
		 */
		public static String formatDate(Date date, DateTimeFormatter formatter) {
			if (null == date) return "";
			DateTime datetime = new DateTime(date.getTime());
			return formatDate(datetime, formatter);
		}

		public static Date fromStringToDate(String time) {
			if (StringUtil.isBlank(time)) {
				return null;
			}
			Date date = null;
			try {
				date = dateFormat.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}

		public static String fromDateToString(Date time) {
			if (time == null) {
				return null;
			}
			return dateFormat.format(time);
		}

		/**
		 * 格式化java.util.Date 为 “yyyy-MM-dd HH:mm”
		 *
		 * @param date java.util.Date
		 * @return
		 */
		public static String formatDateTime(Date date) {
			return formatDate(date, dateTimeFormatter);
		}

		/**
		 * 格式化java.util.Date 为 “yyyy-MM-dd HH:mm:ss”
		 *
		 * @param date java.util.Date
		 * @return
		 */
		public static String formatDateTime2(Date date) {
			return formatDate(date, dateTimeFormatter2);
		}

		/**
		 * 格式化java.util.Date 为 “yyyyMMdd”
		 *
		 * @param date java.util.Date
		 * @return
		 */
		public static String formatshortDate(Date date) {
			return formatDate(date, shortDateFormatter);
		}

		/**
		 * 将日期转为YYMMdd格式的字符串
		 *
		 * @param date
		 * @return
		 */
		public static String formatshortDate1(Date date) {
			return formatDate(date, shortDateFormatter1);
		}

		/**
		 * 格式化java.util.Date 为 “yyyy/MM/dd”
		 *
		 * @param date java.util.Date
		 * @return
		 */
		public static String formatshortDate2(Date date) {
			return formatDate(date, shortDateFormatter2);
		}

		/**
		 * 格式化java.util.Date 为 “yyyy-MM-dd”
		 *
		 * @param date java.util.Date
		 * @return
		 */
		public static String formatDate(Date date) {
			return formatDate(date, dateFormatter);
		}

		public static String formatYeanMonthDate(Date date) {
			return formatDate(date, dateYearMonthFormatter);
		}

		/**
		 * 将日期转为YYYY-MM-dd格式的字符串
		 */
		public static String formatDate(DateTime datetime) {
			return formatDate(datetime, dateFormatter);
		}

		/**
		 * 将日期转为YYYY-MM-dd格式的字符串
		 */
		public static String formatShortDate(DateTime datetime) {
			return formatDate(datetime, shortDateFormatter);
		}

		/**
		 * 将日期转为YYYY/MM/dd格式的字符串
		 */
		public static String formatShortDate2(DateTime datetime) {
			return formatDate(datetime, shortDateFormatter2);
		}

		/**
		 * 将日期转为HH:mm:ss格式的字符串
		 */
		public static String formatTime(Date datetime) {
			return formatDate(datetime, timeFormatter);
		}

		/**
		 * 将日期转为指定格式的字符串
		 *
		 * @param datetime
		 * @param dateFormatter
		 * @return
		 */
		public static String formatDate(DateTime datetime, DateTimeFormatter dateFormatter) {
			return dateFormatter.print(datetime);
		}


		/**
		 * 计算date1和date2之间的跨天数 (默认取绝对值)
		 *
		 * @param date1 格式： yyyy-MM-dd
		 * @param date2 格式： yyyy-MM-dd
		 * @return
		 */
		public static int daysBetween(String date1, String date2) {
			return daysBetween(date1, date2, true);
		}

		/**
		 * 计算date1和date2之间的跨天数
		 *
		 * @param date1 格式： yyyy-MM-dd
		 * @param date2 格式： yyyy-MM-dd
		 * @param abs   是否对跨天数取绝对值
		 * @return
		 */
		public static int daysBetween(String date1, String date2, boolean abs) {
			if (StringUtil.isEmpty(date1) || StringUtil.isEmpty(date2)) {
				return 0;
			}
			try {
				DateTime d1 = parseDate(date1);
				DateTime d2 = parseDate(date2);
				long t1 = d1.getMillis();
				long t2 = d2.getMillis();
				long interval = t2 - t1;
				if (abs) interval = Math.abs(interval);
				return (int) (interval / (1000 * 60 * 60 * 24));
			} catch (Exception e) {
				return 0;
			}
		}

		/***
		 * 判断一个日期是否在2个日期直接，包含开始和结束日期。
		 * @param middleDate 中间日期
		 * @param dayStartDate 开始日期
		 * @param dayEndDate 结束日期
		 * @return true or false
		 */
		public static boolean dayBetweenDayAndDay(String middleDate, String dayStartDate, String dayEndDate) {
			boolean flag = false;
			if (StringUtil.isBlank(middleDate) || StringUtil.isBlank(dayStartDate) || StringUtil.isBlank(dayEndDate)) {
				return flag;
			}
			DateTime mDate = parseDate(middleDate);
			DateTime sDate = parseDate(dayStartDate);
			DateTime eDate = parseDate(dayEndDate);
			long t1 = mDate.getMillis();
			long t2 = sDate.getMillis();
			long t3 = eDate.getMillis();
			if (t1 >= t2 && t1 <= t3) {
				flag = true;
			}
			return flag;
		}

		/**
		 * 2015-01-25 -> (int)20150115
		 *
		 * @param date
		 * @return
		 */
		public static int getDateInt(String date) {
			return Integer.parseInt(date.replaceAll("-", ""));
		}

		/**
		 * 将短整形的日期(20150128) 转为 普通日期格式(2015-01-28)
		 *
		 * @param shortDate
		 * @return
		 */
		public static String shortDate2Normal(String shortDate) {
			if (null == shortDate || "".equals(shortDate.trim())) return null;
			try {
				DateTime datetime = parseDate(shortDate, shortDateFormatter);
				return formatDate(datetime, DateUtil.dateFormatter);
			} catch (Exception e) {
				logger.error("invalid input date format. input: "+ shortDate);
			}
			return null;
		}

		/**
		 * 当前日期时间字符串
		 * yyyy-MM-dd HH:mm
		 *
		 * @return
		 */
		public static String getCurrentDateTime() {
			DateTime dateTime = new DateTime();
			return dateTime.toString(dateTimeFormatter);
		}

		/**
		 * 当前日期时间字符串
		 * yyyy-MM-dd HH:mm:ss
		 *
		 * @return
		 */
		public static String getCurrentDateTime2() {
			DateTime dateTime = new DateTime();
			return dateTime.toString(dateTimeFormatter2);
		}

		/**
		 * 当前日期时间字符串
		 * yyyy-MM-dd
		 *
		 * @return
		 */
		public static String getCurrentDate() {
			DateTime dateTime = DateTime.now();
			return dateTime.toString(dateFormatter);
		}


		/**
		 * 获取类似于yyyy-MM-dd
		 *
		 * @param dateTime
		 * @return
		 */
		public static String getShortDate(DateTime dateTime) {
			return dateTime.toString(dateFormatter);
		}


		public static boolean isValidDataTimeHours(String dateTime) {
			try {
				if (StringUtil.isBlank(dateTime)) {
					return false;
				}
				String hoursStr = dateTime.substring(0, 2);
				int hours = Integer.parseInt(hoursStr);
				if (hours < 0 || hours > 23) {
					return false;
				}
				String minutesStr = dateTime.substring(dateTime.length() - 2);
				int minutes = Integer.parseInt(minutesStr);
				if (minutes < 0 || minutes > 59) {
					return false;
				}

			} catch (Exception e) {
				return false;
			}
			return true;
		}

		/**
		 * 格式化查询开始日期
		 *
		 * @param startDate
		 * @return
		 */
		public static String formatStartDate(String startDate) {
			if (StringUtil.isNotBlank(startDate)) {
				return startDate + " 00:00:00";
			}
			return startDate;
		}

		/**
		 * 格式化查询结束日期
		 *
		 * @param endDate
		 * @return
		 */
		public static String formatEndDate(String endDate) {
			if (StringUtil.isNotBlank(endDate)) {
				return endDate + " 23:59:59";
			}
			return endDate;
		}

		public static String format10minNearEndDate(String endDate) {
			if (StringUtil.isNotBlank(endDate)) {
				return endDate + " 23:50:00";
			}
			return endDate;
		}

		/**
		 * 获取当前时间 后两两个月的日期
		 *
		 * @param monthNum 后几个月
		 * @return
		 */
		public static String twoMonthLaterDate(Integer monthNum) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, monthNum);
			return formatDate(c.getTime(), dateFormatter);
		}

		/**
		 * 判断两个时间大小
		 *
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static int compareDate(String date1, String date2) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dt1 = df.parse(date1);
				Date dt2 = df.parse(date2);
				if (dt1.getTime() > dt2.getTime()) {
					return -1; //date1 在 date2 后
				} else if (dt1.getTime() < dt2.getTime()) {
					return 1; //date1 在 date2 前
				} else if (dt1.getTime() == dt2.getTime()) {
					return 2; //date1 在 date2 相等
				} else {
					return 0;//异常了
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return 0;
		}


		/**
		 * 获取传入日期的差值 比如获取传入日期前四天的日期 day=-4即可,反之亦然
		 *
		 * @param date
		 * @param day
		 * @return
		 */
		public static String getSomeDayDate(String date, String day) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parseDate(date).toDate());
			if (StringUtil.isNotBlank(day)) {
				calendar.add(Calendar.DATE, Integer.valueOf(day));
			} else {
				calendar.add(Calendar.DATE, 0);
			}
			return formatDate(calendar.getTime(), dateFormatter);
		}

		public static int getDateInterval(String startDate, String endDate)
				throws RuntimeException {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			Date date1, date2;
			try {
				date1 = formatter.parse(startDate);
				date2 = formatter.parse(endDate);
				cal1.setTime(date1);
				cal2.setTime(date2);
				return cal2.get(Calendar.DAY_OF_YEAR)
						- cal1.get(Calendar.DAY_OF_YEAR);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * 根据传入日期获取指定月数（前后）时间
		 *
		 * @param date
		 * @param monthNum <0表示之前 ==0表示当天 >0表示之后
		 * @return
		 */
		public static String getMonthWithNum(Date date, Integer monthNum) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, monthNum);
			return formatDateTime2(c.getTime());
		}

		/**
		 * 根据传入日期获取指定天数（前后）时间
		 *
		 * @param date
		 * @param num  <0表示之前 ==0表示当天 >0表示之后
		 * @return
		 */
		public static String getDateWithNum(Date date, int num) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + num);
			return formatDateTime2(calendar.getTime());
		}

		/**
		 * 根据传入日期获取指定小时（前后）时间
		 *
		 * @param date
		 * @param hourNum <0表示之前 ==0表示当天 >0表示之后
		 * @return
		 */
		public static String getHourWithNum(Date date, Integer hourNum) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR_OF_DAY, hourNum);
			return formatDateTime2(c.getTime());
		}

		/**
		 * 获取上/下个月的第N天 几号
		 *
		 * @param difmonths “-” 取上几个月
		 * @param difdays   几号
		 * @return
		 * @throws Exception
		 */
		public static String getBfOrAfMonthDate(int difmonths, int difdays) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, difmonths);
			calendar.set(Calendar.DAY_OF_MONTH, difdays);
			return formatDate(calendar.getTime(), dateFormatter);
		}

		/**
		 * 获取上个月的最后一天
		 *
		 * @return
		 * @throws Exception
		 */
		public static String getBfLastDayMonthDate() {
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH);
			calendar.set(Calendar.MONTH, month - 1);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			return formatDate(calendar.getTime(), dateFormatter);
		}

		public static String getBeforeFirstMonthdate() {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			return formatDate(calendar.getTime(), dateFormatter);
		}

		public static long getDiffInTwoTime(String time1, String time2) {
			Date date1 = DateUtil.fromStringToDate(time1);
			Date date2 = DateUtil.fromStringToDate(time2);
			long l = date1.getTime() - date2.getTime();
			return l;
		}

		/**
		 * 取得指定日期所在月
		 *
		 * @param date 指定日期
		 * @return
		 */
		private static int getMonth(Date date) {
			Calendar c = new GregorianCalendar();
			c.setTime(date);
			return c.get(Calendar.MONTH) + 1;
		}

		/**
		 * 取得指定日期的当前天
		 *
		 * @param date 指定日期
		 * @return
		 */
		private static int getDate(Date date) {
			Calendar c = new GregorianCalendar();
			c.setTime(date);
			return c.get(Calendar.DATE);
		}

		public static String getTimeOfDay(int hourOfDay,int minute,int second,int millisecond) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(new Date().getTime());
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, second);
			calendar.set(Calendar.MILLISECOND, millisecond);
			return DateUtil.formatDateTime2(new Date(calendar.getTimeInMillis()));
		}
		/**
		 * 获取指定月的最后一天
		 *
		 * @return
		 * @throws Exception
		 */
		public static String getBfLastDayMonthDate(String date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parseYearMonthDate(date).toDate());
			int month = calendar.get(Calendar.MONTH);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			return formatDate(calendar.getTime(), dateFormatter);
		}

}
