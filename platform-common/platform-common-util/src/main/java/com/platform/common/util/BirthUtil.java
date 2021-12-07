package com.platform.common.util;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author
 */
public class BirthUtil {

    public static int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }



/*
	public static Date getBirthByDate() {
		//  如果从HL7文件中，未能解析出生日期，则根据所填年龄进行换算
		if (null == birthTime) {
			if (null != age) {
				Calendar cal = Calendar.getInstance();
				if ("Y".equalsIgnoreCase(ageUnit) || StringUtils.isBlank(ageUnit)) {    //  默认为：年龄
					cal.add(Calendar.YEAR, 0-Integer.parseInt(age));
				}else if ("M".equalsIgnoreCase(ageUnit)) {
					cal.add(Calendar.YEAR, 0-Integer.parseInt(age) / 12);       //  初略计算，将月龄除以12月，得出大概的年龄
				}else if ("D".equalsIgnoreCase(ageUnit)) {
					cal.add(Calendar.YEAR, 0-Integer.parseInt(age) / 365);       //  初略计算，将日龄除以365天，得出大概的年龄
				}

				return new Date(cal.getTimeInMillis());
			}
		}else if (null != this.birthTime) {
			//  根据出生日期，进行计算
			//  从HL7中识别出的 出生日期 字段内容 为： 20100406000000
			try {
				return DateUtil.parseDatetime(this.birthTime, "yyyyMMddHHmmss");
			} catch (ParseException e) {
				e.printStackTrace();
				return null;    //  从HL7文件中识别出的日期格式不对，返回未提供患者出生日期
			}
		}

		return null;    //  HL7文件中未提供出生日期，返回未提供患者出生日期
	}
*/

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


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bithday = format.parse("1981-08-23 17:20:20");
        System.out.println(BirthUtil.getAgeByBirth(bithday));
    }
}
