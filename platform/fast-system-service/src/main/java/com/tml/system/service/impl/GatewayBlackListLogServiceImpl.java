package com.tml.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayBlackListLogDto;
import com.tml.system.entity.GatewayBlackListLog;
import com.tml.system.mapper.GatewayBlackListLogMapper;
import com.tml.system.service.IGatewayBlackListLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 黑名单服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayBlackListLogServiceImpl extends BaseServiceImpl<GatewayBlackListLogMapper, GatewayBlackListLog> implements IGatewayBlackListLogService {
    @Override
    public PageVo<GatewayBlackListLog> pageList(GatewayBlackListLogDto gatewayBlackListLogDto) {
        Page<GatewayBlackListLog> page = new Page<>(gatewayBlackListLogDto.getPage(), gatewayBlackListLogDto.getLimit());
        QueryWrapper<GatewayBlackListLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayBlackListLogDto.getIp()), GatewayBlackListLog::getIp, gatewayBlackListLogDto.getIp())
                .eq(StringUtils.isNoneBlank(gatewayBlackListLogDto.getRequestUri()), GatewayBlackListLog::getRequestUri, gatewayBlackListLogDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayBlackListLogDto.getRequestMethod()), GatewayBlackListLog::getRequestMethod, gatewayBlackListLogDto.getRequestMethod())
                .ge(StringUtils.isNoneBlank(gatewayBlackListLogDto.getCreateTimeFrom()), GatewayBlackListLog::getCreateTime, gatewayBlackListLogDto.getCreateTimeFrom())
                .le(StringUtils.isNoneBlank(gatewayBlackListLogDto.getCreateTimeTo()), GatewayBlackListLog::getCreateTime, gatewayBlackListLogDto.getCreateTimeTo())
        ;

        return new PageVo<>(this.page(page, queryWrapper));
    }
}
