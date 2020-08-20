package com.tml.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.GatewayDynamicRoute;
import com.tml.common.core.entity.QueryRequest;

import java.util.List;

/**
 * 动态路由配置表 Service接口
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:27
 */
public interface IGatewayDynamicRouteService extends IService<GatewayDynamicRoute> {
    /**
     * 查询（分页）
     *
     * @param request             QueryRequest
     * @param gatewayDynamicRoute gatewayDynamicRoute
     * @return IPage<GatewayDynamicRoute>
     */
    IPage<GatewayDynamicRoute> pageGatewayDynamicRoute(QueryRequest request, GatewayDynamicRoute gatewayDynamicRoute);

    /**
     * 查询（所有）
     *
     * @param gatewayDynamicRoute gatewayDynamicRoute
     * @return List<GatewayDynamicRoute>
     */
    List<GatewayDynamicRoute> listGatewayDynamicRoute(GatewayDynamicRoute gatewayDynamicRoute);

    /**
     * 检查路由ID
     *
     * @param routeId
     * @return
     */
    boolean checkRouteId(String routeId);

    /**
     * 新增
     *
     * @param gatewayDynamicRoute gatewayDynamicRoute
     */
    void saveGatewayDynamicRoute(GatewayDynamicRoute gatewayDynamicRoute);

    /**
     * 修改
     *
     * @param gatewayDynamicRoute gatewayDynamicRoute
     */
    void updateGatewayDynamicRoute(GatewayDynamicRoute gatewayDynamicRoute);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteGatewayDynamicRoute(String[] ids);

    /**
     * 缓存动态路由
     */
    void cacheGatewayDynamicRoute();
}
