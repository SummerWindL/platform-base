package com.platform.core.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Advance
 * @date 2022年07月17日 11:42
 * @since V1.0.0
 */
public class StringUtil extends StringUtils {

    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";

    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";

    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";

    private static final Pattern P_SCRIPT = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_STYLE = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_HTML = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
    /**
     * 判断两个字符串是否相等 如果都为null则判断为相等,一个为null另一个not null则判断不相等 否则如果s1=s2则相等
     *
     * @param s1 参数1
     * @param s2 参数2
     * @return 是否
     */
    public static boolean equals(Object s1, Object s2) {
        if (!(s1 instanceof String)) {
            return false;
        }
        if (null != s1 && null != s2) {
            return s1.equals(s2);
        }
        return false;
    }

    /**
     * 取得一个非空的整数值
     *
     * @param value 源字符串
     * @param defaultValue 默认数据
     * @return 整数
     */
    public static int integerValueOf(String value, int defaultValue) {
        int result = 0;
        try {
            result = Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            result = defaultValue;
        }
        return result;
    }

    /**
     * 取得一个非空的整数值
     *
     * @param value 源数据
     * @return 整数值
     */
    public static int integerValueOf(String value) {
        return integerValueOf(value, 0);
    }

    /**
     * 获取一个对象的字符串的值，如果该字符串为空则取默认值
     *
     * @param value 对象
     * @param defaultValue 默认值
     * @return 对象的字符串表示
     */
    public static final String getOrElse(Object value, String defaultValue) {
        if (value != null && isNotEmpty(value.toString())) {
            return value.toString();
        }
        else if (isNotEmpty(defaultValue)) {
            return defaultValue;
        }
        return EMPTY;
    }

    /**
     * 获取一个对象的字符串的值，如果该字符串为空则取默认值
     *
     * @param value 对象
     * @param supplier 默认值,延迟计算
     * @return 对象的字符串表示
     */
    public static final String getOrElse(Object value, Supplier<String> supplier) {
        if (value != null && isNotEmpty(value.toString())) {
            return value.toString();
        }
        else if (supplier != null) {
            return supplier.get();
        }
        return EMPTY;
    }

    /**
     * 数字转字符串,如果num&lt;=0 则输出&quot;&quot;
     *
     * @param num 数字
     * @return 字符串
     */
    public static String numberToString(Number num) {
        if (num == null) {
            return null;
        }
        String result = EMPTY;
        if (num instanceof Short && (Short)num > 0) {
            result = Short.toString((Short)num);
        }
        if (num instanceof Integer && (Integer)num > 0) {
            result = Integer.toString((Integer)num);
        }
        else if (num instanceof Long && (Long)num > 0) {
            result = Long.toString((Long)num);
        }
        else if (num instanceof Float && (Float)num > 0) {
            result = Float.toString((Float)num);
        }
        else if (num instanceof Double && (Double)num > 0) {
            result = Double.toString((Double)num);
        }
        else if (num instanceof BigDecimal) {
            result = ((BigDecimal)num).stripTrailingZeros().toPlainString();
        }
        return result;
    }

    /**
     * 判断对象是否为空
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNotEmpty(Object str) {
        boolean flag = true;
        if (str != null && !str.equals("")) {
            if (str.toString().length() > 0) {
                flag = true;
            }
        }
        else {
            flag = false;
        }
        return flag;
    }

    /**
     * 自定义的分隔字符串函数 例如: 1,2,3 =&gt; [1,2,3] 3个元素 ,2,3 =&gt; [,2,3] 3个元素 ,2,3,=&gt;[,2,3,] 4个元素
     * ,,,=&gt;[,,,] 4个元素 5.22算法修改，为提高速度不用正则表达式 两个间隔符,,返回""元素（空字符串或者null会返回空的集合）
     *
     * @param split 分割字符 默认,
     * @param src 输入字符串
     * @return 分隔后的list
     * @author Robin
     */
    public static List<String> splitToList(String split, String src) {
        if (isEmpty(src)) {
            return new ArrayList<>();
        }
        // 默认,
        String sp = ",";
        if (split != null && split.length() == 1) {
            sp = split;
        }
        List<String> r = new ArrayList<String>();
        int lastIndex = -1;
        int index = src.indexOf(sp);
        if (-1 == index && src != null) {
            r.add(src);
            return r;
        }
        while (index >= 0) {
            if (index > lastIndex) {
                r.add(src.substring(lastIndex + 1, index));
            }
            else {
                r.add("");
            }

            lastIndex = index;
            index = src.indexOf(sp, index + 1);
            if (index == -1) {
                r.add(src.substring(lastIndex + 1, src.length()));
            }
        }
        return r;
    }

