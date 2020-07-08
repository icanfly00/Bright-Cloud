package com.tml.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
@RefreshScope
public class FastAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastAdminApplication.class, args);
    }

}
