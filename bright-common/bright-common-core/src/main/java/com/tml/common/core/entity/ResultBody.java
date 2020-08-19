package com.tml.common.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 响应结果
 * @since 2020/8/10 21:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResultBody<T> implements Serializable {
    /**
     * 相应编码
     */
    private int code = 0;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 请求路径
     */
    private String path;
    /**
     * 响应时间
     */
    private long timestamp = System.currentTimeMillis();
    /**
     * http状态码
     */
    @JsonIgnore
    private int httpStatus;

    public ResultBody(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultBody(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultBody<T> ok(T data) {
        return new ResultBody<T>(200, "操作成功", data);
    }

    public static <T> ResultBody<T> failed(String message) {
        return new ResultBody<T>(500, message);
    }
}
