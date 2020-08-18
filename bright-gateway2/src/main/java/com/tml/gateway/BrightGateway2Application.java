package com.tml.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

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
@EnableReactiveMongoRepositories(basePackages = "com.tml.gateway.enhance.mapper")
public class BrightGateway2Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BrightGateway2Application.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}
