package com.tml.common.swagger2.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description Swagger2配置
 * @Author TuMingLong
 * @Date 2020/4/6 15:56
 */

@Data
@ConfigurationProperties(prefix = "tml.swagger2")
public class Swagger2Properties {
    /**
     * 是否启用swagger,生产环境建议关闭
     */
    private boolean enabled;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;

    /**
     * 版本
     */
    private String version;
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * 客户端授权范围
     */
    private String scope;
    /**
     * 获取token
     */
    private String accessTokenUri;
    /**
     * 认证地址
     */
    private String userAuthorizationUri;

}
