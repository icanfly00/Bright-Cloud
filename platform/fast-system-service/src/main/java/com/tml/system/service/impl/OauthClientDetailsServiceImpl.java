package com.tml.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.constant.CacheConstant;
import com.tml.common.exception.APIException;
import com.tml.common.redis.service.RedisService;
import com.tml.common.web.dto.CommonDto;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.OauthClientDetailsDto;
import com.tml.system.entity.OauthClientDetails;
import com.tml.system.mapper.OauthClientDetailsMapper;
import com.tml.system.service.IOauthClientDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @Description OAuth2.0客户端信息服务接口
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OauthClientDetailsServiceImpl extends BaseServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    public PageVo<OauthClientDetails> pageList(OauthClientDetailsDto oauthClientDetailsDto) {
        Page page = new Page<>(oauthClientDetailsDto.getPage(), oauthClientDetailsDto.getLimit());
        QueryWrapper<OauthClientDetails> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(StringUtils.isNoneBlank(oauthClientDetailsDto.getClientId()),OauthClientDetails::getClientId,oauthClientDetailsDto.getClientId());
        IPage iPage = this.page(page,queryWrapper);
        return new PageVo<>(iPage);
    }

    @Override
    public OauthClientDetails findById(String clientId) {
        return this.baseMapper.selectById(clientId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOauthClientDetails(OauthClientDetails oauthClientDetails){
        OauthClientDetails byId = this.findById(oauthClientDetails.getClientId());
        if (byId != null) {
            throw new APIException("该Client已存在");
        }
        oauthClientDetails.setOriginSecret(oauthClientDetails.getClientSecret());
        oauthClientDetails.setClientSecret(passwordEncoder.encode(oauthClientDetails.getClientSecret()));
        boolean saved = this.save(oauthClientDetails);
        if (saved) {
            log.info("缓存Client -> {}", oauthClientDetails);
            redisService.set(CacheConstant.CLIENT_DETAILS_KEY+":"+oauthClientDetails.getClientId(),oauthClientDetails);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOauthClientDetails(OauthClientDetails oauthClientDetails) {
        String clientId = oauthClientDetails.getClientId();

        LambdaQueryWrapper<OauthClientDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OauthClientDetails::getClientId, oauthClientDetails.getClientId());

        oauthClientDetails.setClientId(null);
        oauthClientDetails.setClientSecret(null);
        boolean updated = this.update(oauthClientDetails, queryWrapper);
        if (updated) {
            log.info("更新Client -> {}", oauthClientDetails);
            redisService.del(CacheConstant.CLIENT_DETAILS_KEY+":"+clientId);
            redisService.set(CacheConstant.CLIENT_DETAILS_KEY+":"+clientId,oauthClientDetails);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOauthClientDetails(String clientIds) {
        Object[] clientIdArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(clientIds, ",");
        LambdaQueryWrapper<OauthClientDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OauthClientDetails::getClientId, clientIdArray);
        boolean removed = this.remove(queryWrapper);
        if (removed) {
            log.info("删除ClientId为({})的Client", clientIds);
            Arrays.stream(clientIdArray).forEach(c ->   redisService.del(CacheConstant.CLIENT_DETAILS_KEY+":"+c));

        }
    }
}
