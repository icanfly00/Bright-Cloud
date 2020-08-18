package com.tml.server.system.service.impl;

import com.tml.api.system.entity.GatewayRouteLimitRule;
import com.tml.common.core.entity.constant.CacheConstant;
import com.tml.common.core.utils.JacksonUtil;
import com.tml.common.starter.redis.service.RedisService;
import com.tml.server.system.mapper.GatewayRouteLimitRuleMapper;
import com.tml.server.system.service.IGatewayRouteLimitRuleService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.common.core.entity.QueryRequest;

import java.util.List;

/**
 * 限流规则 Service实现
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:22
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GatewayRouteLimitRuleServiceImpl extends ServiceImpl<GatewayRouteLimitRuleMapper, GatewayRouteLimitRule> implements IGatewayRouteLimitRuleService {

    private final RedisService redisService;

    @Override
    public IPage<GatewayRouteLimitRule> pageGatewayRouteLimitRule(QueryRequest request, GatewayRouteLimitRule gatewayRouteLimitRule) {
        LambdaQueryWrapper<GatewayRouteLimitRule> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(gatewayRouteLimitRule.getRequestUri()),GatewayRouteLimitRule::getRequestUri,gatewayRouteLimitRule.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRule.getRequestMethod()),GatewayRouteLimitRule::getRequestMethod,gatewayRouteLimitRule.getRequestMethod());
        Page<GatewayRouteLimitRule> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<GatewayRouteLimitRule> listGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule) {
        LambdaQueryWrapper<GatewayRouteLimitRule> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(gatewayRouteLimitRule.getRequestUri()),GatewayRouteLimitRule::getRequestUri,gatewayRouteLimitRule.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayRouteLimitRule.getRequestMethod()),GatewayRouteLimitRule::getRequestMethod,gatewayRouteLimitRule.getRequestMethod());
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean checkUriAndMethod(String requestUri, String requestMethod) {
        LambdaQueryWrapper<GatewayRouteLimitRule> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(requestUri),GatewayRouteLimitRule::getRequestUri,requestUri)
                .eq(StringUtils.isNoneBlank(requestMethod),GatewayRouteLimitRule::getRequestMethod,requestMethod);
        int count= this.baseMapper.selectCount(queryWrapper);
        if(count>0){
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule) {
        boolean save=this.save(gatewayRouteLimitRule);
        if(save && gatewayRouteLimitRule.getStatus().equals("1")){
            String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":" + gatewayRouteLimitRule.getRequestUri() + ":" + gatewayRouteLimitRule.getRequestMethod();
            redisService.set(key, JacksonUtil.toJson(gatewayRouteLimitRule));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule) {
        boolean update=this.saveOrUpdate(gatewayRouteLimitRule);
        if(update){
            String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":" + gatewayRouteLimitRule.getRequestUri() + ":" + gatewayRouteLimitRule.getRequestMethod();
            redisService.del(key);
            if(gatewayRouteLimitRule.getStatus().equals("1")){
                redisService.set(key,JacksonUtil.toJson(gatewayRouteLimitRule));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule) {
      boolean remove=this.removeById(gatewayRouteLimitRule.getId());
      if(remove){
          String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":" + gatewayRouteLimitRule.getRequestUri() + ":" + gatewayRouteLimitRule.getRequestMethod();
          redisService.del(key);
      }
    }

    @Override
    public GatewayRouteLimitRule getGatewayRouteLimitRule(String uri, String method){
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":" + uri + ":" + method;
        if (redisService.hasKey(key)) {
            Object o=redisService.get(key);
            if(ObjectUtils.isNotEmpty(o)){
                return JacksonUtil.toObject(o.toString(),GatewayRouteLimitRule.class) ;
            }else {
                return null;
            }

        }else{
            LambdaQueryWrapper<GatewayRouteLimitRule> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNoneBlank(uri),GatewayRouteLimitRule::getRequestUri,uri)
                    .eq(StringUtils.isNoneBlank(method),GatewayRouteLimitRule::getRequestMethod,method)
                    .eq(GatewayRouteLimitRule::getStatus,"1");
            GatewayRouteLimitRule routeLimitRule= this.getOne(queryWrapper);
            if(ObjectUtils.isNotEmpty(routeLimitRule)){
                redisService.set(key,JacksonUtil.toJson(routeLimitRule));
            }else {
                redisService.set(key,"");
            }

            return routeLimitRule;
        }
    }

    @Override
    public void cacheGatewayRouteLimitRule() {
        LambdaQueryWrapper<GatewayRouteLimitRule> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(GatewayRouteLimitRule::getStatus,"1");
        List<GatewayRouteLimitRule> list= this.list(queryWrapper);
        if(list!=null && list.size()>0){
            list.stream().forEach(gatewayRouteLimitRule -> {
                String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":" + gatewayRouteLimitRule.getRequestUri() + ":" + gatewayRouteLimitRule.getRequestMethod();
                redisService.set(key,JacksonUtil.toJson(gatewayRouteLimitRule));
            });
        }
    }

    @Override
    public int getCurrentRequestCount(String uri, String ip) {
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_COUNT_CACHE + ":" + uri + ":" + ip;
        return (int) redisService.get(key);
    }

    @Override
    public void setCurrentRequestCount(String uri, String ip, Long time) {
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_COUNT_CACHE + ":" + uri + ":" + ip;
        redisService.set(key, 1, time);

    }

    @Override
    public void incrCurrentRequestCount(String uri, String ip) {
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_COUNT_CACHE + ":" + uri + ":" + ip;
        redisService.incr(key, 1L);

    }
}
