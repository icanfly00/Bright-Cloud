package com.tml.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 路由模型
 * @Author TuMingLong
 * @Date 2020/7/15 14:58
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GatewayRouteDefinition {
    /**
     * 路由Id
     */
    private String id;
    /**
     * 路由断言集合配置
     */
    private List<GatewayPredicateDefinition> predicates = new ArrayList<>();
    /**
     * 路由过滤器集合配置
     */
    private List<GatewayFilterDefinition> filters = new ArrayList<>();
    /**
     * 路由规则转发的目标uri
     */
    private String uri;
    /**
     * 路由执行的顺序
     */
    private int order = 0;
    //此处省略get和set方法
}
