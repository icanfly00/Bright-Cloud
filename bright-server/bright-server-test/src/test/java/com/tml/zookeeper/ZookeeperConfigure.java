package com.tml.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JacksonTu
 * @version 1.0
 * @description com.hdw.zookeeper
 * @since 2020/9/2 16:21
 */
@Configuration
public class ZookeeperConfigure {

    private String zkUrl="localhost:2181";

    @Bean
    public CuratorFramework getCuratorFramework(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(zkUrl)
                .namespace("curator")
                .retryPolicy(retryPolicy)
                .build();
        curatorFramework.start();
        return curatorFramework;
    }
}
