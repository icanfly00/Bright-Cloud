package com.tml.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 过滤器模型
 * @Author TuMingLong
 * @Date 2020/7/15 14:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GatewayFilterDefinition {

    private String name;
    //对应的路由规则
    private Map<String, String> args = new LinkedHashMap<>();

}
