package com.tml.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayBlackListDto;
import com.tml.system.entity.GatewayBlackList;
import com.tml.system.mapper.GatewayBlackListMapper;
import com.tml.system.service.IGatewayBlackListService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 黑名单日志服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayBlackListServiceImpl extends BaseServiceImpl<GatewayBlackListMapper, GatewayBlackList> implements IGatewayBlackListService {
    @Override
    public PageVo<GatewayBlackList> pageList(GatewayBlackListDto gatewayBlackListDto) {
        Page<GatewayBlackList> page = new Page<>(gatewayBlackListDto.getPage(), gatewayBlackListDto.getLimit());
        QueryWrapper<GatewayBlackList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getIp()), GatewayBlackList::getIp, gatewayBlackListDto.getIp())
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getRequestUri()), GatewayBlackList::getRequestUri, gatewayBlackListDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getRequestMethod()), GatewayBlackList::getRequestMethod, gatewayBlackListDto.getRequestMethod());
        return new PageVo<>(this.page(page, queryWrapper));
    }

    @Override
    public GatewayBlackList findByCondition(GatewayBlackListDto gatewayBlackListDto) {
        QueryWrapper<GatewayBlackList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StringUtils.isNoneBlank(gatewayBlackListDto.getIp()), GatewayBlackList::getIp, gatewayBlackListDto.getIp())
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getRequestUri()), GatewayBlackList::getRequestUri, gatewayBlackListDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getRequestMethod()), GatewayBlackList::getRequestMethod, gatewayBlackListDto.getRequestMethod());
        return this.getOne(queryWrapper);
    }
}
