package com.tml.gateway.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 开发API配置
 * @Author TuMingLong
 * @Date 2020/7/8 14:01
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "tml.api")
public class ApiProperties {
    /**
     * 参数签名验证
     */
    private Boolean checkSign = true;
    /**
     * 访问权限控制
     */
    private Boolean accessControl = true;
    /**
     * Swagger2调试,生产环境设为false
     */
    private Boolean apiDebug = false;
    /**
     * 始终放行
     */
    private List<String> permitAll;
    /**
     * 忽略权限鉴定的请求
     */
    private List<String> authorityIgnores;
    /**
     * 签名忽略请求
     */
    private List<String> signIgnores;
}
