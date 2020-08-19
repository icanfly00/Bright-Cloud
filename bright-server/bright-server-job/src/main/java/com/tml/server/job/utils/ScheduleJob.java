package com.tml.server.job.utils;


import com.alibaba.fastjson.JSONObject;
import com.tml.server.job.entity.Job;
import com.tml.server.job.entity.JobLog;
import com.tml.server.job.service.IJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 定时任务
 *
 * @Author TuMingLong
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {

    /**
     * 服装均衡
     */
    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private RestTemplate restTemplate;

    @Resource(name = "asyncThreadPoolTaskExecutor")
    private ThreadPoolTaskExecutor scheduleJobExecutorService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        //ThreadPoolTaskExecutor scheduleJobExecutorService = SpringContextUtil.getBean(ThreadPoolTaskExecutor.class);
        Job scheduleJob = (Job) context.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);
        IJobLogService scheduleJobLogService = SpringContextUtil.getBean(IJobLogService.class);
        JobLog jobLog = new JobLog();
        jobLog.setJobId(scheduleJob.getJobId());
        jobLog.setJobType(scheduleJob.getJobType());
        jobLog.setBeanName(scheduleJob.getBeanName());
        jobLog.setMethodName(scheduleJob.getMethodName());
        jobLog.setServiceId(scheduleJob.getServiceId());
        jobLog.setPath(scheduleJob.getPath());
        jobLog.setRequestMethod(scheduleJob.getRequestMethod());
        jobLog.setContentType(scheduleJob.getContentType());
        jobLog.setParams(scheduleJob.getParams());
        jobLog.setAlarmMail(scheduleJob.getAlarmMail());
        jobLog.setCreateTime(new Date());

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        dataMap.entrySet().stream().forEach((entry) -> {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        });
        String body = dataMap.getString("body");

        long startTime = System.currentTimeMillis();

        try {
            // 执行任务
            log.info("任务准备执行，任务ID：{}", scheduleJob.getJobId());
            if (scheduleJob.getJobType().equals("0")) {
                ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
                Future<?> future = scheduleJobExecutorService.submit(task);
                future.get();
                long times = System.currentTimeMillis() - startTime;
                jobLog.setTimes(times);
                jobLog.setStatus(JobLog.JOB_SUCCESS);
                log.info("本地方法任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", scheduleJob.getJobId(), times);
            } else {
                String contentType = StringUtils.isBlank(scheduleJob.getContentType()) ? MediaType.APPLICATION_FORM_URLENCODED_VALUE : scheduleJob.getContentType();
                ServiceInstance serviceInstance = loadBalancerClient.choose(jobLog.getServiceId());
                //获取服务实例
                if (serviceInstance == null) {
                    throw new RuntimeException(String.format("%s服务暂不可用", scheduleJob.getServiceId()));
                }
                String url = String.format("%%s", serviceInstance.getUri(), scheduleJob.getPath());
                HttpHeaders headers = new HttpHeaders();
                HttpMethod httpMethod = HttpMethod.resolve(scheduleJob.getRequestMethod().toUpperCase());
                HttpEntity requestEntity = null;
                headers.setContentType(MediaType.parseMediaType(contentType));

                if (contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                    //json 格式
                    requestEntity = new HttpEntity(body, headers);
                } else {
                    //表单形式
                    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                    if (StringUtils.isNoneBlank(body)) {
                        Map data = JSONObject.parseObject(body, Map.class);
                        params.putAll(data);
                        requestEntity = new HttpEntity(body, headers);
                    }
                }
                log.info("----- url[{}] method[{}] data=[{}] -----", url, httpMethod, requestEntity);
                ResponseEntity<String> result = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
                log.info("----- result [{}] -----", result.getBody());
                long times = System.currentTimeMillis() - startTime;
                jobLog.setTimes(times);
                jobLog.setStatus(JobLog.JOB_SUCCESS);
                log.info("远程方法任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", scheduleJob.getJobId(), times);
            }
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes(times);
            jobLog.setStatus(JobLog.JOB_FAIL);
            jobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.saveJobLog(jobLog);
        }
    }
}
