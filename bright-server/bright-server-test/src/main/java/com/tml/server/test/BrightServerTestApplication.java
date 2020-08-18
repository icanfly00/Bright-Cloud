package com.tml.server.test;

import com.tml.common.starter.security.annotation.EnableBrightCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableBrightCloudResourceServer
@MapperScan("com.tml.server.test.mapper")
public class BrightServerTestApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BrightServerTestApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
