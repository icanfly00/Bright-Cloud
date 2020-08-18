package com.tml.common.starter.security.configure;

import com.tml.common.starter.security.interceptor.BrightServerProtectInterceptor;
import com.tml.common.starter.security.properties.BrightCloudSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public class BrightCloudSecurityInterceptorConfigure implements WebMvcConfigurer {

    private BrightCloudSecurityProperties properties;

    @Autowired
    public void setProperties(BrightCloudSecurityProperties properties) {
        this.properties = properties;
    }

    @Bean
    public HandlerInterceptor brightServerProtectInterceptor() {
        BrightServerProtectInterceptor brightServerProtectInterceptor = new BrightServerProtectInterceptor();
        brightServerProtectInterceptor.setProperties(properties);
        return brightServerProtectInterceptor;
    }

    @Override
    @SuppressWarnings("all")
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(brightServerProtectInterceptor());
    }
}
