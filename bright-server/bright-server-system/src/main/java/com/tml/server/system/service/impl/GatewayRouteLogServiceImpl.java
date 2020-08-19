package com.tml.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.api.system.entity.GatewayRouteLog;
import com.tml.common.core.entity.QueryRequest;
import com.tml.server.system.mapper.GatewayRouteLogMapper;
import com.tml.server.system.service.IGatewayRouteLogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 网关日志 Service实现
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:06
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GatewayRouteLogServiceImpl extends ServiceImpl<GatewayRouteLogMapper, GatewayRouteLog> implements IGatewayRouteLogService {

    private final GatewayRouteLogMapper gatewayRouteLogMapper;

    @Override
    public IPage<GatewayRouteLog> pageGatewayRouteLog(QueryRequest request, GatewayRouteLog gatewayRouteLog) {
        LambdaQueryWrapper<GatewayRouteLog> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(gatewayRouteLog.getIp()), GatewayRouteLog::getIp, gatewayRouteLog.getIp())
                .eq(StringUtils.isNoneBlank(gatewayRouteLog.getTargetServer()), GatewayRouteLog::getTargetServer, gatewayRouteLog.getTargetServer())
                .eq(StringUtils.isNoneBlank(gatewayRouteLog.getRequestMethod()), GatewayRouteLog::getRequestMethod, gatewayRouteLog.getRequestMethod())
                .gt(StringUtils.isNoneBlank(gatewayRouteLog.getCreateTimeFrom()), GatewayRouteLog::getCreateTime, gatewayRouteLog.getCreateTimeFrom())
                .lt(StringUtils.isNoneBlank(gatewayRouteLog.getCreateTimeTo()), GatewayRouteLog::getCreateTime, gatewayRouteLog.getCreateTimeTo())
                .orderByDesc(GatewayRouteLog::getCreateTime);
        Page<GatewayRouteLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<GatewayRouteLog> listGatewayRouteLog(GatewayRouteLog gatewayRouteLog) {
        LambdaQueryWrapper<GatewayRouteLog> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(gatewayRouteLog.getIp()), GatewayRouteLog::getIp, gatewayRouteLog.getIp())
                .eq(StringUtils.isNoneBlank(gatewayRouteLog.getTargetServer()), GatewayRouteLog::getTargetServer, gatewayRouteLog.getTargetServer())
                .eq(StringUtils.isNoneBlank(gatewayRouteLog.getRequestMethod()), GatewayRouteLog::getRequestMethod, gatewayRouteLog.getRequestMethod())
                .gt(StringUtils.isNoneBlank(gatewayRouteLog.getCreateTimeFrom()), GatewayRouteLog::getCreateTime, gatewayRouteLog.getCreateTimeFrom())
                .lt(StringUtils.isNoneBlank(gatewayRouteLog.getCreateTimeTo()), GatewayRouteLog::getCreateTime, gatewayRouteLog.getCreateTimeTo())
                .orderByDesc(GatewayRouteLog::getCreateTime);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGatewayRouteLog(GatewayRouteLog gatewayRouteLog) {
        this.save(gatewayRouteLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGatewayRouteLog(GatewayRouteLog gatewayRouteLog) {
        this.saveOrUpdate(gatewayRouteLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGatewayRouteLog(GatewayRouteLog gatewayRouteLog) {
        // TODO 设置删除条件
        this.removeById(gatewayRouteLog.getId());
    }
}
