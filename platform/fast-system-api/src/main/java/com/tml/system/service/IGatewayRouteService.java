package com.tml.system.service;


import com.tml.common.web.service.IBaseService;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRouteDto;
import com.tml.system.entity.GatewayRoute;

import java.util.List;


/**
 * @Description 动态路由管理
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
public interface IGatewayRouteService extends IBaseService<GatewayRoute> {

    PageVo<GatewayRoute> pageList(GatewayRouteDto gatewayRouteDto);

    /**
     * 查询可用路由列表
     *
     * @return
     */
    List<GatewayRoute> findRouteList();

    /**
     * 获取路由信息
     *
     * @param routeId
     * @return
     */
    GatewayRoute getRoute(Long routeId);

    /**
     * 添加路由
     *
     * @param route
     */
    void addRoute(GatewayRoute route);

    /**
     * 更新路由
     *
     * @param route
     */
    void updateRoute(GatewayRoute route);

    /**
     * 删除路由
     *
     * @param routeId
     */
    void removeRoute(Long routeId);

    /**
     * 是否存在
     *
     * @param routeName
     * @return
     */
    Boolean isExist(String routeName);
}
