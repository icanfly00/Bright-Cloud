package com.tml.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.constant.CacheConstant;
import com.tml.common.redis.service.RedisService;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayBlackListDto;
import com.tml.gateway.entity.GatewayBlackList;
import com.tml.gateway.mapper.GatewayBlackListMapper;
import com.tml.gateway.service.IGatewayBlackListService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 黑名单日志服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Service
public class GatewayBlackListServiceImpl extends BaseServiceImpl<GatewayBlackListMapper, GatewayBlackList> implements IGatewayBlackListService {

    @Resource
    private RedisService redisService;

    @Override
    public PageVo<GatewayBlackList> pageList(GatewayBlackListDto gatewayBlackListDto) {
        Page<GatewayBlackList> page = new Page<>(gatewayBlackListDto.getPage(), gatewayBlackListDto.getLimit());
        QueryWrapper<GatewayBlackList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getIp()), GatewayBlackList::getIp, gatewayBlackListDto.getIp())
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getRequestUri()), GatewayBlackList::getRequestUri, gatewayBlackListDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getRequestMethod()), GatewayBlackList::getRequestMethod, gatewayBlackListDto.getRequestMethod())
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getStatus()), GatewayBlackList::getStatus, gatewayBlackListDto.getStatus());
        return new PageVo<>(this.page(page, queryWrapper));
    }

    @Override
    public GatewayBlackList findByCondition(GatewayBlackListDto gatewayBlackListDto) {
        QueryWrapper<GatewayBlackList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getIp()), GatewayBlackList::getIp, gatewayBlackListDto.getIp())
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getRequestUri()), GatewayBlackList::getRequestUri, gatewayBlackListDto.getRequestUri())
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getRequestMethod()), GatewayBlackList::getRequestMethod, gatewayBlackListDto.getRequestMethod())
                .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getStatus()), GatewayBlackList::getStatus, gatewayBlackListDto.getStatus());
        return this.getOne(queryWrapper);
    }

    @CachePut(value = CacheConstant.GATEWAY_BLACK_LIST_CACHE, key = "#gatewayBlackList.ip+':'+#gatewayBlackList.requestUri+':'+#gatewayBlackList.requestMethod", unless = "#gatewayBlackList.status!=0")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveGatewayBlackList(GatewayBlackList gatewayBlackList) {
        boolean flag = this.save(gatewayBlackList);
        //TODO: 刷新网关
        return flag;
    }

    @CachePut(value = CacheConstant.GATEWAY_BLACK_LIST_CACHE, key = "#gatewayBlackList.ip+':'+#gatewayBlackList.requestUri+':'+#gatewayBlackList.requestMethod", unless = "#gatewayBlackList.status!=0")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateGatewayBlackList(GatewayBlackList gatewayBlackList) {
        boolean flag = this.updateById(gatewayBlackList);
        //TODO: 刷新网关
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteGatewayBlackList(List<String> ids) {
        ids.stream().forEach(id -> {
            GatewayBlackList gatewayBlackList = this.getById(id);
            String key = CacheConstant.GATEWAY_BLACK_LIST_CACHE + ":"
                    + gatewayBlackList.getIp() + ":"
                    + gatewayBlackList.getRequestUri() + ":"
                    + gatewayBlackList.getRequestMethod();
            if (redisService.hasKey(key)) {
                redisService.del(key);
            }

        });
        boolean flag = this.removeByIds(ids);
        //TODO: 刷新网关
        return flag;
    }

    @Override
    public List<GatewayBlackList> findAllBackList() {
        QueryWrapper<GatewayBlackList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GatewayBlackList::getStatus, 1);
        return this.list(queryWrapper);
    }
}
