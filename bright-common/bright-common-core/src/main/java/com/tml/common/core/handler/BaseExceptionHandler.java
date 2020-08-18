package com.tml.common.core.handler;

import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.exception.FileDownloadException;
import com.tml.common.core.utils.BrightUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleException(Exception e) {
        String message = BrightUtil.containChinese(e.getMessage()) ? e.getMessage() : "系统内部异常";
        log.error(message, e);
        return new CommonResult().message(message);
    }

    @ExceptionHandler(value = BrightException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleBrightException(BrightException e) {
        log.error("系统异常", e);
        return new CommonResult().message(e.getMessage());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return CommonResult
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(StringConstant.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString());
        return new CommonResult().message(message.toString());
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return CommonResult
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(StringConstant.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString());
        return new CommonResult().message(message.toString());
    }

    /**
     * 统一处理请求参数校验(json)
     *
     * @param e ConstraintViolationException
     * @return CommonResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(StringConstant.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString());
        return new CommonResult().message(message.toString());
    }

    @ExceptionHandler(value = FileDownloadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleFileDownloadException(FileDownloadException e) {
        log.error("FileDownloadException", e);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult handleAccessDeniedException() {
        return new CommonResult().message("没有权限访问该资源");
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String message = "该方法不支持" + StringUtils.substringBetween(e.getMessage(), "'", "'") + "媒体类型";
        log.error(message);
        return new CommonResult().message(message);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = "该方法不支持" + StringUtils.substringBetween(e.getMessage(), "'", "'") + "请求方法";
        log.error(message);
        return new CommonResult().message(message);
    }

}
