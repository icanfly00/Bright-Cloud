package com.tml.gateway.runner;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.tml.gateway.service.IGatewayDynamicRouteService;
import com.tml.gateway.service.IGatewayRouteEnhanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Description com.tml.common.runner
 * @Author TuMingLong
 * @Date 2020/6/4 11:40
 */
@Component
@RequiredArgsConstructor
public class BootstrapRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;

    private final Environment environment;

    private final IGatewayDynamicRouteService gatewayDynamicRouteService;

    private final IGatewayRouteEnhanceService gatewayRouteEnhanceService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (context.isActive()) {
            gatewayDynamicRouteService.loadRoue();
            gatewayRouteEnhanceService.loadAllBlackListCache();
            gatewayRouteEnhanceService.loadAllRouteLimitRuleCache();

            String banner = "-----------------------------------------\n" +
                    "服务启动成功，时间：" + DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_PATTERN) + "\n" +
                    "服务名称：" + environment.getProperty("spring.application.name") + "\n" +
                    "端口号：" + environment.getProperty("server.port") + "\n" +
                    "-----------------------------------------";
            System.out.println(banner);
        }
    }
}
