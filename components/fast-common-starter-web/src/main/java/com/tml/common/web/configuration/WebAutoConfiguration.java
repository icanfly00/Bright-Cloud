package com.tml.common.web.configuration;

import com.tml.common.web.mail.service.MailService;
import com.tml.common.web.xss.XssFilter;
import org.springframework.context.annotation.Bean;

/**
 * @Description com.tml.common.web.configuration
 * @Author TuMingLong
 * @Date 2020/7/2 15:42
 */
public class WebAutoConfiguration {

    @Bean
    public MailService mailService(){
        return new MailService();
    }

    @Bean
    public XssFilter xssFilter(){
        return new XssFilter();
    }

}
