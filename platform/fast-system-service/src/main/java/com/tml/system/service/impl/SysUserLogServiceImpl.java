package com.tml.system.service.impl;

import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.SysUserLog;
import com.tml.system.mapper.SysUserLogMapper;
import com.tml.system.service.ISysUserLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Description com.tml.system.service.impl
 * @Author TuMingLong
 * @Date 2020/6/3 17:34
 */
@Service
public class SysUserLogServiceImpl extends BaseServiceImpl<SysUserLogMapper, SysUserLog> implements ISysUserLogService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserLog(SysUserLog sysUserLog) {
        sysUserLog.setLoginTime(LocalDateTime.now());
        this.save(sysUserLog);
    }
}
