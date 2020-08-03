package com.tml.log.configuration;

import com.tml.log.aspect.AutoLogAspect;
import com.tml.log.event.SysLogListener;
import com.tml.log.service.ISysLogService;
import com.tml.log.service.impl.SysLogServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 日志配置类
 * @Author TuMingLong
 * @Date 2020/7/2 14:45
 */
@Configuration
public class LogConfiguration {

    @Bean(name="iSysLogService")
    public ISysLogService iSysLogService(){
        return new SysLogServiceImpl();
    }

    @Bean
    public AutoLogAspect autoLogAspect(){
        return new AutoLogAspect();
    }

    @Bean
    public SysLogListener sysLogListener(){
        return new SysLogListener();
    }
}
