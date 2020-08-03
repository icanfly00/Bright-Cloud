package com.tml.gateway.handler;

import com.tml.common.api.CommonResult;
import com.tml.common.api.ResultCode;
import com.tml.common.exception.GlobalExceptionHandler;
import com.tml.gateway.service.IGatewayRouteEnhanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.List;


/**
 * @Description 统一异常处理
 * @Author TuMingLong
 * @Date 2020/7/29 19:04
 */
@Slf4j
public class FastExceptionHandler implements ErrorWebExceptionHandler {

    private IGatewayRouteEnhanceService gatewayRouteEnhanceService;

    public FastExceptionHandler(IGatewayRouteEnhanceService gatewayRouteEnhanceService) {
        this.gatewayRouteEnhanceService = gatewayRouteEnhanceService;
    }

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * 存储处理异常后的信息
     */
    private ThreadLocal<CommonResult> exceptionHandlerResult = new ThreadLocal<>();

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageReaders
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param viewResolvers
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageWriters
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        /**
         * 按照异常类型进行处理
         */
        CommonResult commonResult;
        ServerHttpRequest request = exchange.getRequest();
        if("/favicon.ico".equals(exchange.getRequest().getURI().getPath())){
            return Mono.empty();
        }
        if (ex instanceof NotFoundException) {
            commonResult = CommonResult.failed(ResultCode.SERVICE_UNAVAILABLE.getCode(),ResultCode.SERVICE_UNAVAILABLE.getMessage(),request.getURI().getPath(),HttpStatus.SERVICE_UNAVAILABLE.value());
            log.error("错误解析:{}", commonResult);
        } else {
            commonResult = GlobalExceptionHandler.resolveException((Exception) ex, exchange.getRequest().getURI().getPath());
        }
        /**
         * 参考AbstractErrorWebExceptionHandler
         */
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        exceptionHandlerResult.set(commonResult);
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> {
                    return write(exchange, response,ex);
                });
    }

    /**
     * 参考DefaultErrorWebExceptionHandler
     *
     * @param request
     * @return
     */
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        CommonResult commonResult = exceptionHandlerResult.get();
        return ServerResponse.status(commonResult.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(commonResult));
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param exchange
     * @param response
     * @return
     */
    private Mono<? extends Void> write(ServerWebExchange exchange,
                                       ServerResponse response, Throwable ex) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        // 保存日志
        gatewayRouteEnhanceService.saveRouteLog(exchange, (Exception) ex);
        return response.writeTo(exchange, new FastExceptionHandler.ResponseContext());
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return FastExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return FastExceptionHandler.this.viewResolvers;
        }

    }
}
