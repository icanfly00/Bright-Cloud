package com.tml.common.security.configuration;

import cn.hutool.core.util.IdUtil;
import com.tml.common.security.constant.SecurityConstant;
import com.tml.common.security.converter.RestJwtAccessTokenConverter;
import com.tml.common.security.converter.RestJwtTokenService;
import com.tml.common.security.converter.RestRedisTokenService;
import com.tml.common.security.converter.RestUserAuthenticationConverter;
import com.tml.common.security.handler.RestAccessDeniedHandler;
import com.tml.common.security.handler.RestAuthenticationEntryPoint;
import com.tml.common.security.properties.RestSecurityProperties;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import javax.annotation.Resource;

/**
 * @Description com.tml.common.security.configuration
 * @Author TuMingLong
 * @Date 2020/7/2 17:44
 */
@Slf4j
@Configuration
public class RestSecurityAutoConfiguration {

    @Resource
    public RestSecurityProperties properties;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public RestAccessDeniedHandler accessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public RestAuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    /**
     * 令牌存储策略
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        //TODO: JWT令牌存储方式
        log.info("-----注入TokenStore-----");
        if (properties.getJwtEnable()) {
            return new JwtTokenStore(buildJwtAccessTokenConverter());
        } else {
            RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
            redisTokenStore.setAuthenticationKeyGenerator(oAuth2Authentication -> IdUtil.fastUUID());
            redisTokenStore.setPrefix(properties.getOauthPrefix());
            return redisTokenStore;
        }
    }

    /**
     * 构建token转换器
     *
     * @return
     */
    @Bean
    public DefaultAccessTokenConverter buildAccessTokenConverter() {
        log.info("-----注入DefaultAccessTokenConverter-----");
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        RestUserAuthenticationConverter userAuthenticationConverter = new RestUserAuthenticationConverter();
        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        return accessTokenConverter;
    }

    /**
     * 构建jwt token转换器
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter buildJwtAccessTokenConverter() {
        log.info("-----注入JwtAccessTokenConverter-----");
        RestJwtAccessTokenConverter converter = new RestJwtAccessTokenConverter();
        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        RestUserAuthenticationConverter userAuthenticationConverter = new RestUserAuthenticationConverter();
        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        converter.setAccessTokenConverter(accessTokenConverter);
        converter.setSigningKey(properties.getJwtSigningKey());//对称秘钥，资源服务器使用该秘钥验证
        return converter;
    }

    /**
     * token服务
     *
     * @return
     */
    @Bean
    public ResourceServerTokenServices resourceServerTokenServices() {
        if (properties.getJwtEnable()) {
            RestJwtTokenService tokenService = new RestJwtTokenService();
            tokenService.setTokenStore(tokenStore());
            tokenService.setJwtAccessTokenConverter(buildJwtAccessTokenConverter());
            tokenService.setDefaultAccessTokenConverter(buildAccessTokenConverter());
            return tokenService;
        } else {
            RestRedisTokenService tokenService = new RestRedisTokenService();
            tokenService.setTokenStore(tokenStore());
            return tokenService;
        }
    }

    /**
     * Feign 请求拦截器
     *
     * @return
     */
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                requestTemplate.header(HttpHeaders.AUTHORIZATION,
                        String.format("%s %s"), SecurityConstant.OAUTH2_TOKEN_TYPE, details.getTokenValue());
            }
        };
    }
}
