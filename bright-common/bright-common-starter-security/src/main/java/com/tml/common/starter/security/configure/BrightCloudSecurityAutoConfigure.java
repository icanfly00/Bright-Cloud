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
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.util.Base64Utils;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(BrightCloudSecurityProperties.class)
@ConditionalOnProperty(value = "bright.cloud.security.enable", havingValue = "true", matchIfMissing = true)
public class BrightCloudSecurityAutoConfigure extends GlobalMethodSecurityConfiguration {

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
    @Primary
    @ConditionalOnMissingBean(DefaultTokenServices.class)
    public BrightUserInfoTokenServices brightUserInfoTokenServices(ResourceServerProperties properties) {
        return new BrightUserInfoTokenServices(properties.getUserInfoUri(), properties.getClientId());
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

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }
}
