package com.tml.gateway.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 过滤器模型
 * @Author TuMingLong
 * @Date 2020/7/15 14:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GatewayFilterDefinition {
    /**
     * 过滤器名称
     */
    private String name;
    /**
     * 配置的路由规则
     */
    private Map<String, String> args = new LinkedHashMap<>();

}
