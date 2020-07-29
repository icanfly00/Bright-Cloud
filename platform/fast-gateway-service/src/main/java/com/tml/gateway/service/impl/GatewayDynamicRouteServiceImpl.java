package com.tml.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayDynamicRouteDto;
import com.tml.gateway.dto.GatewayRouteDefinition;
import com.tml.gateway.entity.GatewayDynamicRoute;
import com.tml.gateway.mapper.GatewayDynamicRouteMapper;
import com.tml.gateway.service.IGatewayDynamicRouteService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 动态路由配置 服务层 实现类
 * @Author TuMingLong
 * @Date 2020/7/28 14:35
 */
@Service
public class GatewayDynamicRouteServiceImpl extends BaseServiceImpl<GatewayDynamicRouteMapper, GatewayDynamicRoute> implements IGatewayDynamicRouteService {

    public static final String HTTP = "http";

    @Resource
    private DynamicRouteServiceImpl dynamicRouteService;

    @Override
    public PageVo<GatewayDynamicRoute> pageList(GatewayDynamicRouteDto dynamicRouteDto) {
        Page<GatewayDynamicRoute> page = new Page<>(dynamicRouteDto.getPage(), dynamicRouteDto.getLimit());
        QueryWrapper<GatewayDynamicRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNoneBlank(dynamicRouteDto.getRouteId()), GatewayDynamicRoute::getRouteId, dynamicRouteDto.getRouteId())
                .like(StringUtils.isNoneBlank(dynamicRouteDto.getRouteName()), GatewayDynamicRoute::getRouteName, dynamicRouteDto.getRouteName())
        ;
        IPage<GatewayDynamicRoute> iPage = this.page(page, queryWrapper);
        return new PageVo<>(iPage);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean add(GatewayDynamicRoute dynamicRoute) {
        boolean flag = this.save(dynamicRoute);
        if (flag) {
            if (dynamicRoute.getEnable() == 1) {
                RouteDefinition routeDefinition = getRouteDefinition(dynamicRoute);
                boolean routeFlag = dynamicRouteService.addRoute(routeDefinition);
                if (routeFlag) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;

    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean update(GatewayDynamicRoute dynamicRoute) {
        boolean flag = this.updateById(dynamicRoute);
        if (flag) {
            RouteDefinition routeDefinition = getRouteDefinition(dynamicRoute);
            if (dynamicRoute.getEnable() == 1) {
                boolean routeFlag = dynamicRouteService.updateRoute(routeDefinition);
                if (routeFlag) {
                    return true;
                }
            } else {
                boolean routeFlag = dynamicRouteService.deleteRoute(routeDefinition.getId());
                if (routeFlag) {
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean delete(Long id) {
        GatewayDynamicRoute dynamicRoute = this.getById(id);
        if (dynamicRoute != null) {
            boolean routeFlag = dynamicRouteService.deleteRoute(dynamicRoute.getRouteId());
            if (routeFlag) {
                this.removeById(id);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean refresh() {
        return dynamicRouteService.refreshRoute();
    }

    @Override
    public List<RouteDefinition> getRouteList() {
        QueryWrapper<GatewayDynamicRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GatewayDynamicRoute::getEnable, 1);
        List<GatewayDynamicRoute> list = this.list(queryWrapper);
        List<RouteDefinition> routeDefinitions = Lists.newArrayList();
        if (!list.isEmpty()) {
            routeDefinitions = list.stream().map(gatewayDynamicRoute -> {
                RouteDefinition routeDefinition = getRouteDefinition(gatewayDynamicRoute);
                return routeDefinition;
            }).collect(Collectors.toList());
        }

        return routeDefinitions;
    }

    @Override
    public void loadRoue() {
        List<RouteDefinition> list = getRouteList();
        if (!list.isEmpty()) {
            list.stream().forEach(routeDefinition -> {
                dynamicRouteService.addRoute(routeDefinition);
            });
        }
    }

    @Override
    public RouteDefinition getRouteDefinition(GatewayDynamicRoute dynamicRoute) {
        GatewayRouteDefinition gatewayRouteDefinition = new GatewayRouteDefinition();
        gatewayRouteDefinition.setId(dynamicRoute.getRouteId());
        gatewayRouteDefinition.setUri(dynamicRoute.getRouteUri());
        gatewayRouteDefinition.setOrder(dynamicRoute.getRouteOrder());
        gatewayRouteDefinition.setPredicates(dynamicRoute.getPredicateDefinitionList());
        gatewayRouteDefinition.setFilters(dynamicRoute.getFilterDefinitionList());

        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayRouteDefinition.getId());
        routeDefinition.setOrder(gatewayRouteDefinition.getOrder());
        routeDefinition.setUri(getUri(gatewayRouteDefinition.getUri()));
        if (gatewayRouteDefinition.getPredicates() != null && gatewayRouteDefinition.getPredicates().size() > 0) {
            List<PredicateDefinition> predicates = gatewayRouteDefinition.getPredicates().stream()
                    .map(gatewayPredicateDefinition -> {
                        PredicateDefinition predicateDefinition = new PredicateDefinition();
                        predicateDefinition.setName(gatewayPredicateDefinition.getName());
                        predicateDefinition.setArgs(gatewayPredicateDefinition.getArgs());
                        return predicateDefinition;
                    }).collect(Collectors.toList());
            routeDefinition.setPredicates(predicates);
        }
        if (gatewayRouteDefinition.getFilters() != null && gatewayRouteDefinition.getFilters().size() > 0) {
            List<FilterDefinition> filters = gatewayRouteDefinition.getFilters().stream()
                    .map(gatewayFilterDefinition -> {
                        FilterDefinition filterDefinition = new FilterDefinition();
                        filterDefinition.setName(gatewayFilterDefinition.getName());
                        filterDefinition.setArgs(gatewayFilterDefinition.getArgs());
                        return filterDefinition;
                    }).collect(Collectors.toList());
            routeDefinition.setFilters(filters);
        }
        return routeDefinition;
    }

    private URI getUri(String uriStr) {
        URI uri;
        if (uriStr.startsWith(HTTP)) {
            //TODO: http地址
            uri = UriComponentsBuilder.fromHttpUrl(uriStr).build().toUri();
        } else {
            //TODO: 注册中心
            uri = UriComponentsBuilder.fromUriString(uriStr).build().toUri();
        }
        return uri;
    }
}
