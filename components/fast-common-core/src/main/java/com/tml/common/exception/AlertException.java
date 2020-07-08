package com.tml.common.exception;

/**
 * @Description com.tml.common.exception
 * @Author TuMingLong
 * @Date 20:50
 */
public class AlertException extends APIException {

    public AlertException() {
    }

    public AlertException(String msg) {
        super(msg);
    }

    public AlertException(int code, String msg) {
        super(code, msg);
    }

    public AlertException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
