package com.xx.exception;

/**
 * @Author: xueqimiao
 * @Date: 2022/9/8 16:16
 */
public class PermissionException extends RuntimeException {

    public PermissionException() {
        super();
    }

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable errorCourse) {
        super(message, errorCourse);
    }
}
