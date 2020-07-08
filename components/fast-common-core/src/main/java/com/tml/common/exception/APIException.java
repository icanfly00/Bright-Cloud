package com.tml.common.exception;

import com.tml.common.api.ResultCode;

/**
 * @Description 自定义API异常
 * @Author TuMingLong
 * @Date 2020/3/28 16:41
 */
public class APIException extends RuntimeException {

    private int code = ResultCode.FAILED.getCode();

    public APIException() {

    }

    public APIException(String msg) {
        super(msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public APIException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
