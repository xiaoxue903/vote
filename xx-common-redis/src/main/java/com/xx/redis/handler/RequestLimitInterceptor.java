package com.xx.redis.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.xx.common.ResultCodeEnum;
import com.xx.common.ResultData;
import com.xx.redis.anno.RequestLimit;
import com.xx.redis.service.RedisService;
import com.xx.redis.strategy.IpKeyGenerateStrategy;
import com.xx.redis.util.CacheKeyUtil;
import com.xx.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xueqimiao
 * @Date: 2022/4/26 10:56
 */
@Slf4j
@Component
public class RequestLimitInterceptor implements HandlerInterceptor {

    private RedisService redisService;

    public RequestLimitInterceptor(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                // 获取RequestLimit注解
                RequestLimit requestLimit = handlerMethod.getMethodAnnotation(RequestLimit.class);
                if (null == requestLimit) {
                    return true;
                }
                //限制的时间范围
                int interval = requestLimit.interval();

                TimeUnit timeUnit = requestLimit.unit();
                //时间内的 最大次数
                Long count = requestLimit.count();
                String[] strategyS = requestLimit.strategy();
                // String ipaddr = null;
                StringBuffer cacheKey = new StringBuffer();
                cacheKey.append(CacheKeyUtil.TCM_REQUEST_KEY + CacheKeyUtil.KEY_COLON);
                for (String strategy : strategyS) {
                    cacheKey.append(strategy);
                    cacheKey.append(CacheKeyUtil.KEY_COLON);
                    if(IpKeyGenerateStrategy.TYPE.equals(strategy)){
                        cacheKey.append(new IpKeyGenerateStrategy().getKey(request));
                    }
                }
                cacheKey.append(request.getContextPath() + CacheKeyUtil.KEY_COLON + request.getServletPath());
                // 存储key
                String key = cacheKey.toString();
                // 已经访问的次数
                Integer requestCount = (Integer) redisService.get(key);
                //log.info("检测到目前ip{}对接口={}已经访问的次数:{}", ipaddr, request.getServletPath(), ValidationUtil.isEmpty(requestCount) ? 1 : requestCount + 1);
                if (ValidationUtil.isEmpty(requestCount) || -1 == requestCount) {
                    redisService.set(key, 1, Long.valueOf(interval), timeUnit);
                    return true;
                }
                if (requestCount < count) {
                    redisService.increment(key);
                    return true;
                }
                log.warn("请求过于频繁，稍后再试哦~");
                returnData(response, requestLimit.message());
                return false;
            }
            return true;
        } catch (Exception e) {
            log.warn("请求过于频繁，稍后再试哦~", e.getMessage());
        }
        return true;
    }

    public void returnData(HttpServletResponse response, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(new ResultData(ResultCodeEnum.REQUEST_OFTEN_FAIL.getCode(), message)));
        return;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
