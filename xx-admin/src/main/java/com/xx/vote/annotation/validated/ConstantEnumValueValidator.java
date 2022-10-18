package com.xx.vote.annotation.validated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 15:10
 */
public class ConstantEnumValueValidator implements ConstraintValidator<ConstantEnumValue, Object> {

    private String[] strValues;
    private int[] intValues;

    @Override
    public void initialize(ConstantEnumValue constraintAnnotation) {
        strValues = constraintAnnotation.strValues();
        intValues = constraintAnnotation.intValues();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof String) {
            for (String s : strValues) {
                if (s.equals(value)) {
                    return true;
                }
            }
        } else if (value instanceof Integer) {
            for (int s : intValues) {
                if (s == ((Integer) value).intValue()) {
                    return true;
                }
            }
        }
        return false;
    }
}
