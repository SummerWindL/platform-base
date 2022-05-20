package com.platform.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.Calendar;
import java.util.Date;

import com.platform.common.util.StringUtil;
import org.apache.commons.lang3.time.DateUtils;


/**
 * 日期处理工具类
 * @version 1.0 2018年1月16日
 * @see DateUtil
 * @since 1.0
 */
public class DateUtil extends DateUtils {

    /**
     * 中文日期格式 :"yyyy年MM月dd日"
     */
    public static final String DEFAULT_DATE_FORMAT_CN = "yyyy年MM月dd日";

    /**
     * 中文日期格式 :"yyyy年MM月"
     */
    public static final String DEFAULT_MONTH_DATE_FORMAT_CN = "yyyy年MM月";

    /**
     * 中文日期格式:"yyyy年M月"
     */
    public static final String MONTH_DATE_FORMAT_CN = "yyyy年M月";

    /**
     * 中文日期格式:"yyyy年M月d日"
     */
    public static final String ABB_DATE_FORMAT_CN = "yyyy年M月d日";

    /**
     * 标准的日期格式 :"yyyy-MM-dd"
     */
    public static final String DEFAULT_DATE_FORMAT_EN = "yyyy-MM-dd";

    /**
     * 标准的日期格式 :"yyyy-MM"
     */
    public static final String MONTH_DATE_FORMAT_EN = "yyyy-MM";

    /**
     * 到毫秒日期格式 :"yyyyMMddHHmmssSSS"
     */
    public static final String DATETIME_FORMAT_WITH_SSS_YEAR1 = "yyyyMMddHHmmssSSS";

    /**
     * 到毫秒日期格式 :"yyMMddHHmmssSSS"
     */
    public static final String DATETIME_FORMAT_WITH_SSS_YEAR2 = "yyMMddHHmmssSSS";

    /**
     * "yyyy-MM-dd HH:mm:ss"
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * "yyyy_MM_dd_HH_mm_ss_SSS" 用做文件名
     */
    public static final String DEFAULT_DATETIME_FORMAT_SSS = "yyyy_MM_dd_HH_mm_ss_SSS";

    /**
     * "yyyyMMdd"
     */
    public static final String DEFAULT_OTHER_FORMAT = "yyyyMMdd";

    /**
     * "yyyy/MM/dd"
     */
    public static final String DEFAULT_ANOTHER_FORMAT = "yyyy/MM/dd";

    /**
     * dd-yy月-yyyy
     */
    public static final String DEFAULT_FOREI_FORMAT = "dd-MM月-yyyy";

    /**
     * "yyyyMM"
     */
    public static final String MONTH_OTHER_FORMAT = "yyyyMM";

    /**
     * "yyyy"
     */
    public static final String YEAR_DATE_FORMAT_EN = "yyyy";

    /**
     * "yyyyMMdd HH:mm:ss"
     */
    public static final String DEFAULT_DATETIME_FORMAT_ODS = "yyyyMMdd HH:mm:ss";

    /**
     * 期间类型
     */
    public enum DurationType {
        /**
         *
         */
        秒,
        /**
         *
         */
        分钟,
        /**
         *
         */
        小时,
        /**
         *
         */
        天,
        /**
         *
         */
        月,
        /**
         *
         */
        年;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter DATETIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);

