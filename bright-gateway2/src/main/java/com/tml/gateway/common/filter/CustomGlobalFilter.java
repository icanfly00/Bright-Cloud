package com.tml.gateway.common.filter;

import com.tml.common.core.entity.constant.BrightConstant;
import com.tml.gateway.enhance.service.RouteEnhanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Slf4j
@Component
@Order(0)
@RequiredArgsConstructor
public class CustomGlobalFilter implements GlobalFilter {

    private final RouteEnhanceService routeEnhanceService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Value("${bright.gateway.enhance:false}")
    private Boolean routeEnhance;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (routeEnhance) {
            Mono<Void> blackListResult = routeEnhanceService.filterBlockList(exchange);
            if (blackListResult != null) {
                routeEnhanceService.saveBlockLog(exchange);
                return blackListResult;
            }
            Mono<Void> rateLimitResult = routeEnhanceService.filterRateLimit(exchange);
            if (rateLimitResult != null) {
                routeEnhanceService.saveRateLimitLog(exchange);
                return rateLimitResult;
            }
            routeEnhanceService.saveRouteLog(exchange);
        }

        byte[] token = Base64Utils.encode((BrightConstant.GATEWAY_TOKEN_VALUE).getBytes());
        String[] headerValues = {new String(token)};
        ServerHttpRequest newRequest = exchange.getRequest().mutate().header(BrightConstant.GATEWAY_TOKEN_HEADER, headerValues).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        return chain.filter(newExchange);
    }
}
