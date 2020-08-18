package com.tml.server.system.service;

import com.tml.api.system.entity.GatewayBlockListLog;

import com.tml.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 黑名单日志 Service接口
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:31
 */
public interface IGatewayBlockListLogService extends IService<GatewayBlockListLog> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param gatewayBlockListLog gatewayBlackListLog
     * @return IPage<GatewayBlockListLog>
     */
    IPage<GatewayBlockListLog> pageGatewayBlockListLog(QueryRequest request, GatewayBlockListLog gatewayBlockListLog);

    /**
     * 查询（所有）
     *
     * @param gatewayBlockListLog gatewayBlackListLog
     * @return List<GatewayBlockListLog>
     */
    List<GatewayBlockListLog> listGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog);

    /**
     * 新增
     *
     * @param gatewayBlockListLog gatewayBlackListLog
     */
    void saveGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog);

    /**
     * 修改
     *
     * @param gatewayBlockListLog gatewayBlackListLog
     */
    void updateGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog);

    /**
     * 删除
     *
     * @param gatewayBlockListLog gatewayBlackListLog
     */
    void deleteGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog);
}
