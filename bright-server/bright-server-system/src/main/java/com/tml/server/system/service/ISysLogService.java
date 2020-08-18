package com.tml.server.system.service;


import com.tml.api.system.entity.SysLog;
import com.tml.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.Method;

/**
 * @description 日志业务层
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 查询操作日志分页
     *
     * @param log     日志
     * @param request QueryRequest
     * @return IPage<SystemLog>
     */
    IPage<SysLog> findLogs(SysLog log, QueryRequest request);

    /**
     * 删除操作日志
     *
     * @param logIds 日志 ID集合
     */
    void deleteLogs(String[] logIds);

    /**
     * 异步保存操作日志
     *
     * @param point     切点
     * @param method    Method
     * @param ip        ip
     * @param operation 操作内容
     * @param username  操作用户
     * @param start     开始时间
     */
    @Async
    void saveLog(ProceedingJoinPoint point, Method method, String ip, String operation, String username, long start);
}
