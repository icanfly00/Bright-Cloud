package com.tml.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.constant.CacheConstant;
import com.tml.common.redis.service.RedisService;
import com.tml.common.util.JacksonUtil;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayRouteLimitRuleDto;
import com.tml.gateway.entity.GatewayRouteLimitRule;
import com.tml.gateway.mapper.GatewayRouteLimitRuleMapper;
import com.tml.gateway.service.IGatewayRouteLimitRuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 限流服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
public class GatewayRouteLimitRuleServiceImpl extends BaseServiceImpl<GatewayRouteLimitRuleMapper, GatewayRouteLimitRule> implements IGatewayRouteLimitRuleService {

    @Resource
    private RedisService redisService;

    @Override
    public PageVo<GatewayRouteLimitRule> pageList(GatewayRouteLimitRuleDto gatewayRouteLimitRuleDto) {
        Page page = new Page<>(gatewayRouteLimitRuleDto.getPage(), gatewayRouteLimitRuleDto.getLimit());
        QueryWrapper<GatewayRouteLimitRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleDto.getRequestUri()), GatewayRouteLimitRule::getRequestUri, gatewayRouteLimitRuleDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleDto.getRequestMethod()), GatewayRouteLimitRule::getRequestMethod, gatewayRouteLimitRuleDto.getRequestMethod())
                .eq(gatewayRouteLimitRuleDto.getStatus() != null, GatewayRouteLimitRule::getStatus, gatewayRouteLimitRuleDto.getStatus());
        return new PageVo<>(this.page(page, queryWrapper));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public GatewayRouteLimitRule findByCondition(GatewayRouteLimitRuleDto gatewayRouteLimitRuleDto) {
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":"
                + gatewayRouteLimitRuleDto.getRequestUri() + ":"
                + gatewayRouteLimitRuleDto.getRequestMethod();
        if (redisService.hasKey(key)) {
            return getGatewayRouteLimitRuleCache(gatewayRouteLimitRuleDto.getRequestUri(), gatewayRouteLimitRuleDto.getRequestMethod());
        } else {
            QueryWrapper<GatewayRouteLimitRule> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleDto.getRequestUri()), GatewayRouteLimitRule::getRequestUri, gatewayRouteLimitRuleDto.getRequestUri())
                    .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleDto.getRequestMethod()), GatewayRouteLimitRule::getRequestMethod, gatewayRouteLimitRuleDto.getRequestMethod())
                    .eq(gatewayRouteLimitRuleDto.getStatus() != null, GatewayRouteLimitRule::getStatus, gatewayRouteLimitRuleDto.getStatus());
            return this.getOne(queryWrapper);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean saveGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule) {
        boolean flag = this.save(gatewayRouteLimitRule);
        saveGatewayRouteLimitRuleCache(gatewayRouteLimitRule);
        return flag;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean updateGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule) {
        boolean flag = this.updateById(gatewayRouteLimitRule);
        saveGatewayRouteLimitRuleCache(gatewayRouteLimitRule);
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteGatewayRouteLimitRule(List<String> ids) {
        ids.stream().forEach(id -> {
            GatewayRouteLimitRule routeLimitRule = this.getById(id);
            String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":"
                    + routeLimitRule.getRequestUri() + ":"
                    + routeLimitRule.getRequestMethod();
            if (redisService.hasKey(key)) {
                redisService.del(key);
            }
        });
        boolean flag = this.removeByIds(ids);
        return flag;
    }

    @Override
    public List<GatewayRouteLimitRule> findAllRouteLimitRule() {
        QueryWrapper<GatewayRouteLimitRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GatewayRouteLimitRule::getStatus, 1);
        return this.list(queryWrapper);
    }

    @Override
    public void saveGatewayRouteLimitRuleCache(GatewayRouteLimitRule gatewayRouteLimitRule) {
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":"
                + gatewayRouteLimitRule.getRequestUri() + ":"
                + gatewayRouteLimitRule.getRequestMethod();
        redisService.set(key, JacksonUtil.toJson(gatewayRouteLimitRule));

    }

    @Override
    public GatewayRouteLimitRule getGatewayRouteLimitRuleCache(String uri, String method) {
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":"
                + uri + ":"
                + method;
        if (redisService.hasKey(key)) {
            return (GatewayRouteLimitRule) redisService.get(key);
        }
        return null;
    }

    @Override
    public void saveAllGatewayRouteLimitRuleCache() {
        List<GatewayRouteLimitRule> gatewayRouteLimitRules = findAllRouteLimitRule();
        if (gatewayRouteLimitRules != null && gatewayRouteLimitRules.size() > 0) {
            gatewayRouteLimitRules.stream().forEach(gatewayRouteLimitRule -> {
                saveGatewayRouteLimitRuleCache(gatewayRouteLimitRule);
            });
        }
    }

    @Override
    public void saveCurrentRequestCount(String uri, String ip, Long time) {
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_COUNT_CACHE + ":" + uri + ":" + ip;
        redisService.set(key, 1, time);
    }

    @Override
    public int getCurrentRequestCount(String uri, String ip) {
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_COUNT_CACHE + ":" + uri + ":" + ip;
        return redisService.hasKey(key) ? (int) redisService.get(key) : 0;
    }

    @Override
    public void incrCurrentRequestCount(String uri, String ip) {
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_COUNT_CACHE + ":" + uri + ":" + ip;
        redisService.incr(key, 1L);

    }
}
