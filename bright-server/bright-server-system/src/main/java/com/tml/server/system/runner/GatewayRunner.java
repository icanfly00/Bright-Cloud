package com.tml.server.system.runner;

import com.tml.server.system.service.IGatewayBlockListService;
import com.tml.server.system.service.IGatewayDynamicRouteService;
import com.tml.server.system.service.IGatewayRouteLimitRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author JacksonTu
 * @version 1.0
 * @description ApplicationRunner
 * @since 2020/8/13 14:55
 */
@Component
@RequiredArgsConstructor
public class GatewayRunner implements ApplicationRunner {

    private final IGatewayBlockListService blockListService;
    private final IGatewayRouteLimitRuleService routeLimitRuleService;
    private final IGatewayDynamicRouteService dynamicRouteService;

    @Override
    public void run(ApplicationArguments args) {
        blockListService.cacheGatewayBlockList();
        routeLimitRuleService.cacheGatewayRouteLimitRule();
        dynamicRouteService.cacheGatewayDynamicRoute();
        String banner = "-----------------------------------------\n" +
                "已缓存黑名单、限流规则、动态路由。" + "\n" +
                "-----------------------------------------";
        System.out.println(banner);
    }
}
