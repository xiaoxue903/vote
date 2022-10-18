package com.xx.exception;

import com.xx.common.ResultCodeEnum;
import com.xx.common.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;


/**
 * @Author: xueqimiao
 * @Date: 2020/9/18 15:31
 */
@Component
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData businessExceptionException(BusinessException e) {
        log.error("系统异常：", e);
        return new ResultData(ResultCodeEnum.BUSINESS_DATA_VERIFICATION_FAIL, e.getMessage());
    }

    @ExceptionHandler(ParamVerifyException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData paramVerifyException(ParamVerifyException e) {
        log.error("系统异常：", e);
        return new ResultData(ResultCodeEnum.PARAM_VERIFICATION_FAIL, e.getMessage());
    }


    @ExceptionHandler(DataEmptyException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData dataEmptyException(DataEmptyException e) {
        log.error("系统异常：", e);
        return new ResultData(ResultCodeEnum.DATA_EMPTY_FAIL, e.getMessage());
    }

    @ExceptionHandler(DataNotExistException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData dataNotExistException(DataNotExistException e) {
        log.error("系统异常：", e);
        return new ResultData(ResultCodeEnum.DATA_NOT_EXIST_FAIL, e.getMessage());
    }


    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData permissionException(DataNotExistException e) {
        log.error("系统异常：", e);
        return new ResultData(ResultCodeEnum.PERMISSION_AUTH_FAIL, e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("系统异常：", e);
        return new ResultData<>(ResultCodeEnum.PARAM_VERIFICATION_FAIL, e.getMessage(), "请求参数异常");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("系统异常：", e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return new ResultData(ResultCodeEnum.PARAM_VERIFICATION_FAIL, message);
    }

    /**
     * 系统异常 预期以外异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData handleUnexpectedServer(Exception ex) {
        log.error("系统异常：", ex);
        return new ResultData(ResultCodeEnum.SYSTEM_ERROR, "系统发生异常，请联系管理员");
    }
}
