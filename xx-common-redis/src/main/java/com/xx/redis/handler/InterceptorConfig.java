package com.xx.redis.handler;

import com.xx.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: xueqimiao
 * @Date: 2022/4/26 10:56
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private RedisService redisService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//addPathPatterns拦截的路径
        String[] addPathPatterns = {
                "/**"
        };
        //excludePathPatterns排除的路径
        String[] excludePathPatterns = {
                "/css/**","/js/**","/img/**","/template/**"
        };
        //创建用户拦截器对象并指定其拦截的路径和排除的路径
        registry.addInterceptor(new RequestLimitInterceptor(redisService)).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
    }
}
