package com.tml.monitor.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class BrightAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrightAdminApplication.class, args);
    }

}
