package com.tml.server.job.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tml.server.job.entity.Job;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
public interface JobMapper extends BaseMapper<Job> {

    /**
     * 获取定时任务列表
     *
     * @return 定时任务列表
     */
    List<Job> queryList();
}