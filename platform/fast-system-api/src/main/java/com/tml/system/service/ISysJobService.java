package com.tml.system.service;

import com.tml.common.web.service.IBaseService;
import com.tml.system.entity.SysJob;

/**
 * @Description 岗位管理 服务类
 * @Author TuMingLong
 * @Date 2020/4/5 21:12
 */
public interface ISysJobService extends IBaseService<SysJob> {

    /**
     * 根据岗位id获取岗位名称
     *
     * @param jobId
     * @return
     */
    String selectJobNameByJobId(int jobId);
}
