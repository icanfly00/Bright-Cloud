package com.tml.common.api;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description com.tml.common.api
 * @Author TuMingLong
 * @Date 2020/3/28 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "响应结果")
public class CommonResult<T> {
    /**
     * 响应编码
     */
    @ApiModelProperty(value = "响应编码")
    private Integer code;
    /**
     *
     */
    @ApiModelProperty(value = "提示消息")
    private String message;
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;
    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
    private String path;
    /**
     * http状态码
     */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    @ApiModelProperty(value = "http状态码")
    private int httpStatus;


    public CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param code    错误码
     * @param message 提示信息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(int code, String message) {
        return new CommonResult<T>(code, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param code
     * @param message
     * @param path
     * @param httpStatus
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(int code,String message,String path,int httpStatus) {
        return new CommonResult<T>(code, message, null,path,httpStatus);
    }


    /**
     * 失败返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
    }

    /**
     * 参数验证失败返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage());
    }

    /**
     * 提示信息
     *
     * @param message 提示信息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     *
     * @param data 获取的数据
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     *
     * @param data 获取的数据
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * 未授权返回结果
     *
     * @param message 消息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> forbidden(String message) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), message);
    }
}
