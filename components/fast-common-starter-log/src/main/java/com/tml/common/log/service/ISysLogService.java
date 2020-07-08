package com.tml.common.log.service;

import com.tml.common.log.entity.SysLog;
import com.tml.common.web.service.IBaseService;

/**
 * @Description 系统日志接口
 * @Author TuMingLong
 * @Date 2020/6/3 17:03
 */
public interface ISysLogService extends IBaseService<SysLog> {

    /**
     * 保存系统日志
     *
     * @param sysLog 系统日志
     */
    void saveLogin(SysLog sysLog);
}