    /**
     * Description:判断list是否为空
     *
     * @param list 需要判断的list
     * @return 是否为空
     */
    public static boolean isEmptyList(List<?> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 删除html标签，防止js注入
     *
     * @param htmlStr 字符串
     * @return 过滤后的字符串
     * @see
     * @since 1.0
     */
    public static String trimHtmlTag(String htmlStr) {
        if (isEmpty(htmlStr)) {
            return htmlStr;
        }
        Matcher m_script = P_SCRIPT.matcher(htmlStr);
        htmlStr = m_script.replaceAll(StringUtil.EMPTY); // 过滤script标签

        Matcher m_style = P_STYLE.matcher(htmlStr);
        htmlStr = m_style.replaceAll(StringUtil.EMPTY); // 过滤style标签

        Matcher m_html = P_HTML.matcher(htmlStr);
        htmlStr = m_html.replaceAll(StringUtil.EMPTY); // 过滤html标签

        return htmlStr.trim(); // 返回文本字符串
    }

    /**
     * 主要功能:sou中是否存在finds 如果指定的finds字符串有一个在sou中找到 注意事项:无
     *
     * @param sou 查找字符串
     * @param finds 查询集合
     * @return 是否
     */
    public static boolean strPos(String sou, String finds) {
        List<String> t = splitToList(",", finds);
        return strPos(sou, t);
    }

    /**
     * 主要功能:sou中是否存在finds 如果指定的finds字符串有一个在sou中找到 注意事项:无
     *
     * @param sou 查找字符串
     * @param finds 查询集合
     * @return 是否
     */
    public static boolean strPos(String sou, List<String> finds) {
        if (sou != null && finds != null && finds.size() > 0) {
            for (String s : finds) {
                if (sou.indexOf(s) > -1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 过滤特殊字符
     *
     * @param str 待过滤的字符串
     * @return 过滤后的字符串
     * @see
     * @since 1.0
     */
    public static String StringFilter(String str) {
        if (isEmpty(str)) {
            return str;
        }
        // 清除掉所有特殊字符
        String regEx = "[']";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 主要功能:字符串转换整形 注意事项:无
     *
     * @param s 字符串
     * @return int
     */
    public static int toInt(String s) {
        if (s != null && !"".equals(s.trim())) {
            try {
                return Integer.parseInt(s);
            }
            catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 将list转为指定分隔符分割的字符串
     *
     * @param list 列表
     * @param delimiter 分隔符
     * @return 字符串
     * @see
     * @since 1.0
     */
    public static String joinString(List<String> list, CharSequence delimiter) {
        return list.stream().collect(Collectors.joining(delimiter));
    }

    /**
     * 将list转为指定分隔符分割的字符串，同时提供前缀和后缀
     *
     * @param list 列表
     * @param delimiter 分隔符
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 字符串
     * @see
     * @since 1.0
     */
    public static String joinString(List<String> list, CharSequence delimiter, CharSequence prefix,
                                    CharSequence suffix) {
        return list.stream().collect(Collectors.joining(delimiter, prefix, suffix));
    }

    /**
     * 存文本替换
     *
     * @param s 源字符串
     * @param sf 子字符串
     * @param sb 替换字符串
     * @return 替换后的字符串
     */
    public static String replaceAll(String s, String sf, String sb) {
        int i = 0;
        int j = 0;
        int l = sf.length();
        boolean b = true;
        boolean o = true;
        String str = "";
        do {
            j = i;
            i = s.indexOf(sf, j);
            if (i >= j) {
                str += s.substring(j, i);
                str += sb;
                i += l;
                o = false;
            }
            else {
                str += s.substring(j);
                b = false;
            }
        }
        while (b);
        if (o) {
            str = s;
        }
        return str;
    }

    /**
     * 截取字符串 超出的字符用symbol代替
     *
     * @param len 字符串长度 长度计量单位为一个GBK汉字 两个英文字母计算为一个单位长度
     * @param str 字符串
     * @param symbol 符号
     * @return 字符串
     */
    public static String getLimitLengthString(String str, int len, String symbol) {
        int iLen = len * 2;
        int counterOfDoubleByte = 0;
        String strRet = "";
        try {
            if (str != null) {
                byte[] b = str.getBytes("GBK");
                if (b.length <= iLen) {
                    return str;
                }
                for (int i = 0; i < iLen; i++ ) {
                    if (b[i] < 0) {
                        counterOfDoubleByte++ ;
                    }
                }
                if (counterOfDoubleByte % 2 == 0) {
                    strRet = new String(b, 0, iLen, "GBK") + symbol;
                    return strRet;
                }
                else {
                    strRet = new String(b, 0, iLen - 1, "GBK") + symbol;
                    return strRet;
                }
            }
            else {
                return "";
            }
        }
        catch (Exception ex) {
            return str.substring(0, len);
        }
        finally {
            strRet = null;
        }
    }

    /**
     * 截取字符串 超出的字符用symbol代替
     *
     * @param len
     *            字符串长度 长度计量单位为一个GBK汉字 两个英文字母计算为一个单位长度
     * @param str
     *            字符串
     * @return 截取字符串
     */
    public static String getLimitLengthString(String str, int len) {
        return getLimitLengthString(str, len, "...");
    }



    /**
     * 取得字符串的字节长度（考虑了汉字的情况）
     *
     * @param srcStr 源字符串
     * @return 字符串的实际长度
     */
    public static int byteLength(String srcStr) {
        int returnValue = 0;
        if (srcStr != null) {
            byte[] byteArr = srcStr.getBytes(Charset.forName("UTF-8"));
            returnValue = byteArr.length;
        }
        return returnValue;
    }
}
