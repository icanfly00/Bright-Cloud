package com.tml.common.core.entity.constant;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 缓存常量
 * @since 2020/8/12 11:07
 */
public interface CacheConstant {
    /**
     * oauth2.0客户端缓存
     */
    String CLIENT_DETAILS_CACHE = "oauth:client:details";
    /**
     * 网关缓存
     */
    String GATEWAY_ROUTE_CACHE="gateway:route";
    /**
     * 黑名单缓存
     */
    String GATEWAY_BLOCK_LIST_CACHE="gateway:block:list";
    /**
     * 限流规则缓存
     */
    String GATEWAY_ROUTE_LIMIT_RULE_CACHE="gateway:route:limit:rule";
    /**
     * 限流次数缓存
     */
    String GATEWAY_ROUTE_LIMIT_COUNT_CACHE="gateway:route:limit:count";
    /**
     * 动态路由
     */
    String GATEWAY_DYNAMIC_ROUTE_CACHE="gateway:dynamic:route";

}
