package com.tml.gateway.service.feign;

import com.tml.common.api.CommonResult;
import com.tml.system.entity.*;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;


/**
 * @Description com.tml.gateway.service.feign
 * @Author TuMingLong
 * @Date 2020/7/10 16:03
 */
@Slf4j
@Configuration
public class GatewayEnhanceFeignServiceFallbackFactory implements FallbackFactory<GatewayEnhanceFeignService> {

    @Override
    public GatewayEnhanceFeignService create(Throwable throwable) {

        return new GatewayEnhanceFeignService() {

            @Override
            public CommonResult<Integer> loadAllBackList() {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<Integer> loadAllRouteLimitRule() {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<Integer> loadAllRoute() {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<GatewayBlackList> findGatewayBlackList(String ip, String requestUri, String requestMethod) {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<GatewayRouteLimitRule> findGatewayRouteLimitRule(String requestUri, String requestMethod) {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<Integer> addGatewayRouteLog(GatewayRouteLog gatewayRouteLog) {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<Integer> addGatewayBackListLog(GatewayBlackListLog gatewayBlackListLog) {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<Integer> addGatewayRouteLimitLog(GatewayRouteLimitLog gatewayRouteLimitLog) {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }
        };
    }
}
