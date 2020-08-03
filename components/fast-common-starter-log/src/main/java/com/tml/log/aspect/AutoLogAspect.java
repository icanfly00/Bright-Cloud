package com.tml.log.aspect;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgentUtil;
import com.tml.common.api.CommonResult;
import com.tml.log.entity.SysLog;
import com.tml.log.event.SysLogEvent;
import com.tml.log.util.LogUtil;
import com.tml.common.entity.RestUserDetails;
import com.tml.common.util.SecurityUtil;
import com.tml.common.util.AddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Description 系统日志切面
 * @Author TuMingLong
 * @Date 2020/6/3 17:58
 */
@Slf4j
@Aspect
public class AutoLogAspect {

    public AutoLogAspect() {
        log.info("-----注入AutoLogAspect-----");
    }

    private ThreadLocal<SysLog> sysLogThreadLocal = new ThreadLocal<>();

    /**
     * 事件发布是由ApplicationContext对象管控的，我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布
     **/
    @Resource
    private ApplicationContext applicationContext;

    /***
     * 定义controller切入点拦截规则，拦截ControllerEndpoint注解的方法
     */
    @Pointcut("@annotation(com.tml.log.annotation.AutoLog)")
    public void sysLogAspect() {

    }

    /***
     * 拦截控制层的操作日志
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Before(value = "sysLogAspect()")
    public void recordLog(JoinPoint joinPoint) throws Throwable {
        SysLog sysLog = new SysLog();
        //将当前实体保存到threadLocal
        sysLogThreadLocal.set(sysLog);
        // 开始时间
        long beginTime = Instant.now().toEpochMilli();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        RestUserDetails securityUser = SecurityUtil.getUser();
        sysLog.setUsername(securityUser.getUsername());
        sysLog.setActionUrl(URLUtil.getPath(request.getRequestURI()));
        sysLog.setStartTime(LocalDateTime.now());
        String ip = ServletUtil.getClientIP(request);
        sysLog.setIp(ip);
        sysLog.setLocation(AddressUtil.getCityInfo(ip));
        sysLog.setRequestMethod(request.getMethod());
        String uaStr = request.getHeader("user-agent");
        sysLog.setBrowser(UserAgentUtil.parse(uaStr).getBrowser().toString());
        sysLog.setOs(UserAgentUtil.parse(uaStr).getOs().toString());

        //访问目标方法的参数 可动态改变参数值
        Object[] args = joinPoint.getArgs();
        //获取执行的方法名
        sysLog.setActionMethod(joinPoint.getSignature().getName());
        // 类名
        sysLog.setClassPath(joinPoint.getTarget().getClass().getName());
        sysLog.setActionMethod(joinPoint.getSignature().getName());
        sysLog.setFinishTime(LocalDateTime.now());
        // 参数
        sysLog.setParams(Arrays.toString(args));
        sysLog.setDescription(LogUtil.getControllerMethodDescription(joinPoint));
        long endTime = Instant.now().toEpochMilli();
        sysLog.setConsumingTime(endTime - beginTime);
    }

    /**
     * 返回通知
     *
     * @param obj
     * @throws Throwable
     */
    @AfterReturning(returning = "obj", pointcut = "sysLogAspect()")
    public void doAfterReturning(Object obj) {
        //得到当前线程的log对象
        SysLog sysLog = sysLogThreadLocal.get();
        // 处理完请求，返回内容
        CommonResult commonResult = Convert.convert(CommonResult.class, obj);
        if (commonResult.getCode() == 200) {
            // 正常返回
            sysLog.setType(1);
        } else {
            sysLog.setType(2);
            sysLog.setExDetail(commonResult.getMessage());
        }
        // 发布事件
        applicationContext.publishEvent(new SysLogEvent(sysLog));
        //移除当前log实体
        sysLogThreadLocal.remove();
    }

    /**
     * 异常通知
     *
     * @param e
     */
    @AfterThrowing(pointcut = "sysLogAspect()", throwing = "e")
    public void doAfterThrowable(Throwable e) {
        SysLog sysLog = sysLogThreadLocal.get();
        // 异常
        sysLog.setType(2);
        // 异常对象
        sysLog.setExDetail(LogUtil.getStackTrace(e));
        // 异常信息
        sysLog.setExDesc(e.getMessage());
        // 发布事件
        applicationContext.publishEvent(new SysLogEvent(sysLog));
        //移除当前log实体
        sysLogThreadLocal.remove();
    }
}
