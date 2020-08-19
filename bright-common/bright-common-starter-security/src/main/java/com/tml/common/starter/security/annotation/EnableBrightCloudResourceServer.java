package com.tml.common.starter.security.annotation;

import com.tml.common.starter.security.configure.BrightCloudResourceServerConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(BrightCloudResourceServerConfigure.class)
public @interface EnableBrightCloudResourceServer {
}
