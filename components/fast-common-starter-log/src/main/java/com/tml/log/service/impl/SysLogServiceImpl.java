package com.tml.log.service.impl;

import com.tml.log.entity.SysLog;
import com.tml.log.mapper.SysLogMapper;
import com.tml.log.service.ISysLogService;
import com.tml.common.web.dto.CommonDto;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 系统日志接口实现类
 * @Author TuMingLong
 * @Date 2020/6/3 17:04
 */
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Override
    public PageVo<SysLog> pageList(CommonDto commonDTO) {
        return super.pageList(commonDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveLogin(SysLog sysLog) {
        this.save(sysLog);
    }
}
