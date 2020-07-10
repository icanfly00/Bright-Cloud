package com.tml.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.constant.CacheConstant;
import com.tml.common.redis.service.RedisService;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRouteLimitRuleDto;
import com.tml.system.entity.GatewayRouteLimitRule;
import com.tml.system.mapper.GatewayRouteLimitRuleMapper;
import com.tml.system.service.IGatewayRouteLimitRuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.annotations.Cacheable;

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
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleDto.getStatus()), GatewayRouteLimitRule::getStatus, gatewayRouteLimitRuleDto.getStatus());
        return new PageVo<>(this.page(page, queryWrapper));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GatewayRouteLimitRule findByCondition(GatewayRouteLimitRuleDto gatewayRouteLimitRuleDto) {
        QueryWrapper<GatewayRouteLimitRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleDto.getRequestUri()), GatewayRouteLimitRule::getRequestUri, gatewayRouteLimitRuleDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleDto.getRequestMethod()), GatewayRouteLimitRule::getRequestMethod, gatewayRouteLimitRuleDto.getRequestMethod())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRuleDto.getStatus()), GatewayRouteLimitRule::getStatus, gatewayRouteLimitRuleDto.getStatus());
        return this.getOne(queryWrapper);
    }

    @CachePut(value = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE,key = "#gatewayRouteLimitRule.requestUri+':'+#gatewayRouteLimitRule.requestMethod",unless = "#gatewayRouteLimitRule.status!=0")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule) {
        boolean flag= this.save(gatewayRouteLimitRule);
        //TODO: 刷新网关
        return flag;
    }

    @CachePut(value = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE,key = "#gatewayRouteLimitRule.requestUri+':'+#gatewayRouteLimitRule.requestMethod",unless = "#gatewayRouteLimitRule.status!=0")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule) {
        boolean flag= this.updateById(gatewayRouteLimitRule);
        //TODO: 刷新网关
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteGatewayRouteLimitRule(List<String> ids) {
        ids.stream().forEach(id ->{
            GatewayRouteLimitRule routeLimitRule=this.getById(id);
            String key=CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE+":"
                    +routeLimitRule.getRequestUri()+":"
                    +routeLimitRule.getRequestMethod();
            if(redisService.hasKey(key)){
                redisService.del(key);
            }
        });
        boolean flag= this.removeByIds(ids);
        //TODO: 刷新网关
        return flag;
    }

    @Override
    public List<GatewayRouteLimitRule> findAllRouteLimitRule() {
        QueryWrapper<GatewayRouteLimitRule> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GatewayRouteLimitRule::getStatus,1);
        return this.list(queryWrapper);
    }
}