    /**
     * yyyy-MM-dd
     */
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT_EN);

    /**
     * 取得当前日期
     *
     * @return java.util.Date
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 取得当前日期
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return format(new Date());
    }

    /**
     * 取得当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     * @see
     * @since 1.0
     */
    public static String getCurrentDateTime() {
        return formatDateTime(now());
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前的微秒数
     *
     * @return
     */
    public static long getClockMillis() {
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();
    }

    /**
     * 返回当前时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getDateTimestamp() {
        return getLocalDateTime().format(DATETIMESTAMP_FORMATTER);
    }

    /**
     * 返回当前时间yyyy-MM-dd
     *
     * @return
     */
    public static String getDate() {
        return getLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 返回当前系统时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 返回当前系统时间
     */
    public static String getDateTime() {
        return getLocalDateTime().format(DATETIME_FORMATTER);
    }

    /**
     * 获取当月第一天 yyyy-MM-dd
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        return getLocalDate().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 获取本月最后一天 yyyy-MM-dd
     *
     * @return
     */
    public static String getLastDayOfMonth() {
        LocalDate localDate = getLocalDate();
        return localDate.withDayOfMonth(localDate.lengthOfMonth()).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * yyyy-MM-dd字符串转LocalDate
     *
     * @param dateString
     * @return
     */
    public static LocalDate parseLocalDate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * yyyy-MM-dd 增加日期
     *
     * @param date
     * @param day
     * @return
     */
    public static String plusDays(String date, int days) {
        LocalDate localDate = parseLocalDate(date);
        return localDate.plusDays(days).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 计算两个日期之间月中的日相相差的天数，可以用来判断是否为每个月同一天<br>
     * 如： 2017-10-09和2018-02-09相差为0, 2017-10-09和2018-02-19相差为10
     *
     * @param startDate
     *            较小的时间 yyyy-MM-dd
     * @param endDate
     *            较大的时间 yyyy-MM-dd
     * @return 相差天数
     */
    public static int dateCompareTo(String startDate, String endDate) {
        LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
        Period period = Period.between(startLocalDate, endLocalDate);
        return period.getDays();
    }

    /**
     * 将日期字符串转为日期形式
     *
     * @param dateString 日期字符串
     * @return 日期
     */
    public static Date parse(String dateString) {
        // 外围系统有用C开发的，他们的日期如果是空的话给的是0
        if (StringUtil.isEmpty(dateString) || StringUtil.isEmpty(dateString.trim())
                || "0".equals(dateString.trim()) || "00000000".equals(dateString.trim())
                || "null".equals(dateString.trim())) {
            return null;
        }

        try {
            return parseDate(dateString,
                    new String[] {DEFAULT_DATE_FORMAT_CN, DEFAULT_DATE_FORMAT_EN,
                            DATETIME_FORMAT_WITH_SSS_YEAR1, DEFAULT_DATETIME_FORMAT, DEFAULT_OTHER_FORMAT,
                            DEFAULT_ANOTHER_FORMAT, "yyyy-MM","yyyyMM",DEFAULT_FOREI_FORMAT,"yyyy-MM-dd HH:mm:ss.0"});
        }
        catch (ParseException e) {
            throw new RuntimeException("Date ParseException");
        }
    }

    /**
     * 根据格式化字符串格式化日期
     *
     * @param date 格式化前日期
     * @return 格式化后日期字符串（yyyy-MM-dd）
     */
    public static String format(Date date) {
        return format(date, DEFAULT_DATE_FORMAT_EN);
    }

    /**
     * 根据格式化字符串格式化日期
     *
     * @param date 格式化前日期
     * @return 格式化后日期字符串（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return format(date, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 根据格式化字符串格式化日期
     *
     * @param date 格式化前日期
     * @param pattern 格式化字符串,如：yyyy-MM-dd
     * @return 格式化后日期字符串
     */
    public static String format(Date date, String pattern) {
        String s = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            s = sdf.format(date);
        }
        return s;
    }

    /**
     * 根据两个日期，取得相隔的天数
     *
     * @param d1 开始日期
     * @param d2 结束日期
     * @return 天数
     */
    public static long getBetweenDayNumber(Date d1, Date d2) {
        return getBetweenDayNumber(format(d1), format(d2));
    }

    /**
     * java.util.Date 转为 java.time.LocalDateTime <br>
     * 日期装java8日期对象
     * @param date 日期
     * @return LocalDate
     */
    private static LocalDateTime dateToLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 比较两个日期的大小，精确到天数
     *
     * @param d1 日期1
     * @param d2 日期2
     * @return 比较结果
     */
    public static int compareTo(Date d1, Date d2) {
        String date1 = format(d1);
        String date2 = format(d2);
        return date1.compareTo(date2);
    }

    /**
     * 根据两个日期，取得相隔的天数
     *
     * @param date1 开始日期
     * @param date2 结束日期
     * @return 相隔的天数
     */
    public static long getBetweenDayNumber(String date1, String date2) {
        return getBetweenDate(date1, date2, DurationType.天);
    }

    /**
     * 得到两个日期的间隔时间，可以是年月日，时分秒
     *
     * @param begin 开始日期
     * @param end 结束日期
     * @param type 年月日，时分秒
     * @return 间隔
     */
    public static long getBetweenDate(String begin, String end, DurationType type) {
        // 根据兩個个日期，取得相隔的天数
        LocalDateTime beginDate = dateToLocalDate(DateUtil.parse(begin));
        LocalDateTime endDate = dateToLocalDate(DateUtil.parse(end));
        // 计算区间天数
        long result = -1;
        switch (type) {
            case 秒:
                result = ChronoUnit.SECONDS.between(beginDate, endDate);
                break;
            case 分钟:
                result = ChronoUnit.MINUTES.between(beginDate, endDate);
                break;
            case 小时:
                result = ChronoUnit.HOURS.between(beginDate, endDate);
                break;
            case 天:
                result = ChronoUnit.DAYS.between(beginDate, endDate);
                break;
            case 月:
                result = ChronoUnit.MONTHS.between(beginDate, endDate);
                break;
            case 年:
                result = ChronoUnit.YEARS.between(beginDate, endDate);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 说明：比较两个日期大小,Calendar格式<br>
     * 备注：cal1 &gt; cal2 返回1, cal1 = cal2 返回0, cal1 &lt; cal2 返回 -1<br>
     *
     * @param cal1 日期1
     * @param cal2 日期2
     * @return 比较结果
     */
    public static int compareDate(Calendar cal1, Calendar cal2) {
        if (cal1.getTimeInMillis() > cal2.getTimeInMillis()) {
            return 1;
        }
        else if (cal1.getTimeInMillis() == cal2.getTimeInMillis()) {
            return 0;
        }
        else {
            return -1;
        }
    }

    /**
     * 说明：比较两个日期大小,Date格式<br>
     * 备注：date1 &gt; date2 返回1 ,date1 = date2 返回0 , date1 &lt; date2 返回 -1<br>
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 比较结果
     */
    public static int compareDate(Date date1, Date date2) {
        int result;
        if (date1 == null && date2 == null) {
            result = 0;
        }
        else if (date1 != null && date2 == null) {
            result = 1;
        }
        else if (date1 == null && date2 != null) {
            result = -1;
        }
        else {
//            String date1Change = DateUtil.format(date1);
//            String date2Change = DateUtil.format(date2);
//            date1 = DateUtil.parse(date1Change);
//            date2 = DateUtil.parse(date2Change);
            result = date1.compareTo(date2);
        }

        return result;
    }

    /**
     * 说明：比较两个日期大小<br>
     * 备注：d1 &gt; d2 返回1 ,d1 = d2 返回0 , d1 &lt; d2 返回 -1<br>
     *
     * @param d1 日期1
     * @param d2 日期2
     * @return 比较结果
     */
    public static int compareDate(String d1, String d2) {
        Date date1 = null;
        Date date2 = null;
        date1 = parse(d1);
        date2 = parse(d2);
        return compareDate(date1, date2);
    }

    /**
     * 说明：比较两个日期大小,YYYY-MM-DD格式<br>
     * 备注：d1 &gt; d2 返回1 ,d1 = d2 返回0 , d1 &lt; d2 返回 -1<br>
     *
     * @param t1 时间1
     * @param t2 时间2
     * @return 比较结果
     * @throws ParseException 格式转化异常
     */
    public static int compareTime(String t1, String t2)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        Date date1 = sdf.parse(t1);
        Date date2 = sdf.parse(t2);
        return compareDate(date1, date2);
    }

    /**
     * 判断两个日期是否相等，如果都是null，则返回true。
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 比较结果
     */
    public static boolean isSameDate(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return date1.equals(date2);
        }
        return date1 == date2;
    }

    /**
     * 主要功能:获取月份 注意事项:无
     *
     * @param date 格式：yyyy-MM-dd
     * @return 月份
     */
    public static int getMonth(String date) {
        LocalDate dateTime = LocalDate.parse(date);
        return dateTime.getMonthValue();
    }

    /**
     * 说明：获取年 备注：
     *
     * @param date 格式：yyyy-MM-dd
     * @return 年份
     */
    public static int getYear(String date) {
        LocalDate dateTime = LocalDate.parse(date);
        return dateTime.getYear();
    }

    /**
     * 说明：获取年字符串 备注：
     *
     * @param date 格式：yyyy-MM-dd
     * @return 年份
     */
    public static String getYearStr(String date) {
        int year = getYear(date);
        return String.valueOf(year);
    }

    /**
     * 说明：获取日 备注：
     *
     * @param date 格式：yyyy-MM-dd
     * @return 天数
     */
    public static int getDayOfMonth(String date) {
        LocalDate dateTime = LocalDate.parse(date);
        return dateTime.getDayOfMonth();
    }

    /**
     * 说明：获取日 备注：
     *
     * @param date 格式：yyyy-MM-dd
     * @return 天数
     */
    public static int getDayOfYear(String date) {
        LocalDate dateTime = LocalDate.parse(date);
        return dateTime.getDayOfYear();
    }

    /**
     * 说明：将java.util.Date转为java.time.LocalDate
     *
     * @param date 格式：yyyy-MM-dd
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(String date) {
        return LocalDate.parse(date);
    }

    /**
     * 说明：将java.util.Date转为java.time.LocalDate
     *
     * @param date 日期
     * @param zone 时区
     * @return LocalDate
     */
    public static LocalDate dateToLocal(Date date, ZoneId zone) {
        return date.toInstant().atZone(zone).toLocalDate();
    }

    /**
     * 说明：将java.util.Date转为java.time.LocalDate
     *
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDate dateToLocal(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 主要功能:计算一个日期加上day天后的日期 date为“yyyy-MM-dd”的形式 注意事项:无
     *
     * @param date 初始日期
     * @param day 天数
     * @return 日期
     */
    public static String addDay(String date, int day) {
        LocalDate dateTime = LocalDate.parse(date);
        dateTime = dateTime.plusDays(day);
        return dateTime.toString();
    }

    /**
     * 主要功能:计算一个日期加上day天后的日期 date
     *
     * @param date 初始日期
     * @param day 天数
     * @return 日期
     */
    public static Date addDay(Date date, int day) {
        LocalDateTime dateTime = dateToLocalDate(date);
        dateTime = dateTime.plusDays(day);
        return dateTimeToDate(dateTime);
    }

    /**
     * 指定格式时间，加年、月、日后得到的日期。
     *
     * @param date 起始日期
     * @param year 年
     * @param month 月
     * @param day 天
     * @return 计算结果
     */
    public static String addDate(String date, int year, int month, int day) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        dateTime = dateTime.plusYears(year).plusMonths(month).plusDays(day);
        return dateTime.toString();
    }

    /**
     * 计算日期，加年、月、日后得到的日期。
     *
     * @param date 起始日期
     * @param year 年
     * @param month 月
     * @param day 天
     * @return 计算结果
     */
    public static Date addDate(Date date, int year, int month, int day) {
        LocalDateTime dateTime = dateToLocalDate(date);
        dateTime = dateTime.plusYears(year).plusMonths(month).plusDays(day);
        return dateTimeToDate(dateTime);
    }

    /**
     * 主要功能:计算一个日期加上year年后的日期
     *
     * @param date 初始日期
     * @param year 年数
     * @return 日期
     */
    public static Date addYear(Date date, int year) {
        LocalDateTime dateTime = dateToLocalDate(date);
        dateTime = dateTime.plusYears(year);
        return dateTimeToDate(dateTime);
    }

    /**
     *
     * @param dateTime LocalDateTime
     * @return Date
     */
    private static Date dateTimeToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 主要功能: 获取月末日期 注意事项:无
     *
     * @param todayDate 今天日期
     * @return 月末日期
     */
    public static Date getLastDay(Date todayDate) {
        Calendar calendar = Calendar.getInstance();
        // 把平台日期赋值给Calendar
        calendar.setTime(todayDate);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        int endday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, endday);
        return calendar.getTime();
    }

    /**
     * 主要功能:获取月初日期 注意事项:无
     *
     * @param todayDate 今天日期
     * @return 月初日期
     */
    public static Date getFirstDay(Date todayDate) {
        Calendar calendar = Calendar.getInstance();
        // 把平台日期赋值给Calendar
        calendar.setTime(todayDate);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 主要功能:获取年初日期 注意事项:无
     *
     * @param todayDate 今天日期
     * @return 月初日期
     */
    public static Date getFirstDayOfYear(Date todayDate) {
        Calendar calendar = Calendar.getInstance();
        // 把平台日期赋值给Calendar
        calendar.setTime(todayDate);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的年月 + 指定日 ※超过当月最大天数时，按当月月末处理
     *
     * @param date 日期
     * @param dayIndex 指定日
     * @return 指定日期的年月 + 指定日
     * @since 1.0
     */
    public static Date getDateOfMonthYearByDay(Date date, int dayIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));

        int endday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (dayIndex > endday) {
            dayIndex = endday;
        }
        calendar.set(Calendar.DAY_OF_MONTH, dayIndex);

        return calendar.getTime();
    }

    /**
     * 主要功能:判断字符串是否为日期 注意事项:无
     *
     * @param str 日期字符串 yyyy-MM-dd
     * @param isLenient 是否使用宽松验证
     * @return 是否
     */
    public static boolean isDateStr(String str,boolean isLenient) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT_EN);
        try {
            format.setLenient(isLenient);
            format.parse(str);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 主要功能:判断字符串是否为日期 注意事项:无
     *
     * @param str 日期字符串 yyyy-MM-dd
     * @return 是否
     */
    public static boolean isDateStr(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT_EN);
        try {
            format.parse(str);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * 主要功能:判断字符串是否为日期 注意事项:无
     *
     * @param str 日期字符串 yyyyMMdd
     * @return 是否
     */
    public static boolean isDateStrE(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_OTHER_FORMAT);
        try {
            format.setLenient(false);
            format.parse(str);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * 主要功能:判断字符串是否为日期 注意事项:无
     *
     * @param str 日期字符串 yyyy-MM-dd
     * @param parsePatterns 格式
     * @return 是否
     */
    public static boolean isDateStr(String str, String... parsePatterns) {
        try {
            parseDate(str, parsePatterns);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 日期按月相加
     *
     * @param date 原日期
     * @param num 相加月数，正数为加，负数为减
     * @return Date
     */
    public static Date addMonthDate(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);// 日期减月
        return calendar.getTime();
    }

    /**
     * 日期按月相加，
     *
     * @param str 原日期
     * @param num 相加月数，正数为加，负数为减
     * @return Date
     * @throws ParseException 异常
     */
    public static Date addMonthDateStr(String str, Integer num)
            throws ParseException {
        return addMonthDate(DateUtil.parse(str), num);
    }

    /**
     * 主要功能: 获取上个月 注意事项:无
     *
     * @param selMonth yyyy-MM
     * @return 上个月字符串
     * @throws ParseException 异常
     */
    public static String getLastMonth(String selMonth)
            throws ParseException {
        Date d = DateUtil.parse(selMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, -1);
        return format(calendar.getTime(), "yyyy-MM");
    }

    /**
     * 主要功能: 获取上个月 注意事项:无
     *
     * @param selMonth MM
     * @return 上个月字符串
     * @throws ParseException 异常
     */
    public static Date getLastMonthInt(String selMonth)
            throws ParseException {
        Date d = DateUtil.parse(selMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的季的第一天
     *
     * @param date 日期
     * @return 日期字符串
     * @throws ParseException 异常
     */
    public static Date getFirstDayOfQuarter(Date date)
            throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int quarterOfYear = calendar.get(Calendar.MONTH) / 3 + 1;
        int month = 0;
        switch (quarterOfYear) {
            case 1:
                month = 0;
                break;
            case 2:
                month = 3;
                break;
            case 3:
                month = 6;
                break;
            case 4:
                month = 9;
                break;
            default:
                month = calendar.get(Calendar.MONTH);
        }
        calendar.set(calendar.get(Calendar.YEAR), month, 1);
        return calendar.getTime();
    }

    /**
     * 判断给定日期是否为月末的一天
     *
     * @param date 日期
     * @return true:是|false:不是
     */
    public static boolean isLastDayOfMonth(Date date) {
        if (date == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断给定日期是否为年末的一天
     *
     * @param date 日期
     * @return true:是|false:不是
     */
    public static boolean isLastDayOfYear(Date date) {
        if (date == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_YEAR) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 格式化日期(中债用)，将yyyy-MM-dd格式的日期变为yyyyMMdd格式的日期
     *
     * @param dateStr 日期
     * @return 格式化后的日期
     * @see
     * @since 1.0
     */
    public static String formatDate(String dateStr) {
        Date date = parse(dateStr);
        return format(date, DEFAULT_OTHER_FORMAT);
    }

    /**
     * 格式化日期(中债用)，将yyyyMMdd格式的日期变为yyyy-MM-dd格式的日期
     *
     * @param dateStr 日期
     * @return 格式化后的日期
     * @see
     * @since 1.0
     */
    public static String formatDateAddSplit(String dateStr) {
        Date date = parse(dateStr);
        return format(date);
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date date
     * @return 季度
     */
    public static int getSeason(Date date) {
        if (date == null) {
            return -1;
        }
        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }
    /**
     * 取得当天日期是周几
     *
     * @param date 传入日期
     * @return  周几
     */
    public static int getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK)-1;
        if(day_of_week<1){
            day_of_week = 7;
        }
        return day_of_week;
    }

    /**
     * 将字符串日期时间转换成java.util.Date类型
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @param datetime
     * @return
     */
    public static Date parseDatetime(String datetime) {
        return DateTimeUtil.parseDatetime(datetime).toDate();
    }
    /**
     * 根据自定义pattern将字符串日期转换成java.util.Date类型
     *
     * @param datetime
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseDatetime(String datetime, String pattern) throws ParseException {
        return DateTimeUtil.parseDatetime(datetime, pattern).toDate();
    }

    /**
     * 格式化日期
     * <p>
     * 日期格式yyyy-MM-dd
     *
     * @return
     */
    public static String formatDate(Date date) {
        return DateTimeUtil.formatDate(date);
    }
}
