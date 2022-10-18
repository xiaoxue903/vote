package com.xx.vote.annotation;

import com.xx.vote.config.DruidDbFieldEncryptConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/9 10:03
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DruidDbFieldEncryptConfig.class)
public @interface EnableDruidDbFieldEncryptConfig {

}
