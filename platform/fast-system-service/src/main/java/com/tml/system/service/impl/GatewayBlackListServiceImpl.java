package com.tml.system.service.impl;

import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.GatewayBlackList;
import com.tml.system.mapper.GatewayBlackListMapper;
import com.tml.system.service.IGatewayBlackListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 黑名单日志服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayBlackListServiceImpl extends BaseServiceImpl<GatewayBlackListMapper, GatewayBlackList> implements IGatewayBlackListService {
}
