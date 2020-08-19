package com.tml.server.job.service.impl;

import com.tml.common.core.entity.QueryRequest;
import com.tml.server.job.entity.JobLog;
import com.tml.server.job.mapper.JobLogMapper;
import com.tml.server.job.service.IJobLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Slf4j
@Service("JobLogService")
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements IJobLogService {

    @Override
    public IPage<JobLog> pageJobLog(QueryRequest request, JobLog jobLog) {
        Page<JobLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(jobLog.getBeanName()),JobLog::getBeanName, jobLog.getBeanName())
                .eq(StringUtils.isNotBlank(jobLog.getMethodName()),JobLog::getMethodName, jobLog.getMethodName())
                .eq(StringUtils.isNotBlank(jobLog.getServiceId()),JobLog::getServiceId, jobLog.getServiceId())
                .eq(StringUtils.isNotBlank(jobLog.getPath()),JobLog::getPath, jobLog.getPath())
                .eq(StringUtils.isNotBlank(jobLog.getParams()),JobLog::getParams, jobLog.getParams())
                .eq(StringUtils.isNotBlank(jobLog.getJobType()),JobLog::getJobType, jobLog.getJobType())
                .eq(StringUtils.isNotBlank(jobLog.getStatus()),JobLog::getStatus, jobLog.getStatus())
                .ge(StringUtils.isNotBlank(jobLog.getCreateTimeFrom()),JobLog::getCreateTime, jobLog.getCreateTimeFrom())
                .le(StringUtils.isNotBlank(jobLog.getCreateTimeTo()),JobLog::getCreateTime, jobLog.getCreateTimeTo())
                .orderByDesc(JobLog::getCreateTime);
        return this.page(page, queryWrapper);
    }

    @Override
    public void saveJobLog(JobLog log) {
        this.save(log);
    }

    @Override
    public void deleteJobLogs(String[] jobLogIds) {
        List<String> list = Arrays.asList(jobLogIds);
        this.baseMapper.deleteBatchIds(list);
    }

}
