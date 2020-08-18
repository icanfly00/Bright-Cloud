package com.tml.gateway.enhance.service.impl;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 动态路由
 * @since 2020/8/15 15:49
 */
@Component
public class GatewayDynamicRouteService implements ApplicationEventPublisherAware {

    @Resource
    private RedisRouteDefinitionRepository routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 添加路由
     *
     * @param routeDefinition
     * @return
     */
    public boolean addRoute(RouteDefinition routeDefinition) {
        this.routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        return true;
    }

    /**
     * 更新路由
     *
     * @param routeDefinition
     * @return
     */
    public boolean updateRoute(RouteDefinition routeDefinition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            this.routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除路由
     *
     * @param id
     * @return
     */
    public boolean deleteRoute(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 刷新路由
     *
     * @return
     */
    public boolean refreshRoute() {
        try {
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
