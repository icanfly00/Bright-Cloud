package com.tml.common.exception;

/**
 * @Description 签名异常
 * @Author TuMingLong
 * @Date 2020/8/2
 */
public class SignatureException extends APIException{

    public SignatureException() {
    }

    public SignatureException(String msg) {
        super(msg);
    }

    public SignatureException(int code, String msg) {
        super(code, msg);
    }

    public SignatureException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
