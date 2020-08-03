package com.tml.gateway.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Description 网关属性配置类
 * @Author TuMingLong
 * @Date 2020/8/1
 */
@Data
@Component
@ConfigurationProperties(prefix = "tml.gateway")
public class APIProperties {
    /**
     * 是否开启签名验证
     */
    private Boolean checkSign=true;
    /**
     * 是否开启API文档
     */
    private Boolean docEnable=true;
    /**
     * 始终放行的请求
     */
    private Set<String> permitAll;
    /**
     * 忽略签名的请求
     */
    private Set<String> ignoreSign;


}
