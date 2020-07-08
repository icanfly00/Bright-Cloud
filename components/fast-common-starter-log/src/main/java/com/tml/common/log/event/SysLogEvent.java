package com.tml.common.log.event;

import com.tml.common.log.entity.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * @Description 系统日志事件
 * @Author TuMingLong
 * @Date 2020/6/3 18:15
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(SysLog sysLog) {
        super(sysLog);
    }
}
