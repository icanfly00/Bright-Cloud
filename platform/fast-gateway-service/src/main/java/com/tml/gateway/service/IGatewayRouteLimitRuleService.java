package com.tml.gateway.service;


import com.tml.common.web.service.IBaseService;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayRouteLimitRuleDto;
import com.tml.gateway.entity.GatewayRouteLimitRule;

import java.util.List;

/**
 * @Description 限流规则 服务类接口
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
public interface IGatewayRouteLimitRuleService extends IBaseService<GatewayRouteLimitRule> {

    PageVo<GatewayRouteLimitRule> pageList(GatewayRouteLimitRuleDto gatewayRouteLimitRuleDto);

    GatewayRouteLimitRule findByCondition(GatewayRouteLimitRuleDto gatewayRouteLimitRuleDto);

    boolean saveGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule);

    boolean updateGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule);

    boolean deleteGatewayRouteLimitRule(List<String> ids);

    /**
     * 获取所有状态为开启（1）的限流规则
     *
     * @return
     */
    List<GatewayRouteLimitRule> findAllRouteLimitRule();

    /**
     * 缓存限流规则
     *
     * @param gatewayRouteLimitRule
     */
    void saveGatewayRouteLimitRuleCache(GatewayRouteLimitRule gatewayRouteLimitRule);

    /**
     * 按路径和方法获取缓存
     *
     * @param uri
     * @param method
     * @return
     */
    GatewayRouteLimitRule getGatewayRouteLimitRuleCache(String uri, String method);

    /**
     * 缓存所有限流规则
     */
    void saveAllGatewayRouteLimitRuleCache();

    /**
     * 缓存请求次数
     * @param uri
     * @param ip
     * @param time
     */
    void saveCurrentRequestCount(String uri,String ip,Long time);

    /**
     * 获取当前请求次数
     * @param uri
     * @param ip
     * @return
     */
    int getCurrentRequestCount(String uri,String ip);

    /**
     * 递增请求次数
     * @param uri
     * @param ip
     */
    void incrCurrentRequestCount(String uri,String ip);
}
