package com.tml.server.system.aspect;

import com.google.common.collect.Maps;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.system.annotation.ControllerEndpoint;
import com.tml.server.system.event.LogEvent;
import com.tml.server.system.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Aspect
@Slf4j
@Component
public class ControllerEndpointAspect extends BaseAspectSupport {

    private ThreadLocal<Map<String,Object>> logThreadLocal = new ThreadLocal<>();

    /**
     * 事件发布是由ApplicationContext对象管控的，我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布
     **/
    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ISysLogService logService;

    @Pointcut("execution(* com.tml.server.system.controller.*.*(..)) && @annotation(com.tml.server.system.annotation.ControllerEndpoint)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws BrightException {
        Object result;
        Method targetMethod = resolveMethod(point);
        ControllerEndpoint annotation = targetMethod.getAnnotation(ControllerEndpoint.class);
        String operation = annotation.operation();
        long start = System.currentTimeMillis();
        try {
            result = point.proceed();
            if (StringUtils.isNotBlank(operation)) {
                String username = BrightUtil.getCurrentUsername();
                String ip = BrightUtil.getHttpServletRequestIpAddress();
                Map<String,Object> params= Maps.newHashMap();
                params.put("point",point);
                params.put("targetMethod",targetMethod);
                params.put("ip",ip);
                params.put("operation",operation);
                params.put("username",username);
                params.put("start",start);
                //TODO:将当前Map保存到threadLocal
                logThreadLocal.set(params);

                //得到当前线程的map
                Map<String,Object> newParams=logThreadLocal.get();
                // 发布事件
                applicationContext.publishEvent(new LogEvent(newParams));
                //移除当前map
                logThreadLocal.remove();
            }
            return result;
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            String exceptionMessage = annotation.exceptionMessage();
            String message = throwable.getMessage();
            String error = BrightUtil.containChinese(message) ? exceptionMessage + "，" + message : exceptionMessage;
            throw new BrightException(error);
        }
    }
}



