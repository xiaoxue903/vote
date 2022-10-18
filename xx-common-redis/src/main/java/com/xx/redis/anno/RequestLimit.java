package com.xx.redis.anno;


import com.xx.redis.strategy.IpKeyGenerateStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xueqimiao
 * @Date: 2022/4/26 10:39
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {

    // 默认效果 ： 单个ip 60秒内 对于使用该注解的接口，只能总请求访问数 不能大于 5次

    /**
     * 允许访问次数
     */
    long count() default 5;

    /**
     * 时间内  默认秒为单位
     */
    int interval() default 60;

    /**
     * 默认秒为单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 自定义策略，默认策略按IP
     */
    String[] strategy() default {IpKeyGenerateStrategy.TYPE};

    /**
     * 提示信息
     */
    String message() default "请求过于频繁，稍后再试哦~";
}
