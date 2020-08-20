package com.tml.server.system.event;


import org.springframework.context.ApplicationEvent;
import java.util.Map;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 系统日志事件
 * @since 2020-08-20 20:30
 */
public class LogEvent extends ApplicationEvent {

    public LogEvent(Map<String,Object> params) {
        super(params);
    }
}
