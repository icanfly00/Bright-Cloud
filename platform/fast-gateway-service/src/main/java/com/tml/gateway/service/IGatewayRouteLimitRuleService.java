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
}
