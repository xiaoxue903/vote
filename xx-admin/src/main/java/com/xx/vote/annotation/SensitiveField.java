package com.xx.vote.annotation;

import com.xx.vote.type.SensitiveType;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveField {
  SensitiveType value();
}
