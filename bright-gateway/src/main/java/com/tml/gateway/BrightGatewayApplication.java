package com.tml.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@SpringBootApplication
public class BrightGatewayApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BrightGatewayApplication.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}
