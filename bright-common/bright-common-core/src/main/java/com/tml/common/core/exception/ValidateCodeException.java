package com.tml.common.core.exception;

/**
 * 验证码类型异常
 *
 * @Author TuMingLong
 */
public class ValidateCodeException extends Exception {

    private static final long serialVersionUID = 7514854456967620043L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
