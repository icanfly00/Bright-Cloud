package com.tml.gateway.runner;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.tml.common.api.CommonResult;
import com.tml.common.api.ResultCode;
import com.tml.gateway.service.feign.GatewayEnhanceFeignService;
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

    private final GatewayEnhanceFeignService gatewayEnhanceFeignService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CommonResult<Integer> commonResult = gatewayEnhanceFeignService.loadAllRoute();
        CommonResult<Integer> commonResult2 = gatewayEnhanceFeignService.loadAllRouteLimitRule();
        CommonResult<Integer> commonResult3 = gatewayEnhanceFeignService.loadAllBackList();
        int count = commonResult.getCode() == ResultCode.FAILED.getCode() ? 0 : commonResult.getData();
        int count2 = commonResult2.getCode() == ResultCode.FAILED.getCode() ? 0 : commonResult.getData();
        int count3 = commonResult3.getCode() == ResultCode.FAILED.getCode() ? 0 : commonResult.getData();

        if (context.isActive()) {
            String banner = "-----------------------------------------\n" +
                    "服务启动成功，时间：" + DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_PATTERN) + "\n" +
                    "服务名称：" + environment.getProperty("spring.application.name") + "\n" +
                    "端口号：" + environment.getProperty("server.port") + "\n" +
                    "已开启网关增强功能：请求日志、动态网关、黑名单、限流" + "\n" +
                    "动态网关数：" + count + "\n" +
                    "限流数：" + count2 + "\n" +
                    "黑名单数：" + count3 + "\n" +
                    "-----------------------------------------";
            System.out.println(banner);
        }
    }
}
