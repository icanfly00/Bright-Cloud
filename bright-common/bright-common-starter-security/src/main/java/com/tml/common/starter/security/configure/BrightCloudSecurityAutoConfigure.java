package com.tml.common.starter.security.configure;

import com.tml.common.core.entity.constant.BrightConstant;
import com.tml.common.core.utils.BrightUtil;
import com.tml.common.starter.security.handler.BrightAccessDeniedHandler;
import com.tml.common.starter.security.handler.BrightAuthExceptionEntryPoint;
import com.tml.common.starter.security.properties.BrightCloudSecurityProperties;
import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Base64Utils;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(BrightCloudSecurityProperties.class)
@ConditionalOnProperty(value = "bright.cloud.security.enable", havingValue = "true", matchIfMissing = true)
public class BrightCloudSecurityAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public BrightAccessDeniedHandler accessDeniedHandler() {
        return new BrightAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public BrightAuthExceptionEntryPoint authenticationEntryPoint() {
        return new BrightAuthExceptionEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BrightCloudSecurityInterceptorConfigure brightCloudSecurityInterceptorConfigure() {
        return new BrightCloudSecurityInterceptorConfigure();
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            String gatewayToken = new String(Base64Utils.encode(BrightConstant.GATEWAY_TOKEN_VALUE.getBytes()));
            requestTemplate.header(BrightConstant.GATEWAY_TOKEN_HEADER, gatewayToken);
            String authorizationToken = BrightUtil.getCurrentTokenValue();
            if (StringUtils.isNotBlank(authorizationToken)) {
                requestTemplate.header(HttpHeaders.AUTHORIZATION, BrightConstant.OAUTH2_TOKEN_TYPE + authorizationToken);
            }
        };
    }
}
