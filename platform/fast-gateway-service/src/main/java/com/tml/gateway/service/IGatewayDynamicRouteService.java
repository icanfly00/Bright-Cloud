package com.tml.gateway.service;

import com.tml.common.web.service.IBaseService;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayDynamicRouteDto;
import com.tml.gateway.entity.GatewayDynamicRoute;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * @Description 动态路由配置 服务层
 * @Author TuMingLong
 * @Date 2020/7/28 14:34
 */
public interface IGatewayDynamicRouteService extends IBaseService<GatewayDynamicRoute> {

    /**
     * 按条件分页
     *
     * @param dynamicRouteDto
     * @return
     */
    PageVo<GatewayDynamicRoute> pageList(GatewayDynamicRouteDto dynamicRouteDto);

    /**
     * 添加路由
     */
    boolean add(GatewayDynamicRoute dynamicRoute);

    /**
     * 更新路由
     *
     * @param dynamicRoute
     * @return
     */
    boolean update(GatewayDynamicRoute dynamicRoute);

    /**
     * 删除路由
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 刷新路由
     *
     * @return
     */
    boolean refresh();

    /**
     * 获取路由信息
     */
    List<RouteDefinition> getRouteList();

    /**
     * 加载路由
     */
    void loadRoue();

    /**
     * 路由模型
     *
     * @param dynamicRoute
     * @return
     */
    RouteDefinition getRouteDefinition(GatewayDynamicRoute dynamicRoute);

}
