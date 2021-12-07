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
