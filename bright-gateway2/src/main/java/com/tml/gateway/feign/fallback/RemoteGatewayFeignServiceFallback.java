package com.tml.gateway.feign.fallback;

import com.tml.api.system.entity.*;
import com.tml.common.core.entity.ResultBody;
import com.tml.gateway.feign.RemoteGatewayFeignService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020/8/13 15:10
 */
@Slf4j
@Component
public class RemoteGatewayFeignServiceFallback implements FallbackFactory<RemoteGatewayFeignService> {
    @Override
    public RemoteGatewayFeignService create(Throwable throwable) {
        return new RemoteGatewayFeignService() {
            @Override
            public ResultBody<List<GatewayBlockList>> listGatewayBlockList() {
                log.error("服务调用失败：[listGatewayBlockList] - {}", throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody<GatewayRouteLimitRule> getGatewayRouteLimitRule(String uri, String method) {
                log.error("服务调用失败：[getGatewayRouteLimitRule] - {}", throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody saveGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog) {
                log.error("服务调用失败：[saveGatewayBlockListLog] - {}", throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody saveGatewayRouteLimitRuleLog(GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog) {
                log.error("服务调用失败：[saveGatewayRouteLimitRuleLog] - {}", throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody saveGatewayRouteLog(GatewayRouteLog gatewayRouteLog) {
                log.error("服务调用失败：[saveGatewayRouteLog] - {}", throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody<Integer> getCurrentRequestCount(String uri, String ip) {
                log.error("服务调用失败：[getCurrentRequestCount] - {}", throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody setCurrentRequestCount(String uri, String ip, Long time) {
                log.error("服务调用失败：[setCurrentRequestCount] - {}", throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody incrCurrentRequestCount(String uri, String ip) {
                log.error("服务调用失败：[incrCurrentRequestCount] - {}", throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }
        };
    }
}
