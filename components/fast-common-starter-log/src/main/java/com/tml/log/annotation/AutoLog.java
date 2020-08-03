package com.tml.log.annotation;

import java.lang.annotation.*;

/**
 * @Description 系统日志注解
 * @Author TuMingLong
 * @Date 2020/6/3 17:52
 */
//1、RetentionPolicy.SOURCE 注解只保留在源文件中，在编译成class文件的时候被遗弃
//2、RetentionPolicy.CLASS 注解被保留在class中，但是在jvm加载的时候北欧抛弃，这个是默认的声明周期
//3、RetentionPolicy.RUNTIME 注解在jvm加载的时候仍被保留
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {

    /**
     * 描述
     *
     * @return
     */
    String description() default "";
}
