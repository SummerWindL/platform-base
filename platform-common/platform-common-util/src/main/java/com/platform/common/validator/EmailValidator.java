package com.platform.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * @ClassName EmailValidator
 * @Description
 * @Author yanl
 * @Date 2020/8/25 0:50
 * @Version 1.0
 **/
public class EmailValidator {
    private static final int MAX_LOCAL_PART_LENGTH = 64;

    private static final String LOCAL_PART_ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~\u0080-\uFFFF-]";
    private static final String LOCAL_PART_INSIDE_QUOTES_ATOM = "([a-z0-9!#$%&'*.(),<>\\[\\]:;  @+/=?^_`{|}~\u0080-\uFFFF-]|\\\\\\\\|\\\\\\\")";
    /**
     * Regular expression for the local part of an email address (everything before '@')
     */
    private static final Pattern LOCAL_PART_PATTERN = Pattern.compile(
            "(" + LOCAL_PART_ATOM + "+|\"" + LOCAL_PART_INSIDE_QUOTES_ATOM + "+\")" +
                    "(\\." + "(" + LOCAL_PART_ATOM + "+|\"" + LOCAL_PART_INSIDE_QUOTES_ATOM + "+\")" + ")*", CASE_INSENSITIVE
    );

    public static boolean isValid(CharSequence value) {
        if (value == null || value.length() == 0) {
            return true;
        }

        // cannot split email string at @ as it can be a part of quoted local part of email.
        // so we need to split at a position of last @ present in the string:
        String stringValue = value.toString();
        int splitPosition = stringValue.lastIndexOf('@');

        // need to check if
        if (splitPosition < 0) {
            return false;
        }

        String localPart = stringValue.substring(0, splitPosition);
        String domainPart = stringValue.substring(splitPosition + 1);

        if (!isValidEmailLocalPart(localPart)) {
            return false;
        }

        return DomainNameUtil.isValidEmailDomainAddress(domainPart);
    }

    private static boolean isValidEmailLocalPart(String localPart) {
        if (localPart.length() > MAX_LOCAL_PART_LENGTH) {
            return false;
        }
        Matcher matcher = LOCAL_PART_PATTERN.matcher(localPart);
        return matcher.matches();
    }
}
