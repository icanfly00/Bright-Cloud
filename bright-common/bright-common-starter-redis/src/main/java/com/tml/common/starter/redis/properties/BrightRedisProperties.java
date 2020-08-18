package com.tml.common.starter.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@ConfigurationProperties(prefix = "bright.lettuce.redis")
public class BrightRedisProperties {

    /**
     * 是否开启Lettuce Redis
     */
    private Boolean enable = true;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "BrightRedisProperties{" +
                "enable=" + enable +
                '}';
    }
}
