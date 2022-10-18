package com.xx.utils;

import java.util.regex.Pattern;

/**
 * @ClassName: PatternUtil
 * @Author: zm
 * @Date: 2022/9/1 23"23
 */
public class PatternUtil {

    /**
     * 验证手机号格式
     * @param phoneNumber
     * @return
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (!ValidationUtil.isEmpty(phoneNumber)) {
            return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
        }
        return false;
    }

}
