package com.tml.common.datasource.annotaiton;

import java.lang.annotation.*;

/**
 * @Description 数据权限注解
 * @Author TuMingLong
 * @Date 2020/6/3 14:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * mapper层需要数据权限过滤的方法名集合
     *
     * @return 方法名数组
     */
    String[] methods() default {};

    /**
     * mapper层需要数据过滤的方法名前缀，
     * 比如指定为find，表示所有以find开头的方法
     * 都会进行数据权限过滤
     *
     * @return 方法名前缀
     */
    String methodPrefix() default "";

    /**
     * 数据权限关联字段
     * 目前系统数据权限通过enterprise_id关联
     *
     * @return String
     */
    String field() default "enterprise_id";
}
