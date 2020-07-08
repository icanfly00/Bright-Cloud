package com.tml.common.redis.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @Description Redsson配置
 * @Author TuMingLong
 * @Date 2020/4/1 12:26
 */
@ConditionalOnProperty(prefix = "hdw.redisson", name = "enabled", havingValue = "true")
public class RedissonConfiguration {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient RedissonClient() throws IOException {
        Config config = Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
