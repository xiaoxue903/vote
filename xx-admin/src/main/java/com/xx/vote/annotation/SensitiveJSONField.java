package com.xx.vote.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveJSONField {
  SensitiveJSONFieldKey[] sensitivelist();
}
