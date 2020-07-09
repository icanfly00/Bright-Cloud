package com.tml.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRateLimitLogDto;
import com.tml.system.entity.GatewayRateLimitLog;
import com.tml.system.mapper.GatewayRateLimitLogMapper;
import com.tml.system.service.IGatewayRateLimitLogService;
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
public class GatewayRateLimitLogServiceImpl extends BaseServiceImpl<GatewayRateLimitLogMapper, GatewayRateLimitLog> implements IGatewayRateLimitLogService {
    @Override
    public PageVo<GatewayRateLimitLog> pageList(GatewayRateLimitLogDto gatewayRateLimitLogDto) {
        Page<GatewayRateLimitLog> page = new Page<>(gatewayRateLimitLogDto.getPage(), gatewayRateLimitLogDto.getLimit());
        QueryWrapper<GatewayRateLimitLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayRateLimitLogDto.getIp()), GatewayRateLimitLog::getIp, gatewayRateLimitLogDto.getIp())
                .eq(StringUtils.isNoneBlank(gatewayRateLimitLogDto.getRequestUri()), GatewayRateLimitLog::getRequestUri, gatewayRateLimitLogDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRateLimitLogDto.getRequestMethod()), GatewayRateLimitLog::getRequestMethod, gatewayRateLimitLogDto.getRequestMethod())
                .ge(StringUtils.isNoneBlank(gatewayRateLimitLogDto.getCreateTimeFrom()), GatewayRateLimitLog::getCreateTime, gatewayRateLimitLogDto.getCreateTimeFrom())
                .le(StringUtils.isNoneBlank(gatewayRateLimitLogDto.getCreateTimeTo()), GatewayRateLimitLog::getCreateTime, gatewayRateLimitLogDto.getCreateTimeTo())
        ;

        return new PageVo<>(this.page(page, queryWrapper));
    }
}
