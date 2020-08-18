package com.tml.server.system.service;

import com.tml.api.system.entity.GatewayRouteLimitRule;

import com.tml.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 限流规则 Service接口
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:22
 */
public interface IGatewayRouteLimitRuleService extends IService<GatewayRouteLimitRule> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param gatewayRouteLimitRule gatewayRouteLimitRule
     * @return IPage<GatewayRouteLimitRule>
     */
    IPage<GatewayRouteLimitRule> pageGatewayRouteLimitRule(QueryRequest request, GatewayRouteLimitRule gatewayRouteLimitRule);

    /**
     * 查询（所有）
     *
     * @param gatewayRouteLimitRule gatewayRouteLimitRule
     * @return List<GatewayRouteLimitRule>
     */
    List<GatewayRouteLimitRule> listGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule);

    /**
     * 检查
     * @param requestUri 请求URI
     * @param requestMethod 请求方法
     * @return
     */
    boolean checkUriAndMethod(String requestUri,String requestMethod);

    /**
     * 新增
     *
     * @param gatewayRouteLimitRule gatewayRouteLimitRule
     */
    void saveGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule);

    /**
     * 修改
     *
     * @param gatewayRouteLimitRule gatewayRouteLimitRule
     */
    void updateGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule);

    /**
     * 删除
     *
     * @param gatewayRouteLimitRule gatewayRouteLimitRule
     */
    void deleteGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule);

    /**
     * 根据路径和方法获取
     * @param uri
     * @param method
     * @return
     */
    GatewayRouteLimitRule getGatewayRouteLimitRule(String uri, String method);

    /**
     * 缓存限流规则
     */
    void cacheGatewayRouteLimitRule();

    /**
     * 获取当前请求次数
     *
     * @param uri uri
     * @param ip  ip
     * @return 次数
     */
    int getCurrentRequestCount(String uri, String ip);

    /**
     * 设置请求次数
     *
     * @param uri  uri
     * @param ip   ip
     * @param time time
     */
    void setCurrentRequestCount(String uri, String ip, Long time);

    /**
     * 递增请求次数
     *
     * @param uri uri
     * @param ip  ip
     */
    void incrCurrentRequestCount(String uri, String ip);
}
