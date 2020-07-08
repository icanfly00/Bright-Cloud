package com.tml.system.service.impl;

import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.GatewayRouteLog;
import com.tml.system.mapper.GatewayRouteLogMapper;
import com.tml.system.service.IGatewayRouteLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 网关日志服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayRouteLogServiceImpl extends BaseServiceImpl<GatewayRouteLogMapper, GatewayRouteLog> implements IGatewayRouteLogService {
}
