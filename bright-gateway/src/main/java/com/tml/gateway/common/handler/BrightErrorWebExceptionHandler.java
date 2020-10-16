package com.tml.gateway.common.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.common.core.entity.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 全局异常处理
 * 网关异常通用处理器，只作用在webflux 环境下 , 优先级低于 ResponseStatusExceptionHandler执行
 * @since 2020/10/16 13:29
 */
@Slf4j
@Order(-1)
@Configuration
@RequiredArgsConstructor
public class BrightErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }

        /**
         * token失效重新登录
         */
        if (ex instanceof InvalidTokenException) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
        }

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(new CommonResult().message(ex.getMessage())));
            } catch (JsonProcessingException e) {
                log.error("Error writing response：{}", ex);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
