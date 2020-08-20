package com.tml.auth;

import com.tml.common.starter.security.annotation.EnableBrightCloudResourceServer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableBrightCloudResourceServer
@EnableBinding(Processor.class)
public class BrightAuthApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BrightAuthApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
