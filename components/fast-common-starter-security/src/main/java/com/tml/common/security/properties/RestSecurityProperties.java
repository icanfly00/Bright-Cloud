package com.tml.common.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 安全配置
 * @Author TuMingLong
 * @Date 2020/3/28 17:22
 */

@Data
@Component
@ConfigurationProperties(prefix = "tml.security")
public class RestSecurityProperties {
    /**
     * 是否使用JWT令牌
     */
    private Boolean jwtEnable;

    /**
     * JWT加密秘钥
     */
    private String jwtSigningKey;

    /**
     * 基于Redis实现，令牌保存到缓存，缓存前缀
     */
    private String oauthPrefix;

    /**
     * 免认证资源路径，支持通配符
     * 多个值时使用逗号分隔
     */
    private List<String> anonUris = new ArrayList<>();
}
