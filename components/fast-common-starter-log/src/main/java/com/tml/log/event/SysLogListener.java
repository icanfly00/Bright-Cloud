package com.tml.log.event;

import com.tml.log.entity.SysLog;
import com.tml.log.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import javax.annotation.Resource;

/**
 * @Description 注解形式的监听 异步监听日志事件
 * @Author TuMingLong
 * @Date 2020/6/4 11:13
 */
@Slf4j
public class SysLogListener {

    @Resource
    private ISysLogService sysLogService;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        // 保存日志
        sysLogService.save(sysLog);
    }
}
