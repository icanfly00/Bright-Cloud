package com.tml.gateway.oauth2.handler;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * @Description 请求前缀过滤器,增加请求时间
 * @Author TuMingLong
 * @Date 2020/8/2
 */
public class PreRequestFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 添加自定义请求头
        ServerHttpRequest request  = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        ServerWebExchange build = exchange.mutate().request(request).response(response).build();
        // 添加请求时间
        build.getAttributes().put("requestTime", LocalDateTime.now());
        return chain.filter(build);
    }
}
