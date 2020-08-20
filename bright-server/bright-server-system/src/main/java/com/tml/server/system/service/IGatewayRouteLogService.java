package com.tml.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.GatewayRouteLog;
import com.tml.common.core.entity.QueryRequest;

import java.util.List;

/**
 * 网关日志 Service接口
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:06
 */
public interface IGatewayRouteLogService extends IService<GatewayRouteLog> {
    /**
     * 查询（分页）
     *
     * @param request         QueryRequest
     * @param gatewayRouteLog gatewayRouteLog
     * @return IPage<GatewayRouteLog>
     */
    IPage<GatewayRouteLog> pageGatewayRouteLog(QueryRequest request, GatewayRouteLog gatewayRouteLog);

    /**
     * 查询（所有）
     *
     * @param gatewayRouteLog gatewayRouteLog
     * @return List<GatewayRouteLog>
     */
    List<GatewayRouteLog> listGatewayRouteLog(GatewayRouteLog gatewayRouteLog);

    /**
     * 新增
     *
     * @param gatewayRouteLog gatewayRouteLog
     */
    void saveGatewayRouteLog(GatewayRouteLog gatewayRouteLog);

    /**
     * 修改
     *
     * @param gatewayRouteLog gatewayRouteLog
     */
    void updateGatewayRouteLog(GatewayRouteLog gatewayRouteLog);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteGatewayRouteLog(String[] ids);
}
