package com.tml.gateway.oauth2.handler;

import com.alibaba.fastjson.JSONObject;
import com.tml.common.api.CommonResult;
import com.tml.common.exception.GlobalExceptionHandler;
import com.tml.gateway.service.IGatewayRouteEnhanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @Description 网关认证异常处理,记录日志
 * @Author TuMingLong
 * @Date 2020/8/2
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FastAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private final IGatewayRouteEnhanceService gatewayRouteEnhanceService;

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        CommonResult commonResult = GlobalExceptionHandler.resolveException(e,exchange.getRequest().getURI().getPath());
        return Mono.defer(() -> {
            return Mono.just(exchange.getResponse());
        }).flatMap((response) -> {
            response.setStatusCode(HttpStatus.valueOf(commonResult.getHttpStatus()));
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(commonResult).getBytes(Charset.defaultCharset()));
            // 保存日志
            log.info("网关认证异常处理,记录日志");
            gatewayRouteEnhanceService.saveRouteLog(exchange,e);
            return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
                DataBufferUtils.release(buffer);
            });
        });
    }
}
