package com.tml.server.job.mapper;


import com.tml.server.job.entity.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface JobMapper extends BaseMapper<Job> {

    /**
     * 获取定时任务列表
     *
     * @return 定时任务列表
     */
    List<Job> queryList();
}