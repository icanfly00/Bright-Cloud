package com.tml.gateway.common.runner;

import com.google.common.collect.Lists;
import com.tml.api.system.dto.RouteDefinitionDTO;
import com.tml.common.core.entity.constant.CacheConstant;
import com.tml.common.core.utils.BrightUtil;
import com.tml.common.core.utils.JacksonUtil;
import com.tml.common.starter.redis.service.RedisService;
import com.tml.gateway.enhance.service.impl.GatewayDynamicRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BootstrapRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final Environment environment;
    private final RedisService redisService;
    private final GatewayDynamicRouteService dynamicRouteService;

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive()) {
            loadDynamicRoute();
            BrightUtil.printSystemUpBanner(environment);
        }
    }

    /**
     * 加载动态路由
     */
    public void loadDynamicRoute() {
        Set<Object> set = redisService.sGet(CacheConstant.GATEWAY_ROUTE_CACHE);
        if (set != null && set.size() > 0) {
            set.stream().forEach(o -> {
                RouteDefinitionDTO definitionDTO = JacksonUtil.toObject(o.toString(), RouteDefinitionDTO.class);
                RouteDefinition routeDefinition = getRouteDefinition(definitionDTO);
                dynamicRouteService.addRoute(routeDefinition);
            });
            dynamicRouteService.refreshRoute();
        }
    }

    private RouteDefinition getRouteDefinition(RouteDefinitionDTO definitionDTO) {
        List<PredicateDefinition> predicates = Lists.newArrayList();
        definitionDTO.getPredicates().stream().forEach(dto -> {
                    PredicateDefinition predicateDefinition = new PredicateDefinition();
                    BeanUtils.copyProperties(dto, predicateDefinition);
                    predicates.add(predicateDefinition);
                }
        );

        List<FilterDefinition> filters = Lists.newArrayList();
        definitionDTO.getFilters().stream().forEach(dto -> {
                    FilterDefinition filterDefinition = new FilterDefinition();
                    BeanUtils.copyProperties(dto, filterDefinition);
                    filters.add(filterDefinition);
                }
        );
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(definitionDTO.getId());
        routeDefinition.setUri(definitionDTO.getUri());
        routeDefinition.setOrder(definitionDTO.getOrder());
        routeDefinition.setPredicates(predicates);
        routeDefinition.setFilters(filters);
        routeDefinition.setMetadata(definitionDTO.getMetadata());
        return routeDefinition;
    }
}
