package com.tml.gateway.enhance.service;

import com.tml.common.core.entity.constant.BrightConstant;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface RouteEnhanceService {

    /**
     * 根据黑名单规则进行过滤
     *
     * @param exchange ServerWebExchange
     * @return Mono<Void>
     */
    Mono<Void> filterBlockList(ServerWebExchange exchange);

    /**
     * 根据限流规则进行过滤
     *
     * @param exchange ServerWebExchange
     * @return Mono<Void>
     */
    Mono<Void> filterRateLimit(ServerWebExchange exchange);

    /**
     * 异步存储请求日志
     *
     * @param exchange ServerWebExchange
     */
    @Async(BrightConstant.ASYNC_POOL)
    void saveRouteLog(ServerWebExchange exchange);

    /**
     * 异步存储拦截日志
     *
     * @param exchange ServerWebExchange
     */
    @Async(BrightConstant.ASYNC_POOL)
    void saveBlockLog(ServerWebExchange exchange);

    /**
     * 异步存储限流日志
     *
     * @param exchange ServerWebExchange
     */
    @Async(BrightConstant.ASYNC_POOL)
    void saveRateLimitLog(ServerWebExchange exchange);
}
