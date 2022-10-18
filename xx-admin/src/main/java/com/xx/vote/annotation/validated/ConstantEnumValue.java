package com.xx.vote.annotation.validated;

import com.baomidou.mybatisplus.annotation.EnumValue;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 15:09
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ConstantEnumValueValidator.class})
public @interface ConstantEnumValue {

    // 默认错误消息
    String message() default "必须为指定值";

    String[] strValues() default {};

    int[] intValues() default {};

    // 分组
    Class<?>[] groups() default {};

    // 负载
    Class<? extends Payload>[] payload() default {};

    // 指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        EnumValue[] value();
    }
}
