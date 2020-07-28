package com.tml.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 断言模型
 * @Author TuMingLong
 * @Date 2020/7/15 14:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GatewayPredicateDefinition {
    /**
     * 断言名称
     */
    private String name;
    /**
     * 配置的断言规则
     */
    private Map<String, String> args = new LinkedHashMap<>();

}
