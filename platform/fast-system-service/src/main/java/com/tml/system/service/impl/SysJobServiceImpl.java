package com.tml.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.SysJob;
import com.tml.system.mapper.SysJobMapper;
import com.tml.system.service.ISysJobService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @Description 岗位管理 服务类实现
 * @Author TuMingLong
 * @Date 2020/4/5 21:12
 */
@Service
public class SysJobServiceImpl extends BaseServiceImpl<SysJobMapper, SysJob> implements ISysJobService {

    @Override
    public String selectJobNameByJobId(int jobId) {
        SysJob sysJob = this.baseMapper.selectOne(Wrappers.<SysJob>lambdaQuery()
                .select(SysJob::getJobId, SysJob::getJobName)
                .eq(SysJob::getJobId, jobId));
        if (!ObjectUtils.isEmpty(sysJob)) {
            return sysJob.getJobName();
        } else {
            return null;
        }
    }
}
