package com.tml.system.service.impl;

import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.GatewayRateLimitLog;
import com.tml.system.mapper.GatewayRateLimitLogMapper;
import com.tml.system.service.IGatewayRateLimitLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 限流日志服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayRateLimitLogServiceImpl extends BaseServiceImpl<GatewayRateLimitLogMapper, GatewayRateLimitLog> implements IGatewayRateLimitLogService {
}
