package com.tml.system.service.impl;

import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.SysTenant;
import com.tml.system.mapper.SysTenantMapper;
import com.tml.system.service.ISysTenantService;
import org.springframework.stereotype.Service;

/**
 * @Description 租户管理 服务类实现
 * @Author TuMingLong
 * @Date 2020/4/5 21:30
 */
@Service
public class SysTenantServiceImpl extends BaseServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {
}
