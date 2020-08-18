package com.tml.server.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.tml.api.system.dto.FilterDefinitionDTO;
import com.tml.api.system.dto.PredicateDefinitionDTO;
import com.tml.api.system.dto.RouteDefinitionDTO;
import com.tml.api.system.entity.GatewayDynamicRoute;
import com.tml.common.core.entity.constant.CacheConstant;
import com.tml.common.core.utils.JacksonUtil;
import com.tml.common.starter.redis.service.RedisPubService;
import com.tml.common.starter.redis.service.RedisService;
import com.tml.server.system.mapper.GatewayDynamicRouteMapper;
import com.tml.server.system.service.IGatewayDynamicRouteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.common.core.entity.QueryRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * 动态路由配置表 Service实现
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:27
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GatewayDynamicRouteServiceImpl extends ServiceImpl<GatewayDynamicRouteMapper, GatewayDynamicRoute> implements IGatewayDynamicRouteService {

    private final RedisPubService redisPubService;

    private final RedisService redisService;

    @Override
    public IPage<GatewayDynamicRoute> pageGatewayDynamicRoute(QueryRequest request, GatewayDynamicRoute gatewayDynamicRoute) {
        LambdaQueryWrapper<GatewayDynamicRoute> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.like(StringUtils.isNoneBlank(gatewayDynamicRoute.getRouteName()),GatewayDynamicRoute::getRouteName,gatewayDynamicRoute.getRouteName())
                .eq(StringUtils.isNoneBlank(gatewayDynamicRoute.getRouteId()),GatewayDynamicRoute::getRouteId,gatewayDynamicRoute.getRouteId());
        Page<GatewayDynamicRoute> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<GatewayDynamicRoute> listGatewayDynamicRoute(GatewayDynamicRoute gatewayDynamicRoute) {
        LambdaQueryWrapper<GatewayDynamicRoute> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.like(StringUtils.isNoneBlank(gatewayDynamicRoute.getRouteName()),GatewayDynamicRoute::getRouteName,gatewayDynamicRoute.getRouteName())
                .eq(StringUtils.isNoneBlank(gatewayDynamicRoute.getRouteId()),GatewayDynamicRoute::getRouteId,gatewayDynamicRoute.getRouteId());
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean checkRouteId(String routeId) {
        LambdaQueryWrapper<GatewayDynamicRoute> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(routeId),GatewayDynamicRoute::getRouteId,routeId);
        int count=this.baseMapper.selectCount(queryWrapper);
        if(count>0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGatewayDynamicRoute(GatewayDynamicRoute gatewayDynamicRoute) {
        boolean save = this.save(gatewayDynamicRoute);
        if (save && gatewayDynamicRoute.getEnable()==1) {
            RouteDefinitionDTO dto = getRouteDefinition(gatewayDynamicRoute);
            redisPubService.publish("/redis/dynamicRoute", "admin", JacksonUtil.toJson(dto), 1);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGatewayDynamicRoute(GatewayDynamicRoute gatewayDynamicRoute) {
        boolean update = this.saveOrUpdate(gatewayDynamicRoute);
        if (update) {
            if(gatewayDynamicRoute.getEnable()==1){
                RouteDefinitionDTO dto = getRouteDefinition(gatewayDynamicRoute);
                redisPubService.publish("/redis/dynamicRoute", "admin", JacksonUtil.toJson(dto), 2);
            }
            if(gatewayDynamicRoute.getEnable()==0){
                redisPubService.publish("/redis/dynamicRoute", "admin", gatewayDynamicRoute.getRouteId(), 3);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGatewayDynamicRoute(GatewayDynamicRoute gatewayDynamicRoute) {
        boolean delete = this.removeById(gatewayDynamicRoute.getId());
        if (delete) {
            redisPubService.publish("/redis/dynamicRoute", "admin", gatewayDynamicRoute.getRouteId(), 3);
        }
    }

    @Override
    public void cacheGatewayDynamicRoute() {
        LambdaQueryWrapper<GatewayDynamicRoute> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(GatewayDynamicRoute::getEnable,1);
        List<GatewayDynamicRoute> list=this.list(lambdaQueryWrapper);

        if(list!=null && list.size()>0){
            String key= CacheConstant.GATEWAY_ROUTE_CACHE;
            redisService.del(key);
            list.stream().forEach(dynamicRoute -> {
                RouteDefinitionDTO dto=getRouteDefinition(dynamicRoute);
                redisService.sSet(key,JacksonUtil.toJson(dto));
            });
        }
    }

    public RouteDefinitionDTO getRouteDefinition(GatewayDynamicRoute dynamicRoute) {
        RouteDefinitionDTO routeDefinitionDTO = new RouteDefinitionDTO();
        routeDefinitionDTO.setId(dynamicRoute.getRouteId());
        routeDefinitionDTO.setUri(getUri(dynamicRoute.getRouteUri()));
        routeDefinitionDTO.setOrder(dynamicRoute.getRouteOrder());
        List<PredicateDefinitionDTO> predicateDefinitionDTOList = listPredicateDefinitionDTO(dynamicRoute);
        List<FilterDefinitionDTO> filterDefinitionDTOList = listFilterDefinitionDTO(dynamicRoute);
        if (predicateDefinitionDTOList != null && predicateDefinitionDTOList.size() > 0) {
            routeDefinitionDTO.setPredicates(predicateDefinitionDTOList);
        }
        if (filterDefinitionDTOList != null && filterDefinitionDTOList.size() > 0) {
            routeDefinitionDTO.setFilters(filterDefinitionDTOList);
        }
        return routeDefinitionDTO;
    }


    /**
     * 获取断言集合
     *
     * @return
     */
    private List<PredicateDefinitionDTO> listPredicateDefinitionDTO(GatewayDynamicRoute dynamicRoute) {
        if (StringUtils.isNoneBlank(dynamicRoute.getPredicates())) {
            return JSON.parseArray(dynamicRoute.getPredicates(), PredicateDefinitionDTO.class);
        }
        return null;
    }

    /**
     * 获取过滤器集合
     *
     * @return
     */
    private List<FilterDefinitionDTO> listFilterDefinitionDTO(GatewayDynamicRoute dynamicRoute) {
        if (StringUtils.isNoneBlank(dynamicRoute.getFilters())) {
            return JSON.parseArray(dynamicRoute.getFilters(), FilterDefinitionDTO.class);
        }
        return null;
    }

    private URI getUri(String uriStr) {
        URI uri;
        if (uriStr.startsWith("http")) {
            //TODO: http地址
            uri = UriComponentsBuilder.fromHttpUrl(uriStr).build().toUri();
        } else {
            //TODO: 注册中心
            uri = UriComponentsBuilder.fromUriString(uriStr).build().toUri();
        }
        return uri;
    }

}
