package com.tml.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRouteLogDto;
import com.tml.system.entity.GatewayRouteLog;
import com.tml.system.entity.GatewayRouteLog;
import com.tml.system.mapper.GatewayRouteLogMapper;
import com.tml.system.service.IGatewayRouteLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 网关日志服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayRouteLogServiceImpl extends BaseServiceImpl<GatewayRouteLogMapper, GatewayRouteLog> implements IGatewayRouteLogService {
    @Override
    public PageVo<GatewayRouteLog> pageList(GatewayRouteLogDto gatewayRouteLogDto) {
        Page<GatewayRouteLog> page = new Page<>(gatewayRouteLogDto.getPage(), gatewayRouteLogDto.getLimit());
        QueryWrapper<GatewayRouteLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayRouteLogDto.getIp()), GatewayRouteLog::getIp, gatewayRouteLogDto.getIp())
                .eq(StringUtils.isNoneBlank(gatewayRouteLogDto.getRequestUri()), GatewayRouteLog::getRequestUri, gatewayRouteLogDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRouteLogDto.getRequestMethod()), GatewayRouteLog::getRequestMethod, gatewayRouteLogDto.getRequestMethod())
                .eq(StringUtils.isNoneBlank(gatewayRouteLogDto.getTargetServer()), GatewayRouteLog::getTargetServer, gatewayRouteLogDto.getTargetServer())
                .ge(StringUtils.isNoneBlank(gatewayRouteLogDto.getCreateTimeFrom()), GatewayRouteLog::getCreateTime, gatewayRouteLogDto.getCreateTimeFrom())
                .le(StringUtils.isNoneBlank(gatewayRouteLogDto.getCreateTimeTo()), GatewayRouteLog::getCreateTime, gatewayRouteLogDto.getCreateTimeTo())
        ;

        return new PageVo<>(this.page(page, queryWrapper));
    }
}
