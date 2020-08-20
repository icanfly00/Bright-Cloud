package com.tml.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.GatewayRouteLimitRuleLog;
import com.tml.common.core.entity.QueryRequest;

import java.util.List;

/**
 * 限流规则日志 Service接口
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:12
 */
public interface IGatewayRouteLimitRuleLogService extends IService<GatewayRouteLimitRuleLog> {
    /**
     * 查询（分页）
     *
     * @param request                  QueryRequest
     * @param gatewayRouteLimitRuleLog gatewayRouteLimitRuleLog
     * @return IPage<GatewayRouteLimitRuleLog>
     */
    IPage<GatewayRouteLimitRuleLog> pageGatewayRouteLimitRuleLog(QueryRequest request, GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog);

    /**
     * 查询（所有）
     *
     * @param gatewayRouteLimitRuleLog gatewayRouteLimitRuleLog
     * @return List<GatewayRouteLimitRuleLog>
     */
    List<GatewayRouteLimitRuleLog> listGatewayRouteLimitRuleLog(GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog);

    /**
     * 新增
     *
     * @param gatewayRouteLimitRuleLog gatewayRouteLimitRuleLog
     */
    void saveGatewayRouteLimitRuleLog(GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog);

    /**
     * 修改
     *
     * @param gatewayRouteLimitRuleLog gatewayRouteLimitRuleLog
     */
    void updateGatewayRouteLimitRuleLog(GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteGatewayRouteLimitRuleLog(String[] ids);
}
