package com.xx.vote.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EncryptQueryParams {
  String[] value() default {};
}
