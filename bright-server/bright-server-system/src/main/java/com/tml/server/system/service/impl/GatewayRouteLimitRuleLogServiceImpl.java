package com.tml.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.api.system.entity.GatewayRouteLimitRuleLog;
import com.tml.common.core.entity.QueryRequest;
import com.tml.server.system.mapper.GatewayRouteLimitRuleLogMapper;
import com.tml.server.system.service.IGatewayRouteLimitRuleLogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 限流规则日志 Service实现
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:12
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GatewayRouteLimitRuleLogServiceImpl extends ServiceImpl<GatewayRouteLimitRuleLogMapper, GatewayRouteLimitRuleLog> implements IGatewayRouteLimitRuleLogService {

    @Override
    public IPage<GatewayRouteLimitRuleLog> pageGatewayRouteLimitRuleLog(QueryRequest request, GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog) {
        LambdaQueryWrapper<GatewayRouteLimitRuleLog> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleLog.getIp()), GatewayRouteLimitRuleLog::getIp, gatewayRouteLimitRuleLog.getIp())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleLog.getRequestUri()), GatewayRouteLimitRuleLog::getRequestUri, gatewayRouteLimitRuleLog.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleLog.getRequestMethod()), GatewayRouteLimitRuleLog::getRequestMethod, gatewayRouteLimitRuleLog.getRequestMethod())
                .gt(StringUtils.isNoneBlank(gatewayRouteLimitRuleLog.getCreateTimeFrom()), GatewayRouteLimitRuleLog::getCreateTime, gatewayRouteLimitRuleLog.getCreateTimeFrom())
                .lt(StringUtils.isNoneBlank(gatewayRouteLimitRuleLog.getCreateTimeTo()), GatewayRouteLimitRuleLog::getCreateTime, gatewayRouteLimitRuleLog.getCreateTimeTo())
                .orderByDesc(GatewayRouteLimitRuleLog::getCreateTime);
        Page<GatewayRouteLimitRuleLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<GatewayRouteLimitRuleLog> listGatewayRouteLimitRuleLog(GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog) {
        LambdaQueryWrapper<GatewayRouteLimitRuleLog> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleLog.getIp()), GatewayRouteLimitRuleLog::getIp, gatewayRouteLimitRuleLog.getIp())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleLog.getRequestUri()), GatewayRouteLimitRuleLog::getRequestUri, gatewayRouteLimitRuleLog.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleLog.getRequestMethod()), GatewayRouteLimitRuleLog::getRequestMethod, gatewayRouteLimitRuleLog.getRequestMethod())
                .gt(StringUtils.isNoneBlank(gatewayRouteLimitRuleLog.getCreateTimeFrom()), GatewayRouteLimitRuleLog::getCreateTime, gatewayRouteLimitRuleLog.getCreateTimeFrom())
                .lt(StringUtils.isNoneBlank(gatewayRouteLimitRuleLog.getCreateTimeTo()), GatewayRouteLimitRuleLog::getCreateTime, gatewayRouteLimitRuleLog.getCreateTimeTo())
                .orderByDesc(GatewayRouteLimitRuleLog::getCreateTime);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGatewayRouteLimitRuleLog(GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog) {
        this.save(gatewayRouteLimitRuleLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGatewayRouteLimitRuleLog(GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog) {
        this.saveOrUpdate(gatewayRouteLimitRuleLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGatewayRouteLimitRuleLog(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.removeByIds(list);
    }
}
