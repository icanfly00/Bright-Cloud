package com.tml.server.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.server.job.entity.Job;
import com.tml.server.job.mapper.JobMapper;
import com.tml.server.job.service.IJobService;
import com.tml.server.job.utils.ScheduleUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Slf4j
@Service("JobService")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

    private final Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<Job> scheduleJobList = this.baseMapper.queryList();
        // 如果不存在，则创建
        scheduleJobList.forEach(scheduleJob -> {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        });
    }

    @Override
    public Job getJob(Long jobId) {
        return this.getById(jobId);
    }

    @Override
    public IPage<Job> pageJob(QueryRequest request, Job job) {
        Page<Job> page = new Page<>(request.getPageNum(), request.getPageSize());

        LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(job.getBeanName()), Job::getBeanName, job.getBeanName())
                .eq(StringUtils.isNotBlank(job.getMethodName()), Job::getMethodName, job.getMethodName())
                .eq(StringUtils.isNotBlank(job.getServiceId()), Job::getServiceId, job.getServiceId())
                .eq(StringUtils.isNotBlank(job.getPath()), Job::getPath, job.getPath())
                .eq(StringUtils.isNotBlank(job.getParams()), Job::getParams, job.getParams())
                .eq(StringUtils.isNotBlank(job.getRemark()), Job::getRemark, job.getRemark())
                .eq(StringUtils.isNotBlank(job.getJobType()), Job::getJobType, job.getJobType())
                .eq(StringUtils.isNotBlank(job.getStatus()), Job::getStatus, job.getStatus())
                .ge(StringUtils.isNotBlank(job.getCreateTimeFrom()), Job::getCreateTime, job.getCreateTimeFrom())
                .le(StringUtils.isNotBlank(job.getCreateTimeTo()), Job::getCreateTime, job.getCreateTimeTo())
                .orderByDesc(Job::getCreateTime);

        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createJob(Job job) {
        job.setCreateTime(new Date());
        job.setStatus(Job.ScheduleStatus.PAUSE.getValue());
        this.save(job);
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(Job job) {
        ScheduleUtils.updateScheduleJob(scheduler, job);
        this.baseMapper.updateById(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJobs(String[] jobIds) {
        List<String> list = Arrays.asList(jobIds);
        list.forEach(jobId -> ScheduleUtils.deleteScheduleJob(scheduler, Long.valueOf(jobId)));
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(String jobIds, String status) {
        List<String> list = Arrays.asList(jobIds.split(StringConstant.COMMA));
        Job job = new Job();
        job.setStatus(status);
        this.baseMapper.update(job, new LambdaQueryWrapper<Job>().in(Job::getJobId, list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(String jobIds) {
        String[] list = jobIds.split(StringConstant.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.run(scheduler, this.getJob(Long.valueOf(jobId))));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(String jobIds) {
        String[] list = jobIds.split(StringConstant.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.pauseJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, Job.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(String jobIds) {
        String[] list = jobIds.split(StringConstant.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.resumeJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, Job.ScheduleStatus.NORMAL.getValue());
    }
}
