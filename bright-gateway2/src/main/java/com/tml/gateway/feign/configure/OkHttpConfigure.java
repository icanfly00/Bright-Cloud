package com.tml.gateway.feign.configure;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author TuMingLong
 * @description OkHttp配置
 * @sence 2020/3/8 15:36
 */
@Slf4j
@Configuration
public class OkHttpConfigure {

    @Bean
    public OkHttpClient okHttpClient() {
        log.info("-----OkHttpClient初始化连接池-----");
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(10, 5L, TimeUnit.MINUTES))
                .addInterceptor(new OkHttpLogInterceptor())
                .build();
    }
}
