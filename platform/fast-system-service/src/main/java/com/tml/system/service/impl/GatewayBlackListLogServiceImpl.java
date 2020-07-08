package com.tml.system.service.impl;

import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.GatewayBlackListLog;
import com.tml.system.mapper.GatewayBlackListLogMapper;
import com.tml.system.service.IGatewayBlackListLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 黑名单服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayBlackListLogServiceImpl extends BaseServiceImpl<GatewayBlackListLogMapper, GatewayBlackListLog> implements IGatewayBlackListLogService {
}
