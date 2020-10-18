package com.tml.server.job.utils;


import com.alibaba.fastjson.JSONObject;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.utils.JacksonUtil;
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
                String requestMethod=scheduleJob.getRequestMethod();
                String contentType = StringUtils.isBlank(scheduleJob.getContentType()) ? MediaType.APPLICATION_JSON_VALUE : scheduleJob.getContentType();
                ServiceInstance serviceInstance = loadBalancerClient.choose(jobLog.getServiceId());
                //获取服务实例
                if (serviceInstance == null) {
                    throw new RuntimeException(String.format("%s服务暂不可用", scheduleJob.getServiceId()));
                }
                String url ="http://"+ scheduleJob.getServiceId()+scheduleJob.getPath();
                System.out.println("url: "+url);
                StringBuffer newUrl=new StringBuffer();
                String params= scheduleJob.getParams();
                if(params.indexOf(",")>-1){
                    String[] paramArr = params.substring(0, (params.length()-1)).split(",");
                    StringBuffer sb=new StringBuffer();
                    for(int i=0;i<paramArr.length;i++){
                        sb.append(paramArr[i]);
                        if(i< paramArr.length-1){
                            sb.append("/");
                        }
                    }
                    newUrl.append(url.substring(0,url.indexOf("{")));
                    newUrl.append(sb.toString());
                }else{
                    newUrl.append(url.substring(0,url.indexOf("{")));
                    newUrl.append(params);
                }
                log.info("----- url: {} newUrl: {} method: {} contentType: {} data: {} -----", url, newUrl,requestMethod ,contentType,params);
                CommonResult commonResult = this.restTemplate.getForObject(newUrl.toString(), CommonResult.class);
                log.info("----- result: {} -----", JacksonUtil.toJson(commonResult));
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
