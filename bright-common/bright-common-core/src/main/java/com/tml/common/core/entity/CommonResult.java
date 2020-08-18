package com.tml.common.core.entity;

import java.util.HashMap;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public class CommonResult extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public CommonResult message(String message) {
        this.put("message", message);
        return this;
    }

    public CommonResult data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public CommonResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}
