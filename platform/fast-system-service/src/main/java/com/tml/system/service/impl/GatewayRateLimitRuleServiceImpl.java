package com.tml.system.service.impl;

import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.GatewayRateLimitRule;
import com.tml.system.mapper.GatewayRateLimitRuleMapper;
import com.tml.system.service.IGatewayRateLimitRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 限流服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayRateLimitRuleServiceImpl extends BaseServiceImpl<GatewayRateLimitRuleMapper, GatewayRateLimitRule> implements IGatewayRateLimitRuleService {
}
