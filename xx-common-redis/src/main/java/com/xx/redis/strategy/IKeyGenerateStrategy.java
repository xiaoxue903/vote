package com.xx.redis.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: xueqimiao
 * @Date: 2022/4/26 14:59
 */
public interface IKeyGenerateStrategy {

    /**
     * 获取类型
     * @return
     */
    String getType();

    /**
     * 获取key
     * @param request
     * @return
     */
    String getKey(HttpServletRequest request);
}
