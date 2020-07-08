package com.tml.system;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.tml.common.security.annotation.EnableFastCloudResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@RefreshScope
@EnableFastCloudResourceServer
public class FastSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastSystemApplication.class, args);
    }

}
