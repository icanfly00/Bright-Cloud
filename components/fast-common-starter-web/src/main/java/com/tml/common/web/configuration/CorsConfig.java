package com.tml.common.web.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Description 跨域配置
 * @Author TuMingLong
 * @Date 2019/11/1 12:03
 */
@Slf4j
public class CorsConfig {
    // 当前跨域请求最大有效时长。这里默认7天
    private long maxAge = 60 * 60 * 24 * 7;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*"); // 1 设置访问源地址
        corsConfig.addAllowedHeader("*"); // 2 设置访问源请求头
        corsConfig.addAllowedMethod("*"); // 3 设置访问源请求方法
        corsConfig.setMaxAge(maxAge);
        corsConfig.setAllowCredentials(true);
        return corsConfig;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }
}
