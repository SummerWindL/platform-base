package com.platform.common.util;

import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtil.class);

	public static final String datetimeFormat = "yyyy-MM-dd HH:mm:ss";
	
	public static final String dateFormat = "yyyy-MM-dd";
	
	public static final String dateFormatx = "yyyyMMdd";
	
	public static final String timeFormat = "HH:mm:ss";
	
	public static final String timeMqFormat = "dd/MM/yyyy HH:mm:ss";
	
	public static final String datetimeFormatx = "yyyyMMddHHmmss";
	
	public static final String datetimeFormatyy = "yyMMddHHmmss";
	
	public static final String format_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	
	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentDatetime() {
		return DateTime.now().toString(datetimeFormat);
	}
	
	/**
	 * 格式化日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatDatex(Date date) {
		if (date == null) {
			return "";
		}
		return new DateTime(date).toString(dateFormatx);
	}
	
	public static String format_yyyyMMddHHmmssSSS(Date date) {
		if (date == null) {
			return "";
		}
		return new DateTime(date).toString(format_yyyyMMddHHmmssSSS);
	}
	
	public static Integer formatDatexInt(Date date) {
		if (date == null) {
			return null;
		}
		String dateStr = new DateTime(date).toString(dateFormatx);
		Integer dateInt = Integer.parseInt(dateStr);
		return dateInt;
	}
	
	public static String formatDatetime(Date date) {
		if (date == null) {
			return "";
		}
		return new DateTime(date).toString(datetimeFormat);
	}
	
	public static String formatDatetimex(Date date) {
		if (date == null) {
			return "";
		}
		return new DateTime(date).toString(datetimeFormatx);
	}

	public static String formatDatetimeyy(Date date) {
		if (date == null) {
			return "";
		}
		return new DateTime(date).toString(datetimeFormatyy);
	}
	
	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 * @param pattern 格式化模式
	 * @return
	 */
	public static String formatDatetime(Date date, String pattern) {
		return new DateTime(date).toString(pattern);
	}
	
	/**
	 * 获得当前日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String currentDate() {
		return DateTime.now().toString(dateFormat);
	}
	
	/**
	 * 格式化日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String formatDate(Date date) {
		return new DateTime(date).toString(dateFormat);
	}
	
	/**
	 * 获得当前时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentTime() {
		return DateTime.now().toString(timeFormat);
	}
	
	public static DateTime addDay(Date d, int num) {
		DateTime dateTime = new DateTime(d);
		dateTime = dateTime.plusDays(num);
		return dateTime;
	}

    public static DateTime addDays(DateTime dateTime, int num) {
        return dateTime.plusDays(num);
    }

	public static DateTime addMonth(Date d, int num) {
		DateTime dateTime = new DateTime(d);
		dateTime = dateTime.plusMonths(num);
		return dateTime;
	}

	public static DateTime addMinute(Date d, int num) {
		DateTime dateTime = new DateTime(d);
		dateTime = dateTime.plusMinutes(num);
		return dateTime;
	}
	
	/**
	 * 格式化时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatTime(Date date) {
		return new DateTime(date).toString(timeFormat);
	}
	
	public static DateTime now() {
		return DateTime.now();
	}
	
	/**
	 * 
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param datetime
	 * @return
	 */
	public static DateTime parseDatetime(String datetime) {
		try {
			DateTimeFormatter format = DateTimeFormat.forPattern(datetimeFormat);  
			DateTime date = DateTime.parse(datetime, format);
			return date;
		} catch (Exception e) {
			LOGGER.error(" parseDatetime error e ! ", e);
			e.printStackTrace();
			try {
				long ts = Long.parseLong(datetime);
				return new DateTime(ts);
			} catch (Exception e2) {
				e2.printStackTrace();
				LOGGER.error(" parseDatetime error e2 ! ", e2);
				return null;
			}
		}
	}
	
	/**
	 * 
	 * <p>
	 * 日期时间格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static DateTime parseDate(String date) {
		DateTimeFormatter format = DateTimeFormat.forPattern(dateFormat);  
		return DateTime.parse(date, format);
	}
	
	public static DateTime parseDatex(String date) {
		DateTimeFormatter format = DateTimeFormat.forPattern(dateFormatx);  
		return DateTime.parse(date, format);
	}
	
	public static DateTime parseDatex(int date) {
		return parseDatex(String.valueOf(date));
	}
	
	public static DateTime parse_yyyyMMddHHmmssSSS(String date) {
		DateTimeFormatter format = DateTimeFormat.forPattern(format_yyyyMMddHHmmssSSS);  
		return DateTime.parse(date, format);
	}

	public static DateTime parseDateMq(String date) {
		DateTimeFormatter format = DateTimeFormat.forPattern(timeMqFormat);  
		return DateTime.parse(date, format);
	}
	
	/**
	 *
	 * 
	 * @param datetime
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static DateTime parseDatetime(String datetime, String pattern) {
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(datetime, format);
	}
	
	/**
	 * 获取与当前日期差天数
	 * @param	
	 * @return  int
	 */
	public static int getCurrentDaysBetween(String date) {
		DateTime regDate = parseDatex(date);
		DateTime nowDate = now();
		return Days.daysBetween(regDate, nowDate).getDays();
	}
	
	public static int getCurrentDaysBetween(Date date) {
		DateTime regDate = new DateTime(date);
		DateTime nowDate = now();
		return Days.daysBetween(regDate, nowDate).getDays();
	}
	
	public static int getCurrentNaturalDaysBetween(Date date) {
		String datex = formatDatex(date);
		return getCurrentDaysBetween(datex);
	}
	
	public static int getCurrentYearsBetween(Date date) {
		DateTime regDate = new DateTime(date);
		DateTime nowDate = now();
		return Years.yearsBetween(regDate, nowDate).getYears();
	}
	
	public static Date plusHours(Date date, int plusHour) {
		DateTime dateTime = new DateTime(date);
		DateTime plusDateTime = dateTime.plusHours(plusHour);
		return plusDateTime.toDate();
	}
	
	public static Date plusDays(Date date, int plusDay) {
		DateTime dateTime = new DateTime(date);
		DateTime plusDateTime = dateTime.plusDays(plusDay);
		return plusDateTime.toDate();
	}
	
	public static Date minusHours(Date date, int minusHour) {
		DateTime dateTime = new DateTime(date);
		DateTime minusDateTime = dateTime.minusHours(minusHour);
		return minusDateTime.toDate();
	}
	
	public static Date minusDays(Date date, int minusDay) {
		DateTime dateTime = new DateTime(date);
		DateTime minusDateTime = dateTime.minusDays(minusDay);
		return minusDateTime.toDate();
	}

	public static Date minusYears(Date date, int minusYear) {
		DateTime dateTime = new DateTime(date);
		DateTime minusDateTime = dateTime.minusYears(minusYear);
		return minusDateTime.toDate();
	}
	
	public static void main(String[] args) {
		DateTime a = parseDatex("20171222");
		int res = getCurrentDaysBetween(a.toDate());
		System.out.println(res);
		System.out.println(a.minusHours(8));
	}
	
}
