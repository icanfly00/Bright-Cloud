package com.tml.server.job;

import com.tml.common.starter.security.annotation.EnableBrightCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableBrightCloudResourceServer
@MapperScan("com.tml.server.job.mapper")
public class BrightServerJobApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BrightServerJobApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
