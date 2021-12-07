package com.platform.common.validator;

import java.util.regex.Pattern;

/**
 * @ClassName Validator
 * @Description 校验器：利用正则表达式校验邮箱、手机号等
 * @Author yanl
 * @Date 2020/8/25 0:49
 * @Version 1.0
 **/
public class Validator {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9`~!@#$%^&*()-_=+\\[\\]{}\\|;:'\",<.>/?]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1[3|4|5|7|8]\\d{9}$";

    /**
     * 验证码表达式
     */
    public static final String REGEX_CHECKCODE = "^\\d{4}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式：生日
     */
    public static final String REGEX_BIRTH = "(19|20)\\d{2}-(1[0-2]|0[1-9])-(0[1-9]|[1-2][0-9]|3[0-1])";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return EmailValidator.isValid(email);
    }

    /**
     * 检验验证码
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isCheckcode(String checkcode) {
        return Pattern.matches(REGEX_CHECKCODE, checkcode);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    public static void main(String[] args) {
        String username = "fdsdfsdj";
        System.out.println(Validator.isUsername(username));
        System.out.println(Validator.isChinese(username));
    }
}
