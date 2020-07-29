package com.tml.gateway.filter;

import com.tml.gateway.service.IGatewayRouteEnhanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description 自定义GlobalFilter
 * @Author TuMingLong
 * @Date 2020/7/28 18:00
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private final IGatewayRouteEnhanceService gatewayRouteEnhanceService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Mono<Void> blackListResult = gatewayRouteEnhanceService.filterBackList(exchange);
        if (blackListResult != null) {
            gatewayRouteEnhanceService.saveBlackListLog(exchange);
            return blackListResult;
        }
        Mono<Void> routeLimitResult = gatewayRouteEnhanceService.filterRateLimit(exchange);
        if (routeLimitResult != null) {
            gatewayRouteEnhanceService.saveRateLimitLog(exchange);
            return routeLimitResult;
        }
        gatewayRouteEnhanceService.saveRouteLog(exchange);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
