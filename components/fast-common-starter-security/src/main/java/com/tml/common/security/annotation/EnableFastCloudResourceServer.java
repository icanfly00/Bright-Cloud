package com.tml.common.security.annotation;

import com.tml.common.security.configuration.ResourceServerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description 开启资源服务器注解
 * @Author TuMingLong
 * @Date 2020/7/3 13:52
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ResourceServerConfiguration.class)
public @interface EnableFastCloudResourceServer {
}
