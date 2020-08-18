package com.tml.gateway.enhance.configure;

import com.tml.gateway.enhance.runner.BrightRouteEnhanceRunner;
import com.tml.gateway.enhance.service.BlackListService;
import com.tml.gateway.enhance.service.RateLimitRuleService;
import com.tml.gateway.enhance.service.RouteEnhanceCacheService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@EnableAsync
@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.tml.gateway.enhance.mapper")
@ConditionalOnProperty(name = "bright.gateway.enhance", havingValue = "true")
public class BrightRouteEnhanceConfigure {

    @Resource
    private Environment env;

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        // 设置最大线程数
        executor.setMaxPoolSize(new Double(Math.floor(Runtime.getRuntime().availableProcessors() / (1 - 0.9))).intValue());
        // 设置队列容量
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors());
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix(env.getProperty("spring.application.name"));
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationMillis(60);
        executor.initialize();
        return executor;
    }

    @Bean
    public ApplicationRunner brightRouteEnhanceRunner(RouteEnhanceCacheService cacheService,
                                                    BlackListService blackListService,
                                                    RateLimitRuleService rateLimitRuleService) {
        return new BrightRouteEnhanceRunner(cacheService, blackListService, rateLimitRuleService);
    }
}
