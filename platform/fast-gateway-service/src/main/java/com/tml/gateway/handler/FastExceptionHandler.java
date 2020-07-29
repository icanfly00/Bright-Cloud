package com.tml.gateway.handler;

import com.tml.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;


/**
 * @Description 统一异常处理
 * @Author TuMingLong
 * @Date 2020/7/29 19:04
 */
@Slf4j
public class FastExceptionHandler extends AbstractErrorWebExceptionHandler {


    public FastExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, applicationContext);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        Throwable error = super.getError(request);
        log.error("请求发生异常，请求URI：{}，请求方法：{}，异常信息：{}",
                request.path(), request.methodName(), error.getMessage());
        String errorMessage;
        if (error instanceof NotFoundException) {
            String serverId = StringUtils.substringAfterLast(error.getMessage(), "Unable to find instance for ");
            serverId = StringUtils.replace(serverId, "\"", StringUtils.EMPTY);
            errorMessage = String.format("无法找到%s服务", serverId);
        } else if (StringUtils.containsIgnoreCase(error.getMessage(), "connection refused")) {
            errorMessage = "目标服务拒绝连接";
        } else if (error instanceof TimeoutException) {
            errorMessage = "访问服务超时";
        } else if (error instanceof ResponseStatusException
                && StringUtils.containsIgnoreCase(error.getMessage(), HttpStatus.NOT_FOUND.toString())) {
            errorMessage = "未找到该资源";
        } else {
            errorMessage = "网关转发异常";
        }

        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CommonResult.failed(errorMessage)));
    }

}
