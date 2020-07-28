package com.tml.gateway.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description 网关增强服务接口
 * 网关的职责包括路由、鉴权、限流、日志、监控、灰度发布等
 * @Author TuMingLong
 * @Date 2020/7/28 18:03
 */
public interface IGatewayRouteEnhanceService {
    /**
     * 按黑名单规则进行限流
     * @param exchange
     * @return
     */
    Mono<Void> filterBackList(ServerWebExchange exchange);

    /**
     * 根据黑名单规则进行过滤
     * @param exchange
     * @return
     */
    Mono<Void> filterRateLimit(ServerWebExchange exchange);

    /**
     * 异步存储黑名单拦截日志
     * @param exchange
     */
    @Async
    void saveBlackListLog(ServerWebExchange exchange);

    /**
     * 异步存储限流拦截日志
     * @param exchange
     */
    @Async
    void saveRateLimitLog(ServerWebExchange exchange);

    /**
     * 异步存储网关日志
     * @param exchange
     */
    @Async
    void saveRouteLog(ServerWebExchange exchange);



}
