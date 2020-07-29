package com.tml.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.constant.CacheConstant;
import com.tml.common.redis.service.RedisService;
import com.tml.common.util.JacksonUtil;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayBlackListDto;
import com.tml.gateway.entity.GatewayBlackList;
import com.tml.gateway.mapper.GatewayBlackListMapper;
import com.tml.gateway.service.IGatewayBlackListService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                .eq(gatewayBlackListDto.getStatus() != null, GatewayBlackList::getStatus, gatewayBlackListDto.getStatus());
        return new PageVo<>(this.page(page, queryWrapper));
    }

    @Override
    public List<GatewayBlackList> findByCondition(GatewayBlackListDto gatewayBlackListDto) {
        String key = CacheConstant.GATEWAY_BLACK_LIST_CACHE + ":" + gatewayBlackListDto.getIp();
        if (redisService.hasKey(key)) {
            List<GatewayBlackList> gatewayBlackLists = Lists.newArrayList();
            Set<Object> set = redisService.sMembers(key);
            set.stream().forEach(o -> {
                GatewayBlackList blackList = JacksonUtil.toObject(o.toString(), GatewayBlackList.class);
                if (gatewayBlackListDto.getIp().equals(blackList.getIp())
                        || gatewayBlackListDto.getRequestUri().equals(blackList.getRequestUri())
                        || gatewayBlackListDto.getRequestMethod().equals(blackList.getRequestMethod())
                        || gatewayBlackListDto.getStatus() == blackList.getStatus()) {
                    gatewayBlackLists.add(blackList);
                }
            });
            return gatewayBlackLists;
        } else {
            QueryWrapper<GatewayBlackList> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getIp()), GatewayBlackList::getIp, gatewayBlackListDto.getIp())
                    .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getRequestUri()), GatewayBlackList::getRequestUri, gatewayBlackListDto.getRequestUri())
                    .eq(StringUtils.isNoneBlank(gatewayBlackListDto.getRequestMethod()), GatewayBlackList::getRequestMethod, gatewayBlackListDto.getRequestMethod())
                    .eq(gatewayBlackListDto.getStatus() != null, GatewayBlackList::getStatus, gatewayBlackListDto.getStatus());
            return this.list(queryWrapper);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveGatewayBlackList(GatewayBlackList gatewayBlackList) {
        boolean flag = this.save(gatewayBlackList);
        saveGatewayBlackListCache(gatewayBlackList);
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateGatewayBlackList(GatewayBlackList gatewayBlackList) {
        boolean flag = this.updateById(gatewayBlackList);
        saveGatewayBlackListCache(gatewayBlackList);
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteGatewayBlackList(List<String> ids) {
        ids.stream().forEach(id -> {
            GatewayBlackList gatewayBlackList = this.getById(id);
            String key = CacheConstant.GATEWAY_BLACK_LIST_CACHE + ":"
                    + gatewayBlackList.getIp();
            if (redisService.hasKey(key)) {
                redisService.sRemove(key, gatewayBlackList);
            }
        });
        boolean flag = this.removeByIds(ids);
        return flag;
    }

    @Override
    public List<GatewayBlackList> findAllBackList() {
        QueryWrapper<GatewayBlackList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GatewayBlackList::getStatus, 1);
        return this.list(queryWrapper);
    }

    @Override
    public void saveGatewayBlackListCache(GatewayBlackList gatewayBlackList) {
        String key = CacheConstant.GATEWAY_BLACK_LIST_CACHE + ":" + gatewayBlackList.getIp();
        redisService.sAdd(key, JacksonUtil.toJson(gatewayBlackList));
    }

    @Override
    public Set<Object> getGatewayBlackListCache(String ip) {
        String key = CacheConstant.GATEWAY_BLACK_LIST_CACHE + ":" + ip;
        if (redisService.hasKey(key)) {
            return redisService.sMembers(key);
        }
        return null;
    }

    @Override
    public void saveAllGatewayBlackListCache() {
        List<GatewayBlackList> gatewayBlackLists = findAllBackList();
        if (gatewayBlackLists != null && gatewayBlackLists.size() > 0) {
            gatewayBlackLists.stream().forEach(gatewayBlackList -> {
                saveGatewayBlackListCache(gatewayBlackList);
            });
        }
    }
}
