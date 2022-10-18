package com.xx.utils;

import cn.hutool.core.convert.Convert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/14 9:38
 */
public class ServletUtil {


    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getHttpRequest().getParameter(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getHttpRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getHttpRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getHttpRequest().getParameter(name), defaultValue);
    }

    /**
     * get the http servlet request
     *
     * @return
     */
    public static HttpServletRequest getHttpRequest() {
        RequestAttributes ra = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
        return request;
    }
}
