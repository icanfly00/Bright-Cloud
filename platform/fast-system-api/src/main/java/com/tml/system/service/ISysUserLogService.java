package com.tml.system.service;

import com.tml.common.web.service.IBaseService;
import com.tml.system.entity.SysUserLog;

/**
 * @Description com.tml.system.service
 * @Author TuMingLong
 * @Date 2020/6/3 17:32
 */
public interface ISysUserLogService extends IBaseService<SysUserLog> {

    /**
     * 保存登录日志
     *
     * @param sysUserLog
     */
    void saveUserLog(SysUserLog sysUserLog);
}
