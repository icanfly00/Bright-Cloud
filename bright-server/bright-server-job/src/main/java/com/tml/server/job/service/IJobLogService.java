package com.tml.server.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.BrightConstant;
import com.tml.server.job.entity.JobLog;
import org.springframework.scheduling.annotation.Async;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
public interface IJobLogService extends IService<JobLog> {

    /**
     * 获取定时任务日志分页数据
     *
     * @param request request
     * @param jobLog  jobLog
     * @return 定时任务日志分页数据
     */
    IPage<JobLog> pageJobLog(QueryRequest request, JobLog jobLog);

    /**
     * 保存定时任务日志
     *
     * @param log 定时任务日志
     */
    @Async(BrightConstant.ASYNC_POOL)
    void saveJobLog(JobLog log);

    /**
     * 删除定时任务日志
     *
     * @param jobLogIds 定时任务日志id数组
     */
    void deleteJobLogs(String[] jobLogIds);
}
