package com.xx.annoation;

import com.xx.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/17 10:12
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(GlobalExceptionHandler.class)
public @interface EnableGlobalException {
}
