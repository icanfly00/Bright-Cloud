package com.tml.gateway.handler;

import com.tml.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Description 统一异常处理
 * @Author TuMingLong
 * @Date 2020/7/8 16:06
 */
@Slf4j
@RestControllerAdvice
public class FastGatewayGlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public CommonResult handle(Exception e) {
        String message = e.getMessage();
        String className = e.getClass().getName();
        if (className.contains(" NotFoundException")) {
            String serverId = StringUtils.substringAfterLast(e.getMessage(), "Unable to find instance for ");
            serverId = StringUtils.replace(serverId, "\"", StringUtils.EMPTY);
            message = String.format("无法找到%s服务", serverId);
        } else if (StringUtils.containsIgnoreCase(e.getMessage(), "connection refused")) {
            message = "目标服务拒绝连接";
        } else if (className.contains("TimeoutException")) {
            message = "访问服务超时";
        } else if ( className.contains("ResponseStatusException")
                && StringUtils.containsIgnoreCase(e.getMessage(), HttpStatus.NOT_FOUND.toString())) {
            message = "未找到该资源";
        } else {
            message = "网关转发异常";
        }
        return CommonResult.failed(message);
    }
}
