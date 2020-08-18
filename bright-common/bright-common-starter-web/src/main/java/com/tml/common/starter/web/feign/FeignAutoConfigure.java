package com.tml.common.starter.web.feign;

import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;


/**
 * @description Feign OAuth2 request interceptor
 * @author TuMingLong
 * @sence 2020/5/9 15:14
 */
@Slf4j
public class FeignAutoConfigure {
    /**
     * 日志level有4个级别
     * 1.NONE，不记录任何日志。
     * 2.BASIC，仅记录请求方法和URL以及响应状态代码和执行时间。
     * 3.HEADERS，记录基本信息以及请求和响应标头。
     * 4.FULL,记录请求和响应的标题，正文和元数据
     */
    @Bean
    Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }

    @Bean
    public Encoder feignFormEncoder() {
        Encoder encoder = new SpringFormEncoder();
        log.info("-----注入Encoder-----");
        return encoder;
    }
}
