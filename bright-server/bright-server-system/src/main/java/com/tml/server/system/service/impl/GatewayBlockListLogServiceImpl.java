package com.tml.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.api.system.entity.GatewayBlockListLog;
import com.tml.common.core.entity.QueryRequest;
import com.tml.server.system.mapper.GatewayBlockListLogMapper;
import com.tml.server.system.service.IGatewayBlockListLogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 黑名单日志 Service实现
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:31
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GatewayBlockListLogServiceImpl extends ServiceImpl<GatewayBlockListLogMapper, GatewayBlockListLog> implements IGatewayBlockListLogService {

    private final GatewayBlockListLogMapper gatewayBlockListLogMapper;

    @Override
    public IPage<GatewayBlockListLog> pageGatewayBlockListLog(QueryRequest request, GatewayBlockListLog gatewayBlockListLog) {
        LambdaQueryWrapper<GatewayBlockListLog> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(gatewayBlockListLog.getIp()), GatewayBlockListLog::getIp, gatewayBlockListLog.getIp())
                .eq(StringUtils.isNoneBlank(gatewayBlockListLog.getRequestUri()), GatewayBlockListLog::getRequestUri, gatewayBlockListLog.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayBlockListLog.getRequestMethod()), GatewayBlockListLog::getRequestMethod, gatewayBlockListLog.getRequestMethod())
                .gt(StringUtils.isNoneBlank(gatewayBlockListLog.getCreateTimeFrom()), GatewayBlockListLog::getCreateTime, gatewayBlockListLog.getCreateTime())
                .lt(StringUtils.isNoneBlank(gatewayBlockListLog.getCreateTimeTo()), GatewayBlockListLog::getCreateTime, gatewayBlockListLog.getCreateTimeTo())
                .orderByDesc(GatewayBlockListLog::getCreateTime);
        Page<GatewayBlockListLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<GatewayBlockListLog> listGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog) {
        LambdaQueryWrapper<GatewayBlockListLog> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(gatewayBlockListLog.getIp()), GatewayBlockListLog::getIp, gatewayBlockListLog.getIp())
                .eq(StringUtils.isNoneBlank(gatewayBlockListLog.getRequestUri()), GatewayBlockListLog::getRequestUri, gatewayBlockListLog.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayBlockListLog.getRequestMethod()), GatewayBlockListLog::getRequestMethod, gatewayBlockListLog.getRequestMethod())
                .gt(StringUtils.isNoneBlank(gatewayBlockListLog.getCreateTimeFrom()), GatewayBlockListLog::getCreateTime, gatewayBlockListLog.getCreateTime())
                .lt(StringUtils.isNoneBlank(gatewayBlockListLog.getCreateTimeTo()), GatewayBlockListLog::getCreateTime, gatewayBlockListLog.getCreateTimeTo())
                .orderByDesc(GatewayBlockListLog::getCreateTime);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog) {
        this.save(gatewayBlockListLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog) {
        this.saveOrUpdate(gatewayBlockListLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog) {
        // TODO 设置删除条件
        this.removeById(gatewayBlockListLog.getId());
    }
}
