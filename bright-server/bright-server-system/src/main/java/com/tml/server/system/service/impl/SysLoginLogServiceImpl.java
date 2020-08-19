package com.tml.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.api.system.entity.SysLoginLog;
import com.tml.api.system.entity.SysUser;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.system.mapper.SysLoginLogMapper;
import com.tml.server.system.service.ISysLoginLogService;
import com.tml.server.system.utils.AddressUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 登录日志业务层实现
 * @since 2020-08-10 20:30
 */

@Service("loginLogService")
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements ISysLoginLogService {

    @Override
    public IPage<SysLoginLog> pageLoginLog(SysLoginLog loginLog, QueryRequest request) {
        Page<SysLoginLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        QueryWrapper<SysLoginLog> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(loginLog.getUsername())) {
            queryWrapper.lambda().eq(SysLoginLog::getUsername, loginLog.getUsername().toLowerCase());
        }
        if (StringUtils.isNotBlank(loginLog.getLoginTimeFrom()) && StringUtils.isNotBlank(loginLog.getLoginTimeTo())) {
            queryWrapper.lambda()
                    .ge(SysLoginLog::getLoginTime, loginLog.getLoginTimeFrom())
                    .le(SysLoginLog::getLoginTime, loginLog.getLoginTimeTo());
        }
        queryWrapper.lambda().orderByDesc(SysLoginLog::getLoginTime);
        return this.page(page, queryWrapper);
    }

    @Override
    public void saveLoginLog(SysLoginLog loginLog) {
        loginLog.setLoginTime(new Date());
        String ip = BrightUtil.getHttpServletRequestIpAddress();
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        this.save(loginLog);
    }

    @Override
    public void deleteLoginLogs(String[] ids) {
        List<String> list = Arrays.asList(ids);
        baseMapper.deleteBatchIds(list);
    }

    @Override
    public Long findTotalVisitCount() {
        return this.baseMapper.findTotalVisitCount();
    }

    @Override
    public Long findTodayVisitCount() {
        return this.baseMapper.findTodayVisitCount();
    }

    @Override
    public Long findTodayIp() {
        return this.baseMapper.findTodayIp();
    }

    @Override
    public List<Map<String, Object>> findLastTenDaysVisitCount(SysUser user) {
        return this.baseMapper.findLastTenDaysVisitCount(user);
    }

    @Override
    public List<SysLoginLog> findUserLastSevenLoginLogs(String username) {
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setUsername(username);

        QueryRequest request = new QueryRequest();
        request.setPageNum(1);
        // 近7日记录
        request.setPageSize(7);

        IPage<SysLoginLog> loginLogs = this.pageLoginLog(loginLog, request);
        return loginLogs.getRecords();
    }
}
