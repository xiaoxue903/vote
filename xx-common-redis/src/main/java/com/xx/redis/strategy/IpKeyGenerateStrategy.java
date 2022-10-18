package com.xx.redis.strategy;


import com.xx.utils.IPUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: xueqimiao
 * @Date: 2022/4/26 15:00
 */
public class IpKeyGenerateStrategy implements IKeyGenerateStrategy{

    public final static String TYPE = "IP";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getKey(HttpServletRequest request) {
        return IPUtils.getIpAddr(request);
    }
}
