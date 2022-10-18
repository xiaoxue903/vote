package com.xx.vote.annotation;

import com.xx.vote.config.TransactionConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: xueqimiao
 * @Date: 2022/3/1 15:53
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(TransactionConfiguration.class)
public @interface EnableXXTransactionManagement {
}
