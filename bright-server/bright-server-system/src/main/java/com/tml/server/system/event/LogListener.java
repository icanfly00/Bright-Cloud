package com.tml.server.system.event;

import com.tml.server.system.service.ISysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author 注解形式的监听 异步监听日志事件
 * @version 1.0
 * @description 系统日志事件
 * @since 2020-08-20 20:30
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogListener {

    private final ISysLogService logService;

    @EventListener(LogEvent.class)
    public void saveSysLog(LogEvent event) {
       Map<String,Object> params= (Map<String, Object>) event.getSource();

        // 保存日志
        logService.saveLog((ProceedingJoinPoint)params.get("point"),
                (Method)params.get("targetMethod"),
                params.get("ip").toString(),
                params.get("operation").toString(),
                params.get("username").toString(),
                Long.valueOf(params.get("start").toString()));
    }
}
