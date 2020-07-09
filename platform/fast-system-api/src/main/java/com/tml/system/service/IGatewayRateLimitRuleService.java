package com.tml.system.service;

import com.tml.common.web.service.IBaseService;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRateLimitRuleDto;
import com.tml.system.entity.GatewayRateLimitRule;

/**
 * @Description 限流规则服务类接口
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
public interface IGatewayRateLimitRuleService extends IBaseService<GatewayRateLimitRule> {

    PageVo<GatewayRateLimitRule> pageList(GatewayRateLimitRuleDto gatewayRateLimitRuleDto);

    GatewayRateLimitRule findByCondition(GatewayRateLimitRuleDto gatewayRateLimitRuleDto);
}
