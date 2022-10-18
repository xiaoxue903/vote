package com.xx.vote.annotation;

import com.xx.vote.interceptor.PageDataInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/14 16:43
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({PageDataInterceptor.class})
public @interface EnableAnnotationPage {
}
