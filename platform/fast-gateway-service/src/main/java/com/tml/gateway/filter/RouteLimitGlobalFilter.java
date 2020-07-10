package com.tml.gateway.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @Description 限流全局过滤器
 * @Author TuMingLong
 * @Date 2020/7/10 10:46
 */
@Slf4j
public class RouteLimitGlobalFilter implements GlobalFilter, Ordered {
    //TODO: 桶的最大容量，即能装载Token的最大数量
    int capacity=1000000;
    //每次Token补充量
    int refillTokens = 1;
    //补充Token的时间间隔
    Duration duration = Duration.ofSeconds(1);

    private Bucket createNewBucket()
    {

        Refill refill = Refill.greedy(refillTokens, duration);
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("----RouteLimitGlobalFilter init----");
        String uri=exchange.getRequest().getURI().getPath();

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }


}
