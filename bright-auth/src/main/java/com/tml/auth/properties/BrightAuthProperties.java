package com.tml.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @description jwt配置
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "bright.auth")
public class BrightAuthProperties {
    /**
     * JWT加签密钥
     */
    private String jwtAccessKey;
    /**
     * 是否使用 JWT令牌
     */
    private Boolean enableJwt;

    /**
     * 社交登录所使用的 Client
     */
    private String socialLoginClientId;
}
