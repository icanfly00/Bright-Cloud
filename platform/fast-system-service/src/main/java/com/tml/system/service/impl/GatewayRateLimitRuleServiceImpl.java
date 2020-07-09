package com.tml.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayBlackListDto;
import com.tml.system.dto.GatewayRateLimitRuleDto;
import com.tml.system.entity.GatewayBlackList;
import com.tml.system.entity.GatewayRateLimitRule;
import com.tml.system.mapper.GatewayRateLimitRuleMapper;
import com.tml.system.service.IGatewayRateLimitRuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 限流服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GatewayRateLimitRuleServiceImpl extends BaseServiceImpl<GatewayRateLimitRuleMapper, GatewayRateLimitRule> implements IGatewayRateLimitRuleService {
    @Override
    public PageVo<GatewayRateLimitRule> pageList(GatewayRateLimitRuleDto gatewayRateLimitRuleDto) {
        Page page = new Page<>(gatewayRateLimitRuleDto.getPage(), gatewayRateLimitRuleDto.getLimit());
        QueryWrapper<GatewayRateLimitRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayRateLimitRuleDto.getRequestUri()), GatewayRateLimitRule::getRequestUri, gatewayRateLimitRuleDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRateLimitRuleDto.getRequestMethod()), GatewayRateLimitRule::getRequestMethod, gatewayRateLimitRuleDto.getRequestMethod());
        return new PageVo<>(this.page(page, queryWrapper));
    }

    @Override
    public GatewayRateLimitRule findByCondition(GatewayRateLimitRuleDto gatewayRateLimitRuleDto) {
        QueryWrapper<GatewayRateLimitRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayRateLimitRuleDto.getRequestUri()), GatewayRateLimitRule::getRequestUri, gatewayRateLimitRuleDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRateLimitRuleDto.getRequestMethod()), GatewayRateLimitRule::getRequestMethod, gatewayRateLimitRuleDto.getRequestMethod());
        return this.getOne(queryWrapper);
    }
}
