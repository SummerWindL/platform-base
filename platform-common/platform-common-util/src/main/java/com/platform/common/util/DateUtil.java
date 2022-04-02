package com.platform.common.util;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author
 */
public class DateUtil {

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
     * 获得当前日期时间
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentDatetime() {
        return DateTimeUtil.currentDatetime();
    }

    /**
     * 格式化日期时间
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */

    public static String formatDatex(Date date) {
        return DateTimeUtil.formatDatex(date);
    }

    public static String formatDatetime(Date date) {
        return DateTimeUtil.formatDatetime(date);
    }

    public static String formatDatetimex(Date date) {
        return DateTimeUtil.formatDatetimex(date);
    }

    public static String formatDatetimeyy(Date date) {
        return DateTimeUtil.formatDatetimeyy(date);
    }

    /**
     * 格式化日期时间
     *
     * @param date
     * @param pattern 格式化模式
     * @return
     */
    public static String formatDatetime(Date date, String pattern) {
        return DateTimeUtil.formatDatetime(date, pattern);
    }

    /**
     * 获得当前日期
     * <p>
     * 日期格式yyyy-MM-dd
     *
     * @return
     */
    public static String currentDate() {
        return DateTimeUtil.currentDate();
    }

    public static String getYestodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.today());
        calendar.add(calendar.DATE, -1);        //把日期往后增加一天.整数往后推,负数往前移动
        String yestoday = DateUtil.formatDate(calendar.getTime());

        return yestoday;
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

    /**
     * 获得当前时间
     * <p>
     * 时间格式HH:mm:ss
     *
     * @return
     */
    public static String currentTime() {
        return DateTimeUtil.currentTime();
    }

    public static Date addDay(Date d, int num) {
        return DateTimeUtil.addDay(d, num).toDate();
    }

    public static Date addMonth(Date d, int num) {
        return DateTimeUtil.addMonth(d, num).toDate();
    }

    public static Date addMinute(Date d, int num) {
        return DateTimeUtil.addMinute(d, num).toDate();
    }

    public static Date minusYear(Date d, int num) {
        return DateTimeUtil.minusYears(d, num);
    }

    /**
     * 格式化时间
     * <p>
     * 时间格式HH:mm:ss
     *
     * @return
     */
    public static String formatTime(Date date) {
        return DateTimeUtil.formatTime(date);
    }

    public static Date now() {
        return DateTimeUtil.now().toDate();
    }

    public static Date today() {
        DateTime now = DateTimeUtil.now().withMillisOfDay(0);
        return now.toDate();
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
     * 将字符串日期转换成java.util.Date类型
     * <p>
     * 日期时间格式yyyy-MM-dd
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date) throws ParseException {

        return DateTimeUtil.parseDate(date).toDate();
    }

    public static Date parseDatex(String date) throws ParseException {

        return DateTimeUtil.parseDatex(date).toDate();
    }

    public static Date parseDatetx(String date) throws ParseException {

        return DateTimeUtil.parseDatex(date).toDate();
    }

    public static Date parseDateMq(String date) {
        return DateTimeUtil.parseDateMq(date).toDate();
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

    public static void main(String[] args) {
        System.out.println(DateUtil.getYestodayDate());
        System.out.println(DateUtil.currentDate());

        System.out.println(DateUtil.formatDatetime(new Date(), "yyyy-MM-dd"));
        System.out.println(DateUtil.formatDatetime(new Date(), "HH:mm:ss"));
    }

}
