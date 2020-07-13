package com.tml.gateway.controller;

import com.tml.common.api.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * @Description 响应超时熔断处理器
 * @Author TuMingLong
 * @Date 2020/7/8 13:39
 */
public class FallbackController {

    @RequestMapping("/fallback")
    public Mono<CommonResult> fallback() {
        return Mono.just(CommonResult.failed("服务访问超时，请稍后再试"));
    }
}
