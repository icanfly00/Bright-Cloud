package com.tml.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRouteLimitLogDto;
import com.tml.system.entity.GatewayRouteLimitLog;
import com.tml.system.mapper.GatewayRouteLimitLogMapper;
import com.tml.system.service.IGatewayRouteLimitLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 限流日志服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayRouteLimitLogServiceImpl extends BaseServiceImpl<GatewayRouteLimitLogMapper, GatewayRouteLimitLog> implements IGatewayRouteLimitLogService {
    @Override
    public PageVo<GatewayRouteLimitLog> pageList(GatewayRouteLimitLogDto gatewayRouteLimitLogDto) {
        Page<GatewayRouteLimitLog> page = new Page<>(gatewayRouteLimitLogDto.getPage(), gatewayRouteLimitLogDto.getLimit());
        QueryWrapper<GatewayRouteLimitLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitLogDto.getIp()), GatewayRouteLimitLog::getIp, gatewayRouteLimitLogDto.getIp())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitLogDto.getRequestUri()), GatewayRouteLimitLog::getRequestUri, gatewayRouteLimitLogDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitLogDto.getRequestMethod()), GatewayRouteLimitLog::getRequestMethod, gatewayRouteLimitLogDto.getRequestMethod())
                .ge(StringUtils.isNoneBlank(gatewayRouteLimitLogDto.getCreateTimeFrom()), GatewayRouteLimitLog::getCreateTime, gatewayRouteLimitLogDto.getCreateTimeFrom())
                .le(StringUtils.isNoneBlank(gatewayRouteLimitLogDto.getCreateTimeTo()), GatewayRouteLimitLog::getCreateTime, gatewayRouteLimitLogDto.getCreateTimeTo())
        ;

        return new PageVo<>(this.page(page, queryWrapper));
    }
}
